package www.fiberathome.com.parkingapp.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import www.fiberathome.com.parkingapp.HomeActivity;
import www.fiberathome.com.parkingapp.R;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private Button signinBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        signinBtn = findViewById(R.id.signin_btn);



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
                Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                startActivity(intent);
                finish();
                break;


        }
    }
}
