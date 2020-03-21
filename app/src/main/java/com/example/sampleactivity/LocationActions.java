package com.example.sampleactivity;

import com.google.android.gms.maps.model.Marker;

public interface LocationActions {
    public void onLocationRemove(int position);
    public void onLocationSetOnMap(Marker marker);
}
