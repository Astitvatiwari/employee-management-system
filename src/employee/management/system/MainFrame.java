package employee.management.system;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.RowSorter;
import javax.swing.SortOrder;
import javax.swing.SwingConstants;
import javax.swing.table.TableRowSorter;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.nio.file.Path;
import java.text.MessageFormat;
import java.util.List;

public class MainFrame extends JFrame {
    private final EmployeeService employeeService;
    private final EmployeeTableModel tableModel = new EmployeeTableModel();
    private final JTable employeeTable = new JTable(tableModel);
    private final JTextField searchField = new JTextField(22);
    private final JLabel statusLabel = new JLabel("Ready");

    public MainFrame(EmployeeService employeeService, Path dataFile) {
        this.employeeService = employeeService;
        buildUi(dataFile);
        refreshTable(employeeService.getAllEmployees(), "Loaded employee records.");
    }

    private void buildUi(Path dataFile) {
        setTitle("Employee Management System");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(1120, 620);
        setMinimumSize(new Dimension(980, 560));
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(12, 12));

        JLabel title = new JLabel("Employee Management System");
        title.setFont(new Font("SansSerif", Font.BOLD, 24));
        title.setForeground(new Color(25, 58, 89));

        JPanel headerText = new JPanel(new BorderLayout());
        headerText.setOpaque(false);
        headerText.add(title, BorderLayout.CENTER);

        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton searchButton = new JButton("Search");
        JButton resetButton = new JButton("View All");
        searchButton.addActionListener(event -> searchEmployees());
        resetButton.addActionListener(event -> refreshTable(employeeService.getAllEmployees(), "Showing all employees."));
        searchPanel.add(new JLabel("Search by ID, name, or department:"));
        searchPanel.add(searchField);
        searchPanel.add(searchButton);
        searchPanel.add(resetButton);

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBorder(BorderFactory.createEmptyBorder(14, 16, 0, 16));
        topPanel.add(headerText, BorderLayout.WEST);
        topPanel.add(searchPanel, BorderLayout.EAST);

        employeeTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        employeeTable.setRowHeight(24);
        employeeTable.setAutoCreateRowSorter(true);
        TableRowSorter<EmployeeTableModel> sorter = new TableRowSorter<>(tableModel);
        sorter.setSortKeys(List.of(new RowSorter.SortKey(0, SortOrder.ASCENDING)));
        employeeTable.setRowSorter(sorter);

