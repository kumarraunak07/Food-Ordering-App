package com.example.carvefood;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;


public class Profile extends Fragment {

    TextInputEditText user_name,address,hotel_name,email,mobile;
    DatabaseReference mDatabase;
    FirebaseAuth auth;
    FirebaseUser user;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_profile, container, false);

        user_name = v.findViewById(R.id.user_name);
        address = v.findViewById(R.id.hotel_address);
        hotel_name = v.findViewById(R.id.hotel_name);
        email = v.findViewById(R.id.user_email);
        mobile = v.findViewById(R.id.mobile);



        FirebaseDatabase database = FirebaseDatabase.getInstance();
        mDatabase = database.getReference("Signin");

        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        String user_id = user.getUid();

        String usereml = user.getEmail();
        //Toast.makeText(getContext(),usereml,Toast.LENGTH_LONG).show();

        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for(DataSnapshot dss:snapshot.getChildren()){
                    if(dss.child("email").getValue().equals(usereml)){

                        hotel_name.setText(dss.child("hotel_name").getValue(String.class));
                        address.setText(dss.child("hotel_address").getValue(String.class));
                        user_name.setText(dss.child("user_name").getValue(String.class));
                        email.setText(dss.child("email").getValue(String.class));
                        mobile.setText(dss.child("mobile").getValue(String.class));

                    }

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(),error.getMessage(),Toast.LENGTH_LONG).show();
            }
        });

        /*update_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String hn = hotel_name.getText().toString();
                String ha = address.getText().toString();
                String un = user_name.getText().toString();
                String eml = email.getText().toString();
                String mobi = mobile.getText().toString();
                

                *//*mDatabase.child(user_id).updateChildren(hashMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {

                    }
                });*//*

            }
        });
*/


        return v;
    }
}