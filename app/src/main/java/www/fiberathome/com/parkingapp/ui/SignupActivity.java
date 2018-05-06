package www.fiberathome.com.parkingapp.ui;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import www.fiberathome.com.parkingapp.HomeActivity;
import www.fiberathome.com.parkingapp.Manifest;
import www.fiberathome.com.parkingapp.R;
import www.fiberathome.com.parkingapp.model.User;
import www.fiberathome.com.parkingapp.utils.AppConfig;
import www.fiberathome.com.parkingapp.utils.AppController;
import www.fiberathome.com.parkingapp.utils.ConstructJSON;
import www.fiberathome.com.parkingapp.utils.HttpsTrustManager;
import www.fiberathome.com.parkingapp.utils.SharedPreManager;

import static android.Manifest.permission.CAMERA;

public class SignupActivity extends AppCompatActivity implements View.OnClickListener {


    public static final String TAG = SignupActivity.class.getSimpleName();

    private Button signupBtn;
    private ImageView upload_profile_image;
    private EditText fullnameET;
    private EditText mobileET;
    private EditText vehicleET;
    private EditText passwordET;
    private TextView link_login;


    // image permission
    private static final int IMAGE_PERMISSION = 4;
    private static final int IMAGE_CAPTURE_REQUEST = 1001;
    private String currentPhotoPath;



    private ProgressDialog progressDialog;


    private TextInputLayout inputLayoutFullName;
    private TextInputLayout inputLayoutMobile;
    private TextInputLayout inputLayoutVehicle;
    private TextInputLayout inputLayoutPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        // Check user is logged in
        if (SharedPreManager.getInstance(getApplicationContext()).isLoggedIn()){
            Intent intent = new Intent(SignupActivity.this, HomeActivity.class);
            startActivity(intent);
            finish();
        }



        // Initialize Components
        initializeComponent();


    }

    private void initializeComponent() {
        signupBtn = findViewById(R.id.signup_final_btn);
        fullnameET = findViewById(R.id.input_fullname);
        mobileET = findViewById(R.id.input_mobile_number);
        vehicleET = findViewById(R.id.input_vehicle_no);
        passwordET = findViewById(R.id.input_password);
        link_login = findViewById(R.id.link_login);
        upload_profile_image = findViewById(R.id.upload_profile_image);

        inputLayoutFullName = findViewById(R.id.layout_fullname);
        inputLayoutMobile = findViewById(R.id.layout_mobile_number);
        inputLayoutVehicle = findViewById(R.id.layout_vehicle_no);
        inputLayoutPassword = findViewById(R.id.layout_password);



    }


    @Override
    protected void onStart() {
        super.onStart();

        signupBtn.setOnClickListener(this);
        link_login.setOnClickListener(this);
        upload_profile_image.setOnClickListener(this);

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

            case R.id.link_login:
                Intent intent = new Intent(SignupActivity.this, LoginActivity.class);
                startActivity(intent);
                break;

            case R.id.upload_profile_image:
                showMessage("CAMERA!");
                startCameraActivityIntent();
                break;

        }

    }

    private void startCameraActivityIntent() {
        // Require Camera permission
        String[] permissions = {
                "android.permission.CAMERA",
        };

        // Intent to start Camera
        Intent startCamera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, permissions, IMAGE_CAPTURE_REQUEST);

        }else{
            if (startCamera.resolveActivity(getPackageManager()) != null){
                File photoFile = createImageFile();

                if (photoFile != null){
                    Uri photoURL = FileProvider.getUriForFile(this, "www.fiberathome.com.parkingapp.fileprovider", photoFile);
                    // For non bitmap full sized images use EXTRA_OUTPUT during INTENT
                    startCamera.putExtra(MediaStore.EXTRA_OUTPUT, photoURL);
                    startActivityForResult(startCamera, IMAGE_CAPTURE_REQUEST);
                }
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == IMAGE_CAPTURE_REQUEST && resultCode == RESULT_OK){


        }
    }


    private File createImageFile() {
        // create image filename
        String imageFileName = "JPEG_00";

        // ACCESS storage directory for photos and create temporary photo
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = null;

        try{
            image = File.createTempFile(imageFileName, ".jpg", storageDir);
        } catch (IOException e) {
            e.printStackTrace();
        }


        assert image != null;
        currentPhotoPath = image.getAbsolutePath();
        Log.e("Photo Path", currentPhotoPath);
        return image;

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
                    }else{
                        showMessage(jsonObject.getString("message"));
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