        JScrollPane tableScrollPane = new JScrollPane(employeeTable);
        tableScrollPane.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createEmptyBorder(8, 16, 0, 16),
                tableScrollPane.getBorder()
        ));

        JPanel actionPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        actionPanel.setBorder(BorderFactory.createEmptyBorder(4, 16, 4, 16));
        JButton addButton = new JButton("Add Employee");
        JButton editButton = new JButton("Update Employee");
        JButton deleteButton = new JButton("Delete Employee");
        JButton detailsButton = new JButton("View Details");
        JButton printButton = new JButton("Print Table");
        addButton.addActionListener(event -> addEmployee());
        editButton.addActionListener(event -> editEmployee());
        deleteButton.addActionListener(event -> deleteEmployee());
        detailsButton.addActionListener(event -> showEmployeeDetails());
        printButton.addActionListener(event -> printTable());
        actionPanel.add(addButton);
        actionPanel.add(editButton);
        actionPanel.add(deleteButton);
        actionPanel.add(detailsButton);
        actionPanel.add(printButton);

        JPanel footer = new JPanel(new BorderLayout());
        footer.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(1, 0, 0, 0, new Color(220, 220, 220)),
                BorderFactory.createEmptyBorder(8, 16, 8, 16)
        ));
        statusLabel.setHorizontalAlignment(SwingConstants.LEFT);
        footer.add(statusLabel, BorderLayout.WEST);
        footer.add(new JLabel("Data file: " + dataFile.toAbsolutePath()), BorderLayout.EAST);

        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.add(actionPanel, BorderLayout.NORTH);
        bottomPanel.add(footer, BorderLayout.SOUTH);

        add(topPanel, BorderLayout.NORTH);
        add(tableScrollPane, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);
    }

    private void addEmployee() {
        EmployeeFormDialog dialog = new EmployeeFormDialog(this, "Add Employee", null, false);
        dialog.setVisible(true);
        if (!dialog.isConfirmed()) {
            return;
        }
        try {
            Employee created = employeeService.addEmployee(dialog.getEmployee());
            refreshTable(employeeService.getAllEmployees(), "Employee " + created.getId() + " added successfully.");
        } catch (IllegalArgumentException exception) {
            showError(exception.getMessage());
        }
    }

    private void editEmployee() {
        Employee selected = getSelectedEmployee();
        if (selected == null) {
            showInfo("Select an employee row to update.");
            return;
        }

        EmployeeFormDialog dialog = new EmployeeFormDialog(this, "Update Employee", selected, true);
        dialog.setVisible(true);
        if (!dialog.isConfirmed()) {
            return;
        }
        try {
            Employee updated = employeeService.updateEmployee(dialog.getEmployee());
            refreshTable(employeeService.searchEmployees(searchField.getText()),
                    "Employee " + updated.getId() + " updated successfully.");
        } catch (IllegalArgumentException exception) {
            showError(exception.getMessage());
        }
    }

    private void deleteEmployee() {
        Employee selected = getSelectedEmployee();
        if (selected == null) {
            showInfo("Select an employee row to delete.");
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this,
                "Delete employee " + selected.getName() + " (ID " + selected.getId() + ")?",
                "Confirm Delete",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE);
        if (confirm != JOptionPane.YES_OPTION) {
            return;
        }

        try {
            employeeService.deleteEmployee(selected.getId());
            refreshTable(employeeService.searchEmployees(searchField.getText()),
                    "Employee " + selected.getId() + " deleted successfully.");
        } catch (IllegalArgumentException exception) {
            showError(exception.getMessage());
        }
    }

    private void showEmployeeDetails() {
        Employee selected = getSelectedEmployee();
        if (selected == null) {
            showInfo("Select an employee row to view details.");
            return;
        }

        String details = """
                Employee ID: %d
                Name: %s
                Father's Name: %s
                Date of Birth: %s
                Address: %s
                Highest Education: %s
                Aadhar Number: %s
                Mobile Number: %s
                Email ID: %s
                Salary: %.2f
                Department: %s
                """.formatted(
                selected.getId(),
                selected.getName(),
                selected.getFathersName(),
                selected.getDateOfBirth(),
                selected.getAddress(),
                selected.getHighestEducation(),
                selected.getAadharNumber(),
                selected.getMobileNumber(),
                selected.getEmailId(),
                selected.getSalary(),
                selected.getDepartment()
        );

        JOptionPane.showMessageDialog(this, details, "Employee Details", JOptionPane.INFORMATION_MESSAGE);
    }

    private void printTable() {
        try {
            boolean printed = employeeTable.print(
                    JTable.PrintMode.FIT_WIDTH,
                    new MessageFormat("Employee Management System"),
                    new MessageFormat("Page {0}")
            );
            statusLabel.setText(printed ? "Table sent to printer dialog." : "Printing was cancelled.");
        } catch (Exception exception) {
            showError("Unable to print the table: " + exception.getMessage());
        }
    }

    private void searchEmployees() {
        List<Employee> results = employeeService.searchEmployees(searchField.getText());
        refreshTable(results, "Found " + results.size() + " matching employee(s).");
    }

    private void refreshTable(List<Employee> employees, String status) {
        tableModel.setEmployees(employees);
        statusLabel.setText(status + " Total records: " + employeeService.getAllEmployees().size());
    }

    private Employee getSelectedEmployee() {
        int selectedRow = employeeTable.getSelectedRow();
        if (selectedRow < 0) {
            return null;
        }
        int modelRow = employeeTable.convertRowIndexToModel(selectedRow);
        return tableModel.getEmployeeAt(modelRow);
    }

    private void showError(String message) {
        JOptionPane.showMessageDialog(this, message, "Validation Error", JOptionPane.ERROR_MESSAGE);
    }

    private void showInfo(String message) {
        JOptionPane.showMessageDialog(this, message, "Information", JOptionPane.INFORMATION_MESSAGE);
    }
}
