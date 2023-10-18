package com.tanish256.quotes;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class categoryAdapter extends RecyclerView.Adapter<categoryAdapter.ViewHolder> {
    private ArrayList<QuoteObject> Items;
    public categoryAdapter(ArrayList<QuoteObject> items) {
        Items = items;
    }

    @NonNull
    @Override
    public categoryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_author,parent,false);
        return new categoryAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull categoryAdapter.ViewHolder holder, int position) {
        int Position = position;
        Bitmap bitmap = BitmapFactory.decodeResource(holder.img.getContext().getResources(), Items.get(position).getImg());
        holder.img.setImageBitmap(bitmap);
        holder.name.setText(Items.get(position).getTag());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(view.getContext(),AuthorviewActivity.class);
                intent.putExtra("tag",Items.get(Position).getTag());
                view.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return Items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView name;
        private ImageView img;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            img =itemView.findViewById(R.id.Authordp);
            name =itemView.findViewById(R.id.authorname);
        }
    }
}
