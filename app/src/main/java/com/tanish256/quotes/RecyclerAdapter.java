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

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder>{
    private ArrayList<QuoteObject> Items;
    public RecyclerAdapter(ArrayList<QuoteObject> items) {
        Items = items;
    }

    @NonNull
    @Override
    public RecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_author,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerAdapter.ViewHolder holder, int position) {
        int Position = position;
        Bitmap bitmap = BitmapFactory.decodeResource(holder.imgdp.getContext().getResources(), Items.get(position).getImg());
        holder.imgdp.setImageBitmap(bitmap);
        holder.name.setText(Items.get(position).getAuthor());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(view.getContext(),AuthorviewActivity.class);
                intent.putExtra("author",Items.get(Position).getAuthor());
                intent.putExtra("tag",Items.get(Position).getTag());
                intent.putExtra("image",Items.get(Position).getImg());
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
        private ImageView imgdp;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name =itemView.findViewById(R.id.authorname);
            imgdp =itemView.findViewById(R.id.Authordp);
        }
    }
}
