package com.example.jd.settingstab;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by jd on 11/11/16.
 */

public class NotificationsFragment extends Fragment {
    @Nullable

    @Override

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceStates) {

        View v = inflater.inflate(R.layout.notifications, container, false);

        return v;
    }


}