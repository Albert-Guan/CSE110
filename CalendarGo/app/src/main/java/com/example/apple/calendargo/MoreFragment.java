package com.example.apple.calendargo;

import android.app.Activity;

import android.content.Context;

import android.content.Intent;

import android.os.Bundle;

import android.support.annotation.Nullable;

import android.support.v4.app.Fragment;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;

import android.view.View;

import android.view.ViewGroup;

import android.widget.AdapterView;

import android.widget.ListView;

import java.util.ArrayList;

/**
 * Created by apple on 10/21/16.
 */

public class MoreFragment extends Fragment {

    private ListView mListViewType;

    private int sizeOfTypes;

    private Activity acitivity;


    @Nullable

    @Override

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceStates) {

        View v = inflater.inflate(R.layout.list, container, false);


        final Context context = getActivity().getApplicationContext();


        mListViewType = (ListView) v.findViewById(R.id.listViewType);


        final String[] types = new String[]{"Create event", "Manage account", "Notifications", "Data & Sync", "About"};


        sizeOfTypes = types.length;


        //typesAdapter adapter_type = new typesAdapter(getContext(), types);


        //mListViewType.setAdapter(adapter_type);


        // mListViewPop = (ListView) v.findViewById(R.id.listViewPop);


        //popAdapter adapter_list = new popAdapter(getContext(),events);


        //mListViewPop.setAdapter(adapter_list);


        settingsAdapter typesAdapter = new settingsAdapter(getContext(), types);


        mListViewType.setAdapter(typesAdapter);


        mListViewType.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override

            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                String tabName = types[position];
                Fragment newFragment;
                FragmentManager fragmentManager;
                FragmentTransaction fragmentTransaction;

                switch (tabName) {
                    case "Create event":
                        fragmentManager = getActivity().getSupportFragmentManager();
                        fragmentTransaction = fragmentManager.beginTransaction();
                        newFragment = new CreateEventFragment();

                        fragmentTransaction.replace(R.id.frame, newFragment);
                        fragmentTransaction.addToBackStack(null);
                        fragmentTransaction.commit();
                        break;
                    case "Manage account":
                        fragmentManager = getActivity().getSupportFragmentManager();
                        fragmentTransaction = fragmentManager.beginTransaction();
                        newFragment = new ManageAccountFragment();

                        fragmentTransaction.replace(R.id.frame, newFragment);
                        fragmentTransaction.addToBackStack(null);
                        fragmentTransaction.commit();
                        break;
                    case "Notifications":
                        fragmentManager = getActivity().getSupportFragmentManager();
                        fragmentTransaction = fragmentManager.beginTransaction();
                        newFragment = new NotificationsFragment();

                        fragmentTransaction.replace(R.id.frame, newFragment);
                        fragmentTransaction.addToBackStack(null);
                        fragmentTransaction.commit();
                        break;
                    case "Data & Sync":
                        fragmentManager = getActivity().getSupportFragmentManager();
                        fragmentTransaction = fragmentManager.beginTransaction();
                        newFragment = new DataFragment();

                        fragmentTransaction.replace(R.id.frame, newFragment);
                        fragmentTransaction.addToBackStack(null);
                        fragmentTransaction.commit();
                        break;
                    case "About":
                        fragmentManager = getActivity().getSupportFragmentManager();
                        fragmentTransaction = fragmentManager.beginTransaction();
                        newFragment = new AboutFragment();

                        fragmentTransaction.replace(R.id.frame, newFragment);
                        fragmentTransaction.addToBackStack(null);
                        fragmentTransaction.commit();
                }


            }

        });


        return v;
    }
}
