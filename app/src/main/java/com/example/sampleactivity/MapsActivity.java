package com.example.sampleactivity;

import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.bottomsheet.BottomSheetBehavior;

import java.util.ArrayList;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnMapClickListener, LocationActions {

    private static final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1;
    RecyclerView locationList;
    RecyclerView.LayoutManager layoutManager;
    Button apply_button;
    LocationsAdapter mAdapter;
    ArrayList<LocationItem> locationItems;
    ArrayList<Marker> markers;
    LocationManager locationManager;
    LocationHandler locationHandler;
    BottomSheetBehavior bottomSheetBehavior;
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
        apply_button = findViewById(R.id.apply_button);

        markers = new ArrayList<Marker>();
        locationList.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        locationList.setLayoutManager(layoutManager);
        locationItems = new ArrayList<>();
        mAdapter = new LocationsAdapter(locationItems, getApplicationContext(), this);
        locationList.setAdapter(mAdapter);


        LinearLayout llBottomSheet = findViewById(R.id.bottom_sheet);
        BottomSheetBehavior bottomSheetBehavior = BottomSheetBehavior.from(llBottomSheet);


        bottomSheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                TextView bottom_sheet_text=(TextView) findViewById(R.id.bottom_sheet_text);
                Drawable  drawable=null;

                if (BottomSheetBehavior.STATE_EXPANDED == newState) {
                    drawable=getResources().getDrawable(R.mipmap.down);
                }
                if(BottomSheetBehavior.STATE_COLLAPSED == newState)
                {
                    drawable=getResources().getDrawable(R.mipmap.up);
                }

                if(drawable!=null)
                bottom_sheet_text.setCompoundDrawablesWithIntrinsicBounds(null,drawable,null,null);

            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {

            }
        });

    }


    public void setLocationPermision() {
        locationHandler = new LocationHandler(this);
        locationHandler.setLocationMarkerAtCurrentLocation(mMap, this);
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
                    locationHandler.setLocationMarkerAtCurrentLocation(mMap, this);
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
        if (this.locationItems.size() == 0) {
            apply_button.setVisibility(View.INVISIBLE);
        } else {
            if (apply_button.getVisibility() == View.INVISIBLE)
                apply_button.setVisibility(View.VISIBLE);
        }
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
