package com.tanish.quotes

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


        list.add(QuoteObject(R.drawable.science,s, "", "Elon Musk", "science"));
        list.add(QuoteObject(R.drawable.hope,s, "", "Albert Einstein", "hope"));
        list.add(QuoteObject(R.drawable.love,s, "", "William Shakespeare", "love"));
        list.add(QuoteObject(R.drawable.life,s, "", "Aesop", "life"));
        list.add(QuoteObject(R.drawable.religion,s, "", "Mahatma Gandhi", "religion"));
        list.add(QuoteObject(R.drawable.logo,s, "", "Mahatma Gandhi", "God"));
        list.add(QuoteObject(R.drawable.logo,s, "", "Mahatma Gandhi", "happiness"));
        list.add(QuoteObject(R.drawable.logo,s, "", "Mahatma Gandhi", "men"));
        list.add(QuoteObject(R.drawable.logo,s, "", "Mahatma Gandhi", "work"));
        list.add(QuoteObject(R.drawable.nature,s, "", "Mahatma Gandhi", "nature"));
        list.add(QuoteObject(R.drawable.logo,s, "", "Mahatma Gandhi", "great"));
        list.add(QuoteObject(R.drawable.logo,s, "", "Mahatma Gandhi", "knowledge"));
        list.add(QuoteObject(R.drawable.logo,s, "", "Mahatma Gandhi", "business"));
        list.add(QuoteObject(R.drawable.logo,s, "", "Mahatma Gandhi", "power"));
        list.add(QuoteObject(R.drawable.logo,s, "", "Mahatma Gandhi", "fear"));
        list.add(QuoteObject(R.drawable.politics,s, "", "Mahatma Gandhi", "politics"));
        list.add(QuoteObject(R.drawable.funy,s, "", "Mark Twain", "funny"));
        list.add(QuoteObject(R.drawable.logo,s, "", "Mahatma Gandhi", "history"));
        list.add(QuoteObject(R.drawable.logo,s, "", "Mahatma Gandhi", "marriage"));
        list.add(QuoteObject(R.drawable.logo,s, "", "Mahatma Gandhi", "government"));
        list.add(QuoteObject(R.drawable.logo,s, "", "Mahatma Gandhi", "society"));
        list.add(QuoteObject(R.drawable.logo,s, "", "Mahatma Gandhi", "relationship"));
        list.add(QuoteObject(R.drawable.family,s, "", "Mahatma Gandhi", "family"));
        list.add(QuoteObject(R.drawable.art,s, "", "Mahatma Gandhi", "art"));
        list.add(QuoteObject(R.drawable.food,s, "", "Mahatma Gandhi", "food"));




        var view = inflater.inflate(R.layout.fragment_category, container, false)
        recycler = view?.findViewById(R.id.recycler)
        adapter = categoryAdapter(list)
        layoutManager = GridLayoutManager(context, 2, RecyclerView.VERTICAL, false)
        recycler!!.adapter = adapter

        recycler?.layoutManager = layoutManager

        return view
    }


}