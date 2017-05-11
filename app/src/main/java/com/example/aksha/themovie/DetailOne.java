package com.example.aksha.themovie;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class DetailOne extends AppCompatActivity {


    private static final String TAG = "DetailOneActivity";
    ImageView poster;
    TextView release_date,overview,vote,title;
    String url="http://image.tmdb.org/t/p/w185/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_one);
        poster= (ImageView) findViewById(R.id.poster);
        release_date= (TextView) findViewById(R.id.release);
        overview= (TextView) findViewById(R.id.overview);
        vote= (TextView) findViewById(R.id.vote);
        title= (TextView) findViewById(R.id.title);


        Bundle intent=getIntent().getExtras();

        if(intent!=null){
            Log.i(TAG, "onCreate: "+intent.getString("title")+" " +intent.getString("overview")+"  "+intent.get("poster"));
            title.setText(intent.getString("title"));
            release_date.setText(intent.getString("release"));
            overview.setText(intent.getString("overview"));
            url=url+intent.getString("poster");

            Picasso.with(this).load(url).into(poster);
        }
        else{
            Log.i(TAG, "onCreate: Oh no");
        }
    }
}
