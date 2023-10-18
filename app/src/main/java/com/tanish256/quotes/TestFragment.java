package com.tanish256.quotes;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;


public class TestFragment extends Fragment {

    private RecyclerAdapter adapter;
    private RecyclerView recycler;
    private RecyclerView.LayoutManager layoutManager;
    //ArrayList<String> list=new ArrayList<>();
    ArrayList<QuoteObject> list=new ArrayList<>();

    public TestFragment() {
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
        View view = inflater.inflate(R.layout.fragment_test, container, false);
        recycler=view.findViewById(R.id.rec);
        String s = "1";

        list.add(new QuoteObject(R.drawable.marktwain,  "Mark Twain"));
        list.add(new QuoteObject(R.drawable.elonmusk, "Elon Musk"));
        list.add(new QuoteObject(R.drawable.albertan,"Albert Einstein"));
        list.add(new QuoteObject(R.drawable.williamsp, "William Shakespeare"));
        list.add(new QuoteObject(R.drawable.aesop, "Aesop"));
        list.add(new QuoteObject(R.drawable.mahatma, "Mahatma Gandhi"));
        list.add(new QuoteObject(R.drawable.abrahamlcn,"Abraham Lincoln"));
        list.add(new QuoteObject(R.drawable.benjaminfk,"Benjamin Franklin"));
        list.add(new QuoteObject(R.drawable.wch,"Winston Churchill"));
        list.add(new QuoteObject(R.drawable.mlk,"Martin Luther King"));
        list.add(new QuoteObject(R.drawable.woodyalen,"Woody Allen"));
        list.add(new QuoteObject(R.drawable.anon,"Anonymous"));
        list.add(new QuoteObject(R.drawable.henrydt,"Henry David Thoreau"));
        list.add(new QuoteObject(R.drawable.ralpw,"Ralph Waldo Emerson"));
        list.add(new QuoteObject(R.drawable.friedrickn,"Friedrich Nietzsche"));
        list.add(new QuoteObject(R.drawable.oscaw, "Oscar Wilde"));
        list.add(new QuoteObject(R.drawable.wd,"Walt Disney"));
        list.add(new QuoteObject(R.drawable.stevej,"Steve Jobs"));
        list.add(new QuoteObject(R.drawable.samuelj,"Samuel Johnson"));
        list.add(new QuoteObject(R.drawable.jfk,"John F. Kennedy"));
        list.add(new QuoteObject(R.drawable.napoleonb,"Napoleon Bonaparte"));



        adapter=new RecyclerAdapter(list);
        layoutManager = new GridLayoutManager(getContext(),2,RecyclerView.VERTICAL,false);
        recycler.setAdapter(adapter);
        recycler.setLayoutManager(layoutManager);

        return view;
    }
}