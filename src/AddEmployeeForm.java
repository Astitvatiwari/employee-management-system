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

public class AddEmployeeForm extends JFrame {
    private final EmployeeService service;
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

    public AddEmployeeForm(EmployeeService service) {
        this.service = service;
        setTitle("Add Employee");
        setSize(500, 520);
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
        addField(formPanel, gbc, "Employee Name", nameField);
        addField(formPanel, gbc, "Father's Name", fatherField);
        addField(formPanel, gbc, "Date of Birth", dobField);
        addField(formPanel, gbc, "Address", addressField);
        addField(formPanel, gbc, "Highest Education", educationField);
        addField(formPanel, gbc, "Aadhar No", aadharField);
        addField(formPanel, gbc, "Mobile No", mobileField);
        addField(formPanel, gbc, "Email ID", emailField);
        addField(formPanel, gbc, "Employee Salary", salaryField);
        addField(formPanel, gbc, "Department", departmentField);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton saveButton = new JButton("Save");
        JButton closeButton = new JButton("Close");
        saveButton.addActionListener(event -> saveEmployee());
        closeButton.addActionListener(event -> dispose());
        buttonPanel.add(saveButton);
        buttonPanel.add(closeButton);

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

    private void saveEmployee() {
        try {
            Employee employee = new Employee(
                    Integer.parseInt(idField.getText().trim()),
                    nameField.getText().trim(),
                    fatherField.getText().trim(),
                    dobField.getText().trim(),
                    addressField.getText().trim(),
                    aadharField.getText().trim(),
                    mobileField.getText().trim(),
                    emailField.getText().trim(),
                    educationField.getText().trim(),
                    Double.parseDouble(salaryField.getText().trim()),
                    departmentField.getText().trim()
            );

            service.addEmployee(employee);
            JOptionPane.showMessageDialog(this, service.getLastMessage());
            if ("Employee added successfully!".equals(service.getLastMessage())) {
                clearForm();
            }
        } catch (NumberFormatException exception) {
            JOptionPane.showMessageDialog(
                    this,
                    "Error: Please enter valid numeric formats for ID and Salary.",
                    "Invalid Input",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    private void clearForm() {
        idField.setText("");
        nameField.setText("");
        fatherField.setText("");
        dobField.setText("");
        addressField.setText("");
        educationField.setText("");
        aadharField.setText("");
        mobileField.setText("");
        emailField.setText("");
        salaryField.setText("");
        departmentField.setText("");
    }
}
