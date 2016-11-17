package com.example.apple.calendargo;

import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

import static com.google.android.gms.wearable.DataMap.TAG;

/**
 * Created by apple on 10/21/16.
 */

public class MapFragment extends Fragment implements OnMapReadyCallback {

    private GoogleMap mMap;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceStates){
        View v = inflater.inflate(R.layout.map, null,false);
        SupportMapFragment mapFragment = (SupportMapFragment) this.getChildFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        return v;
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        ArrayList<Event> markersArray = new ArrayList<Event>();

        markersArray = EventJson.getEventsFromFile("mostPop.json",getContext());



        for( int i = 0; i < markersArray.size(); i++)
        {
            createMarker(markersArray.get(i).getLongitude(), markersArray.get(i).getLatitude(), markersArray.get(i).getName());

        }

        //if (ActivityCompat.checkSelfPermission(getContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
        // TODO: Consider calling
        //    ActivityCompat#requestPermissions
        // here to request the missing permissions, and then overriding
        //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
        //                                          int[] grantResults)
        // to handle the case where the user grants the permission. See the documentation
        // for ActivityCompat#requestPermissions for more details.
        //  return;
        //}

        LatLng ucsd = new LatLng(32.8801, -117.2340);
        mMap.addMarker(new MarkerOptions().position(ucsd).title("UCSD"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(ucsd, 18));


        // mMap.setMyLocationEnabled(true);
    }

    private void createMarker( double longitude, double latitude, String name )
    {
        mMap.addMarker(new MarkerOptions().position(new LatLng(longitude, latitude)).title(name));
    }


}
