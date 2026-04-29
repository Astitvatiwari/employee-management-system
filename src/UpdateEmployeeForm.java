import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

public class UpdateEmployeeForm extends JFrame {
    private final EmployeeService service;
    private final JTextField idField = new JTextField(18);
    private final JTextField fatherField = new JTextField(18);
    private final JTextField addressField = new JTextField(18);
    private final JTextField educationField = new JTextField(18);
    private final JTextField mobileField = new JTextField(18);
    private final JTextField emailField = new JTextField(18);
    private final JTextField salaryField = new JTextField(18);
    private final JTextField departmentField = new JTextField(18);

    public UpdateEmployeeForm(EmployeeService service) {
        this.service = service;
        setTitle("Update Employee");
        setSize(520, 430);
        setLocationRelativeTo(null);
        buildUi();
    }

    private void buildUi() {
        setLayout(new BorderLayout(10, 10));

        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 10, 15));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6, 6, 6, 6);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 0;

        addField(formPanel, gbc, "Employee ID", idField);
        addField(formPanel, gbc, "New Father's Name", fatherField);
        addField(formPanel, gbc, "New Address", addressField);
        addField(formPanel, gbc, "New Highest Education", educationField);
        addField(formPanel, gbc, "New Mobile No", mobileField);
        addField(formPanel, gbc, "New Email ID", emailField);
        addField(formPanel, gbc, "New Salary", salaryField);
        addField(formPanel, gbc, "New Department", departmentField);

        JLabel noteLabel = new JLabel("Note: ID, Name, DOB, and Aadhar Number cannot be changed.");
        noteLabel.setBorder(BorderFactory.createEmptyBorder(0, 15, 0, 15));

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton updateButton = new JButton("Update");
        JButton closeButton = new JButton("Close");
        updateButton.addActionListener(event -> updateEmployee());
        closeButton.addActionListener(event -> dispose());
        buttonPanel.add(updateButton);
        buttonPanel.add(closeButton);

        add(noteLabel, BorderLayout.NORTH);
        add(formPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void addField(JPanel panel, GridBagConstraints gbc, String label, JTextField field) {
        gbc.gridx = 0;
        panel.add(new JLabel(label + ":"), gbc);
        gbc.gridx = 1;
        gbc.weightx = 1;
        panel.add(field, gbc);
        gbc.gridy++;
        gbc.weightx = 0;
    }

    private void updateEmployee() {
        try {
            int employeeId = Integer.parseInt(idField.getText().trim());
            service.updateEmployee(
                    employeeId,
                    fatherField.getText(),
                    addressField.getText(),
                    educationField.getText(),
                    mobileField.getText(),
                    emailField.getText(),
                    salaryField.getText(),
                    departmentField.getText()
            );
            JOptionPane.showMessageDialog(this, service.getLastMessage());
        } catch (NumberFormatException exception) {
            JOptionPane.showMessageDialog(this, "Error: Invalid ID format!", "Invalid Input", JOptionPane.ERROR_MESSAGE);
        }
    }
}
