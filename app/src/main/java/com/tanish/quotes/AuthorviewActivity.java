package com.tanish.quotes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Cache;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.tanish.quotes.QuoteObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class AuthorviewActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private AuthorView adapter;
    private ImageView back_btn;
    private CallQOTD api_call;
    private String author_name;
    private String tag;
    private TextView header;
    private Button nextb;
    private Button backq;
    private TextView paget;
    String urli;
    private int page = 1;
    private boolean lastpage;
    ArrayList<QuoteObject> quotelist = new ArrayList<>();
    ProgressBar progressBar;
    TextView progress;
    private InterstitialAd mInterstitialAd;
    SharedPreferences preferences;
    ArrayList<QuoteObject> favquotelist;
    String serializedFavoriteQuote;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authorview);
        header = findViewById(R.id.header);
        progressBar=findViewById(R.id.progressBar);
        progress=findViewById(R.id.progress);
        nextb = findViewById(R.id.nxt);
        recyclerView = findViewById(R.id.recyclerview);
        back_btn = findViewById(R.id.back);
        paget=findViewById(R.id.pg);
        backq =findViewById(R.id.backq);
        preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        serializedFavoriteQuote= preferences.getString("favorite_quotes", null);

        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });

        AdRequest adRequest = new AdRequest.Builder().build();

        InterstitialAd.load(this,"ca-app-pub-8621721957934319/5398883214", adRequest,
                new InterstitialAdLoadCallback() {
                    @Override
                    public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                        // The mInterstitialAd reference will be null until
                        // an ad is loaded.
                        mInterstitialAd = interstitialAd;
                        //Log.i(TAG, "onAdLoaded");

                        if (mInterstitialAd != null) {
                            mInterstitialAd.show(AuthorviewActivity.this);
                        } else {
                            Log.d("TAG", "The interstitial ad wasn't ready yet.");
                        }
                    }

                    @Override
                    public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                        // Handle the error
                        //Log.d(TAG, loadAdError.toString());
                        mInterstitialAd = null;

                    }
                });
        if (getIntent().getExtras().getString("author") != null) {
            author_name = getIntent().getExtras().getString("author");
            urli = "https://favqs.com/api/quotes/?filter=" + author_name + "&type=author&page=";
            header.setText(author_name);
        } else if (getIntent().getExtras().getString("tag") != null){
            tag = getIntent().getExtras().getString("tag");
            urli = "https://favqs.com/api/quotes/?filter=" + tag + "&type=tag&page=";
            header.setText(tag);
        }
        nextb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!lastpage) {
                    page = page + 1;
                    requestQuotesFromAPI(page);
                } else {
                    Toast.makeText(getApplicationContext(), "This is the last page", Toast.LENGTH_LONG).show();
                }
            }
        });
        backq.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                if (!(page <=1)) {
                    page = page - 1;
                    requestQuotesFromAPI(page);
                } else {
                    Toast.makeText(getApplicationContext(), "This is the first page", Toast.LENGTH_LONG).show();
                }
            }
        });

        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        // Initialize an empty quotelist and adapter
        adapter = new AuthorView(quotelist);
        layoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(layoutManager);

        // Make API request and populate the quotelist
        requestQuotesFromAPI(page);
    }

    public void requestQuotesFromAPI(int refpage) {
        RequestQueue queue = Volley.newRequestQueue(this);

        // Enable caching
         // Clear cache before each request


        String url = urli + refpage;

        // Check if the response is available in the cache
        Cache.Entry cachedResponse = queue.getCache().get(url);
        if (cachedResponse != null && !cachedResponse.isExpired()) {
            try {
                String cachedData = new String(cachedResponse.data, "UTF-8");
                JSONObject response = new JSONObject(cachedData);
                progress.setVisibility(View.GONE);
                progressBar.setVisibility(View.GONE);
                handleResponse(response);
                if (mInterstitialAd != null) {
                    mInterstitialAd.show(this);
                } else {
                    Log.d("TAG", "The interstitial ad wasn't ready yet.");
                }
            } catch (UnsupportedEncodingException | JSONException e) {
                e.printStackTrace();
            }
        } else {


            JsonObjectRequest getRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            // Handle the response
                            handleResponse(response);

                            // Cache the response
                            Cache.Entry cacheEntry = new Cache.Entry();
                            try {
                                cacheEntry.data = response.toString().getBytes("UTF-8");
                            } catch (UnsupportedEncodingException e) {
                                e.printStackTrace();
                            }
                            cacheEntry.responseHeaders = new HashMap<>();
                            cacheEntry.ttl = System.currentTimeMillis() + 7L *24 * 60 * 60 * 1000; // Cache for 24 hours
                            queue.getCache().put(url, cacheEntry);
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.d("ERROR", "error => " + error.toString());
                            Toast.makeText(getApplicationContext(),"Connect To Intenet and Try Again",Toast.LENGTH_LONG).show();
                        }
                    }
            ) {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    params.put("Authorization", "Token token=\"e54006b61dbe0ed4cdccdd30bdc2a545\"");
                    params.put("Content-Type", "application/json");

                    return params;
                }
            };

            queue.add(getRequest);
        }
    }


    private void handleResponse(JSONObject response) {
        try {
            quotelist.clear();
            JSONArray quotesArray = response.getJSONArray("quotes");
            page = response.getInt("page");
            lastpage = response.getBoolean("last_page");

            for (int i = 0; i < quotesArray.length(); i++) {
                JSONObject quoteObject = quotesArray.getJSONObject(i);
                String id = String.valueOf(quoteObject.getInt("id"));
                String body = quoteObject.getString("body");
                String author = quoteObject.getString("author");

                String n = String.valueOf(i + 1);
                QuoteObject quote = new QuoteObject(1, id, body, author, "funny");
                if (serializedFavoriteQuote != null) {
                    favquotelist= deserializeQuote(serializedFavoriteQuote);
                    for (QuoteObject quotepl : favquotelist) {
                        if(quotepl.getQId().equals(quote.getQId())) {
                            quote.setLiked(true);
                        }
                    }

                }
                quotelist.add(quote);
            }
            String json = lastpage ? "Last Page" : "Page: " + page;

            paget.setText(json);
            progressBar.setVisibility(View.GONE);
            progress.setVisibility(View.GONE);


            // Notify the adapter about the data change
            recyclerView.setAdapter(new AuthorView(quotelist));
            adapter.notifyDataSetChanged();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    public ArrayList<QuoteObject> deserializeQuote(String json) {
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<QuoteObject>>() {}.getType();
        return gson.fromJson(json, type);
    }

}
