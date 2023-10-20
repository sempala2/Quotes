package com.tanish256.quotes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

class CategoryFragment : Fragment() {
    private var adapter: categoryAdapter? = null
    private var recycler: RecyclerView? = null
    private var layoutManager: RecyclerView.LayoutManager? = null
    private var Quoteobject: QuoteObject? = null
    var list = ArrayList<QuoteObject>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
            val s = ( 1).toString()


        list.add(QuoteObject(R.drawable.hope,s, "", "Elon Musk", "Hope"));
        list.add(QuoteObject(R.drawable.love,s, "", "Albert Einstein", "Love"));
        list.add(QuoteObject(R.drawable.science,s, "", "William Shakespeare", "Science"));
        list.add(QuoteObject(R.drawable.life,s, "", "Aesop", "Life"));
        list.add(QuoteObject(R.drawable.religion,s, "", "Mahatma Gandhi", "Religion"));
        list.add(QuoteObject(R.drawable.logo,s, "", "Mahatma Gandhi", "God"));
        list.add(QuoteObject(R.drawable.logo,s, "", "Mahatma Gandhi", "Happiness"));
        list.add(QuoteObject(R.drawable.logo,s, "", "Mahatma Gandhi", "Men"));
        list.add(QuoteObject(R.drawable.logo,s, "", "Mahatma Gandhi", "Work"));
        list.add(QuoteObject(R.drawable.nature,s, "", "Mahatma Gandhi", "Nature"));
        list.add(QuoteObject(R.drawable.logo,s, "", "Mahatma Gandhi", "Great"));
        list.add(QuoteObject(R.drawable.logo,s, "", "Mahatma Gandhi", "Knowledge"));
        list.add(QuoteObject(R.drawable.logo,s, "", "Mahatma Gandhi", "Business"));
        list.add(QuoteObject(R.drawable.logo,s, "", "Mahatma Gandhi", "Power"));
        list.add(QuoteObject(R.drawable.logo,s, "", "Mahatma Gandhi", "Fear"));
        list.add(QuoteObject(R.drawable.politics,s, "", "Mahatma Gandhi", "Politics"));
        list.add(QuoteObject(R.drawable.funy,s, "", "Mark Twain", "Funny"));
        list.add(QuoteObject(R.drawable.logo,s, "", "Mahatma Gandhi", "History"));
        list.add(QuoteObject(R.drawable.logo,s, "", "Mahatma Gandhi", "Marriage"));
        list.add(QuoteObject(R.drawable.logo,s, "", "Mahatma Gandhi", "Government"));
        list.add(QuoteObject(R.drawable.logo,s, "", "Mahatma Gandhi", "Society"));
        list.add(QuoteObject(R.drawable.logo,s, "", "Mahatma Gandhi", "Relationship"));
        list.add(QuoteObject(R.drawable.family,s, "", "Mahatma Gandhi", "Family"));
        list.add(QuoteObject(R.drawable.art,s, "", "Mahatma Gandhi", "Art"));
        list.add(QuoteObject(R.drawable.food,s, "", "Mahatma Gandhi", "Food"));




        var view = inflater.inflate(R.layout.fragment_category, container, false)
        recycler = view?.findViewById(R.id.recycler)
        adapter = categoryAdapter(list)
        layoutManager = GridLayoutManager(context, 1, RecyclerView.VERTICAL, false)
        recycler!!.adapter = adapter

        recycler?.layoutManager = layoutManager

        return view
    }


}