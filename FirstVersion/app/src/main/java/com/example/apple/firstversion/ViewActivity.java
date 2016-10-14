package com.example.apple.firstversion;

import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnMenuTabSelectedListener;

public class ViewActivity extends AppCompatActivity {
    private CoordinatorLayout coordinatorLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);
        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.activity_view);

        BottomBar bottomBar = BottomBar.attach(this, savedInstanceState);
        bottomBar.setItemsFromMenu(R.menu.three_buttons_menu, new OnMenuTabSelectedListener() {
            @Override
            public void onMenuItemSelected(int itemId) {
                switch (itemId) {
                    case R.id.recent_item:
                        Snackbar.make(coordinatorLayout, "Recent Item Selected", Snackbar.LENGTH_LONG).show();
                        break;
                    case R.id.favorite_item:
                        Snackbar.make(coordinatorLayout, "Favorite Item Selected", Snackbar.LENGTH_LONG).show();
                        break;
                    case R.id.location_item:
                        Snackbar.make(coordinatorLayout, "Location Item Selected", Snackbar.LENGTH_LONG).show();
                        break;
                }
            }
        });
    }
}
