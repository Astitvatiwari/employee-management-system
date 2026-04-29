package employee.management.system;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
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

public class EmployeeFormDialog extends JDialog {
    private final JTextField idField = new JTextField(18);
    private final JTextField nameField = new JTextField(18);
    private final JTextField fatherField = new JTextField(18);
    private final JTextField dobField = new JTextField(18);
    private final JTextField addressField = new JTextField(18);
    private final JTextField educationField = new JTextField(18);
    private final JTextField aadharField = new JTextField(18);
    private final JTextField mobileField = new JTextField(18);
    private final JTextField emailField = new JTextField(18);
    private final JTextField salaryField = new JTextField(18);
    private final JTextField departmentField = new JTextField(18);

    private Employee employee;
    private boolean confirmed;

    public EmployeeFormDialog(JFrame owner, String title, Employee employee, boolean editing) {
        super(owner, title, true);
        this.employee = employee == null ? new Employee() : employee.copy();
        buildUi(editing);
        populateFields();
    }

    public boolean isConfirmed() {
        return confirmed;
    }

    public Employee getEmployee() {
        return employee.copy();
    }

    private void buildUi(boolean editing) {
        setLayout(new BorderLayout(12, 12));

        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBorder(BorderFactory.createEmptyBorder(16, 16, 8, 16));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6, 6, 6, 6);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 0;

        addField(formPanel, gbc, "Employee ID", idField);
        addField(formPanel, gbc, "Name", nameField);
        addField(formPanel, gbc, "Father's Name", fatherField);
        addField(formPanel, gbc, "Date of Birth", dobField);
        addField(formPanel, gbc, "Address", addressField);
        addField(formPanel, gbc, "Highest Education", educationField);
        addField(formPanel, gbc, "Aadhar Number", aadharField);
        addField(formPanel, gbc, "Mobile Number", mobileField);
        addField(formPanel, gbc, "Email ID", emailField);
        addField(formPanel, gbc, "Salary", salaryField);
        addField(formPanel, gbc, "Department", departmentField);

        idField.setEditable(!editing);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton saveButton = new JButton(editing ? "Update" : "Save");
        JButton cancelButton = new JButton("Cancel");
        saveButton.addActionListener(event -> onSave());
        cancelButton.addActionListener(event -> dispose());
        buttonPanel.add(saveButton);
        buttonPanel.add(cancelButton);

        add(formPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        pack();
        setLocationRelativeTo(getOwner());
    }

    private void addField(JPanel formPanel, GridBagConstraints gbc, String labelText, JTextField field) {
        gbc.gridx = 0;
        gbc.weightx = 0;
        formPanel.add(new JLabel(labelText + ":"), gbc);
        gbc.gridx = 1;
        gbc.weightx = 1;
        formPanel.add(field, gbc);
        gbc.gridy++;
    }

    private void populateFields() {
        if (employee.getId() > 0) {
            idField.setText(String.valueOf(employee.getId()));
        }
        nameField.setText(safe(employee.getName()));
        fatherField.setText(safe(employee.getFathersName()));
        dobField.setText(safe(employee.getDateOfBirth()));
        addressField.setText(safe(employee.getAddress()));
        educationField.setText(safe(employee.getHighestEducation()));
        aadharField.setText(safe(employee.getAadharNumber()));
        mobileField.setText(safe(employee.getMobileNumber()));
        emailField.setText(safe(employee.getEmailId()));
        if (employee.getSalary() > 0) {
            salaryField.setText(String.valueOf(employee.getSalary()));
        }
        departmentField.setText(safe(employee.getDepartment()));
    }

    private String safe(String value) {
        return value == null ? "" : value;
    }

    private void onSave() {
        try {
            Employee updated = new Employee(
                    Integer.parseInt(idField.getText().trim()),
                    nameField.getText().trim(),
                    fatherField.getText().trim(),
                    dobField.getText().trim(),
                    addressField.getText().trim(),
                    educationField.getText().trim(),
                    aadharField.getText().trim(),
                    mobileField.getText().trim(),
                    emailField.getText().trim(),
                    Double.parseDouble(salaryField.getText().trim()),
                    departmentField.getText().trim()
            );
            employee = updated;
            confirmed = true;
            dispose();
        } catch (NumberFormatException exception) {
            JOptionPane.showMessageDialog(this,
                    "Employee ID must be an integer and salary must be a valid number.",
                    "Invalid Input",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
}
