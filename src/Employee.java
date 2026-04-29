public class Employee extends Person {
    private String highestEducation;
    private double salary;
    private String department;

    public Employee(int id, String name, String fathersName, String dob, String address, String aadharNo, String mobileNo, String emailId, String highestEducation, double salary, String department) {
        super(id, name, fathersName, dob, address, aadharNo, mobileNo, emailId);
        this.highestEducation = highestEducation;
        this.salary = salary;
        this.department = department;
    }

    public String getHighestEducation() { return highestEducation; }
    public void setHighestEducation(String highestEducation) { this.highestEducation = highestEducation; }

    public double getSalary() { return salary; }
    public void setSalary(double salary) { this.salary = salary; }

    public String getDepartment() { return department; }
    public void setDepartment(String department) { this.department = department; }

    @Override
    public void displayDetails() {
        System.out.println("-------------------------------------------------");
        System.out.println("ID               : " + getId());
        System.out.println("Name             : " + getName());
        System.out.println("Father's Name    : " + getFathersName());
        System.out.println("Date of Birth    : " + getDob());
        System.out.println("Address          : " + getAddress());
        System.out.println("Highest Education: " + getHighestEducation());
        System.out.println("Aadhar No        : " + getAadharNo());
        System.out.println("Mobile No        : " + getMobileNo());
        System.out.println("Email ID         : " + getEmailId());
        System.out.println("Salary           : " + salary);
        System.out.println("Department       : " + department);
        System.out.println("-------------------------------------------------");
    }
}
