package com.example.apple.calendargo;

/**
 * Created by apple on 11/11/16.
 * Define the method to extract event object from the Json file
 */

import android.content.Context;

import com.google.firebase.database.DatabaseReference;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

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

    public static void saveEventToFirebase(DatabaseReference myRef, Event event){

        Map<String,Event> map = new HashMap<String,Event>();

        map.put(event.name,event);

        myRef.child(event.type).child(event.name).setValue(map);

    }


}
