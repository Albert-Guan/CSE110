package com.example.apple.calendargo;

import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.*;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import android.widget.TextView;

import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnMenuTabSelectedListener;

public class MainActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {
    private CoordinatorLayout coordinatorLayout;
    private Fragment f;
    private SearchView mSearchView;
    private TextView mStatusView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        f = new ListFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.frame,f).commit();

        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.four_buttons_activity);

        BottomBar bottomBar = BottomBar.attach(this, savedInstanceState);

        bottomBar.setItemsFromMenu(R.menu.four_bottons_menu, new OnMenuTabSelectedListener() {
            @Override
            public void onMenuItemSelected(int itemId) {
                switch (itemId) {
                    case R.id.list_item:
                        Snackbar.make(coordinatorLayout, "List Selected", Snackbar.LENGTH_LONG).show();
                        f = new ListFragment();
                        getSupportFragmentManager().beginTransaction().replace(R.id.frame,f).commit();
                        break;
                    case R.id.map_item:
                        f = new MapFragment();
                        getSupportFragmentManager().beginTransaction().replace(R.id.frame,f).commit();
                        Snackbar.make(coordinatorLayout, "Map Selected", Snackbar.LENGTH_LONG).show();
                        break;
                    case R.id.calendar_item:
                        f = new CalendarFragment();
                        getSupportFragmentManager().beginTransaction().replace(R.id.frame,f).commit();
                        Snackbar.make(coordinatorLayout, "Calendar Selected", Snackbar.LENGTH_LONG).show();
                        break;
                    case R.id.more_item:
                        f = new MoreFragment();
                        getSupportFragmentManager().beginTransaction().replace(R.id.frame,f).commit();
                        Snackbar.make(coordinatorLayout, "More Selected", Snackbar.LENGTH_LONG).show();
                        break;
                }
            }
        });

        bottomBar.setActiveTabColor("#C2185B");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.search_menu, menu);
        MenuItem searchItem = menu.findItem(R.id.search);
        mSearchView = (SearchView) searchItem.getActionView();
        mSearchView.setOnQueryTextListener(this);

        return true;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        mStatusView = (TextView) findViewById(R.id.searchShow);
        f = new SearchFragment();
        Bundle args = new Bundle();
        args.putString("query_string", query);
        f.setArguments(args);
        getSupportFragmentManager().beginTransaction().replace(R.id.frame,f).commit();
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }
}
