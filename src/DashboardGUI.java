import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

public class DashboardGUI extends JFrame {
    private final EmployeeService service;

    public DashboardGUI(EmployeeService service) {
        this.service = service;
        setTitle("Employee Management System - Dashboard");
        setSize(540, 420);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        buildUi();
    }

    private void buildUi() {
        setLayout(new BorderLayout(10, 10));

        JLabel titleLabel = new JLabel("Employee Management Dashboard", JLabel.CENTER);
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 22));
        titleLabel.setForeground(new Color(32, 64, 96));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(20, 10, 10, 10));
        add(titleLabel, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel(new GridBagLayout());
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 20, 20));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1;
        gbc.gridx = 0;
        gbc.gridy = 0;

        addButton(buttonPanel, gbc, "Add Employee", event -> new AddEmployeeForm(service).setVisible(true));
        addButton(buttonPanel, gbc, "View All Employees", event -> new ViewEmployeesGUI(service).setVisible(true));
        addButton(buttonPanel, gbc, "Search Employee", event -> new SearchEmployeeDialog(this, service).setVisible(true));
        addButton(buttonPanel, gbc, "Update Employee", event -> new UpdateEmployeeForm(service).setVisible(true));
        addButton(buttonPanel, gbc, "Delete Employee", event -> deleteEmployee());
        addButton(buttonPanel, gbc, "Print Employee Details", event -> printEmployeeDetails());
        addButton(buttonPanel, gbc, "Exit", event -> System.exit(0));

        add(buttonPanel, BorderLayout.CENTER);
    }

    private void addButton(JPanel panel, GridBagConstraints gbc, String text, java.awt.event.ActionListener listener) {
        JButton button = new JButton(text);
        button.addActionListener(listener);
        panel.add(button, gbc);
        gbc.gridy++;
    }

    private void deleteEmployee() {
        JTextField idField = new JTextField();
        Object[] message = {"Enter Employee ID to delete:", idField};
        int option = JOptionPane.showConfirmDialog(this, message, "Delete Employee", JOptionPane.OK_CANCEL_OPTION);
        if (option != JOptionPane.OK_OPTION) {
            return;
        }

        try {
            int employeeId = Integer.parseInt(idField.getText().trim());
            int confirm = JOptionPane.showConfirmDialog(
                    this,
                    "Are you sure you want to delete employee ID " + employeeId + "?",
                    "Confirm Delete",
                    JOptionPane.YES_NO_OPTION
            );
            if (confirm != JOptionPane.YES_OPTION) {
                return;
            }

            service.deleteEmployee(employeeId);
            JOptionPane.showMessageDialog(this, service.getLastMessage());
        } catch (NumberFormatException exception) {
            JOptionPane.showMessageDialog(this, "Error: Invalid ID format!", "Invalid Input", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void printEmployeeDetails() {
        JTextField idField = new JTextField();
        Object[] message = {"Enter Employee ID to print:", idField};
        int option = JOptionPane.showConfirmDialog(this, message, "Print Employee Details", JOptionPane.OK_CANCEL_OPTION);
        if (option != JOptionPane.OK_OPTION) {
            return;
        }

        try {
            int employeeId = Integer.parseInt(idField.getText().trim());
            service.printEmployeeDetails(employeeId);
            JOptionPane.showMessageDialog(this, service.getLastMessage());
        } catch (NumberFormatException exception) {
            JOptionPane.showMessageDialog(this, "Error: Invalid ID format!", "Invalid Input", JOptionPane.ERROR_MESSAGE);
        }
    }
}
