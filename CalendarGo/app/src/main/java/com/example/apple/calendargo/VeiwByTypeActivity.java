package com.example.apple.calendargo;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ListView;

import java.util.ArrayList;

public class VeiwByTypeActivity extends AppCompatActivity {

    private ListView mListView;
    private String type;
    private ArrayList<Event> eventList;
    private popAdapter adapter_list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //setTheme(R.style.splashScreenTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_veiw_by_type);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        String title = this.getIntent().getExtras().getString("type");

        setTitle(title);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mListView = (ListView) findViewById(R.id.type_view_list);

        eventList = EventJson.getEventsFromFile("mostPop.json",getBaseContext());

        popAdapter adapter_list = new popAdapter(getBaseContext(), eventList);

        mListView.setAdapter(adapter_list);
    }

}
