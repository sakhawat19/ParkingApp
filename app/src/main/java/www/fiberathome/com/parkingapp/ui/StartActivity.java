package www.fiberathome.com.parkingapp.ui;

import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.iotsens.sdk.IoTSensApiClient;
import com.iotsens.sdk.IoTSensApiClientBuilder;
import com.iotsens.sdk.sensors.SensorBasic;
import com.iotsens.sdk.sensors.SensorsRequest;
import com.iotsens.sdk.sensors.SensorsRequestBuilder;

import www.fiberathome.com.parkingapp.HomeActivity;
import www.fiberathome.com.parkingapp.R;
import www.fiberathome.com.parkingapp.utils.BaseActivity;
import www.fiberathome.com.parkingapp.utils.ConnectivityReceiver;
import www.fiberathome.com.parkingapp.utils.SharedPreManager;

public class StartActivity extends BaseActivity
        implements View.OnClickListener{

    private Button loginBtn;
    private Button signupBtn;





    public static final String APPLICATION_ID = "FIBERATHOMEAPP"; // must be proper application identifier
    public static final String SECRET = "6b42713e7fcb0f08b9e01298eaad5805"; // must be proper secret
    public static final String DEFAULT_USER = "atif.hafizuddin"; // must be a proper user





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        IoTSensApiClient apiClient = IoTSensApiClientBuilder.aIoTSensClient()
                .withApplication(APPLICATION_ID)
                .withSecret(SECRET)
                .withDefaultUser(DEFAULT_USER)
                .build();

        SensorsRequest sensorsRequest = SensorsRequestBuilder.aSensorRequest()
                .build();

//        for (SensorBasic sensorBasic :  apiClient.getSensors(sensorsRequest)) {
//            System.out.println("Sensor = " + sensorBasic.toString());
//        }

        // Check user is logged in
        if (SharedPreManager.getInstance(getApplicationContext()).isLoggedIn()){
            Intent intent = new Intent(StartActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        }


        loginBtn = findViewById(R.id.login_btn);
        signupBtn = findViewById(R.id.signup_btn);

    }

    @Override
    protected void onStart() {
        super.onStart();

        loginBtn.setOnClickListener(this);
        signupBtn.setOnClickListener(this);

    }


    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.login_btn:
            Intent intent = new Intent(StartActivity.this, LoginActivity.class);
            startActivity(intent);
            break;
        }
        switch (view.getId()){
            case R.id.signup_btn:
                Intent intent = new Intent(StartActivity.this, SignupActivity.class);
                startActivity(intent);
                break;
        }
    }



    private void showMessage(String message) {
        Toast.makeText(StartActivity.this, message, Toast.LENGTH_LONG).show();
    }



}
