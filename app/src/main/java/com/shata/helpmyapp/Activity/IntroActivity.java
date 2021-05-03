package com.shata.helpmyapp.Activity;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.shata.helpmyapp.R;

public class IntroActivity extends AppCompatActivity {


    private FirebaseAuth firebaseAuth;
    FirebaseAuth.AuthStateListener authStateListener = firebaseAuth -> {

        Thread intro = new Thread(() -> {
            // Sleep UI 4 Seconds
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            // Check User is found
            if (firebaseAuth.getCurrentUser() != null) {
                startActivity(new Intent(IntroActivity.this, HomeActivity.class));
            } else {
                startActivity(new Intent(IntroActivity.this, LoginActivity.class));
            }
            finish();
        });
        intro.start();
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseAuth.addAuthStateListener(authStateListener);

    }
}