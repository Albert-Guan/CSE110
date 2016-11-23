package com.example.apple.calendargo;

import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.GroundOverlay;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.google.android.gms.wearable.DataMap.TAG;

/**
 * Created by apple on 10/21/16.
 */

public class NewEventMapFragment extends Fragment implements OnMapReadyCallback {

    private GoogleMap mMap;
    private EventJson ej;
    String address;
    String name;
    float colorVal;
    String description;
    private Marker marker;

    public void setDetails(String address, String name, float colorVal, String description){
        this.address = address;
        this.name = name;
        this.colorVal = colorVal;
        this.description = description;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceStates){
        View v = inflater.inflate(R.layout.new_event_map, null,false);
        SupportMapFragment mapFragment = (SupportMapFragment) this.getChildFragmentManager().findFragmentById(R.id.map);

        CoordinatorLayout coordinatorLayoutLayout = (CoordinatorLayout) v.findViewById(R.id.drag_map_layout);

        // Drag event notification
        Snackbar snack = Snackbar.make(coordinatorLayoutLayout, "To drag your event, select and hold.", Snackbar.LENGTH_INDEFINITE);
        View snackView = snack.getView();
        CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams)snackView.getLayoutParams();
        params.gravity = Gravity.TOP;
        snackView.setLayoutParams(params);
        snack.show();

        mapFragment.getMapAsync(this);
        return v;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        marker = mMap.addMarker(new MarkerOptions().position(getLocationFromAddress(getContext(), address)).title(name).icon(BitmapDescriptorFactory.defaultMarker(colorVal)).draggable(true));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(getLocationFromAddress(getContext(), address), 18));
    }

    private void createMarker( double longitude, double latitude, String name, Float colorVal, String description)
    {
        mMap.addMarker(new MarkerOptions().position(new LatLng(longitude, latitude)).title(name).alpha(0.7f).icon(BitmapDescriptorFactory.defaultMarker(colorVal)).snippet(description));
    }

    private void createMarkerByAddress(String address, String name, Float colorVal, String description){
        if (getLocationFromAddress(getContext(),address) != null)
            mMap.addMarker(new MarkerOptions().position(getLocationFromAddress(getContext(),address)).title(name).alpha(0.7f).icon(BitmapDescriptorFactory.defaultMarker(colorVal)).snippet(description));
    }

    /*
    private void createMarkerNewEvent( double longitude, double latitude, String name )
    {
        mMap.addMarker(new MarkerOptions().position(new LatLng(longitude, latitude)).title(name).alpha(0.7f));
    }
    */

    public LatLng getLocationFromAddress(Context context, String strAddress)
    {
        Geocoder coder= new Geocoder(context);
        List<Address> address;
        LatLng p1 = null;

        try
        {
            address = coder.getFromLocationName(strAddress, 5);
            if(address==null)
            {
                return null;
            }
            Address location = address.get(0);
            location.getLatitude();
            location.getLongitude();

            p1 = new LatLng(location.getLatitude(), location.getLongitude());
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return p1;

    }

}
