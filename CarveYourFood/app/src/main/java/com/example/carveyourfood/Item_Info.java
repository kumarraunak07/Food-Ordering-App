package com.example.carveyourfood;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.ArrayMap;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;

public class Item_Info extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST = 1;
    private static final int RESULT_OK = -1;
    private TextInputEditText name,desc,ing,cost,msg,extra_ing;
    ImageView image;
    Button place_order_btn;
    //private StorageReference storageReference;
    private DatabaseReference databaseReference;
    private FirebaseAuth auth;
    private FirebaseUser user;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item_info);

        image = (ImageView) findViewById(R.id.info_item_image);
        name = (TextInputEditText) findViewById(R.id.info_item_name);
        desc = (TextInputEditText) findViewById(R.id.info_item_description);
        ing = (TextInputEditText) findViewById(R.id.info_item_ingredients);
        cost = (TextInputEditText) findViewById(R.id.info_item_cost);
        msg = (TextInputEditText) findViewById(R.id.item_extra_msg);
        extra_ing = (TextInputEditText) findViewById(R.id.item_extra_ing);

        place_order_btn = findViewById(R.id.place_order_btn);

        //storageReference = FirebaseStorage.getInstance().getReference("newuploads");
        databaseReference = FirebaseDatabase.getInstance().getReference("neworders");
        auth = FirebaseAuth.getInstance();

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
        String vendor = getIntent().getStringExtra("vendor_id");



        Glide.with(this).asBitmap().load(im).into(image);

        name.setText(n);
        desc.setText(d);
        ing.setText(i);
        cost.setText(c);

        place_order_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                auth = FirebaseAuth.getInstance();
                user = auth.getCurrentUser();
                String user_id = user.getUid();

                if(user_id.isEmpty()){
                    Toast.makeText(Item_Info.this, "Please Login First!!!", Toast.LENGTH_SHORT).show();
                }else{
                    String na = name.getText().toString();
                    String de = desc.getText().toString();
                    String in = ing.getText().toString();
                    String co = cost.getText().toString();
                    String ext_ing = extra_ing.getText().toString();
                    String ext_msg = msg.getText().toString();
                    //Upload upload = new Upload(na,de,in,co,user_id);
                    ArrayMap<String,Object> data = new ArrayMap<>();
                    data.put("id",user_id);
                    data.put("name",na);
                    data.put("desc",de);
                    data.put("ing",in);
                    data.put("cost",co);
                    data.put("extraing",ext_ing);
                    data.put("extramsg",ext_msg);
                    data.put("vendor",vendor);
                    String orderid = databaseReference.push().getKey();
                    data.put("item_id",orderid);
                    databaseReference.child(orderid).setValue(data).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(Item_Info.this, "Order Successful!!", Toast.LENGTH_SHORT).show();
                            image.setImageResource(0);
                            name.setText("");
                            desc.setText("");
                            ing.setText("");
                            cost.setText("");
                            extra_ing.setText("");
                            msg.setText("");
                            startActivity(new Intent(Item_Info.this,MainActivity.class));
                        }
                    });
                }



            }
        });

    }

}
