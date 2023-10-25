package com.tanish256.quotes

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class AuthorView(private val Quotes: ArrayList<QuoteObject>, var cont: Context) :
    RecyclerView.Adapter<AuthorView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.quote_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, @SuppressLint("RecyclerView") position: Int) {
        val quote = Quotes[position]

        // Check if the quote is liked based on its ID
        val isLiked = isQuoteLiked(quote.qId)
        if (isLiked) {
            holder.liked.setImageResource(R.drawable.favfull)
        } else {
            holder.liked.setImageResource(R.drawable.favempty)
        }
        holder.quotes.text = Html.fromHtml(quote.quote)
        holder.author.text = quote.author
        holder.count.text = quote.qId
        holder.itemView.setOnClickListener { view ->
            val intent = Intent(view.context, Quote::class.java)
            intent.putExtra("author", Quotes[position].author)
            intent.putExtra("quote", Quotes[position].quote)
            intent.putExtra("id", Quotes[position].qId)
            view.context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return Quotes.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val quotes: TextView
        val author: TextView
        val count: TextView
        val liked: ImageView

        init {
            liked = itemView.findViewById(R.id.liked)
            quotes = itemView.findViewById(R.id.quoteText)
            author = itemView.findViewById(R.id.author)
            count = itemView.findViewById(R.id.count)
        }
    }

    private fun isQuoteLiked(quoteId: String?): Boolean {
        if (quoteId == null) {
            // Handle the case where quoteId is null
            return false // Or handle it according to your requirements
        }
        val sharedPreferences =
            cont.applicationContext.getSharedPreferences("MyPreferences", Context.MODE_PRIVATE)
        val json = sharedPreferences.getString("likedQuotes", "")
        if (json != null) {
            val gson = Gson()
            val type = object : TypeToken<ArrayList<QuoteObject?>?>() {}.type
            val likedQuotes = gson.fromJson<ArrayList<QuoteObject>>(json, type)
            if (likedQuotes != null) {
                // Check if the quote is in the likedQuotes list
                for (likedQuote in likedQuotes) {
                    if (likedQuote.qId == quoteId) {
                        return true
                    }
                }
            }
        }
        return false
    }
}