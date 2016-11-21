package com.example.apple.calendargo;

/**
 * Created by apple on 11/11/16.
 * Define the method to extract event object from the Json file
 */

import android.content.Context;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class EventJson {

    private String name;
    private String description;
    private String time;
    private double longitude;
    private double latitude;
    private int persons;
    private String url;
    private String image;


    public static ArrayList<Event> getEventsFromFile(String filename, Context context){
        final ArrayList<Event> eventList = new ArrayList<Event>();

        try{
            String jsonString = loadJsonFromAsset(filename,context);
            JSONObject json = new JSONObject(jsonString);
            JSONArray events = json.getJSONArray("mostPop");

            for (int i = 0; i < events.length(); i++){
                Event event = new Event();
                JSONObject curr = events.getJSONObject(i);
                event.name =curr.getString("name");
                event.description = curr.getString("description");
                event.time = curr.getString("time");
                event.image = curr.getString("image");
                event.persons = curr.getInt("persons");
                event.longitude = curr.getDouble("longitude");
                event.latitude = curr.getDouble("latitude");
                event.type = curr.getString("type");

                eventList.add(event);
            }



        } catch (JSONException e){
            e.printStackTrace();
        }

        return eventList;
    }

    private static String loadJsonFromAsset(String filename, Context context){
        String json = null;

        try {
            InputStream is = context.getAssets().open(filename);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
            System.out.println(json);
        }
        catch (java.io.IOException ex) {
            ex.printStackTrace();
            return null;
        }

        return json;
    }

    public static void saveEventToFirebase(Event event){

        FirebaseDatabase database;
        DatabaseReference myRef;

        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("Events");

        Map<String,Event> map = new HashMap<String,Event>();

        map.put(event.name,event);

        myRef.child(event.type).child(event.name).setValue(map);

    }

    public static ArrayList<Event> getEventFromFirebaseByType(final String type){
        final ArrayList<Event> events = new ArrayList<Event>();

        FirebaseDatabase database;
        DatabaseReference myRef, typeRef;

        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("Events");

        typeRef = myRef.child(type);

        typeRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot == null && dataSnapshot.getValue() == null) {
                    System.out.println("No records");
                } else {
                    //System.out.println("LogIn Successfully\n");
                    Map<String,Map<String,Map<String,Object>>> type_events = (Map<String,Map<String,Map<String,Object>>>) dataSnapshot.getValue();

                    Collection<Map<String,Map<String,Object>>> string_events = type_events.values();


                    for (Map<String,Map<String,Object>> map : string_events){
                        for (Map<String,Object> event : map.values()){
                            Event new_event = new Event();

                            System.out.println(event.get("longitude"));

                            new_event.address = (String)event.get("address");
                            new_event.time = (String)event.get("time");
                            new_event.longitude = (Double)event.get("longitude");
                            new_event.latitude = (Double) event.get("latitude");
                            new_event.name = (String)event.get("name");
                            new_event.image = (String) event.get("image");

                            System.out.println(new_event.toString());

                            events.add(new_event);
                        }
                    }
                    /*for(Map<String,Event> map:profile){
                        Set<Map.Entry<String,Event>> set = map.entrySet();
                        for (Map.Entry<String,Event> e:set){
                            events.add(e.getValue());
                        }
                    }*/

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        return events;
    }

    public static Event getEventFromFirebaseByName(String name){
        Event event = new Event();
        return event;
    }

}
