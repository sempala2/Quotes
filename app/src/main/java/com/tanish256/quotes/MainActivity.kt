package com.tanish256.quotes

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.ViewPager
import com.android.volley.Cache
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.google.android.material.tabs.TabLayout
import org.json.JSONObject
import java.util.Calendar

class MainActivity : AppCompatActivity(){


    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
         var tablay =TabLayout(this)
        var dquote=ImageView(this)
        var Viewpager = ViewPager(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)



        tablay = findViewById(R.id.tab_layout)
        dquote=findViewById(R.id.dquote)
        Viewpager=findViewById(R.id.viewpager)
        dquote.setOnClickListener {
            //..
            fetchQuote()
            //Toast.makeText(this,":Loading",Toast.LENGTH_SHORT).show()

        }



        tablay.setupWithViewPager(Viewpager,true)
        //tablay.setupWithViewPager(Viewpager)
        val VpAdapter=ViewpagerAdapter(getSupportFragmentManager(),FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT)
        VpAdapter.addFragment(CategoryFragment(),"CATEGORY")
        VpAdapter.addFragment(TestFragment(),"AUTHOR")
        VpAdapter.addFragment(FavouritesFragment(),"FAVOURITES")
        Viewpager.adapter=VpAdapter
    }
    private fun fetchQuote() {

        val url = "https://favqs.com/api/qotd" // Replace with your API endpoint URL
        val queue = Volley.newRequestQueue(this)
        val cacheEntry = queue.cache.get(url)

        // Get the current date and time
        val currentTime = Calendar.getInstance()
        val currentHour = currentTime.get(Calendar.HOUR_OF_DAY)
        val currentMinute = currentTime.get(Calendar.MINUTE)
        val currentSecond = currentTime.get(Calendar.SECOND)

        // Calculate the remaining time until midnight
        val remainingHours = 23 - currentHour
        val remainingMinutes = 59 - currentMinute
        val remainingSeconds = 59 - currentSecond

        // Convert the remaining time to milliseconds
        val remainingTimeMillis =
            (remainingHours * 60 * 60 + remainingMinutes * 60 + remainingSeconds) * 1000

        // Check if the cached response exists and is still valid

        if ((cacheEntry != null) && (cacheEntry.ttl >= System.currentTimeMillis())) {
            // Cached response is still valid, retrieve and handle the quote
            val cachedResponse = String(cacheEntry.data)
            val response = JSONObject(cachedResponse)
            val quoteObject = response.getJSONObject("quote")
            val quote = quoteObject.getString("body")
            val author = quoteObject.getString("author")
            val id = quoteObject.getString("id")
            handleQuote(quote, author,id)
            return
        }

        // Instantiate the RequestQueue.
        val requestQueue = Volley.newRequestQueue(this)

        // Create a JSON object request.
        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET, url, null,
            { response ->
                // Parse the response JSON object to obtain the quote and author.
                val quoteObject = response.getJSONObject("quote")
                val quote = quoteObject.getString("body")
                val author = quoteObject.getString("author")
                val id=quoteObject.getString("id")

                // Handle the obtained quote and author.
                handleQuote(quote, author,id )

                // Cache the response until midnight
                val cacheEntry = Cache.Entry()
                cacheEntry.data = response.toString().toByteArray()
                cacheEntry.ttl = System.currentTimeMillis() + remainingTimeMillis
                queue.cache.put(url, cacheEntry)
            },
            { error ->
                // Handle error if the request fails.
                handleError(error)
            })

        // Add the request to the RequestQueue.
        requestQueue.add(jsonObjectRequest)
    }


    private fun handleQuote(quote: String, author: String,id:String) {
        Toast.makeText(this, "Daily Quote", Toast.LENGTH_LONG).show()
        var intent = Intent(this, Quote::class.java)
        intent.putExtra("quote",quote)
        intent.putExtra("author",author)
        intent.putExtra("id",id)
        startActivity(intent)
    }

    private fun handleError(error: Exception) {
        Toast.makeText(this, "Request failed: Connect to the internet", Toast.LENGTH_LONG).show()
    }


}