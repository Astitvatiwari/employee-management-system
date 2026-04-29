package employee.management.system;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class BackendSmokeTest {
    public static void main(String[] args) throws Exception {
        Path tempFile = Files.createTempFile("ems-smoke-test", ".ser");
        Files.deleteIfExists(tempFile);

        EmployeeRepository repository = new FileEmployeeRepository(tempFile);
        EmployeeService service = new EmployeeService(repository);

        Employee first = new Employee(1001, "Aarav Singh", "Rakesh Singh", "01/01/1998",
                "Delhi", "B.Tech", "111122223333", "9876543210",
                "aarav@example.com", 55000, "Engineering");
        Employee second = new Employee(1002, "Diya Sharma", "Mohan Sharma", "12/07/1997",
                "Noida", "MBA", "444455556666", "9123456780",
                "diya@example.com", 62000, "HR");

        service.addEmployee(first);
        service.addEmployee(second);

        if (service.getAllEmployees().size() != 2) {
            throw new IllegalStateException("Expected 2 employees after add.");
        }

        List<Employee> searchResults = service.searchEmployees("diya");
        if (searchResults.size() != 1 || searchResults.get(0).getId() != 1002) {
            throw new IllegalStateException("Search by name failed.");
        }

        Employee updated = second.copy();
        updated.setDepartment("People Operations");
        updated.setSalary(68000);
        service.updateEmployee(updated);

        Employee reloaded = new EmployeeService(repository).findById(1002)
                .orElseThrow(() -> new IllegalStateException("Updated employee missing after reload."));
        if (!"People Operations".equals(reloaded.getDepartment()) || reloaded.getSalary() != 68000) {
            throw new IllegalStateException("Update persistence failed.");
        }

        service.deleteEmployee(1001);
        if (service.getAllEmployees().size() != 1) {
            throw new IllegalStateException("Delete failed.");
        }

        Files.deleteIfExists(tempFile);
        System.out.println("Backend smoke test passed.");
    }
}
