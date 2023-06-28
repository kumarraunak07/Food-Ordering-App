package com.example.carvefood;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ViewItems extends Fragment implements RecyclerAdapter.OnItemListener {

    private RecyclerView recyclerView;
    private FirebaseDatabase db = FirebaseDatabase.getInstance();
    private FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    private RecyclerAdapter adapter;
    private ArrayList<Upload> list;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_view_items, container, false);

        String userid = user.getUid();

        DatabaseReference root = db.getReference().child("fooditems");

        recyclerView = v.findViewById(R.id.view_item_recycler);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));



        list = new ArrayList<>();
        adapter = new RecyclerAdapter(this.getContext(),list,this);
        recyclerView.setAdapter(adapter);

        root.orderByChild("user").equalTo(userid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot:snapshot.getChildren()){
                    Upload upload = dataSnapshot.getValue(Upload.class);
                    list.add(upload);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



        return v;
    }

    @Override
    public void onItemClick(int position) {
        String id = list.get(position).getCur_id();
        String name = list.get(position).getItem_n();
        String desc = list.get(position).getItem_desc();
        String ing = list.get(position).getItem_ing();
        String cost = list.get(position).getItem_c();
        String img = list.get(position).getImageUri();
        Intent intent = new Intent(getContext(), Item_Info.class);
        intent.putExtra("Selected_customer", String.valueOf(list.get(position)));
        intent.putExtra("Selected_id", id);
        intent.putExtra("Item_name",name);
        intent.putExtra("Item_desc",desc);
        intent.putExtra("Item_ing",ing);
        intent.putExtra("Item_cost",cost);
        intent.putExtra("Item_img",img);
        startActivity(intent);
    }

    @Override
    public void onDeleteClick(int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        String id = list.get(position).getCur_id();

        builder.setMessage("Are You Sure?").setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                FirebaseDatabase.getInstance().getReference("fooditems").child(id).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                    public void onComplete(Task<Void> task) {
                        Toast.makeText(getContext(), "Item Deleted Successfully!!!", Toast.LENGTH_LONG).show();
                        startActivity(new Intent(getContext(), MainActivity.class));
                    }
                });
            }
        }).setNegativeButton("Cancel",null);
        AlertDialog alert = builder.create();
        alert.show();

    }
}