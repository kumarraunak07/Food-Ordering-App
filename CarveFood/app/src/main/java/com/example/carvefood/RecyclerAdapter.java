package com.example.carvefood;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.MyViewHolder> {


    ArrayList<Upload> mList;
    Context context;
    OnItemListener monItemListener;

    public RecyclerAdapter(Context context, ArrayList<Upload> mList,OnItemListener onItemListener){
        this.mList =  mList;
        this.context = context;
        this.monItemListener = onItemListener;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.recycler_card,parent,false);
        return new MyViewHolder(v,monItemListener);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        Upload upload = mList.get(position);
        holder.item_name.setText(upload.getItem_n());
        holder.item_desc.setText(upload.getItem_desc());
        holder.item_ing.setText(upload.getItem_ing());
        holder.item_cost.setText(upload.getItem_c());
        Glide.with(context).load(mList.get(position).getImageUri()).into(holder.item_image);

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView item_name,item_desc,item_ing,item_cost;
        ImageView item_image;
        OnItemListener onItemListener;
        Button card_delete;

        public MyViewHolder(@NonNull View itemView,OnItemListener onItemListener) {
            super(itemView);

            item_name = itemView.findViewById(R.id.card_item_name);
            item_desc = itemView.findViewById(R.id.card_item_desc);
            item_ing = itemView.findViewById(R.id.card_item_ing);
            item_cost = itemView.findViewById(R.id.card_item_cost);
            item_image = itemView.findViewById(R.id.card_item_image);
            this.onItemListener = onItemListener;

            card_delete = itemView.findViewById(R.id.card_delete_btn);
            card_delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onItemListener != null){
                        int position = getAdapterPosition();
                        if(position != RecyclerView.NO_POSITION){
                            onItemListener.onDeleteClick(position);
                        }
                    }
                }
            });

            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            onItemListener.onItemClick(getAdapterPosition());
        }
    }

    public interface OnItemListener{
        void onItemClick(int position);
        void onDeleteClick(int position);
    }

}
