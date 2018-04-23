package www.fiberathome.com.parkingapp.ui;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import android.provider.MediaStore;

import www.fiberathome.com.parkingapp.HomeActivity;
import www.fiberathome.com.parkingapp.R;

public class SignupActivity extends AppCompatActivity implements View.OnClickListener {

    private Button signupBtn;
    private Button signupImageBtn;
    private ImageView signupIv;

    private static final int CAM_REQUEST =1313;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);


        signupBtn = findViewById(R.id.signup_final_btn);
        signupImageBtn = findViewById(R.id.signup_image_btn);

        signupIv = findViewById(R.id.car_photo_IV);


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
    }

    @Override
    protected void onResume() {
        super.onResume();
    }


    @Override
    public void onClick(View view) {

        switch (view.getId()){
            case R.id.signup_final_btn:
                Intent intent = new Intent(SignupActivity.this, StartActivity.class);
                startActivity(intent);

                // Show Logged In Message
                Toast.makeText(SignupActivity.this,
                        "Signed Up Successfully!", Toast.LENGTH_SHORT).show();

                finish();
                break;
        }

    }
}
