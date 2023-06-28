package com.example.carveyourfood;

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

public class Signin extends AppCompatActivity {

    TextInputEditText ed_user_name,ed_user_email,ed_user_contact,ed_user_address,ed_user_password,ed_confirm_password;
    Button sign_in_btn;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signin);

        ed_user_name = (TextInputEditText) findViewById(R.id.user_name);
        ed_user_email = (TextInputEditText) findViewById(R.id.user_email);
        ed_user_contact = (TextInputEditText) findViewById(R.id.user_contact);
        ed_user_address = (TextInputEditText) findViewById(R.id.user_address);
        ed_user_password = (TextInputEditText) findViewById(R.id.user_password);
        ed_confirm_password = (TextInputEditText) findViewById(R.id.user_c_password);

        sign_in_btn = (Button) findViewById(R.id.signin_btn);


        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("usersignin");
        FirebaseAuth mAuth = FirebaseAuth.getInstance();


        sign_in_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = ed_user_name.getText().toString().trim();
                String email = ed_user_email.getText().toString().trim();
                String contact = ed_user_contact.getText().toString().trim();
                String address = ed_user_address.getText().toString().trim();
                String password = ed_user_password.getText().toString().trim();
                String c_password = ed_confirm_password.getText().toString().trim();

                if(TextUtils.isEmpty(name) || TextUtils.isEmpty(email) || TextUtils.isEmpty(contact) || TextUtils.isEmpty(address) || TextUtils.isEmpty(password) || TextUtils.isEmpty(c_password)){
                    Toast.makeText(Signin.this,"Please Fill All Field's!!!",Toast.LENGTH_LONG).show();
                }else if(password.equals(c_password)){

                    mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if(task.isSuccessful()){

                                FirebaseUser user = mAuth.getCurrentUser();
                                String user_id = user.getUid();
                                UserData ud = new UserData(name,email,contact,address,password,user_id);
                                myRef.child(user_id).setValue(ud).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if(task.isSuccessful()){
                                            Toast.makeText(Signin.this,"Sign-in Successful!!!",Toast.LENGTH_LONG).show();
                                            ed_user_name.setText("");
                                            ed_user_email.setText("");
                                            ed_user_contact.setText("");
                                            ed_user_address.setText("");
                                            ed_user_password.setText("");
                                            ed_confirm_password.setText("");
                                            startActivity(new Intent(Signin.this,Login.class));
                                        }
                                        else{
                                            Toast.makeText(Signin.this,"Sign-in Failed!!!",Toast.LENGTH_LONG).show();
                                        }

                                    }
                                });
                            }else{
                                Toast.makeText(Signin.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                            }

                        }
                    });

                }else{
                    Toast.makeText(Signin.this, "Password Does not Match...!", Toast.LENGTH_LONG).show();
                }
            }
        });


    }

    public void lclick(View v){startActivity(new Intent(this,Login.class));}
}
