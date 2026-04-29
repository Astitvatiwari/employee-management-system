package employee.management.system;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

public class EmployeeService {
    private final EmployeeRepository repository;
    private final List<Employee> employees;

    public EmployeeService(EmployeeRepository repository) {
        this.repository = repository;
        this.employees = new ArrayList<>(repository.loadEmployees());
        this.employees.sort(Comparator.comparingInt(Employee::getId));
    }

    public List<Employee> getAllEmployees() {
        return employees.stream()
                .sorted(Comparator.comparingInt(Employee::getId))
                .map(Employee::copy)
                .toList();
    }

    public List<Employee> searchEmployees(String query) {
        String normalizedQuery = query == null ? "" : query.trim().toLowerCase(Locale.ROOT);
        if (normalizedQuery.isEmpty()) {
            return getAllEmployees();
        }

        return employees.stream()
                .filter(employee -> matches(employee, normalizedQuery))
                .sorted(Comparator.comparingInt(Employee::getId))
                .map(Employee::copy)
                .toList();
    }

    public Employee addEmployee(Employee employee) {
        validateEmployee(employee, true);
        Employee toStore = employee.copy();
        employees.add(toStore);
        employees.sort(Comparator.comparingInt(Employee::getId));
        persist();
        return toStore.copy();
    }

    public Employee updateEmployee(Employee employee) {
        validateEmployee(employee, false);
        Employee existing = findRequired(employee.getId());
        existing.setName(employee.getName().trim());
        existing.setFathersName(employee.getFathersName().trim());
        existing.setDateOfBirth(employee.getDateOfBirth().trim());
        existing.setAddress(employee.getAddress().trim());
        existing.setHighestEducation(employee.getHighestEducation().trim());
        existing.setAadharNumber(employee.getAadharNumber().trim());
        existing.setMobileNumber(employee.getMobileNumber().trim());
        existing.setEmailId(employee.getEmailId().trim());
        existing.setSalary(employee.getSalary());
        existing.setDepartment(employee.getDepartment().trim());
        persist();
        return existing.copy();
    }

    public void deleteEmployee(int employeeId) {
        Employee existing = findRequired(employeeId);
        employees.remove(existing);
        persist();
    }

    public Optional<Employee> findById(int employeeId) {
        return employees.stream()
                .filter(employee -> employee.getId() == employeeId)
                .findFirst()
                .map(Employee::copy);
    }

    private boolean matches(Employee employee, String query) {
        return String.valueOf(employee.getId()).contains(query)
                || employee.getName().toLowerCase(Locale.ROOT).contains(query)
                || employee.getDepartment().toLowerCase(Locale.ROOT).contains(query);
    }

    private void validateEmployee(Employee employee, boolean requireNewId) {
        if (employee == null) {
            throw new IllegalArgumentException("Employee details are required.");
        }
        if (employee.getId() <= 0) {
            throw new IllegalArgumentException("Employee ID must be a positive number.");
        }
        if (isBlank(employee.getName())) {
            throw new IllegalArgumentException("Employee name is required.");
        }
        if (isBlank(employee.getFathersName())) {
            throw new IllegalArgumentException("Father's name is required.");
        }
        if (isBlank(employee.getDateOfBirth())) {
            throw new IllegalArgumentException("Date of birth is required.");
        }
        if (isBlank(employee.getAddress())) {
            throw new IllegalArgumentException("Address is required.");
        }
        if (isBlank(employee.getHighestEducation())) {
            throw new IllegalArgumentException("Highest education is required.");
        }
        if (isBlank(employee.getAadharNumber()) || !employee.getAadharNumber().matches("\\d{12}")) {
            throw new IllegalArgumentException("Aadhar number must contain exactly 12 digits.");
        }
        if (isBlank(employee.getMobileNumber()) || !employee.getMobileNumber().matches("\\d{10}")) {
            throw new IllegalArgumentException("Mobile number must contain exactly 10 digits.");
        }
        if (isBlank(employee.getEmailId()) || !employee.getEmailId().matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")) {
            throw new IllegalArgumentException("Enter a valid email address.");
        }
        if (employee.getSalary() < 0) {
            throw new IllegalArgumentException("Salary cannot be negative.");
        }
        if (isBlank(employee.getDepartment())) {
            throw new IllegalArgumentException("Department is required.");
        }

        boolean duplicateId = employees.stream().anyMatch(existing -> existing.getId() == employee.getId());
        if (requireNewId && duplicateId) {
            throw new IllegalArgumentException("An employee with this ID already exists.");
        }
        if (!requireNewId && !duplicateId) {
            throw new IllegalArgumentException("Employee ID " + employee.getId() + " was not found.");
        }

        boolean duplicateAadhar = employees.stream()
                .anyMatch(existing -> existing.getAadharNumber().equals(employee.getAadharNumber())
                        && existing.getId() != employee.getId());
        if (duplicateAadhar) {
            throw new IllegalArgumentException("An employee with this Aadhar number already exists.");
        }
    }

    private Employee findRequired(int employeeId) {
        return employees.stream()
                .filter(employee -> employee.getId() == employeeId)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Employee ID " + employeeId + " was not found."));
    }

    private boolean isBlank(String value) {
        return value == null || value.trim().isEmpty();
    }

    private void persist() {
        repository.saveEmployees(employees);
    }
}
