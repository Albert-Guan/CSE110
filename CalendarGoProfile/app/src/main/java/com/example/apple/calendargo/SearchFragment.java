package com.example.apple.calendargo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by apple on 10/21/16.
 */

public class SearchFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceStates){

        View v = inflater.inflate(R.layout.search, container,false);
        TextView result = (TextView) v.findViewById(R.id.searchShow);
        Bundle bundle = getArguments();
        String search_result = bundle.getString("query_string");
        result.setText(search_result);
        return v;
    }
}
