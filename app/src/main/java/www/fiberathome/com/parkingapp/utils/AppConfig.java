package www.fiberathome.com.parkingapp.utils;

public class AppConfig {

    // ROOT URL
    private static final String ROOT_URL        = "http://163.47.157.195/cportal/parkingapp/";
    public static final String URL_REGISTER     = ROOT_URL + "request_sms.php";
    public static final String URL_LOGIN        = ROOT_URL + "verify_user.php";
    public static final String URL_VERIFY_OTP   = ROOT_URL + "verify_otp.php";
    public static final String IMAGES_URL       = "http://163.47.157.195/cportal/parkingapp/uploads/";


    // SMS provider identification
    // It should match with your SMS gateway origin
    // You can use MSGIND, TESTER and ALERTS as sender ID
    // If you want custom sender Id, approve Msg91 to get one
    public static final String SMS_ORIGIN       = "PARKINGAPP_";
    public static final String SHARED_PREFERENCES = "";

}
