package com.example.apple.calendargo;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
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
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.google.android.gms.wearable.DataMap.TAG;

/**
 * Created by apple on 10/21/16.
 */

public class MapFragment extends Fragment implements OnMapReadyCallback {

    private GoogleMap mMap;
    private EventJson ej;
    public HashMap<Marker, Event> hmap = new HashMap<Marker, Event>();

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

        mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {
                Event event = hmap.get(marker);
                Intent detailEvent = new Intent(getActivity(),DetailTypeActivity.class);
                String[] event_to_edited_string = new String[8];
                event_to_edited_string[0] = event.organizer;
                event_to_edited_string[1] = event.name;
                event_to_edited_string[2] = event.date;
                event_to_edited_string[3] = event.description;
                event_to_edited_string[4] = event.address;
                event_to_edited_string[5] = event.latitude;
                event_to_edited_string[6] = event.longitude;
                event_to_edited_string[7] = event.type;

                Bundle bundle = new Bundle();
                bundle.putStringArray("currEvent",event_to_edited_string);

                detailEvent.putExtras(bundle);
                startActivity(detailEvent);

            }
        });

        FirebaseDatabase database;
        DatabaseReference myRef;

        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("Events");

        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ArrayList<Event> markersArray;

                ej = new EventJson();

                //markersArray = EventJson.getEventsFromFile("mostPop.json",getContext());
                markersArray = ej.checkCurrentDate(ej.getAllEvents(dataSnapshot,getActivity()));

                for(Event e : markersArray)
                {
                    System.out.println("MapFrag: event: "+e.toString());

                    Float colorValue = 0.0f;
                    String colorType = e.type;
                    switch(colorType)
                    {
                        case "Athletics" :
                            colorValue = 10.0f;
                            break;
                        case "Free food" :
                            colorValue = 50.0f;
                            break;
                        case "Music" :
                            colorValue = 100.0f;
                            break;
                        case "Kid friendly/Family" :
                            colorValue = 200.0f;
                            break;
                        case "Pet friendly" :
                            colorValue = 240.0f;
                            break;
                        case "Workshops" :
                            colorValue = 270.0f;
                            break;
                        case "Party" :
                            colorValue = 300.0f;
                            break;
                        case "Other" :
                            colorValue = 340.0f;
                            break;
                        default:
                            colorValue = 0.00f;
                            break;
                    }
                    System.out.println("The longitude is: "+e.longitude);
                    createMarker(Double.parseDouble(e.longitude),Double.parseDouble((e.latitude)),e.name,colorValue,e.description,e);
                    //createMarkerByAddress(markersArray.get(i).address,markersArray.get(i).name,colorValue,markersArray.get(i).description);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });




        /*for( int i = 0; i < markersArray.size(); i++)
        {
            createMarker(markersArray.get(i).getLongitude(), markersArray.get(i).getLatitude(), markersArray.get(i).getName(),markersArray.get(i).colorValue);

        }*/

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

    private void createMarker(double longitude, double latitude, String name, Float colorVal, String description, Event e)
    {
        Marker m = mMap.addMarker(new MarkerOptions().position(new LatLng(longitude, latitude)).title(name).alpha(0.7f).icon(BitmapDescriptorFactory.defaultMarker(colorVal)).snippet(description));
        hmap.put(m, e);
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
        System.out.println("Get Location Function: Address is: "+strAddress);
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
