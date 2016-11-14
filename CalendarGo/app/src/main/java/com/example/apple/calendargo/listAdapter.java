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

public class listAdapter extends BaseAdapter{

    private ArrayList<Event> events;
    private String[] types;
    private Context mContext;
    private LayoutInflater mInflater;
    private int sizeOfTypes;
    private int sizeOfPops;

    public listAdapter(Context mContext, ArrayList<Event> events, String[] types){
        this.mContext = mContext;
        this.events = events;
        this.types  = types;
        sizeOfPops = events.size();
        sizeOfTypes = types.length;

        mInflater = (LayoutInflater) this.mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    @Override
    public int getCount() {
        return (sizeOfPops+sizeOfTypes);
    }

    @Override
    public Object getItem(int position) {
        if (position < sizeOfTypes) return types[position];
        //System.out.println(position+" and "+sizeOfTypes);
        return events.get(position - sizeOfTypes);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View rowView;

        if (position < sizeOfTypes-1){
            rowView = mInflater.inflate(R.layout.type_list, parent, false);

            TextView type = (TextView) rowView.findViewById(R.id.type_list);

            type.setText((String) getItem(position));

        }
        else if (position == sizeOfTypes-1){
            rowView = mInflater.inflate(R.layout.most_pop, parent, false);

            TextView type = (TextView) rowView.findViewById(R.id.most_pop);

            type.setText((String) getItem(position));
        }
        else{
            rowView = mInflater.inflate(R.layout.pop_list, parent, false);

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
            eventDistance.setText("Lat:"+thisEvent.latitude+" Long: "+thisEvent.longitude);
            eventPersons.setText(thisEvent.time);

            ImageView thumbnailImageView =
                    (ImageView) rowView.findViewById(R.id.event_photo);
            Picasso.with(mContext).load(thisEvent.image).placeholder(R.mipmap.ic_launcher).into(thumbnailImageView);
        }

        return rowView;

    }
}
