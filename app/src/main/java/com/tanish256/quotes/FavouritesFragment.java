package com.tanish256.quotes;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class FavouritesFragment extends Fragment {

    ArrayList<QuoteObject> list=new ArrayList<>();
    AuthorView adapter;
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    public FavouritesFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



    }
    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_favourites, container, false);
        // To load a favorite quote
        recyclerView=view.findViewById(R.id.recyclerview);
        ArrayList<QuoteObject> likedQuotesArrayList = new ArrayList<>();
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("MyPreferences", Context.MODE_PRIVATE);
        String json = sharedPreferences.getString("likedQuotes", "");

        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<QuoteObject>>(){}.getType();
        likedQuotesArrayList = gson.fromJson(json, type);

        if (likedQuotesArrayList!=null) {
            adapter = new AuthorView(likedQuotesArrayList,getContext());
            layoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
            recyclerView.setAdapter(adapter);
            recyclerView.setLayoutManager(layoutManager);
            adapter.notifyDataSetChanged();

        }



        return view;

    }
}