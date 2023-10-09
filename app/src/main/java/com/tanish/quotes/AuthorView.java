package com.tanish.quotes;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.icu.text.Transliterator;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AuthorView extends RecyclerView.Adapter<AuthorView.ViewHolder>{
    private ArrayList<QuoteObject> Quotes;
    private Boolean Like;
    public AuthorView(ArrayList<QuoteObject> quote) {
        Quotes=quote;


    }

    @NonNull
    @Override
    public AuthorView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.quote_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AuthorView.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        int Position =position;
        if (Quotes.get(position).getLiked()){
            holder.liked.setImageResource(R.drawable.favfull);
        }

        holder.quotes.setText(Html.fromHtml(Quotes.get(position).getQuote()) );
        holder.author.setText(Quotes.get(position).getAuthor());
        holder.count.setText(Quotes.get(position).getQId());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(view.getContext(), Quote.class);
                intent.putExtra("author", Quotes.get(position).getAuthor());
                intent.putExtra("quote", Quotes.get(position).getQuote());
                intent.putExtra("id", Quotes.get(position).getQId());
                view.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return Quotes.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView quotes;
        private TextView author;
        private TextView count;
        private ImageView liked;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            liked=itemView.findViewById(R.id.liked);
            quotes=itemView.findViewById(R.id.quoteText);
            author=itemView.findViewById(R.id.author);
            count=itemView.findViewById(R.id.count);
        }
    }
}
