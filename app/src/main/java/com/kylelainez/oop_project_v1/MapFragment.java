package com.kylelainez.oop_project_v1;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.location.FusedLocationProviderClient;

import java.util.ArrayList;
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
    private String selectedMarker = null;


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
                if (!restaurantState) {
                    MapLocations getLocations = new MapLocations("restaurants", map, false); //Generates Markers
                    restaurantState = true;
                } else {
                    MapLocations getLocations = new MapLocations("restaurants", map, true); //Clears Markers
                    restaurantState = false;
                }
                ceaMarker();
            }
        });
        laundry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!laundryState) {
                    MapLocations getLocations = new MapLocations("laundry", map, false);
                    laundryState = true;
                } else {
                    MapLocations getLocations = new MapLocations("laundry", map, true);
                    laundryState = false;
                }
                ceaMarker();
            }
        });
        internetCafe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!internetCafeState) {
                    MapLocations getLocations = new MapLocations("internetCafe", map, false);
                    internetCafeState = true;
                } else {
                    MapLocations getLocations = new MapLocations("internetCafe", map, true);
                    internetCafeState = false;
                }
                ceaMarker();
            }
        });
        convenienceStore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!convenienceStoresState) {
                    MapLocations getLocations = new MapLocations("convenienceStore", map, false);
                    convenienceStoresState = true;
                } else {
                    MapLocations getLocations = new MapLocations("convenienceStore", map, true);
                    convenienceStoresState = false;
                }
                ceaMarker();
            }
        });
        location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getDeviceLocation();
            }
        });
//        TextView textView = view.findViewById(R.id.eat_places);
//        textView.setText(MapLocations);
//        MapLocations mapLocations = new MapLocations();
//        int length = mapLocations.getRestaurants();
        return view;
    }

    public void ceaMarker() {
        Drawable drawable = ResourcesCompat.getDrawable(getResources(), R.drawable.ic_pup_logo, null);
        Bitmap icon = MapLocations.drawableToBitmap(drawable);
        Bitmap scaledIcon = Bitmap.createScaledBitmap(icon, 100, 100, true);
        Marker ceaBuilding = map.addMarker(new MarkerOptions()
                .position(new LatLng(14.598815, 121.005397))
                .anchor(0.5f, 0.5f)
                .title("PUP - CEA Building")
                .icon(BitmapDescriptorFactory.fromBitmap(scaledIcon))
                .snippet("College of Engineering and Architecture"));
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
        ceaMarker();

        if (map != null) {
            map.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {
                @Override
                public View getInfoWindow(Marker marker) {
                    return null;
                }

                @Override
                public View getInfoContents(Marker marker) {
                    View view = getActivity().getLayoutInflater().inflate(R.layout.info_window, null);
                    TextView title = view.findViewById(R.id.name);
                    TextView type = view.findViewById(R.id.type);
                    ImageView infoImage = view.findViewById(R.id.infoImage);

                    title.setText(marker.getTitle());
                    type.setText(marker.getSnippet());
                    infoImage.setImageResource(MapLocations.getImage(marker.getTitle()));
                    return view;
                }
            });
        }
        map.setOnInfoWindowClickListener(getInfoWindowClickListener());

    }

    public GoogleMap.OnInfoWindowClickListener getInfoWindowClickListener() {
        return new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {
                info_layout.getMarkerInfo(marker.getTitle(),marker.getSnippet());

                Intent intent = new Intent(getView().getContext(), info_layout.class);
                startActivityForResult(intent, 0);
            }
        };
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
