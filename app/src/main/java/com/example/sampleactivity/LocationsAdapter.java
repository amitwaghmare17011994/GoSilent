package com.example.sampleactivity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class LocationsAdapter extends RecyclerView.Adapter<LocationsAdapter.MyViewHolder> {
    ArrayList<LocationItem> locationItems;
    Context context;
    LocationActions locationActions;


    public LocationsAdapter(ArrayList<LocationItem> locationItems, Context c,LocationActions locationActions) {
        this.locationItems = locationItems;
        this.locationActions=locationActions;
        context=c;
    }

    @Override
    public LocationsAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                            int viewType) {

        View v = (View) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.location_list_item, parent, false);

        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {

        TextView locationName= (TextView) holder.view.findViewById(R.id.locationItem);
        Button locationItemRemove=(Button) holder.view.findViewById(R.id.locationItemRemove);
        locationItemRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                locationActions.onLocationRemove(position);
            }
        });
        locationName.setText(locationItems.get(position).getLocationName());
    }

    @Override
    public int getItemCount() {
        return locationItems.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        public View view;
        public MyViewHolder(View v) {
            super(v);
            view = v;
        }
    }
}
