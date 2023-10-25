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
    private TextView Quoted;
    private ImageView share;
    private TextView Author;
    private ImageView back;
    private ImageView copy;
    private ImageView like;
    boolean in=true;
    QuoteObject quote;
    SharedPreferences preferences;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quote);
        Quoted=findViewById(R.id.quoteText);
        share =findViewById(R.id.shareq);
        Author=findViewById(R.id.author);
        back=findViewById(R.id.back);
        like=findViewById(R.id.like);
        copy=findViewById(R.id.copy);

        try {
            quote=new QuoteObject(getIntent().getExtras().getString("id"),
                    getIntent().getExtras().getString("quote"),
                    getIntent().getExtras().getString("author"));
        }catch (Exception e) {
            Toast.makeText(this,e.getMessage(),Toast.LENGTH_LONG).show();
        }
        Quoted.setText(Html.fromHtml(quote.getQuote()));
        Author.setText(quote.getAuthor());
        SharedPreferences sharedPreferences = getSharedPreferences("MyPreferences", Context.MODE_PRIVATE);
        String json = sharedPreferences.getString("likedQuotes", "");

        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<QuoteObject>>(){}.getType();

        ArrayList<QuoteObject> likeq;
        likeq = gson.fromJson(json, type);
        ArrayList<QuoteObject> likedQuotesArrayList=new ArrayList<>();

        if (likeq!=null){
            likedQuotesArrayList.addAll(likeq);
            for (QuoteObject likedQuote : likedQuotesArrayList){
                if (likedQuote != null && quote != null && likedQuote.getQId() != null && likedQuote.getQId().equals(quote.getQId())) {
                    // Your code when the QIds match
                    like.setImageResource(R.drawable.favfull);
                }
            }
        }


// Now you have your ArrayList of liked quotes in likedQuotesArrayList

        like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Check if the quote is already in the list

                int positionToRemove = -1;
                boolean found = false;
                if (!likedQuotesArrayList.isEmpty()) {
                    for (int i = 0; i < likedQuotesArrayList.size(); i++) {
                        if (likedQuotesArrayList != null && likedQuotesArrayList.get(i) != null && likedQuotesArrayList.get(i).getQId() != null &&
                                quote != null && quote.getQId() != null &&
                                likedQuotesArrayList.get(i).getQId().equals(quote.getQId())) {
                            found = true;
                            positionToRemove = i;
                            break;
                        }
                    }
                }

                // Update SharedPreferences to reflect the change in likedQuotesArrayList
                SharedPreferences sharedPreferences = getSharedPreferences("MyPreferences", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();

                if (found) {
                    // The quote is already in the list; remove it
                    likedQuotesArrayList.remove(positionToRemove);
                    like.setImageResource(R.drawable.favempty);
                    Toast.makeText(getApplicationContext(), "Removed from liked quotes", Toast.LENGTH_SHORT).show();
                } else {
                    // The quote is not in the list; add it
                    likedQuotesArrayList.add(quote);
                    like.setImageResource(R.drawable.favfull);
                    Toast.makeText(getApplicationContext(), "Added to liked quotes", Toast.LENGTH_SHORT).show();
                }

                // Serialize and save likedQuotesArrayList to SharedPreferences
                Gson gson = new Gson();
                String json = gson.toJson(likedQuotesArrayList);
                editor.putString("likedQuotes", json);
                editor.apply();
            }
        });


        share.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                String shareBody=Quoted.getText()+"\n"+" By "+Author.getText();
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
                ClipData clip =ClipData.newPlainText("quote",Quoted.getText()+"\n"+"by "+Author.getText());
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

}