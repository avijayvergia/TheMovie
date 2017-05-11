package com.example.aksha.themovie;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.aksha.themovie.MovieFragment.OnListFragmentInteractionListener;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MyItemRecyclerViewAdapter extends RecyclerView.Adapter<MyItemRecyclerViewAdapter.ViewHolder> {

    private static final String TAG = "Adapter";
    final List<Result> mValues;
    private final OnListFragmentInteractionListener mListener;
    Context mContext;
    String base = "http://image.tmdb.org/t/p/w185/";

    public MyItemRecyclerViewAdapter(List<Result> items, OnListFragmentInteractionListener listener, Context a) {
        mValues = items;
        mListener = listener;
        mContext = a;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_item, parent, false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.title.setText(mValues.get(position).title);
        holder.overview.setText(mValues.get(position).overview);
        String url = base + (mValues.get(position).poster_path);
        Log.i(TAG, "onBindViewHolder: " + url + "       " + mValues.get(position).poster_path);
        Picasso.with(mContext).load(url).into(holder.poster);


        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onListFragmentInteraction(holder.mItem);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView title;
        public Result mItem;
        TextView overview;
        ImageView poster;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            title = (TextView) view.findViewById(R.id.title);
            overview = (TextView) view.findViewById(R.id.overview);
            poster = (ImageView) view.findViewById(R.id.poster);
        }


    }
}
