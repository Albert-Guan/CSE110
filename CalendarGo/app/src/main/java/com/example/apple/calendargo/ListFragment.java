package com.example.apple.calendargo;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Created by apple on 11/6/16.
 * show the fragment for the list page (first page in the main page
 */

public class ListFragment extends Fragment {

    private ListView mListViewType;
    private ArrayList<Event> events;
    private int sizeOfTypes;
    private Activity acitivity;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceStates){
        View v = inflater.inflate(R.layout.list, container,false);

        final Context context = getActivity().getApplicationContext();

        mListViewType = (ListView) v.findViewById(R.id.listViewType);

        final String[] types = new String[] {"Athletics","Free food","Music","Family","Pet friendly","Workshops","Party","Other","The most pop 3 events: "};
        //final String[] types;
        //types = getActivity().getResources().getStringArray(R.array.category_array);

        sizeOfTypes = types.length;

        //typesAdapter adapter_type = new typesAdapter(getContext(), types);

        //mListViewType.setAdapter(adapter_type);


       // mListViewPop = (ListView) v.findViewById(R.id.listViewPop);

        events = EventJson.getEventsFromFile("mostPop.json",context);


        //popAdapter adapter_list = new popAdapter(getContext(),events);

        //mListViewPop.setAdapter(adapter_list);

        listAdapter list = new listAdapter(getContext(),events,types);
        mListViewType.setAdapter(list);

        mListViewType.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if (position < sizeOfTypes - 1){
                    String type = types[position];
                    /*Bundle bundle = new Bundle();
                    bundle.putString("type",type);*/

                    Intent type_view_activity = new Intent(getActivity(), VeiwByTypeActivity.class);
                    type_view_activity.putExtra("type",type);

                    startActivity(type_view_activity);

                    /*Fragment f = new EventsFragment();

                    f.setArguments(bundle);

                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frame,f).commit();*/

                }
                else if (position >= sizeOfTypes){

                            /*Event currEvent = events.get(position-sizeOfTypes);

                            Intent detailIntent = new Intent(context, EventDetailActivity.class);

                            detailIntent.putExtra("title", currEvent.getName());
                            detailIntent.putExtra("url", currEvent.getUrl());

                            // 4
                            startActivity(detailIntent);*/

                }

            }
        });

        return v;
    }


}
