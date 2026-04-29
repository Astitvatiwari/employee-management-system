package employee.management.system;

import java.io.Serializable;
import java.util.Objects;

public class Employee implements Serializable {
    private static final long serialVersionUID = 1L;

    private int id;
    private String name;
    private String fathersName;
    private String dateOfBirth;
    private String address;
    private String highestEducation;
    private String aadharNumber;
    private String mobileNumber;
    private String emailId;
    private double salary;
    private String department;

    public Employee() {
    }

    public Employee(int id, String name, String fathersName, String dateOfBirth, String address,
                    String highestEducation, String aadharNumber, String mobileNumber,
                    String emailId, double salary, String department) {
        this.id = id;
        this.name = name;
        this.fathersName = fathersName;
        this.dateOfBirth = dateOfBirth;
        this.address = address;
        this.highestEducation = highestEducation;
        this.aadharNumber = aadharNumber;
        this.mobileNumber = mobileNumber;
        this.emailId = emailId;
        this.salary = salary;
        this.department = department;
    }

    public Employee copy() {
        return new Employee(id, name, fathersName, dateOfBirth, address, highestEducation,
                aadharNumber, mobileNumber, emailId, salary, department);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFathersName() {
        return fathersName;
    }

    public void setFathersName(String fathersName) {
        this.fathersName = fathersName;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getHighestEducation() {
        return highestEducation;
    }

    public void setHighestEducation(String highestEducation) {
        this.highestEducation = highestEducation;
    }

    public String getAadharNumber() {
        return aadharNumber;
    }

    public void setAadharNumber(String aadharNumber) {
        this.aadharNumber = aadharNumber;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Employee employee)) {
            return false;
        }
        return id == employee.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
