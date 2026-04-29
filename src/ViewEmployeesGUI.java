import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.util.ArrayList;

public class ViewEmployeesGUI extends JFrame {
    private final EmployeeService service;
    private final DefaultTableModel tableModel;

    public ViewEmployeesGUI(EmployeeService service) {
        this.service = service;
        this.tableModel = new DefaultTableModel(
                new Object[]{"ID", "Name", "Father's Name", "DOB", "Address", "Education", "Aadhar", "Mobile", "Email", "Salary", "Department"},
                0
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        setTitle("View All Employees");
        setSize(1100, 420);
        setLocationRelativeTo(null);
        buildUi();
        loadEmployees();
    }

    private void buildUi() {
        setLayout(new BorderLayout(10, 10));

        JTable employeeTable = new JTable(tableModel);
        employeeTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        JScrollPane scrollPane = new JScrollPane(employeeTable);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton refreshButton = new JButton("Refresh");
        JButton closeButton = new JButton("Close");
        refreshButton.addActionListener(event -> loadEmployees());
        closeButton.addActionListener(event -> dispose());
        buttonPanel.add(refreshButton);
        buttonPanel.add(closeButton);

        add(scrollPane, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void loadEmployees() {
        tableModel.setRowCount(0);
        ArrayList<Employee> employees = service.getAllEmployeesData();

        for (Employee employee : employees) {
            tableModel.addRow(new Object[]{
                    employee.getId(),
                    employee.getName(),
                    employee.getFathersName(),
                    employee.getDob(),
                    employee.getAddress(),
                    employee.getHighestEducation(),
                    employee.getAadharNo(),
                    employee.getMobileNo(),
                    employee.getEmailId(),
                    employee.getSalary(),
                    employee.getDepartment()
            });
        }
    }
}
