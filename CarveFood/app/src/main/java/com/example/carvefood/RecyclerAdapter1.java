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

public class RecyclerAdapter1 extends RecyclerView.Adapter<RecyclerAdapter1.MyViewHolder> {


    ArrayList<Upload1> mList;
    Context context;
    OnItemListener monItemListener;

    public RecyclerAdapter1(Context context, ArrayList<Upload1> mList,OnItemListener onItemListener){
        this.mList =  mList;
        this.context = context;
        this.monItemListener = onItemListener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.recycler_card_1,parent,false);
        return new MyViewHolder(v,monItemListener);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        Upload1 upload = mList.get(position);
        holder.item_name.setText(upload.getName());
        holder.item_desc.setText(upload.getDesc());
        holder.item_ing.setText(upload.getIng());
        holder.item_cost.setText(upload.getCost());
        holder.extra_ing.setText(upload.getExtraing());
        holder.extra_msg.setText(upload.getExtramsg());
        //Glide.with(context).load(mList.get(position).getImage_u()).into(holder.item_image);

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView item_name,item_desc,item_ing,item_cost,extra_ing,extra_msg;
        //ImageView item_image;
        OnItemListener onItemListener;
        Button card_delete_btn;

        public MyViewHolder(@NonNull View itemView,OnItemListener onItemListener) {
            super(itemView);

            item_name = itemView.findViewById(R.id.card_item_name1);
            item_desc = itemView.findViewById(R.id.card_item_desc1);
            item_ing = itemView.findViewById(R.id.card_item_ing1);
            item_cost = itemView.findViewById(R.id.card_item_cost1);
            //item_image = itemView.findViewById(R.id.card_item_image1);
            extra_ing = itemView.findViewById(R.id.card_extra_ing1);
            extra_msg = itemView.findViewById(R.id.card_extra_msg1);
            this.onItemListener = onItemListener;

            card_delete_btn = itemView.findViewById(R.id.card_delete_btn);
            card_delete_btn.setOnClickListener(new View.OnClickListener() {
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
