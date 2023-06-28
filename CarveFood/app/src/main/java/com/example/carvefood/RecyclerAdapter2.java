package com.example.carvefood;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecyclerAdapter2 extends RecyclerView.Adapter<RecyclerAdapter2.MyViewHolder> {

    ArrayList<Upload2> mList;
    Context context;
    RecyclerAdapter2.OnItemListener monItemListener;

    public RecyclerAdapter2(Context context, ArrayList<Upload2> mList, RecyclerAdapter2.OnItemListener onItemListener){
        this.mList =  mList;
        this.context = context;
        this.monItemListener = onItemListener;
    }

    @NonNull
    @Override
    public RecyclerAdapter2.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.recycler_card_2,parent,false);
        return new RecyclerAdapter2.MyViewHolder(v,monItemListener);
    }

    @Override
    public void onBindViewHolder( RecyclerAdapter2.MyViewHolder holder, int position) {

        Upload2 upload = mList.get(position);
        holder.item_name.setText(upload.getName());
        holder.item_type.setText(upload.getType());
        holder.item_ing.setText(upload.getIngredients());
        holder.item_msg.setText(upload.getMessage());
        holder.item_extras.setText(upload.getExtras());
        holder.contact.setText(upload.getContact());

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView item_name,item_type,item_ing,item_msg,item_extras,contact;
        RecyclerAdapter2.OnItemListener onItemListener;
        Button card_accept_btn;

        public MyViewHolder(@NonNull View itemView, RecyclerAdapter2.OnItemListener onItemListener) {
            super(itemView);

            item_name = itemView.findViewById(R.id.card_item_name2);
            item_type = itemView.findViewById(R.id.card_item_type2);
            item_ing = itemView.findViewById(R.id.card_item_ing2);
            item_msg = itemView.findViewById(R.id.card_item_msg2);
            item_extras = itemView.findViewById(R.id.card_extra_ext2);
            contact = itemView.findViewById(R.id.card_item_contact2);
            this.onItemListener = onItemListener;

            card_accept_btn = itemView.findViewById(R.id.card_accept_btn);
            card_accept_btn.setOnClickListener(new View.OnClickListener() {
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
