package com.example.carvefood;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Item_Info extends AppCompatActivity {

    TextInputEditText name,desc,ing,cost;
    ImageView image;
    Button update;
    FirebaseAuth firebaseAuth;
    FirebaseDatabase database = FirebaseDatabase.getInstance();


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item_info);

        image = (ImageView) findViewById(R.id.info_item_image);
        name = (TextInputEditText) findViewById(R.id.info_item_name);
        desc = (TextInputEditText) findViewById(R.id.info_item_description);
        ing = (TextInputEditText) findViewById(R.id.info_item_ingredients);
        cost = (TextInputEditText) findViewById(R.id.info_item_cost);

        update = findViewById(R.id.update_item_btn);




        /*String uid = firebaseAuth.getCurrentUser().getUid();*/

        if (!getIntent().hasExtra("Selected_customer") || !getIntent().hasExtra("Selected_id")) {
            Toast.makeText(this, "Something Went Wrong!!!", Toast.LENGTH_LONG).show();
            return;
        }
        Upload upload =(Upload) getIntent().getParcelableExtra("Selected_customer");
        final String id = getIntent().getStringExtra("Selected_id");
        String n = getIntent().getStringExtra("Item_name");
        String d = getIntent().getStringExtra("Item_desc");
        String i = getIntent().getStringExtra("Item_ing");
        String c = getIntent().getStringExtra("Item_cost");
        String im = getIntent().getStringExtra("Item_img");

        /*firebaseAuth = FirebaseAuth.getInstance();
        DatabaseReference root = database.getReference("fooditems");*/
        Glide.with(this).asBitmap().load(im).into(image);
        //image.setImageURI(Uri.parse(im));
        name.setText(n);
        desc.setText(d);
        ing.setText(i);
        cost.setText(c);




        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String n = name.getText().toString();
                String s = desc.getText().toString();
                String i = ing.getText().toString();
                String c = cost.getText().toString();

                FirebaseDatabase.getInstance().getReference().child("fooditems").child(id).child("item_n").setValue(n);
                FirebaseDatabase.getInstance().getReference().child("fooditems").child(id).child("item_desc").setValue(s);
                FirebaseDatabase.getInstance().getReference().child("fooditems").child(id).child("item_ing").setValue(i);
                FirebaseDatabase.getInstance().getReference().child("fooditems").child(id).child("item_c").setValue(c);
                Toast.makeText(Item_Info.this, "Item updated Successfully!!!", Toast.LENGTH_LONG).show();
                Item_Info.this.startActivity(new Intent(Item_Info.this, MainActivity.class));

            }
        });

    }
}
