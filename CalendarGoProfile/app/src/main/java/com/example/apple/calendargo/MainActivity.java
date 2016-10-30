package com.example.apple.calendargo;

import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.*;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.*;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnMenuTabSelectedListener;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {

    private CoordinatorLayout coordinatorLayout;
    private Fragment f;
    private SearchView mSearchView;
    private TextView mStatusView;

    //Defining Variables
    private Toolbar toolbar;
    private NavigationView navigationView;
    private DrawerLayout drawerLayout;
    private CircleImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        // Initializing Toolbar and setting it as the actionbar
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // disable the title of the toolbar
        getSupportActionBar().setDisplayShowTitleEnabled(false);

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

        //Initializing NavigationView
        navigationView = (NavigationView) findViewById(R.id.navigation_view);

        //Setting Navigation View Item Selected Listener to handle the item click of the navigation menu
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener(){
            // This method will trigger on item Click of navigation menu
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {


                //Checking if the item is in checked state or not, if not make it in checked state
                if (menuItem.isChecked()) menuItem.setChecked(false);
                else menuItem.setChecked(true);

                //Closing drawer on item click
                drawerLayout.closeDrawers();

                //Check to see which item was being clicked and perform appropriate action
                switch (menuItem.getItemId()) {


                    //Replacing the main content with ContentFragment Which is our Inbox View;
                    case R.id.sign_up:
                        Toast.makeText(getApplicationContext(), "Sign Up", Toast.LENGTH_SHORT).show();
                        return true;
                    default:
                        Toast.makeText(getApplicationContext(),"Somethings Wrong",Toast.LENGTH_SHORT).show();
                        return true;
                }


            }
        });

        // Initializing Drawer Layout and ActionBarToggle
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer);
        android.support.v7.app.ActionBarDrawerToggle actionBarDrawerToggle = new android.support.v7.app.ActionBarDrawerToggle(this,drawerLayout,null,R.string.openDrawer, R.string.closeDrawer){

            @Override
            public void onDrawerClosed(View drawerView) {
                // Code here will be triggered once the drawer closes as we dont want anything to happen so we leave this blank
                super.onDrawerClosed(drawerView);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                // Code here will be triggered once the drawer open as we dont want anything to happen so we leave this blank

                super.onDrawerOpened(drawerView);
            }
        };

        //Setting the actionbarToggle to drawer layout
        drawerLayout.setDrawerListener(actionBarDrawerToggle);

        imageView = (CircleImageView) findViewById(R.id.profile_image);
        final DrawerLayout mDrawer = (DrawerLayout) findViewById(R.id.drawer);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.openDrawer(Gravity.LEFT);
            }


        });

        //calling sync state is necessay or else your hamburger icon wont show up
        actionBarDrawerToggle.syncState();
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
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
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
