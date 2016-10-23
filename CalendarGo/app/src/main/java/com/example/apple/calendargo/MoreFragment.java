package com.example.apple.calendargo;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Created by apple on 10/21/16.
 */

public class MoreFragment extends Fragment {

    private ListView listView;
    private Context mContext;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceStates){
        final ArrayList<String> title = new ArrayList<String>();
        title.add("Settings");
        title.add("Privacy");
        title.add("Add Activity");

        View v = inflater.inflate(R.layout.more, container,false);
        mContext = getContext();

        listView = (ListView) v.findViewById(R.id.more_list_view);

        listView.setAdapter(new MoreAdapter(mContext,title));

        return v;
    }
}
