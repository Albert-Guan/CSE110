package com.example.apple.calendargo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by apple on 11/6/16.
 */

public class popAdapter extends BaseAdapter {

    private Context thisContext;
    private ArrayList<Event> events;
    private LayoutInflater mInflater;

    public popAdapter(Context context, ArrayList<Event> events){
        thisContext = context;
        this.events = events;
        mInflater = (LayoutInflater) thisContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


    @Override
    public int getCount() {
        return events.size();
    }

    @Override
    public Object getItem(int position) {
        return events.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View rowView = mInflater.inflate(R.layout.pop_list, parent, false);

        // Get title element
        TextView eventName =
                (TextView) rowView.findViewById(R.id.event_name);

// Get subtitle element
        TextView eventPersons =
                (TextView) rowView.findViewById(R.id.event_persons);

// Get detail element
        TextView eventDistance =
                (TextView) rowView.findViewById(R.id.event_distance);

// Get thumbnail element
        /*ImageView eventPhoto =
                (ImageView) rowView.findViewById(R.id.event_photo);*/

        Event thisEvent = (Event) getItem(position);

        eventName.setText(thisEvent.getName());
        eventDistance.setText(thisEvent.address);
        eventPersons.setText(thisEvent.date);

        ImageView thumbnailImageView =
                (ImageView) rowView.findViewById(R.id.event_photo);
        Picasso.with(thisContext).load(thisEvent.image).placeholder(R.mipmap.ic_launcher).into(thumbnailImageView);
        return rowView;
    }
}
