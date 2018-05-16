package www.fiberathome.com.parkingapp.ui.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import www.fiberathome.com.parkingapp.R;
import www.fiberathome.com.parkingapp.ui.LoginActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class ChangePasswordFragment extends Fragment implements View.OnClickListener {

    private TextInputLayout inputLayoutOldPassword;
    private TextInputLayout inputLayoutNewPassword;
    private TextInputLayout inputLayoutConfirmPassword;

    private EditText oldPasswordET;
    private EditText newPasswordET;
    private EditText confirmPasswordET;

    private Button changePasswordBtn;




    public ChangePasswordFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_change_password, container, false);
        getActivity().setTitle(R.string.title_change_password);


        initialize(view);


        return view;
    }

    private void initialize(View view) {
        oldPasswordET = view.findViewById(R.id.input_old_password);
        newPasswordET = view.findViewById(R.id.input_new_password);
        confirmPasswordET = view.findViewById(R.id.input_confirm_password);
        changePasswordBtn = view.findViewById(R.id.change_password_btn);

        inputLayoutOldPassword = view.findViewById(R.id.input_layout_old_password);
        inputLayoutNewPassword = view.findViewById(R.id.input_layout_new_password);
        inputLayoutConfirmPassword = view.findViewById(R.id.input_layout_confirm_password);

        changePasswordBtn.setOnClickListener(this);

        oldPasswordET.addTextChangedListener(new MyTextWatcher(oldPasswordET));
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.change_password_btn:
                changePassword();
                break;
        }
    }

    private void changePassword() {
        // CHECK OLD PASSWORD
        if (!validateEditText(oldPasswordET, inputLayoutOldPassword, R.string.err_old_password)){
            return;
        }

        // CHECK NEW PASSWORD
        if (!validateEditText(newPasswordET, inputLayoutNewPassword, R.string.err_new_password)){
            return;
        }

        // CHECK CONFIRM PASSWORD
        if (!validateEditText(confirmPasswordET, inputLayoutOldPassword, R.string.err_confirm_password)){
            return;
        }

        // CHECK OLD PASSWORD MATCHED
        showMessage("Validation Completed");


        /**
         * TODO : CHECK WEATHER PASSWORD MATCH WITH SERVER PASSWORD
         * 1. if matched, then check the new password and confirmation password
         * 2. if not matched, then show error the status
         * 3. check number of times user tried to change password.
         * ================================================================================
         */
        if (validateEditText(oldPasswordET, inputLayoutOldPassword, R.string.err_old_password)){
            // IF PASSWORD IS VALID, MOVE TO SERVER WITH user request.
            showMessage("VALIDATE PASSWORD!");

            // COLLECT OLD PASSWORD | NEW PASSWORD | CONFIRMATION PASSWORD
            String oldPassword = oldPasswordET.getText().toString().trim();
            String newPassword = newPasswordET.getText().toString().trim();
            String confirmPassword = confirmPasswordET.getText().toString().trim();

            // TODO : CHECK OLD PASSWORD AND NEW PASSWORD SAME
            // TODO : CHECK PASSWORD CONTAINS ANY SPECIAL CHARACTER
            checkPasswordFromServer(oldPassword, newPassword, confirmPassword);

        }

    }

    /**
     * Change old password and update with new password with confirmation.
     * ================================================================================
     * @param oldPassword
     * @param newPassword
     * @param confirmPassword
     */
    private void checkPasswordFromServer(String oldPassword, String newPassword, String confirmPassword) {
        // TODO : HAVE TO CHANGE OLD PASSWORD AND NEW PASSWORD
    }

    private void showMessage(String message){
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }


    private class MyTextWatcher implements TextWatcher{

        private View view;

        public MyTextWatcher(View view){
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
                case R.id.input_old_password:
                    validateEditText(oldPasswordET, inputLayoutOldPassword, R.string.err_old_password);
                    break;

                case R.id.input_new_password:
                    validateEditText(newPasswordET, inputLayoutNewPassword, R.string.err_new_password);
                    break;

                case R.id.input_confirm_password:
                    validateEditText(confirmPasswordET, inputLayoutConfirmPassword, R.string.err_confirm_password);
                    break;

            }
        }
    }

    private boolean validateEditText(EditText editText, TextInputLayout textInputLayout, int errorResource) {
        String value = editText.getText().toString().trim();
        if (value.isEmpty()){
            textInputLayout.setError(getResources().getString(errorResource));
            requestFocus(editText);
            return false;
        }else{
            textInputLayout.setErrorEnabled(false);
        }

        return true;
    }

    private void requestFocus(EditText view) {
        if (view.requestFocus()){
            getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }
}
