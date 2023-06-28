package com.example.carvefood;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Login_page extends AppCompatActivity {

    TextInputEditText email;
    Button login_btn;
    FirebaseAuth mAuth;
    FirebaseAuth.AuthStateListener mAuthListener;
    TextInputEditText password;
    FirebaseUser user;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference();
        mAuth = FirebaseAuth.getInstance();

        email = (TextInputEditText) findViewById(R.id.Username);
        password = (TextInputEditText) findViewById(R.id.Password);
        login_btn = (Button) findViewById(R.id.login_btn);

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            public void onAuthStateChanged(FirebaseAuth firebaseAuth) {
                Login_page login = Login_page.this;
                login.user = login.mAuth.getCurrentUser();
                if (Login_page.this.user != null) {
                    Intent intent = new Intent(Login_page.this, MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                    Login_page.this.startActivity(intent);
                    Login_page.this.finish();
                    return;
                }
                Toast.makeText(Login_page.this, "Login to Continue...!", Toast.LENGTH_LONG).show();
            }
        };

        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String em = Login_page.this.email.getText().toString().trim();
                String pa = Login_page.this.password.getText().toString().trim();
                if (TextUtils.isEmpty(em) || TextUtils.isEmpty(pa)) {
                    Toast.makeText(Login_page.this, "Fill all Fields!!", Toast.LENGTH_LONG).show();
                } else {
                    mAuth.signInWithEmailAndPassword(em, pa).addOnCompleteListener((Activity) Login_page.this, new OnCompleteListener<AuthResult>() {
                        public void onComplete(Task<AuthResult> task) {
                            if (!task.isSuccessful()) {
                                Toast.makeText(Login_page.this, "Enter Correct Credentials!", Toast.LENGTH_LONG).show();
                                return;
                            }
                            Toast.makeText(Login_page.this, "Login Successful!!!", Toast.LENGTH_LONG).show();
                            Login_page.this.startActivity(new Intent(Login_page.this, MainActivity.class));
                        }
                    });
                }
            }
        });



    }

    public void click(View v) {
        startActivity(new Intent(this, Sign_in.class));
    }

    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    public void onStop() {
        super.onStop();
        FirebaseAuth.AuthStateListener authStateListener = mAuthListener;
        if (authStateListener != null) {
            mAuth.removeAuthStateListener(authStateListener);
        }
    }

}
