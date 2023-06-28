package com.example.carvefood;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class Sign_in extends AppCompatActivity {

    TextInputEditText confirm_password,email,name,number,password,hotel_name,address;
    DatabaseReference databaseReference;
    FirebaseAuth mAuth;
    Button sign_in;
    FirebaseUser user;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);

        hotel_name = findViewById(R.id.hotel_name);
        address = findViewById(R.id.address);
        name = findViewById(R.id.full_name);
        email = findViewById(R.id.email);
        number = findViewById(R.id.mobile_no);
        password = findViewById(R.id.password);
        confirm_password = findViewById(R.id.confirm_password);
        sign_in = findViewById(R.id.signin_btn);
        databaseReference = FirebaseDatabase.getInstance().getReference("Signin");
        FirebaseAuth instance = FirebaseAuth.getInstance();
        mAuth = instance;
        user = instance.getCurrentUser();
        sign_in.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                data();
            }
        });
    }


    public void data() {
        String hn = hotel_name.getText().toString().trim();
        String addr = address.getText().toString().trim();
        String n = name.getText().toString().trim();
        String e = email.getText().toString().trim();
        String num = number.getText().toString().trim();
        String p = password.getText().toString().trim();
        String cp = confirm_password.getText().toString().trim();
        if (TextUtils.isEmpty(n) || TextUtils.isEmpty(e) || TextUtils.isEmpty(p) || TextUtils.isEmpty(cp) || TextUtils.isEmpty(hn) || TextUtils.isEmpty(addr) || TextUtils.isEmpty(num)) {
            Toast.makeText(this, "Please fill-out all Field's", Toast.LENGTH_LONG).show();
        } else if (p.equals(cp)) {

            final String str = n;
            final String str2 = e;
            final String str3 = num;
            final String str4 = p;
            final String str5 = hn;
            final String str6= addr;

            mAuth.createUserWithEmailAndPassword(e, p).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                public void onComplete(Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        String id = Sign_in.this.databaseReference.push().getKey();
                        User user = new User(id,str5,str6,str,str2,str3,str4);

                        Sign_in.this.databaseReference.child(id).setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful()){
                                    Toast.makeText(Sign_in.this, "Sign-in Successfully!!", Toast.LENGTH_LONG).show();
                                    name.setText("");
                                    email.setText("");
                                    number.setText("");
                                    password.setText("");
                                    confirm_password.setText("");
                                    hotel_name.setText("");
                                    address.setText("");

                                }
                                else{
                                    Toast.makeText(Sign_in.this, "Something is wrong!!!!!", Toast.LENGTH_LONG).show();
                                }

                            }
                        });


                        return;
                    }
                    Toast.makeText(Sign_in.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                }
            });
        } else {
            Toast.makeText(this, "Password Does not Match...!", Toast.LENGTH_LONG).show();
        }
    }


    public void click(View v) {
        startActivity(new Intent(this, Login_page.class));
    }

}
