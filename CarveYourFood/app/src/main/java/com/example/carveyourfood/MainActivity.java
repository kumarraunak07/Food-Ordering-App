package com.example.carveyourfood;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements RecyclerAdapter.OnItemListener{
    Button logout,send_recipe;
    private RecyclerAdapter adapter;
    private ArrayList<Upload> list;
    private RecyclerView recyclerView;
    private FirebaseDatabase db = FirebaseDatabase.getInstance();
    private FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        logout = findViewById(R.id.logout_btn);
        TextView user_name = findViewById(R.id.user);
        String us = user.getEmail();
        if(!us.isEmpty()){
        logout.setText("Logout");
        user_name.setText(us);}

        send_recipe = (Button) findViewById(R.id.send_reci);

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //String us = user.getUid();
                //if(us.isEmpty()){
                   //logout.setText("Login");
                   //user_name.setText(us);
                //}else{
                    //logout.setText("Logout");
                    FirebaseAuth.getInstance().signOut();
                    Intent i = new Intent(MainActivity.this, Login.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(i);
                //}

            }
        });

        DatabaseReference root = db.getReference().child("fooditems");

        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));

        list = new ArrayList<>();
        adapter = new RecyclerAdapter(MainActivity.this,list,this);
        recyclerView.setAdapter(adapter);

        root.addValueEventListener(new ValueEventListener() {
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

        send_recipe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this,create_recipe.class);
                startActivity(i);
            }
        });

    }

    @Override
    public void onItemClick(int position) {
        String id = list.get(position).getCur_id();
        String vendor = list.get(position).getUser();
        String name = list.get(position).getItem_n();
        String desc = list.get(position).getItem_desc();
        String ing = list.get(position).getItem_ing();
        String cost = list.get(position).getItem_c();
        String img = list.get(position).getImageUri();
        Intent intent = new Intent(this, Item_Info.class);
        intent.putExtra("Selected_customer", String.valueOf(list.get(position)));
        intent.putExtra("Selected_id", id);
        intent.putExtra("vendor_id", vendor);
        intent.putExtra("Item_name",name);
        intent.putExtra("Item_desc",desc);
        intent.putExtra("Item_ing",ing);
        intent.putExtra("Item_cost",cost);
        intent.putExtra("Item_img",img);
        startActivity(intent);
    }

    @Override
    public void onDeleteClick(int position) {

    }
}