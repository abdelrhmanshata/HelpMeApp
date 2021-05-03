package com.shata.helpmyapp.Activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.text.style.UnderlineSpan;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.shata.helpmyapp.Model.ModelUser;
import com.shata.helpmyapp.R;
import com.shata.helpmyapp.databinding.ActivityLoginBinding;

import java.util.Objects;

import es.dmoral.toasty.Toasty;

public class LoginActivity extends AppCompatActivity {

    ActivityLoginBinding loginBinding;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Initialize Binding to inflate Item in UI
        //
        loginBinding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(loginBinding.getRoot());

        // Initialize Firebase Object && getReference
        //
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Users");

        String text = "" + getResources().getString(R.string.have_not_account_register);
        if (text.contains("Register")) {
            SpannableString spannableString = new SpannableString(text);
            UnderlineSpan underlineSpan = new UnderlineSpan();
            ForegroundColorSpan mBlue = new ForegroundColorSpan(Color.BLUE);
            spannableString.setSpan(underlineSpan, 23, 31, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            spannableString.setSpan(mBlue, 23, 31, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            loginBinding.userRegister.setText(spannableString);
        }
        loginBinding.userRegister.setOnClickListener(v -> {
            startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            overridePendingTransition(R.anim.down_to_up, R.anim.up_to_down);
        });

        loginBinding.generateBtn.setOnClickListener(v -> {
            loginUser();
        });
    }

    private void loginUser() {

        //Get complete phone number
        String _getUserEnteredPhoneNumber = Objects.requireNonNull(loginBinding.phoneNumberText.getText()).toString().trim();
        if (TextUtils.isEmpty(_getUserEnteredPhoneNumber)) {
            loginBinding.phoneNumberText.setError("" + getResources().getString(R.string.phone_is_required));
            loginBinding.phoneNumberText.setFocusable(true);
            loginBinding.phoneNumberText.requestFocus();
            return;
        } else {
            //Remove first zero if entered!
            if (_getUserEnteredPhoneNumber.charAt(0) == '0') {
                _getUserEnteredPhoneNumber = _getUserEnteredPhoneNumber.substring(1);
            }
        }
        //Complete phone number
        String UserPhoneNumber = "+" + "20" + _getUserEnteredPhoneNumber;

        if (UserPhoneNumber.length() < 11) {
            loginBinding.phoneNumberText.setError("" + getResources().getString(R.string.please_enter_number));
            loginBinding.phoneNumberText.setFocusable(true);
            loginBinding.phoneNumberText.requestFocus();
            return;
        }

        try {
            // Check UserPhone Number Is Found or not
            databaseReference.child(UserPhoneNumber).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    ModelUser user = snapshot.getValue(ModelUser.class);
                    // if user phoneNumber is found
                    // passing the user object to OTPActivity to complete login
                    //
                    if (user != null) {
                        Intent intent = new Intent(LoginActivity.this, OTPActivity.class);
                        intent.putExtra("ModelUser", user);
                        startActivity(intent);
                        overridePendingTransition(R.anim.down_to_up, R.anim.up_to_down);
                        finish();
                    }
                    // if user phoneNumber is found
                    // Attention user in UI That this phone is Not found
                    //
                    else {
                        Toasty.info(LoginActivity.this, "" + getResources().getString(R.string.phone_not_found), Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        } catch (NullPointerException e) {
            Toast.makeText(this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        // check is currentUser iS found
        // open HomeActivity
        //
        if (firebaseUser != null) {
            startActivity(new Intent(this, HomeActivity.class));
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            finish();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        // check is currentUser iS found
        // open HomeActivity
        //
        if (firebaseUser != null) {
            startActivity(new Intent(this, HomeActivity.class));
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            finish();
        }
    }
}