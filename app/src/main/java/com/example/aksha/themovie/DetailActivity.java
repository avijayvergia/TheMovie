package com.example.aksha.themovie;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Aksha on 10/05/2017.
 */

public class DetailActivity extends AppCompatActivity{

    private static final String TAG = "DetailActivity";
    ImageView poster;
    TextView release_date,overview,vote,title;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        setContentView(R.layout.detail_view);

        poster= (ImageView) findViewById(R.id.poster);
        release_date= (TextView) findViewById(R.id.release);
        overview= (TextView) findViewById(R.id.overview);
        vote= (TextView) findViewById(R.id.vote);
        title= (TextView) findViewById(R.id.title);
        Intent intent=getIntent();

        Log.i(TAG, "onCreate: "+intent.getStringExtra("title")+" " +intent.getStringExtra("overview"));
        title.setText(intent.getStringExtra("title"));
        release_date.setText(intent.getStringExtra("release"));
        overview.setText(intent.getStringExtra("overview"));
    }
}
