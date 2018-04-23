package www.fiberathome.com.parkingapp.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import www.fiberathome.com.parkingapp.R;

public class StartActivity extends AppCompatActivity implements View.OnClickListener {

    private Button loginBtn;
    private Button signupBtn;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

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
}
