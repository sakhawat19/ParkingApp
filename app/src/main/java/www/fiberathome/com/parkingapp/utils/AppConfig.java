package www.fiberathome.com.parkingapp.utils;

public class AppConfig {

    private static final String ROOT_URL = "http://163.47.157.195/cportal/parkingapp/Api.php?apicall=";
    public static final String URL_REGISTER = ROOT_URL + "signup";
    public static final String URL_LOGIN = ROOT_URL + "login";
    public static final String IMAGES_URL = "http://163.47.157.195/cportal/parkingapp/uploads/";


    // SMS provider identification
    // It should match with your SMS gateway origin
    // You can use MSGIND, TESTER and ALERTS as sender ID
    // If you want custom sender Id, approve Msg91 to get one
    public static final String SMS_ORIGIN = "PARKINGAPP_";

    // special character to prefix the otp. Make sure this character appears only once in the sms
    public static final String OTP_DELEMETER = ":";
}
