package www.fiberathome.com.parkingapp.utils;

import android.content.Context;
import android.content.SharedPreferences;

import www.fiberathome.com.parkingapp.model.User;

public class SharedPreManager {

    private static final String SHARED_PREF_NAME = "parkingApp";
    private static final String KEY_FULLNAME = "fullname";
    private static final String KEY_MOBILE_NO = "mobile_no";
    private static final String KEY_VEHICLE_NO = "vehicle_no";
    private static final String KEY_ID = "id";


    private static SharedPreManager instance;
    private static Context mContext;

    public SharedPreManager(Context context) {
        mContext = context;
    }

    public static synchronized SharedPreManager getInstance(Context context){
        if (instance == null){
            instance = new SharedPreManager(context);
        }

        return instance;
    }


    public void userLogin(User user){
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(KEY_ID, user.getId());
        editor.putString(KEY_FULLNAME, user.getFullName());
        editor.putString(KEY_MOBILE_NO, user.getMobileNo());
        editor.putString(KEY_VEHICLE_NO, user.getVehicleNo());
        editor.apply();
    }

    public boolean isLoggedIn(){
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_MOBILE_NO, null) != null;
    }


    public User getUser(){
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        User user = new User();
        user.setId(sharedPreferences.getInt(KEY_ID, -1));
        user.setFullName(sharedPreferences.getString(KEY_FULLNAME, null));
        user.setMobileNo(sharedPreferences.getString(KEY_MOBILE_NO, null));
        user.setVehicleNo(sharedPreferences.getString(KEY_VEHICLE_NO, null));

        return user;
    }

    public void logout(){
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
    }


}
