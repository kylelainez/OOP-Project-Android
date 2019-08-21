package com.kylelainez.oop_project_v1;

import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AppCompatActivity;
import android.Manifest;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;


public class MapActivity extends AppCompatActivity implements OnMapReadyCallback {

    private static final String TAG = "MapActivity";
    private static final String FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;
    private static final String COURSE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;
    private static final int Request_Code = 1234;
    private static final float DefaultZoom = 20f;
    private Boolean LocationPermissionGranted = false;
    private GoogleMap mMap;
    private FusedLocationProviderClient fusedLocationProviderClient;
    private ImageButton locationButton;
    private Button restaurants;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {                                   //Opens the Map Layout Activity
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate: Opening the map");
        setContentView(R.layout.activity_map);

        locationButton = findViewById(R.id.location);
        locationButton.setOnClickListener(new View.OnClickListener() {                               //Location Button
            @Override
            public void onClick(View view) {
                getDeviceLocation();
            }
        });
        restaurants = findViewById(R.id.restaurants);
        restaurants.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showRestaurants();
            }
        });

        getLocationPermission();
    }
    public void showRestaurants(){
        MapLocations getLocations = new MapLocations("restaurants",mMap);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {                                                    // Starts the Google Maps API
        Toast.makeText(MapActivity.this, "Map is Ready", Toast.LENGTH_LONG).show();
        mMap = googleMap;
        googleMap.setMapStyle(
                MapStyleOptions.loadRawResourceStyle(
                        this, R.raw.style_json));
        Log.d(TAG, "onMapReady: Map is ready");

        if (LocationPermissionGranted) {
            moveCameraLocation(new LatLng(14.598815, 121.005397),15f);
            if (ActivityCompat.checkSelfPermission(this,                                      //Checks Permission for Location
                    Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                    && ActivityCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            mMap.setMyLocationEnabled(true);
            mMap.getUiSettings().setMyLocationButtonEnabled(false);

        }
    }

    public void getDeviceLocation(){                                                                 //User Location
        Log.d(TAG, "getDeviceLocation: Getting the devices current location");
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        try{
            if (LocationPermissionGranted){
                Task location = fusedLocationProviderClient.getLastLocation();
                location.addOnCompleteListener(new OnCompleteListener() {
                    @Override
                    public void onComplete(@NonNull Task task) {                                     //Gets the Current User Location
                        if (task.isSuccessful()) {
                            Log.d(TAG, "onComplete: Location found");
                            Location currentLocation = (Location) task.getResult();

                            moveCameraLocation(new LatLng(currentLocation.getLatitude(),             //Moves Camera to Current Location
                                    currentLocation.getLongitude()),DefaultZoom);
                        }else {
                            Log.d(TAG, "onComplete: Current Location could not be detected");
                            Toast.makeText(MapActivity.this, "Unable to reach the current" +
                                    " location of the device",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        }catch (SecurityException e){
            Log.e(TAG, "getDeviceLocation: Security Exception" + e.getMessage());
        }
    }

    private void moveCameraLocation(LatLng latLng, float zoom){                                      //Moves Camera to target
        Log.d(TAG, "moveCameraLocation: Moving Camera to location");
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoom));
    }

    private void getLocationPermission(){                                                            //Gets Permissions for
        Log.d(TAG, "getLocationPermission: Entered Permission Request");                        // Maps Usage
        String[] permissions = {Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION};

        if (ContextCompat.checkSelfPermission(this.getApplicationContext(),
                FINE_LOCATION)==PackageManager.PERMISSION_GRANTED){
            if (ContextCompat.checkSelfPermission(this.getApplicationContext(),
                    COURSE_LOCATION)==PackageManager.PERMISSION_GRANTED){
                LocationPermissionGranted = true;
                Log.d(TAG, "getLocationPermission: Permission is granted");
                initMap();
            }else{
                Log.d(TAG, "getLocationPermission: App Course Permission is declined");
                ActivityCompat.requestPermissions(this,permissions,Request_Code);
            }
        }else{
            Log.d(TAG, "getLocationPermission: App Fine Permission is declined");
            ActivityCompat.requestPermissions(this,permissions,Request_Code);
        }
    }

    private void initMap(){                                                                          //Initializes the map
        Log.d(TAG, "initMap: Initializing the map...");
        final SupportMapFragment mapFragment = (SupportMapFragment)
                getSupportFragmentManager().findFragmentById(R.id.map);

        mapFragment.getMapAsync(MapActivity.this);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,           //Checks if all Permissions are
                                           @NonNull int[] grantResults) {                            //Granted
        LocationPermissionGranted = false;

        switch (requestCode){
            case Request_Code:{
                if (grantResults.length>0){
                    for(int i=0; i<grantResults.length;i++){
                        if(grantResults[i] != PackageManager.PERMISSION_GRANTED){
                            LocationPermissionGranted = false;
                            Log.d(TAG, "onRequestPermissionsResult: Permission Failed");
                            return;
                        }
                    }
                    Log.d(TAG, "onRequestPermissionsResult: Permission Granted");
                    LocationPermissionGranted = true;

                }
            }
        }
    }


}
