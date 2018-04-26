package www.fiberathome.com.parkingapp.ui;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.nfc.Tag;
import android.os.AsyncTask;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import android.provider.MediaStore;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import www.fiberathome.com.parkingapp.R;
import www.fiberathome.com.parkingapp.model.User;
import www.fiberathome.com.parkingapp.utils.AppConfig;
import www.fiberathome.com.parkingapp.utils.AppController;
import www.fiberathome.com.parkingapp.utils.HttpsTrustManager;
import www.fiberathome.com.parkingapp.utils.RequestHandler;
import www.fiberathome.com.parkingapp.utils.SharedPreManager;

public class SignupActivity extends AppCompatActivity implements View.OnClickListener {


    public static final String TAG = SignupActivity.class.getSimpleName();

    private Button signupBtn;
    private Button signupImageBtn;
    private ImageView signupIv;
    private EditText fullnameET;
    private EditText mobileET;
    private EditText vehicleET;
    private EditText passwordET;

    private ProgressDialog progressDialog;


    private TextInputLayout inputLayoutFullName;
    private TextInputLayout inputLayoutMobile;
    private TextInputLayout inputLayoutVehicle;
    private TextInputLayout inputLayoutPassword;


    private static final int CAM_REQUEST = 1313;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);


        // Initialize Components
        initializeComponent();


    }

    private void initializeComponent() {
        signupBtn = findViewById(R.id.signup_final_btn);
        signupImageBtn = findViewById(R.id.signup_image_btn);
        signupIv = findViewById(R.id.car_photo_IV);
        fullnameET = findViewById(R.id.input_fullname);
        mobileET = findViewById(R.id.input_mobile_number);
        vehicleET = findViewById(R.id.input_vehicle_no);
        passwordET = findViewById(R.id.input_password);


        inputLayoutFullName = findViewById(R.id.layout_fullname);
        inputLayoutMobile = findViewById(R.id.layout_mobile_number);
        inputLayoutVehicle = findViewById(R.id.layout_vehicle_no);
        inputLayoutPassword = findViewById(R.id.layout_password);

    }

    // Taking Photo test
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == CAM_REQUEST){
            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
            signupIv.setImageBitmap(bitmap);
        }


    }




    class btnTakePhotoClicker implements  Button.OnClickListener{

        @Override
        public void onClick(View view) {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(intent,CAM_REQUEST);
        }
    }











    @Override
    protected void onStart() {
        super.onStart();

        signupBtn.setOnClickListener(this);
        signupImageBtn.setOnClickListener(new btnTakePhotoClicker());

        fullnameET.addTextChangedListener(new MyTextWatcher(fullnameET));
        mobileET.addTextChangedListener(new MyTextWatcher(mobileET));
        vehicleET.addTextChangedListener(new MyTextWatcher(vehicleET));
        passwordET.addTextChangedListener(new MyTextWatcher(passwordET));
    }

    @Override
    protected void onResume() {
        super.onResume();
    }


    @Override
    public void onClick(View view) {

        switch (view.getId()){
            case R.id.signup_final_btn:
                submitRegistration();
                break;
        }

    }

    private void submitRegistration() {
        // Loading Progress
        if (!validateEditText(fullnameET, inputLayoutFullName, R.string.err_msg_fullname)){
            return;
        }

        if (!validateEditText(mobileET, inputLayoutMobile, R.string.err_msg_mobile)){
            return;
        }

        if (!validateEditText(vehicleET, inputLayoutVehicle, R.string.err_msg_vehicle)){
            return;
        }

        if (!validateEditText(passwordET, inputLayoutPassword, R.string.err_msg_password)){
            return;
        }


        String fullname = fullnameET.getText().toString().trim();
        String mobileNo = mobileET.getText().toString().trim();
        String vehicleNo = vehicleET.getText().toString().trim();
        String password = passwordET.getText().toString().trim();

        //showMessage(mobileNo + " " + vehicleNo + " " + password);
        registerUser(fullname, mobileNo, vehicleNo, password);

    }

    private void registerUser(final String fullname, final String mobileNo, final String vehicleNo, final String password) {
        progressDialog = new ProgressDialog(SignupActivity.this);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        progressDialog.setIndeterminate(false);
        progressDialog.show();


        HttpsTrustManager.allowAllSSL();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, AppConfig.URL_REGISTER, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {

                progressDialog.dismiss();

                try{
                    //converting response to json object
                    JSONObject jsonObject = new JSONObject(response);
                    Log.e("Object", jsonObject.toString());

                    // if no error response
                    if (!jsonObject.getBoolean("error")){
                        showMessage(jsonObject.getString("message"));

                        // getting user object
                        JSONObject userJson = jsonObject.getJSONObject("user");

                        // creating new User Object
                        User user = new User();
                        user.setFullName(userJson.getString("fullname"));
                        user.setFullName(userJson.getString("mobile_no"));
                        user.setFullName(userJson.getString("vehicle_no"));

                        // Store to share preference
                        SharedPreManager.getInstance(getApplicationContext()).userLogin(user);

                        // Move to another activity
                        Intent intent = new Intent(SignupActivity.this, LoginActivity.class);
                        startActivity(intent);
                        finish();
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                showMessage(error.getMessage());
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("fullname", fullname);
                params.put("password", password);
                params.put("mobile_no", mobileNo);
                params.put("vehicle_no", vehicleNo);

                return params;
            }
        };

        AppController.getInstance().addToRequestQueue(stringRequest, TAG);

    }

    private void showMessage(String message) {
        Toast.makeText(SignupActivity.this, message, Toast.LENGTH_LONG).show();
    }


    private class MyTextWatcher implements TextWatcher{

        private View view;
        private MyTextWatcher (View view){
            this.view = view;
        }

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void afterTextChanged(Editable editable) {
            switch (view.getId()){
                case R.id.input_mobile_number:
                    validateEditText(fullnameET, inputLayoutFullName, R.string.err_msg_fullname);
                    break;
            }
        }
    }


    private boolean validateEditText(EditText editText, TextInputLayout inputLayout, int errorMessage){
        String value = editText.getText().toString().trim();
        if (value.isEmpty()){
            inputLayout.setError(getResources().getString(errorMessage));
            requestFocus(editText);
            return false;
        }else{
            inputLayout.setErrorEnabled(false);
        }

        return true;
    }

    private void requestFocus(EditText view) {
        if (view.requestFocus()){
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }
}
