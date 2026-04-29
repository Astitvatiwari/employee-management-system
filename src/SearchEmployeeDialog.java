import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JOptionPane;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

public class SearchEmployeeDialog extends JDialog {
    private final EmployeeService service;
    private final JTextField idField = new JTextField(16);
    private final JTextArea resultArea = new JTextArea(10, 32);

    public SearchEmployeeDialog(JFrame owner, EmployeeService service) {
        super(owner, "Search Employee", true);
        this.service = service;
        setSize(460, 360);
        setLocationRelativeTo(owner);
        buildUi();
    }

    private void buildUi() {
        setLayout(new BorderLayout(10, 10));

        JPanel inputPanel = new JPanel(new GridBagLayout());
        inputPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 5, 15));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6, 6, 6, 6);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 0;
        inputPanel.add(new JLabel("Employee ID:"), gbc);
        gbc.gridx = 1;
        inputPanel.add(idField, gbc);

        resultArea.setEditable(false);
        resultArea.setLineWrap(true);
        resultArea.setWrapStyleWord(true);
        JScrollPane scrollPane = new JScrollPane(resultArea);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton searchButton = new JButton("Search");
        JButton closeButton = new JButton("Close");
        searchButton.addActionListener(event -> searchEmployee());
        closeButton.addActionListener(event -> dispose());
        buttonPanel.add(searchButton);
        buttonPanel.add(closeButton);

        add(inputPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void searchEmployee() {
        try {
            int employeeId = Integer.parseInt(idField.getText().trim());
            Employee employee = service.searchEmployee(employeeId);
            if (employee == null) {
                resultArea.setText(service.getLastMessage());
                return;
            }

            resultArea.setText(
                    "ID               : " + employee.getId() + "\n" +
                    "Name             : " + employee.getName() + "\n" +
                    "Father's Name    : " + employee.getFathersName() + "\n" +
                    "Date of Birth    : " + employee.getDob() + "\n" +
                    "Address          : " + employee.getAddress() + "\n" +
                    "Highest Education: " + employee.getHighestEducation() + "\n" +
                    "Aadhar No        : " + employee.getAadharNo() + "\n" +
                    "Mobile No        : " + employee.getMobileNo() + "\n" +
                    "Email ID         : " + employee.getEmailId() + "\n" +
                    "Salary           : " + employee.getSalary() + "\n" +
                    "Department       : " + employee.getDepartment()
            );
        } catch (NumberFormatException exception) {
            JOptionPane.showMessageDialog(this, "Error: Invalid ID format!", "Invalid Input", JOptionPane.ERROR_MESSAGE);
        }
    }
}
