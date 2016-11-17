package com.example.apple.calendargo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

/**
 * Created by jd on 11/11/16.
 */

public class CreateEventFragment extends Fragment implements View.OnClickListener {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceStates) {

        View v = inflater.inflate(R.layout.create_event, container, false);

        Button b3 = (Button) v.findViewById(R.id.button3);
        Button b2 = (Button) v.findViewById(R.id.button2);

        b3.setOnClickListener(this);
        b2.setOnClickListener(this);

        Spinner spinner = (Spinner) v.findViewById(R.id.spinner2);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.category_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        return v;
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            // create event button
            case R.id.button3:

                break;
            // cancel button
            case R.id.button2:
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                Bundle args = new Bundle();
                args.putBoolean("hasLoggedIn", MainActivity.hasLoggedIn);
                Fragment newFragment = new MoreFragment();
                newFragment.setArguments(args);

                fragmentTransaction.replace(R.id.frame, newFragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
                break;
            default:
        }
    }
}
