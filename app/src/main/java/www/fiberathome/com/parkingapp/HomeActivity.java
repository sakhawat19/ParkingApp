package www.fiberathome.com.parkingapp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.text.SpannableString;
import android.text.style.TextAppearanceSpan;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import www.fiberathome.com.parkingapp.ui.MapActivity;
import www.fiberathome.com.parkingapp.ui.ProfileActivity;
import www.fiberathome.com.parkingapp.ui.QrActivity;
import www.fiberathome.com.parkingapp.ui.SignupActivity;
import www.fiberathome.com.parkingapp.ui.StartActivity;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private ImageView sampleQrIv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);


        Menu menu = navigationView.getMenu();
        MenuItem tools= menu.findItem(R.id.nav_name_communication);
        SpannableString s = new SpannableString(tools.getTitle());
        s.setSpan(new TextAppearanceSpan(this, R.style.TextAppearance44), 0, s.length(), 0);
        tools.setTitle(s);



        navigationView.setNavigationItemSelectedListener(this);





        // Show User QR
//        sampleQrIv = (ImageView)findViewById(R.id.drower_qr_iv);
//
//        String text="uid:100587, ";
//        text = text+"cid:DHK MA 51-5953";
//        MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
//        try {
//            BitMatrix bitMatrix = multiFormatWriter.encode(text, BarcodeFormat.QR_CODE,200,200);
//            BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
//            Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);
//            sampleQrIv.setImageBitmap(bitmap);
//        } catch (WriterException e) {
//            e.printStackTrace();
//        }





    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        //Log.d("myTag", "Caught Id: "+id);

        switch (id) {
            case R.id.nav_user_account:
                //Do some thing here
                // add navigation drawer item onclick method here
                Intent profile_intent = new Intent(HomeActivity.this, ProfileActivity.class);
                startActivity(profile_intent);
                break;
            case R.id.nav_barcode_scan:
                Intent intent = new Intent(HomeActivity.this, QrActivity.class);
                startActivity(intent);


            // Show Logged In Message
//            Toast.makeText(HomeActivity.this,
//                    "Qr item Clicked!", Toast.LENGTH_SHORT).show();
                break;
            case R.id.nav_settings_custom:
                //Do some thing here
                // add navigation drawer item onclick method here
                break;

            case R.id.nav_map:
                //Do some thing here
                // add navigation drawer item onclick method here
                Intent mapintent = new Intent(HomeActivity.this, MapActivity.class);
                startActivity(mapintent);
                break;

            case R.id.nav_logout:
                //Do some thing here
                // add navigation drawer item onclick method here
                break;
        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
