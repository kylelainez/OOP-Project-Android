package com.kylelainez.oop_project_v1;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;


import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;


public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private static final int ERROR_Dialog_Request = 9001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {                                             //On Application Start-up
        setTheme(R.style.AppTheme_NoActionBar);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (isServiceOK()) {
            init();
        }

        BottomNavigationView bottomNavigationView = findViewById(R.id.nav_view);
        bottomNavigationView.setOnNavigationItemSelectedListener(navigationItemSelectedListener);
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navigationItemSelectedListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Fragment fragment = null;
                    switch (item.getItemId()){
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

    public void init() {                                                                             //Initializes the button for opening map
        Button MapBtn = findViewById(R.id.MapBtn);
        MapBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MapActivity.class);
                startActivity(intent);
            }
        });
    }

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
}
