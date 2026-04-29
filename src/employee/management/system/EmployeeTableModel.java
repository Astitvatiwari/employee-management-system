package employee.management.system;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;

public class EmployeeTableModel extends AbstractTableModel {
    private static final String[] COLUMNS = {
            "ID", "Name", "Department", "Salary", "Mobile", "Email", "Education"
    };

    private List<Employee> employees = new ArrayList<>();

    public void setEmployees(List<Employee> employees) {
        this.employees = new ArrayList<>(employees);
        fireTableDataChanged();
    }

    public Employee getEmployeeAt(int rowIndex) {
        return employees.get(rowIndex).copy();
    }

    @Override
    public int getRowCount() {
        return employees.size();
    }

    @Override
    public int getColumnCount() {
        return COLUMNS.length;
    }

    @Override
    public String getColumnName(int column) {
        return COLUMNS[column];
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Employee employee = employees.get(rowIndex);
        return switch (columnIndex) {
            case 0 -> employee.getId();
            case 1 -> employee.getName();
            case 2 -> employee.getDepartment();
            case 3 -> String.format("%.2f", employee.getSalary());
            case 4 -> employee.getMobileNumber();
            case 5 -> employee.getEmailId();
            case 6 -> employee.getHighestEducation();
            default -> "";
        };
    }
}
