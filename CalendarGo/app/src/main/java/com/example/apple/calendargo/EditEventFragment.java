package com.example.apple.calendargo;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.gms.maps.*;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

/**
 * Created by jd on 11/11/16.
 */

public class EditEventFragment extends Fragment implements View.OnClickListener {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceStates) {

        View v = inflater.inflate(R.layout.edit_event, container, false);

        Button b10 = (Button) v.findViewById(R.id.button10);
        Button b13 = (Button) v.findViewById(R.id.button13);

        EditText organizer = (EditText) v.findViewById(R.id.organizer);
        EditText eventName = (EditText) v.findViewById(R.id.eventName);
        EditText month = (EditText) v.findViewById(R.id.month);
        EditText day = (EditText) v.findViewById(R.id.day);
        EditText year = (EditText) v.findViewById(R.id.year);
        EditText description = (EditText) v.findViewById(R.id.description);

        organizer.setText("");
        eventName.setText("");
        month.setText("");
        day.setText("");
        year.setText("");
        description.setText("");

        final TextView deleteEvent = (TextView)v.findViewById(R.id.deleteEvent);
        deleteEvent.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(getActivity())
                        //.setIcon(android.R.drawable.ic_dialog_info)
                        .setTitle("Delete confirmation")
                        .setMessage("Are you sure you want to delete this event?")
                        .setPositiveButton("Delete", new DialogInterface.OnClickListener()
                        {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                                // update login status
                                Bundle args = new Bundle();
                                args.putBoolean("hasLoggedIn", MainActivity.hasLoggedIn);

                                Fragment newFragment = new ManageEvent();

                                fragmentTransaction.replace(R.id.frame, newFragment);
                                fragmentTransaction.addToBackStack(null);
                                fragmentTransaction.commit();

                            }

                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener()
                        {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {}
                        }).show();
            }
        });

        b10.setOnClickListener(this);
        b13.setOnClickListener(this);

        return v;
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            // cancel button
            case R.id.button10:
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                Bundle args = new Bundle();
                args.putBoolean("hasLoggedIn", MainActivity.hasLoggedIn);
                Fragment newFragment = new ManageEvent();
                newFragment.setArguments(args);

                fragmentTransaction.replace(R.id.frame, newFragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
                break;
            // save button
            case R.id.button13:
            default:
        }
    }
}
