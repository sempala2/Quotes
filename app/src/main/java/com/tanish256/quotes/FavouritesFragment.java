package com.tanish256.quotes;

import android.annotation.SuppressLint;
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

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        String serializedFavoriteQuote = preferences.getString("favorite_quotes", null);
        if (serializedFavoriteQuote != null) {
            ArrayList<QuoteObject> favoriteQuote = deserializeQuote(serializedFavoriteQuote);
            for (QuoteObject quotepl : favoriteQuote) {
                quotepl.setLiked(true);
            }
            adapter = new AuthorView(favoriteQuote);
            layoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
            recyclerView.setAdapter(adapter);
            recyclerView.setLayoutManager(layoutManager);
            Toast.makeText(getContext(),String.valueOf(favoriteQuote.size()),Toast.LENGTH_SHORT).show();
            adapter.notifyDataSetChanged();

        }
        else{
            Toast.makeText(getContext(),"empty",Toast.LENGTH_SHORT).show();
        }


        return view;

    }
    public ArrayList<QuoteObject> deserializeQuote(String json) {
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<QuoteObject>>() {}.getType();
        return gson.fromJson(json, type);
    }
}