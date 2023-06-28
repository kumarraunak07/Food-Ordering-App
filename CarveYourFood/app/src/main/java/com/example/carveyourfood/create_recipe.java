package com.example.carveyourfood;

import android.content.Intent;
import android.os.Bundle;
import android.util.ArrayMap;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class create_recipe extends AppCompatActivity {

    private TextInputEditText name,type,ing,msg,extras,contact;
    Button send_recipe_btn;
    private DatabaseReference databaseReference;
    private FirebaseAuth auth;
    private FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_recipe);

        name = (TextInputEditText) findViewById(R.id.recipe_name);
        type = (TextInputEditText) findViewById(R.id.recipe_type);
        ing = (TextInputEditText) findViewById(R.id.recipe_ing);
        msg = (TextInputEditText) findViewById(R.id.recipe_msg);
        extras = (TextInputEditText) findViewById(R.id.recipe_extras);
        contact = (TextInputEditText) findViewById(R.id.contact);

        send_recipe_btn = findViewById(R.id.send_recipe_btn);

        databaseReference = FirebaseDatabase.getInstance().getReference("neworders2");
        auth = FirebaseAuth.getInstance();

        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        String user_id = user.getUid();

        send_recipe_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(user_id.isEmpty()){
                    Toast.makeText(create_recipe.this, "Please Login First!!!", Toast.LENGTH_SHORT).show();
                }else{
                    String n = name.getText().toString();
                    String t = type.getText().toString();
                    String i = ing.getText().toString();
                    String m = msg.getText().toString();
                    String e = extras.getText().toString();
                    String c = contact.getText().toString();


                    ArrayMap<String,Object> data = new ArrayMap<>();
                    data.put("user_id",user_id);
                    data.put("name",n);
                    data.put("type",t);
                    data.put("ingredients",i);
                    data.put("message",m);
                    data.put("extras",e);
                    data.put("contact",c);
                    String orderid = databaseReference.push().getKey();
                    data.put("item_id",orderid);
                    databaseReference.child(orderid).setValue(data).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(create_recipe.this, "Order Successful!!", Toast.LENGTH_SHORT).show();
                            name.setText("");
                            type.setText("");
                            ing.setText("");
                            msg.setText("");
                            extras.setText("");
                            contact.setText("");
                            startActivity(new Intent(create_recipe.this,MainActivity.class));
                        }
                    });
                }


            }
        });

    }
}
