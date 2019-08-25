package com.kylelainez.oop_project_v1;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;


import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;


public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private static final int ERROR_Dialog_Request = 9001;

    private static final String FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;
    private static final String COURSE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;
    private static final int Request_Code = 1234;
    public Boolean LocationPermissionGranted = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {                                             //On Application Start-up
        setTheme(R.style.AppTheme_NoActionBar);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView bottomNavigationView = findViewById(R.id.nav_view);
        bottomNavigationView.setOnNavigationItemSelectedListener(navigationItemSelectedListener);
        if (savedInstanceState == null)
            bottomNavigationView.setSelectedItemId(R.id.home_navigation);
        isServiceOK();
        getLocationPermission();
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navigationItemSelectedListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Fragment fragment = null;
                    switch (item.getItemId()) {
                        case R.id.home_navigation:
                            fragment = new HomeFragment();
                            break;
                        case R.id.profile_navigation:
                            fragment = new ProfileFragment();
                            break;
                        case R.id.maps_navigation:
                            fragment = new MapFragment();
                            break;
                    }
                    getSupportFragmentManager().beginTransaction().replace(R.id.frame_container,
                            fragment).commit();
                    return true;
                }
            };


    public boolean isServiceOK() {                                                                   //Checks whether all Services
        Log.d(TAG, "isServiceOK: Checking Google Services Version");                            //Are up to date and working

        int available = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(
                MainActivity.this);
        if (available == ConnectionResult.SUCCESS) {
            Log.d(TAG, "isServiceOK: Google Play Services is working");
            return true;
        } else if (GoogleApiAvailability.getInstance().isUserResolvableError(available)) {
            Log.d(TAG, "isServiceOK: Error occured but resolvable");
            Dialog dialog = GoogleApiAvailability.getInstance().getErrorDialog
                    (MainActivity.this, available, ERROR_Dialog_Request);
            dialog.show();
        } else {
            Toast.makeText(this, "You can't make map request", Toast.LENGTH_SHORT).show();
        }
        return false;
    }

    public void getLocationPermission() {                                                            //Gets Permissions for
        Log.d(TAG, "getLocationPermission: Entered Permission Request");                        // Maps Usage
        String[] permissions = {Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION};

        if (ContextCompat.checkSelfPermission(this.getApplicationContext(),
                FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            if (ContextCompat.checkSelfPermission(this.getApplicationContext(),
                    COURSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                LocationPermissionGranted = true;
                Log.d(TAG, "getLocationPermission: Permission is granted");
            } else {
                Log.d(TAG, "getLocationPermission: App Course Permission is declined");
                ActivityCompat.requestPermissions(this, permissions, Request_Code);
            }
        } else {
            Log.d(TAG, "getLocationPermission: App Fine Permission is declined");
            ActivityCompat.requestPermissions(this, permissions, Request_Code);
        }
    }

}
