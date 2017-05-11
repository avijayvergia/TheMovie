package com.example.aksha.themovie;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import java.util.ArrayList;
import java.util.List;

public class MovieFragment extends Fragment {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    private static final String TAG = "Fragment";
    private static RequestQueue mRequestQueue;
    // TODO: Customize parameters
    private boolean mColumnCount = false;
    private OnListFragmentInteractionListener mListener;
    private static String jsonUrl = null;
    List<Result> items = new ArrayList<>();
    MyItemRecyclerViewAdapter adapter;

    public MovieFragment() {
    }

    public static MovieFragment newInstance(boolean layout, String json, RequestQueue queue) {
        MovieFragment fragment = new MovieFragment();
        Bundle args = new Bundle();
        jsonUrl = json;
        mRequestQueue = queue;

        args.putBoolean(ARG_COLUMN_COUNT, layout);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getBoolean(ARG_COLUMN_COUNT);
        }
        customGSONRequest();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_item_list, container, false);

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
            if (!mColumnCount) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, 2));
            }
            adapter = new MyItemRecyclerViewAdapter(items, mListener, getContext());
            recyclerView.setAdapter(adapter);
        }
        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnListFragmentInteractionListener) {
            mListener = (OnListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnListFragmentInteractionListener {
        void onListFragmentInteraction(Result item);
    }


    private Response.Listener<MyGson> createSuccessListener() {
        return new Response.Listener<MyGson>() {
            @Override
            public void onResponse(MyGson response) {
                for (int i = 0; i < response.results.size(); i++) {
                    items.add(response.results.get(i));
                    adapter.notifyItemInserted(i);
                }
            }
        };
    }

    private Response.ErrorListener createErrorListener() {
        return new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i(TAG, "onErrorResponse: " + error.getMessage());
            }
        };
    }

    public void customGSONRequest() {
        GsonRequest<MyGson> myReq = new GsonRequest<>(jsonUrl,
                MyGson.class, null,
                createSuccessListener(),
                createErrorListener());
        mRequestQueue.add(myReq);
    }
}
