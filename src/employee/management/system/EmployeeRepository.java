package employee.management.system;

import java.util.List;

public interface EmployeeRepository {
    List<Employee> loadEmployees();

    void saveEmployees(List<Employee> employees);
}
