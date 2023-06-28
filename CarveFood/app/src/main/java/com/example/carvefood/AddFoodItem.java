package com.example.carvefood;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class AddFoodItem extends Fragment {

    private static final int PICK_IMAGE_REQUEST = 1;
    private static final int RESULT_OK = -1;
    private Button button_choose_image,item_add_btn;
    private TextInputEditText et_item_name,et_item_description,et_item_ingredients,et_item_cost;
    private ImageView imageView;
    private Uri image_uri;

    private StorageReference storageReference;
    private DatabaseReference databaseReference;
    private FirebaseAuth auth;
    private FirebaseUser user;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_add_food_item, container, false);


        et_item_name = v.findViewById(R.id.item_name);
        et_item_description = v.findViewById(R.id.item_description);
        et_item_ingredients = v.findViewById(R.id.item_ingredients);
        et_item_cost = v.findViewById(R.id.item_cost);
        imageView = v.findViewById(R.id.item_image_view);
        button_choose_image = v.findViewById(R.id.select_image);

        item_add_btn = v.findViewById(R.id.add_item_btn);

        storageReference = FirebaseStorage.getInstance().getReference("uploads");
        databaseReference = FirebaseDatabase.getInstance().getReference("fooditems");

        button_choose_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openImageChooser();
            }
        });
        item_add_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadData();
            }
        });

        return v;
    }
    private  void openImageChooser(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,PICK_IMAGE_REQUEST);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null  && data.getData() != null){
            image_uri=data.getData();
            imageView.setImageURI(image_uri);
        }
    }
    private String getFileExt(Uri uri){
        ContentResolver cr = getContext().getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cr.getType(uri));
    }
    private void uploadData(){

        if(image_uri!=null){

            StorageReference fileref = storageReference.child(System.currentTimeMillis()+"."+getFileExt(image_uri));
            fileref.putFile(image_uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                    fileref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            //FirebaseDatabase database = FirebaseDatabase.getInstance();
                            //databaseReference = database.getReference("Signin");

                            auth = FirebaseAuth.getInstance();
                            user = auth.getCurrentUser();
                            String user_id = user.getUid();
                            String name = et_item_name.getText().toString();
                            String desc = et_item_description.getText().toString();
                            String ingi = et_item_ingredients.getText().toString();
                            String cost =et_item_cost.getText().toString();
                            String modelid = databaseReference.push().getKey();
                            Upload upload = new Upload(uri.toString(),name,desc,ingi,cost,user_id,modelid);
                            databaseReference.child(modelid).setValue(upload).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Toast.makeText(getContext(), "Upload Successful!!", Toast.LENGTH_SHORT).show();
                                    imageView.setImageResource(0);
                                    et_item_name.setText("");
                                    et_item_description.setText("");
                                    et_item_ingredients.setText("");
                                    et_item_cost.setText("");
                                }
                            });

                        }
                    });


                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(getContext(),e.getMessage(),Toast.LENGTH_LONG).show();
                }
            })/*.addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {

                }
            })*/;

        }else{
            Toast.makeText(getContext(), "No File Selected",Toast.LENGTH_SHORT).show();
        }

    }
}