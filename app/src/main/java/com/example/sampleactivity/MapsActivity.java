package com.example.sampleactivity;

import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnMapClickListener, LocationActions {

    private static final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1;
    RecyclerView locationList;
    RecyclerView.LayoutManager layoutManager;
    LocationsAdapter mAdapter;
    ArrayList<LocationItem> locationItems;
    ArrayList<Marker> markers;
    LocationManager locationManager;
    LocationHandler locationHandler ;

    Boolean mLocationPermissionGranted = false;
    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        locationList = findViewById(R.id.locationList);
        markers = new ArrayList<Marker>();
        locationList.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        locationList.setLayoutManager(layoutManager);
        locationItems = new ArrayList<>();
        mAdapter = new LocationsAdapter(locationItems, getApplicationContext(), this);
        locationList.setAdapter(mAdapter);

    }


    public void setLocationPermision() {
        locationHandler=new LocationHandler(this);
        locationHandler.setLocationMarkerAtCurrentLocation(mMap,this);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        mLocationPermissionGranted = false;
        switch (requestCode) {
            case PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION: {
                 if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                     locationHandler.setLocationMarkerAtCurrentLocation(mMap,this);
                }
            }
        }

    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setOnMapClickListener(this);
        if (mLocationPermissionGranted) {
            mMap.setMyLocationEnabled(true);
        } else {
            setLocationPermision();
        }
    }


    @Override
    public void onMapClick(LatLng latLng) {
        Marker marker = mMap.addMarker(new MarkerOptions().position(latLng));
        this.setMarkerOnMap(marker);
    }


    public void resetList() {
        mAdapter = new LocationsAdapter(this.locationItems, getApplicationContext(), this);
        locationList.setAdapter(mAdapter);
        locationList.invalidate();
    }

    public void setMarkerOnMap(Marker marker) {
        markers.add(marker);
        locationItems.add(new LocationItem("New Location", marker.getPosition(), this));
        this.resetList();

    }

    @Override
    public void onLocationRemove(int position) {

         Marker marker = markers.get(position);
        markers.remove(position);
        locationItems.remove(position);
        marker.remove();
        this.resetList();
    }

    @Override
    public void onLocationSetOnMap(Marker marker) {
        markers.add(marker);
        locationItems.add(new LocationItem("Current Location", marker.getPosition(), this));
        this.resetList();
    }


}
