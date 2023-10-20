package com.tanish256.quotes;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class Quote extends AppCompatActivity {
    private TextView Quote;
    private ImageView share;
    private TextView Author;
    private ImageView back;
    private ImageView copy;
    private ImageView like;
    boolean in=true;
    QuoteObject quote;
    ArrayList<QuoteObject> quotes;
    ArrayList<QuoteObject> favquotelist;
    SharedPreferences preferences;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quote);
        Quote=findViewById(R.id.quoteText);
        share =findViewById(R.id.shareq);
        Author=findViewById(R.id.author);
        back=findViewById(R.id.back);
        like=findViewById(R.id.like);
        copy=findViewById(R.id.copy);
        quotes=new ArrayList<>();

        try {
            quote=new QuoteObject(getIntent().getExtras().getString("id"),
                    getIntent().getExtras().getString("quote"),
                    getIntent().getExtras().getString("author"));
        }catch (Exception e) {
            Toast.makeText(this,e.getMessage(),Toast.LENGTH_LONG);
        }
        quotes.add(quote);
        Quote.setText(Html.fromHtml(quote.getQuote()));
        Author.setText(quote.getAuthor());

        preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor editor = preferences.edit();
        // Load favorite quotes from SharedPreferences
        String serializedFavoriteQuote = preferences.getString("favorite_quotes", null);

        if (serializedFavoriteQuote != null) {
            favquotelist= deserializeQuote(serializedFavoriteQuote);
            for (QuoteObject quotepl : favquotelist) {
                if(quotepl.getQId().equals(quote.getQId())) {
                    like.setImageResource(R.drawable.favfull);
                    in = false;
                }
            }

        }

        like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(in) {
                    favquotelist.add(quote);
                    String qp = serializeQuote(favquotelist);
                    try{
                        editor.putString("favorite_quotes", qp);
                        editor.apply();
                    }catch (Exception e){
                        Log.e(TAG, e.getMessage());
                    }
                    //Toast.makeText(getApplicationContext(),"Added To Favourites",Toast.LENGTH_LONG).show();
                    like.setImageResource(R.drawable.favfull);
                    in = false;

                }  //else{
//
//                    for (int i = 0; i < favquotelist.size(); i++) {
//                        if (favquotelist.get(i).getQId().equals(quote.getQId())) {
//                            favquotelist.remove(i); // Remove the quote at position i
//                            Toast.makeText(getApplicationContext(),"removed",Toast.LENGTH_LONG).show();
//                            like.setImageResource(R.drawable.favempty);
//                            in = true;
//                            break; // Exit the loop once the quote is found and removed
//                        }
//                    }
//                    String qp = serializeQuote(favquotelist);
//                    editor.putString("favorite_quotes", qp);
//                    editor.apply();
//
//                }

            }
        });
        share.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                String shareBody=Quote.getText()+"\n"+" By "+Author.getText();
                /*Create an ACTION_SEND Intent*/
                Intent intent = new Intent(android.content.Intent.ACTION_SEND);
                /*This will be the actual content you wish you share.*/
                /*The type of the content is text, obviously.*/
                intent.setType("text/plain");
                /*Applying information Subject and Body.*/
                intent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Quote");
                intent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
                /*Fire!*/
                startActivity(Intent.createChooser(intent, "Share Your Quote"));
            }
        });
        copy.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                ClipData clip =ClipData.newPlainText("quote",Quote.getText()+"\n"+"by "+Author.getText());
                ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                clipboard.setPrimaryClip(clip);
                Toast.makeText(getApplicationContext(),"copied to clipboard",Toast.LENGTH_LONG).show();
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
    public String serializeQuote(ArrayList<QuoteObject> quote) {
        Gson gson = new Gson();
        return gson.toJson(quote);
    }
    public ArrayList<QuoteObject> deserializeQuote(String json) {
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<QuoteObject>>() {}.getType();
        return gson.fromJson(json, type);
    }

}