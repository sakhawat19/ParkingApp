package www.fiberathome.com.parkingapp.model;

public class User {

    private int id;
    private String fullName;
    private String password;
    private String mobileNo;
    private String vehicleNo;

    public User(){

    }

    public User(int id, String fullName, String password, String mobileNo, String vehicleNo) {
        this.id = id;
        this.fullName = fullName;
        this.password = password;
        this.mobileNo = mobileNo;
        this.vehicleNo = vehicleNo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getVehicleNo() {
        return vehicleNo;
    }

    public void setVehicleNo(String vehicleNo) {
        this.vehicleNo = vehicleNo;
    }
}
