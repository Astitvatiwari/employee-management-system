public abstract class Person {
    private int id;
    private String name;
    private String fathersName;
    private String dob;
    private String address;
    private String aadharNo;
    private String mobileNo;
    private String emailId;

    public Person(int id, String name, String fathersName, String dob, String address, String aadharNo, String mobileNo, String emailId) {
        this.id = id;
        this.name = name;
        this.fathersName = fathersName;
        this.dob = dob;
        this.address = address;
        this.aadharNo = aadharNo;
        this.mobileNo = mobileNo;
        this.emailId = emailId;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getFathersName() { return fathersName; }
    public void setFathersName(String fathersName) { this.fathersName = fathersName; }

    public String getDob() { return dob; }
    public void setDob(String dob) { this.dob = dob; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public String getAadharNo() { return aadharNo; }
    public void setAadharNo(String aadharNo) { this.aadharNo = aadharNo; }

    public String getMobileNo() { return mobileNo; }
    public void setMobileNo(String mobileNo) { this.mobileNo = mobileNo; }

    public String getEmailId() { return emailId; }
    public void setEmailId(String emailId) { this.emailId = emailId; }

    public abstract void displayDetails();
}
