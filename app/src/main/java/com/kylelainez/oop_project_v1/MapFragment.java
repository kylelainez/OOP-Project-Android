package com.kylelainez.oop_project_v1;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.location.FusedLocationProviderClient;

import java.util.Objects;

public class MapFragment extends Fragment implements OnMapReadyCallback {
    private static final String TAG = "MapFragment";
    private GoogleMap map;
    private MapView mapView;
    private FusedLocationProviderClient fusedLocationProviderClient;
    private MainActivity mainActivity;
    private static final float DefaultZoom = 20f;
    private Button restaurants, laundry, internetCafe, convenienceStore;
    private ImageButton location;
    private Boolean restaurantState = false;
    private Boolean laundryState = false;
    private Boolean convenienceStoresState = false;
    private Boolean internetCafeState = false;


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mapView = view.findViewById(R.id.map);
        mapView.onCreate(savedInstanceState);
        mapView.onResume();
        mapView.getMapAsync(this);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.map_fragment, container, false);
        mainActivity = new MainActivity();

        restaurants = view.findViewById(R.id.restaurants);
        laundry = view.findViewById(R.id.laundry);
        internetCafe = view.findViewById(R.id.internetCafe);
        convenienceStore = view.findViewById(R.id.convenienceStore);
        location = view.findViewById(R.id.location);

        restaurants.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (restaurantState == false) {
                    MapLocations getLocations = new MapLocations("restaurants", map, false); //Generates Markers
                    restaurantState = true;
                } else {
                    MapLocations getLocations = new MapLocations("restaurants", map, true); //Clears Markers
                    restaurantState = false;
                }
            }
        });
        laundry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (laundryState == false) {
                    MapLocations getLocations = new MapLocations("laundry", map, false);
                    laundryState = true;
                } else {
                    MapLocations getLocations = new MapLocations("laundry", map, true);
                    laundryState = false;
                }

            }
        });
        internetCafe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (internetCafeState == false) {
                    MapLocations getLocations = new MapLocations("internetCafe", map, false);
                    internetCafeState = true;
                } else {
                    MapLocations getLocations = new MapLocations("internetCafe", map, true);
                    internetCafeState = false;
                }

            }
        });
        convenienceStore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (convenienceStoresState == false) {
                    MapLocations getLocations = new MapLocations("convenienceStore", map, false);
                    convenienceStoresState = true;
                } else {
                    MapLocations getLocations = new MapLocations("convenienceStore", map, true);
                    convenienceStoresState = false;
                }

            }
        });
        location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getDeviceLocation();
            }
        });

        return view;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        googleMap.setMapStyle(
                MapStyleOptions.loadRawResourceStyle(Objects.requireNonNull(getActivity()), R.raw.style_json));
        if (mainActivity.LocationPermissionGranted) {
            if (ContextCompat.checkSelfPermission(getActivity().getApplicationContext(),
                    Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED)
                return;
        }
        map.setMyLocationEnabled(true);
        map.getUiSettings().setMyLocationButtonEnabled(false);
        moveCameraLocation(new LatLng(14.598815, 121.005397), 17f);
    }

    private void moveCameraLocation(LatLng latLng, float zoom) {
        Log.d(TAG, "moveCameraLocation: Moving Camera...");//Moves Camera to target
        map.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoom));
    }

    public void getDeviceLocation() {                                                                 //User Location
        Log.d(TAG, "getDeviceLocation: Method Called");
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(getActivity());
        try {
            Task location = fusedLocationProviderClient.getLastLocation();
            location.addOnCompleteListener(new OnCompleteListener() {
                @Override
                public void onComplete(@NonNull Task task) {                                             //Gets the Current User Location
                    if (task.isSuccessful()) {
                        Log.d(TAG, "onComplete: Getting Location...");
                        Location currentLocation = (Location) task.getResult();
                        moveCameraLocation(new LatLng(currentLocation.getLatitude(),             //Moves Camera to Current Location
                                currentLocation.getLongitude()), DefaultZoom);
                    } else {
                        Log.d(TAG, "onComplete: Getting Location unsuccessful...");
                    }
                }
            });

        } catch (SecurityException e) {
        }
    }
}
