package com.example.aksha.themovie;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.android.volley.RequestQueue;

public class MainActivity extends AppCompatActivity implements MovieFragment.OnListFragmentInteractionListener {

    private static final String TAG = "MainActivity";
    BottomNavigationView navigation;
    RequestQueue mRequestQueue = null;
    boolean layout = false;

    private static final String popularList = "https://api.themoviedb.org/3/movie/popular?api_key=f26dd9ac67f0230519eabc8413019dd1&language=en-US&page=1";
    private static final String upcomingList = "https://api.themoviedb.org/3/movie/upcoming?api_key=f26dd9ac67f0230519eabc8413019dd1&language=en-US&page=1";
    private static final String currentList = "https://api.themoviedb.org/3/movie/now_playing?api_key=f26dd9ac67f0230519eabc8413019dd1&language=en-US&page=1";

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            setFrag(item);
            return true;
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setTitle("Popular Movies");
        mRequestQueue = MySingleton.getInstance(this.getApplicationContext()).
                getRequestQueue();
        navigation = (BottomNavigationView) findViewById(R.id.navigation);
        Fragment fragment = MovieFragment.newInstance(layout, popularList, mRequestQueue);
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.content, fragment, fragment.getTag());
        ft.commit();
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    @Override
    public void onListFragmentInteraction(Result item) {
        Intent intent = new Intent(this, DetailOne.class);
        intent.putExtra("title", item.title);
        intent.putExtra("release", item.release_date);
        intent.putExtra("vote", item.vote_average);
        intent.putExtra("overview", item.overview);
        intent.putExtra("poster",item.backdrop_path);
        startActivity(intent);
    }

    public void setFrag(MenuItem item) {
        Fragment fragment = null;
        switch (item.getItemId()) {
            case R.id.navigation_home:
                fragment = MovieFragment.newInstance(layout, popularList, mRequestQueue);
                break;
            case R.id.navigation_dashboard:
                fragment = MovieFragment.newInstance(layout, currentList, mRequestQueue);
                break;
            case R.id.navigation_notifications:
                fragment = MovieFragment.newInstance(layout, upcomingList, mRequestQueue);
                break;
        }

        for (int i = 0; i < navigation.getMenu().size(); i++) {
            MenuItem menuItem = navigation.getMenu().getItem(i);
            menuItem.setChecked(menuItem.getItemId() == item.getItemId());
        }

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(item.getTitle());
        }


        if (fragment != null) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.content, fragment, fragment.getTag());
            ft.commit();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.select, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.selectIt) {
            layout = !layout;
            setFrag(navigation.getMenu().findItem(navigation.getSelectedItemId()));
        }
        return true;
    }
}
