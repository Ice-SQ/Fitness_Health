package com.example.fitness_health;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import android.Manifest;
import android.widget.Button;

import java.util.List;

public class GoogleMapsFragment extends Fragment {
    private GoogleMap mMap;  // private instance variable named mMap
    private FloatingActionButton mButton;

    private static final int LOCATION_PERMISSION_CODE = 101; //used request permission to access the user's location.

    // A callback that's called when the GoogleMap object is ready to be used
    private OnMapReadyCallback callback = new OnMapReadyCallback() {

        // This method is called when the GoogleMap object is ready to be used
        @Override
        public void onMapReady(GoogleMap googleMap) {
            mMap = googleMap;  //  GoogleMap object is assigned to the mMap variable

            // Check if the app has permission to access the user's location
            if(ContextCompat.checkSelfPermission(getActivity(),Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
                // Enable the display of the user's location on the map
                mMap.setMyLocationEnabled(true);

                // Get the last known location of the user
                LocationManager locationManager = (LocationManager) requireActivity().getSystemService(Context.LOCATION_SERVICE);
                Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

                if (location != null) {
                    // Create a LatLng object to represent the user's location
                    LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());

                    // Create a CameraUpdate object to zoom in on the user's location
                    CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, 16f); // zoom level

                    // Animate the camera to the user's location
                    mMap.animateCamera(cameraUpdate);
                }
            } else {
                // If the app does not have permission to access the user's location, request it
                requestLocationPermission();
            }
        }

        // Check if the app has permission to access the user's location
        private boolean isLocationPermissionGranted(){
            if(ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
                // Enable the display of the user's location on the map
                mMap.setMyLocationEnabled(true);
                return true; // if permission is granted
            }else {
                return false; // if permission is not granted
            }
        }

        // Request permission to access the user's location
        private void requestLocationPermission(){
            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_PERMISSION_CODE);
        }
    };
    private void addMarker(LatLng latLng, String title){
        mMap.addMarker(new MarkerOptions().position(latLng).title(title));
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng,13f);
        mMap.animateCamera(cameraUpdate);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_google_maps, container, false);

        mButton = rootView.findViewById(R.id.MapsBtn);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                addMarker(new LatLng(6.849046881450808, 79.9235960952153),"Maharagama Gym");
                addMarker(new LatLng(6.84614945450729, 79.94638414203202),"SNTD Power Fitness Gym");
                addMarker(new LatLng(6.843635496288184, 79.94059057080743),"Orilka Gym");
                addMarker(new LatLng(6.837797949033319, 79.93230790972339),"Slim Fitness Gym");
                addMarker(new LatLng(6.857653840024859, 79.95908708338372),"Stay Cool Gym");

            }
        });

        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(callback);
        }
    }

}