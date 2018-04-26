package www.fiberathome.com.parkingapp.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import www.fiberathome.com.parkingapp.R;
import www.fiberathome.com.parkingapp.model.User;
import www.fiberathome.com.parkingapp.utils.SharedPreManager;

public class ProfileActivity extends AppCompatActivity {

    private TextView fullnameTV;
    private TextView mobileNoTV;
    private TextView vehicleNoTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        // intialize
        fullnameTV = findViewById(R.id.user_fullName);
        mobileNoTV = findViewById(R.id.user_mobile_no);
        vehicleNoTV = findViewById(R.id.user_vehicle_no);

        User user = SharedPreManager.getInstance(this).getUser();
        fullnameTV.setText(user.getFullName());
        mobileNoTV.setText("+88" + user.getMobileNo());
        vehicleNoTV.setText(user.getVehicleNo());

    }
}
