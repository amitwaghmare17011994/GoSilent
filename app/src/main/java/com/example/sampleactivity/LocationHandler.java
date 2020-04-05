package com.example.sampleactivity;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.location.Location;
import android.location.LocationManager;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class LocationHandler {
    LocationActions locationActions;

    LocationHandler(LocationActions locationActions) {
        this.locationActions = locationActions;
    }

    public void setLocationMarkerAtCurrentLocation(GoogleMap mMap, Activity activity) {
        try {

            if (ContextCompat.checkSelfPermission(activity,
                    android.Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {

                LocationManager locationManager = (LocationManager) activity.getSystemService(Context.LOCATION_SERVICE);
                Location currentLocation = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                if (currentLocation == null)
                    currentLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                if (currentLocation != null) {
                    LatLng current = new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude());


                    int height = 80;
                    int width = 80;
                    BitmapDrawable bitmapdraw = (BitmapDrawable)activity.getResources().getDrawable(R.mipmap.marker);
                    Bitmap b = bitmapdraw.getBitmap();
                    Bitmap smallMarker = Bitmap.createScaledBitmap(b, width, height, false);

                    Marker marker = mMap.addMarker(new MarkerOptions().position(current).title("Marker Label")
                            .icon(BitmapDescriptorFactory.fromBitmap(smallMarker))
);

                    CameraPosition cameraPosition = new CameraPosition.Builder().target(current).zoom(14).build();

                    mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

                    this.locationActions.onLocationSetOnMap((marker));
                }

            }
            else {
                ActivityCompat.requestPermissions(activity,
                        new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                        1);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
