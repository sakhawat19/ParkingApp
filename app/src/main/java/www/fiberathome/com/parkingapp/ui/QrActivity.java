package www.fiberathome.com.parkingapp.ui;

import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import www.fiberathome.com.parkingapp.R;
import www.fiberathome.com.parkingapp.ui.SignupActivity;
import www.fiberathome.com.parkingapp.ui.StartActivity;

public class QrActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView sampleQrIv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qr);

        sampleQrIv = findViewById(R.id.sample_qr_iv);


        //show QR on create
        String text="uid:100587, ";
        text = text+"cid:DHK MA 51-5953";
        MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
        try {
            BitMatrix bitMatrix = multiFormatWriter.encode(text, BarcodeFormat.QR_CODE,200,200);
            BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
            Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);
            sampleQrIv.setImageBitmap(bitmap);
        } catch (WriterException e) {
            e.printStackTrace();
        }


    }


    @Override
    protected void onStart() {
        super.onStart();


    }

    @Override
    protected void onResume() {
        super.onResume();
    }


    @Override
    public void onClick(View view) {



    }
}
