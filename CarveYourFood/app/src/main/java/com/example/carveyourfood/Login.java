package com.example.carveyourfood;

import android.app.Activity;
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

public class Login extends AppCompatActivity {

    TextInputEditText ed_user_email,ed_user_password;
    Button login_btn;
    FirebaseAuth.AuthStateListener mAuthListener;
    FirebaseUser user;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference();
        mAuth = FirebaseAuth.getInstance();

        ed_user_email = findViewById(R.id.ed_username);
        ed_user_password = findViewById(R.id.ed_password);
        login_btn = findViewById(R.id.login_btn);

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            public void onAuthStateChanged(FirebaseAuth firebaseAuth) {
                Login login = Login.this;
                login.user = login.mAuth.getCurrentUser();
                if (Login.this.user != null) {
                    Intent intent = new Intent(Login.this, MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                    Login.this.startActivity(intent);
                    Login.this.finish();
                    return;
                }
                Toast.makeText(Login.this, "Login to Continue...!", Toast.LENGTH_LONG).show();
            }
        };

        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String em = Login.this.ed_user_email.getText().toString().trim();
                String pa = Login.this.ed_user_password.getText().toString().trim();
                if (TextUtils.isEmpty(em) || TextUtils.isEmpty(pa)) {
                    Toast.makeText(Login.this, "Fill all Fields!!", Toast.LENGTH_LONG).show();
                } else {
                    mAuth.signInWithEmailAndPassword(em, pa).addOnCompleteListener((Activity) Login.this, new OnCompleteListener<AuthResult>() {
                        public void onComplete(Task<AuthResult> task) {
                            if (!task.isSuccessful()) {
                                Toast.makeText(Login.this, "Enter Correct Credentials!", Toast.LENGTH_LONG).show();
                                return;
                            }
                            Toast.makeText(Login.this, "Login Successful!!!", Toast.LENGTH_LONG).show();
                            Login.this.startActivity(new Intent(Login.this, MainActivity.class));
                        }
                    });
                }
            }
        });

    }
    public void click(View v){
        startActivity(new Intent(this,Signin.class));
    }

}
