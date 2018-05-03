package www.fiberathome.com.parkingapp;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import www.fiberathome.com.parkingapp.GoogleMapWebService.GooglePlaceSearchNearbySearchListener;
import www.fiberathome.com.parkingapp.gps.GPSTracker;
import www.fiberathome.com.parkingapp.gps.GPSTrackerListener;
import www.fiberathome.com.parkingapp.model.GlobalVars;
import www.fiberathome.com.parkingapp.model.MyLocation;
import www.fiberathome.com.parkingapp.model.SensorList;
import www.fiberathome.com.parkingapp.module.PlayerPrefs;
import www.fiberathome.com.parkingapp.ui.LoginActivity;
import www.fiberathome.com.parkingapp.ui.MapActivity;
import www.fiberathome.com.parkingapp.ui.ProfileActivity;
import www.fiberathome.com.parkingapp.ui.QrActivity;
import www.fiberathome.com.parkingapp.utils.BaseActivity;
import www.fiberathome.com.parkingapp.utils.SharedPreManager;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        OnMapReadyCallback,
        GooglePlaceSearchNearbySearchListener,
        GoogleMap.OnMarkerClickListener,
        GPSTrackerListener,
        GoogleMap.OnInfoWindowClickListener,
        View.OnClickListener{


    private static final String TAG = HomeActivity.class.getSimpleName();

    // google map objects
    private static final int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
    private GoogleMap googleMap;
    private Marker placeMarker;
    private List<Marker> markers = new ArrayList<>();
    private Marker userLocationMarker;
    private GPSTracker gpsTracker;


    private ProgressDialog progressDialog;
    private Button nearest;

    private boolean isGoogleDone = false;
    private boolean isMyServerDone = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);


        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Check user is logged in
        if (!SharedPreManager.getInstance(getApplicationContext()).isLoggedIn()) {
            Intent intent = new Intent(HomeActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        }


        /*if (checkPlayService()){
            // Service Check
            Intent intent = new Intent(HomeActivity.this, LoginActivity.class);
            startActivity(intent);
        }*/


        initialize();

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);


        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();


        navigationView.setNavigationItemSelectedListener(this);

    }

    private void initialize() {

        PlayerPrefs.Initialize(getApplicationContext());
        initMap();
        initComponents();
        initGPS();

    }

    private void initGPS() {
        gpsTracker = new GPSTracker(this, this);
        if (gpsTracker.canGetLocation()) {
            double latitude = gpsTracker.getLatitude();
            double longitude = gpsTracker.getLongitude();

            if (latitude != 0.0 || longitude != 0.0) {
                GlobalVars.location = new MyLocation(latitude, longitude);
            }
        } else {
            gpsTracker.showSettingsAlert();
        }

    }

    private void initComponents() {
        nearest = findViewById(R.id.nearest);
        nearest.setOnClickListener(this);
    }


    private boolean checkPlayService() {
        GoogleApiAvailability apiAvailability = GoogleApiAvailability.getInstance();
        int resultCode = apiAvailability.isGooglePlayServicesAvailable(this);
        if (resultCode != ConnectionResult.SUCCESS) {
            if (apiAvailability.isUserResolvableError(resultCode)) {
                apiAvailability.getErrorDialog(this, resultCode, PLAY_SERVICES_RESOLUTION_REQUEST).show();
            } else {
                Log.e(TAG, "This device is not supported");
                finish();
            }

            return false;
        }

        return true;
    }


    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onStop() {
        gpsTracker.stopUsingGPS();
        super.onStop();
    }

    @Override
    protected void onPause() {
        gpsTracker.stopUsingGPS();
        super.onPause();

    }

    private void showMessage(String message) {
        Toast.makeText(HomeActivity.this, message, Toast.LENGTH_LONG).show();
    }


    @Override
    protected void onStart() {
        super.onStart();
    }





    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
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
        if (id == R.id.action_setting) {
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
                SharedPreManager.getInstance(this).logout();
                Intent intentLogout = new Intent(HomeActivity.this, LoginActivity.class);
                startActivity(intentLogout);
                finish();
                //Do some thing here
                // add navigation drawer item onclick method here
                break;
        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    private void initMap() {
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;
        refreshUserGPSLocation();
        googleMap.setOnInfoWindowClickListener(this);


    }

    private void refreshUserGPSLocation() {
        if (userLocationMarker != null)
            userLocationMarker.remove();

        GlobalVars.IsFakeGPS = false;
        MyLocation userLocation = GlobalVars.getUserLocation();
        if (userLocation != null) {
            LatLng userLatLng = new LatLng(userLocation.latitude, userLocation.longitude);
            //userLocationMarker = googleMap.addMarker(new MarkerOptions().position(userLatLng).title("Your Location").icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_mylocation)));
            if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            googleMap.setMyLocationEnabled(true);
            googleMap.getUiSettings().setAllGesturesEnabled(true);
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(userLatLng, 15));
        }

    }


    public  void userLocationFAB(View view){
        FloatingActionButton FAB = findViewById(R.id.myLocationButton);
        FAB.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("MissingPermission")
            @Override
            public void onClick(View view) {
                if (gpsTracker.canGetLocation()) {
                    double latitude = gpsTracker.getLatitude();
                    double longitude = gpsTracker.getLongitude();

                    LatLng latLng = new LatLng(latitude, longitude);
                    CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, 15);
                    googleMap.animateCamera(cameraUpdate);

                } else {
                    gpsTracker.showSettingsAlert();
                }
            }
        });
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
    }


    @Override
    public void onInfoWindowClick(Marker marker) {

    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        return false;
    }



    @Override
    public void onGooglePlaceSearchStart() {

    }

    @Override
    public void onGooglePlaceSearchSuccess(SensorList sensorList) {

    }




    @Override
    public void onGPSTrackerLocationChanged(Location location) {
        double latitude = location.getLatitude();
        double longitude = location.getLongitude();

        showMessage(latitude + "---" + longitude);
        if (latitude != 0 || longitude != 0) {
            GlobalVars.location = new MyLocation(latitude, longitude);
            if (googleMap != null)
                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(latitude, longitude), 15));

            refreshUserGPSLocation();
        }

    }


    @Override
    public void onGPSTrackerStatusChanged(String provider, int status, Bundle extras) {
        showMessage("Status Changed");
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.nearest:
                // get the nearest sensor information
                nearest.setText("Reverse Spot");
                break;
        }

    }
}
