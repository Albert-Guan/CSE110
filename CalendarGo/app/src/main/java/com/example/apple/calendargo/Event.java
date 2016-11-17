package com.example.apple.calendargo;

/**
 * Created by apple on 11/6/16.
 * Define the Object for single events
 */

public class Event implements Comparable<Event> {
    public String name;
    public String description;
    public  String time;
    public double longitude;
    public double latitude;
    public int persons;
    public String url;
    public String image;
    public String type;
    public String organizer;
    public String address;


    public Event(){
        name = "Event name";
        description = "Event Description";
        time = "Mar-12 -2016";
        longitude = 32.8801;
        latitude = 117.2340;
        persons = 0;
        image = null;
        url = "http://www.google.com";
        type = "Others";
        organizer = "Some one";

    }

    public Event(String organizer, String type, String address, String time, String name ){
        this();
        this.organizer = organizer;
        this.address = address;
        this.time = time;
        this.type = type;
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setImage(String Image){
        this.image = Image;
    }

    public void addPerson(){
        persons++;
    }

    public void setPerson(int target_persons){
        persons = target_persons;
    }

    public String getName(){
        return name;
    }

    public String getDescription(){
        return description;
    }

    public String getTime(){
        return time;
    }


    public String getUrl(){
        return url;
    }

    public int getPersons(){
        return persons;
    }

    public double getLongitude() {return longitude; }

    public double getLatitude() {
        return latitude;
    }

    @Override
    public int compareTo(Event that) {
        if (this.persons > that.persons) return 1;
        if (this.persons < that.persons) return -1;
        return 0;
    }

    public String toString(){
        return name+" will happen at "+time+"\n";
    }
}
