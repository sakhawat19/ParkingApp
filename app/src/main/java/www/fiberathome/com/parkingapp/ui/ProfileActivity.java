package www.fiberathome.com.parkingapp.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import www.fiberathome.com.parkingapp.R;
import www.fiberathome.com.parkingapp.model.User;
import www.fiberathome.com.parkingapp.utils.SharedPreManager;

public class ProfileActivity extends AppCompatActivity {

    private TextView fullname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        // intialize
        fullname = findViewById(R.id.user_fullName);

        User user = SharedPreManager.getInstance(this).getUser();
        fullname.setText(user.getFullName());

    }
}
