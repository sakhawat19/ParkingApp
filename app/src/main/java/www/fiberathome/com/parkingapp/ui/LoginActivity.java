package www.fiberathome.com.parkingapp.ui;

import android.app.ProgressDialog;
import android.content.Intent;
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
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import www.fiberathome.com.parkingapp.HomeActivity;
import www.fiberathome.com.parkingapp.R;
import www.fiberathome.com.parkingapp.model.User;
import www.fiberathome.com.parkingapp.utils.AppConfig;
import www.fiberathome.com.parkingapp.utils.AppController;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private static String TAG = LoginActivity.class.getSimpleName();

    private Button signinBtn;
    private EditText mobileNumberET;
    private EditText passwordET;
    private TextInputLayout inputLayoutMobile;
    private TextInputLayout inputLayoutPassword;

    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // initialize component
        signinBtn = findViewById(R.id.signin_btn);
        mobileNumberET = findViewById(R.id.input_mobile_number);
        passwordET = findViewById(R.id.input_password);

        inputLayoutMobile = findViewById(R.id.input_layout_mobile_number);
        inputLayoutPassword = findViewById(R.id.input_layout_password);

        mobileNumberET.addTextChangedListener(new MyTextWatcher(inputLayoutMobile));
        passwordET.addTextChangedListener(new MyTextWatcher(inputLayoutPassword));

    }


    @Override
    protected void onStart() {
        super.onStart();

        signinBtn.setOnClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.signin_btn:
                submitLogin();
                break;


        }
    }

    /**
     * @desc submit Form
     */
    private void submitLogin(){
        // Loading Progress
        if (!validateMobileNumber()){
            return;
        }

        if (!validatePassword()){
            return;
        }

        String mobileNo = mobileNumberET.getText().toString().trim();
        String password = passwordET.getText().toString().trim();

        checkLogin(mobileNo,password);
    }

    private void checkLogin(final String mobileNo, final String password) {

        progressDialog = new ProgressDialog(LoginActivity.this);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        progressDialog.setIndeterminate(false);

        // inactive button
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                AppConfig.URL_LOGIN, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                // remove the progress bar
                progressDialog.dismiss();

                try {
                    JSONObject jsonObject = new JSONObject(response);

                    Log.e("Object", jsonObject.toString());

                    if (!jsonObject.getBoolean("error")){

                        // getting the user from the response
                        JSONObject userJson = jsonObject.getJSONObject("user");

                        // creating a new user object
                        User user = new User();
                        user.setId(userJson.getInt("id"));
                        user.setFullName(userJson.getString("fullname"));
                        user.setMobileNo(userJson.getString("mobile_no"));
                        user.setVehicleNo(userJson.getString("vehicle_no"));

                        // storing the user in sharedPreference


                        // Move to another Activity
                        Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
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
                Log.e("Volley Error", error.getMessage());
            }
        }){

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("mobile_no", mobileNo);
                params.put("password", password);
                return params;
            }
        };


        AppController.getInstance().addToRequestQueue(stringRequest, TAG);

    }


    /**
     * @desc validate username
     * @return boolean
     */
    private boolean validateMobileNumber() {
        String mobileNumber = mobileNumberET.getText().toString().trim();

        if (mobileNumber.isEmpty()) {
            inputLayoutMobile.setError(getString(R.string.err_msg_mobile));
            requestFocus(mobileNumberET);
            return false;
        } else {
            inputLayoutMobile.setErrorEnabled(false);
        }

        return true;
    }

    /**
     * @desc validate Password
     * @return
     */
    private boolean validatePassword(){
        String password = passwordET.getText().toString().trim();
        if (password.isEmpty()){
            inputLayoutPassword.setError(getResources().getString(R.string.err_msg_password));
            requestFocus(passwordET);
            return false;

        }else{
            inputLayoutPassword.setErrorEnabled(false);
        }

        return true;
    }


    /**
     * request focus
     * =============================================
     * @param view
     */
    private void requestFocus(View view) {
        if (view.requestFocus()){
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }


    private void showMessage(String message){
        Toast.makeText(LoginActivity.this, message, Toast.LENGTH_LONG).show();
    }


    /**
     * Check user input
     */
    private class MyTextWatcher implements TextWatcher {

        private View view;
        private MyTextWatcher(View view) {
            this.view = view;
        }

        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void afterTextChanged(Editable editable) {
            switch (view.getId()) {
                case R.id.input_mobile_number:
                    validateMobileNumber();
                    break;

                case R.id.input_password:
                    validatePassword();
                    break;
            }
        }

    }
}
