package employee.management.system;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

public class LoginFrame extends JFrame {
    private final AuthService authService;
    private final Runnable onLoginSuccess;
    private final JTextField usernameField = new JTextField(16);
    private final JPasswordField passwordField = new JPasswordField(16);

    public LoginFrame(AuthService authService, Runnable onLoginSuccess) {
        this.authService = authService;
        this.onLoginSuccess = onLoginSuccess;
        buildUi();
    }

    private void buildUi() {
        setTitle("Employee Management System - Login");
        setSize(420, 260);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JLabel title = new JLabel("Employee Management System", JLabel.CENTER);
        title.setFont(new Font("SansSerif", Font.BOLD, 20));
        title.setForeground(new Color(25, 58, 89));
        title.setBorder(BorderFactory.createEmptyBorder(20, 10, 8, 10));

        JPanel header = new JPanel(new BorderLayout());
        header.add(title, BorderLayout.CENTER);

        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0;
        gbc.gridy = 0;
        formPanel.add(new JLabel("Username:"), gbc);
        gbc.gridx = 1;
        formPanel.add(usernameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        formPanel.add(new JLabel("Password:"), gbc);
        gbc.gridx = 1;
        formPanel.add(passwordField, gbc);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton loginButton = new JButton("Login");
        loginButton.addActionListener(event -> login());
        buttonPanel.add(loginButton);

        add(header, BorderLayout.NORTH);
        add(formPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
        getRootPane().setDefaultButton(loginButton);
    }

    private void login() {
        String username = usernameField.getText().trim();
        String password = new String(passwordField.getPassword());
        if (authService.authenticate(username, password)) {
            dispose();
            onLoginSuccess.run();
        } else {
            JOptionPane.showMessageDialog(this,
                    "Incorrect username or password.",
                    "Access Denied",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
}
