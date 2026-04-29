import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class EmployeeService {
    private final ArrayList<Employee> employeeList = new ArrayList<>();
    private final Scanner scanner = new Scanner(System.in);
    private String lastMessage = "";

    public void addEmployee() {
        try {
            System.out.print("Enter Employee ID: ");
            int id = Integer.parseInt(scanner.nextLine());

            System.out.print("Enter Employee Name: ");
            String name = scanner.nextLine();

            System.out.print("Enter Father's Name: ");
            String fathersName = scanner.nextLine();

            System.out.print("Enter Date of Birth (e.g., DD/MM/YYYY): ");
            String dob = scanner.nextLine();

            System.out.print("Enter Address: ");
            String address = scanner.nextLine();

            System.out.print("Enter Highest Education: ");
            String education = scanner.nextLine();

            System.out.print("Enter Aadhar No: ");
            String aadhar = scanner.nextLine();

            System.out.print("Enter Mobile No: ");
            String mobile = scanner.nextLine();

            System.out.print("Enter Email ID: ");
            String email = scanner.nextLine();

            System.out.print("Enter Employee Salary: ");
            double salary = Double.parseDouble(scanner.nextLine());

            System.out.print("Enter Employee Department: ");
            String department = scanner.nextLine();

            Employee newEmp = new Employee(id, name, fathersName, dob, address, aadhar, mobile, email, education, salary, department);
            addEmployee(newEmp);
            System.out.println("\n=> " + lastMessage);
        } catch (NumberFormatException e) {
            lastMessage = "Error: Please enter valid numeric formats for ID and Salary.";
            System.out.println("\n=> " + lastMessage);
        }
    }

    public boolean addEmployee(Employee newEmp) {
        for (Employee emp : employeeList) {
            if (emp.getId() == newEmp.getId()) {
                lastMessage = "Error: Employee with this ID already exists!";
                return false;
            }
        }

        for (Employee emp : employeeList) {
            if (emp.getAadharNo().equals(newEmp.getAadharNo())) {
                lastMessage = "Error: Employee with this Aadhar No already exists!";
                return false;
            }
        }

        employeeList.add(newEmp);
        lastMessage = "Employee added successfully!";
        return true;
    }

    public void viewAllEmployees() {
        if (employeeList.isEmpty()) {
            lastMessage = "No employees found. The system is empty.";
            System.out.println(lastMessage);
        } else {
            lastMessage = "Employee list displayed.";
            System.out.println("\n=============== EMPLOYEE LIST ===============");
            for (Employee emp : employeeList) {
                emp.displayDetails();
            }
        }
    }

    public ArrayList<Employee> getAllEmployeesData() {
        return new ArrayList<>(employeeList);
    }

    public void searchEmployee() {
        try {
            System.out.print("Enter Employee ID to search: ");
            int searchId = Integer.parseInt(scanner.nextLine());

            Employee employee = searchEmployee(searchId);
            if (employee != null) {
                System.out.println("\n[ Employee Found ]");
                employee.displayDetails();
            } else {
                System.out.println(lastMessage);
            }
        } catch (NumberFormatException e) {
            lastMessage = "Error: Invalid ID format!";
            System.out.println(lastMessage);
        }
    }

    public Employee searchEmployee(int searchId) {
        for (Employee emp : employeeList) {
            if (emp.getId() == searchId) {
                lastMessage = "Employee found.";
                return emp;
            }
        }
        lastMessage = "Employee with ID " + searchId + " not found.";
        return null;
    }

    public void updateEmployee() {
        try {
            System.out.print("Enter Employee ID to update: ");
            int updateId = Integer.parseInt(scanner.nextLine());

            Employee employee = searchEmployee(updateId);
            if (employee == null) {
                System.out.println(lastMessage);
                return;
            }

            System.out.println("\n[ Employee Found ]");
            System.out.println("Notice: Employee ID, Name, Date of Birth, and Aadhar Number are permanent and cannot be changed.");

            System.out.print("Enter new Father's Name (Current: " + employee.getFathersName() + " | Press Enter to skip): ");
            String newFather = scanner.nextLine();

            System.out.print("Enter new Address (Current: " + employee.getAddress() + " | Press Enter to skip): ");
            String newAddress = scanner.nextLine();

            System.out.print("Enter new Highest Education (Current: " + employee.getHighestEducation() + " | Press Enter to skip): ");
            String newEducation = scanner.nextLine();

            System.out.print("Enter new Mobile No (Current: " + employee.getMobileNo() + " | Press Enter to skip): ");
            String newMobile = scanner.nextLine();

            System.out.print("Enter new Email ID (Current: " + employee.getEmailId() + " | Press Enter to skip): ");
            String newEmail = scanner.nextLine();

            System.out.print("Enter new Salary (Current: " + employee.getSalary() + " | Press Enter to skip): ");
            String newSalaryStr = scanner.nextLine();

            System.out.print("Enter new Department (Current: " + employee.getDepartment() + " | Press Enter to skip): ");
            String newDept = scanner.nextLine();

            updateEmployee(updateId, newFather, newAddress, newEducation, newMobile, newEmail, newSalaryStr, newDept);
            System.out.println("\n=> " + lastMessage);
        } catch (NumberFormatException e) {
            lastMessage = "Error: Invalid ID format!";
            System.out.println(lastMessage);
        }
    }

    public boolean updateEmployee(int updateId, String newFather, String newAddress, String newEducation,
                                  String newMobile, String newEmail, String newSalaryStr, String newDept) {
        Employee employee = searchEmployee(updateId);
        if (employee == null) {
            return false;
        }

        StringBuilder messageBuilder = new StringBuilder();

        if (newFather != null && !newFather.trim().isEmpty()) {
            employee.setFathersName(newFather);
        }

        if (newAddress != null && !newAddress.trim().isEmpty()) {
            employee.setAddress(newAddress);
        }

        if (newEducation != null && !newEducation.trim().isEmpty()) {
            employee.setHighestEducation(newEducation);
        }

        if (newMobile != null && !newMobile.trim().isEmpty()) {
            employee.setMobileNo(newMobile);
        }

        if (newEmail != null && !newEmail.trim().isEmpty()) {
            employee.setEmailId(newEmail);
        }

        if (newSalaryStr != null && !newSalaryStr.trim().isEmpty()) {
            try {
                employee.setSalary(Double.parseDouble(newSalaryStr));
            } catch (Exception e) {
                messageBuilder.append("Invalid salary entered. Keeping old value for Salary.\n");
            }
        }

        if (newDept != null && !newDept.trim().isEmpty()) {
            employee.setDepartment(newDept);
        }

        messageBuilder.append("Employee details updated successfully!");
        lastMessage = messageBuilder.toString();
        return true;
    }

    public void deleteEmployee() {
        try {
            System.out.print("Enter Employee ID to delete: ");
            int deleteId = Integer.parseInt(scanner.nextLine());

            deleteEmployee(deleteId);
            System.out.println("\n=> " + lastMessage);
        } catch (NumberFormatException e) {
            lastMessage = "Error: Invalid ID format!";
            System.out.println(lastMessage);
        }
    }

    public boolean deleteEmployee(int deleteId) {
        for (int i = 0; i < employeeList.size(); i++) {
            if (employeeList.get(i).getId() == deleteId) {
                employeeList.remove(i);
                lastMessage = "Employee deleted successfully!";
                return true;
            }
        }
        lastMessage = "Employee with ID " + deleteId + " not found.";
        return false;
    }

    public void printEmployeeDetails() {
        try {
            System.out.print("Enter Employee ID to print: ");
            int printId = Integer.parseInt(scanner.nextLine());

            printEmployeeDetails(printId);
            if (!lastMessage.isEmpty()) {
                System.out.println("\n=> " + lastMessage);
            }
        } catch (NumberFormatException e) {
            lastMessage = "Error: Invalid ID format!";
            System.out.println(lastMessage);
        }
    }

    public String printEmployeeDetails(int printId) {
        for (Employee emp : employeeList) {
            if (emp.getId() == printId) {
                try {
                    String fileName = "Employee_" + emp.getId() + "_Details.txt";
                    FileWriter writer = new FileWriter(fileName);
                    writer.write("=================================================\n");
                    writer.write("             EMPLOYEE DETAILS REPORT             \n");
                    writer.write("=================================================\n");
                    writer.write("ID               : " + emp.getId() + "\n");
                    writer.write("Name             : " + emp.getName() + "\n");
                    writer.write("Father's Name    : " + emp.getFathersName() + "\n");
                    writer.write("Date of Birth    : " + emp.getDob() + "\n");
                    writer.write("Address          : " + emp.getAddress() + "\n");
                    writer.write("Highest Education: " + emp.getHighestEducation() + "\n");
                    writer.write("Aadhar No        : " + emp.getAadharNo() + "\n");
                    writer.write("Mobile No        : " + emp.getMobileNo() + "\n");
                    writer.write("Email ID         : " + emp.getEmailId() + "\n");
                    writer.write("Salary           : " + emp.getSalary() + "\n");
                    writer.write("Department       : " + emp.getDepartment() + "\n");
                    writer.write("=================================================\n");
                    writer.close();
                    lastMessage = "Success! Details printed to file: " + fileName;
                    return fileName;
                } catch (IOException e) {
                    lastMessage = "Error saving file: " + e.getMessage();
                    return null;
                }
            }
        }
        lastMessage = "Employee with ID " + printId + " not found.";
        return null;
    }

    public String getLastMessage() {
        return lastMessage;
    }
}
