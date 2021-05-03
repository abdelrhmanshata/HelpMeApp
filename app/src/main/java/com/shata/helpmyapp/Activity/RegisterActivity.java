package com.shata.helpmyapp.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.MenuItem;
import android.view.View;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.shata.helpmyapp.Model.ModelUser;
import com.shata.helpmyapp.R;
import com.shata.helpmyapp.databinding.ActivityRegisterBinding;

import java.util.Objects;

import es.dmoral.toasty.Toasty;

public class RegisterActivity extends AppCompatActivity {

    ActivityRegisterBinding registerBinding;
    RadioButton selectedGender;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        registerBinding = ActivityRegisterBinding.inflate(getLayoutInflater());
        setContentView(registerBinding.getRoot());

        setSupportActionBar(registerBinding.toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("");

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Users");

        registerBinding.continueRegister.setOnClickListener(v -> {
            continueRegister();
        });
    }

    private void continueRegister() {

        //Get complete phone number
        String _getUserEnteredPhoneNumber = Objects.requireNonNull(registerBinding.userPhoneNumber.getText()).toString().trim();
        if (TextUtils.isEmpty(_getUserEnteredPhoneNumber)) {
            registerBinding.userPhoneNumber.setError("" + getResources().getString(R.string.phone_is_required));
            registerBinding.userPhoneNumber.setFocusable(true);
            registerBinding.userPhoneNumber.requestFocus();
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
            registerBinding.userPhoneNumber.setError("" + getResources().getString(R.string.please_enter_number));
            registerBinding.userPhoneNumber.setFocusable(true);
            registerBinding.userPhoneNumber.requestFocus();
            return;
        }

        String UserName = Objects.requireNonNull(registerBinding.userName.getText()).toString().trim();

        if (UserName.isEmpty()) {
            registerBinding.userName.setError("" + getResources().getString(R.string.username_is_required));
            registerBinding.userName.setFocusable(true);
            registerBinding.userName.requestFocus();
            return;
        }

        String UserAge = Objects.requireNonNull(registerBinding.userAge.getText()).toString().trim();

        if (UserAge.isEmpty()) {
            registerBinding.userAge.setError("" + getResources().getString(R.string.userage_required));
            registerBinding.userAge.setFocusable(true);
            registerBinding.userAge.requestFocus();
            return;
        }

        String UserEmail = Objects.requireNonNull(registerBinding.userEmail.getText()).toString().trim();

        if (UserEmail.isEmpty()) {
            registerBinding.userEmail.setError("" + getResources().getString(R.string.email_required));
            registerBinding.userEmail.setFocusable(true);
            registerBinding.userEmail.requestFocus();
            registerBinding.progressCircle.setVisibility(View.GONE);
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(UserEmail).matches()) {
            registerBinding.userEmail.setError("" + getResources().getString(R.string.please_enter_email));
            registerBinding.userEmail.setFocusable(true);
            registerBinding.userEmail.requestFocus();
            registerBinding.progressCircle.setVisibility(View.GONE);
            return;
        }

        String UserGender = "";
        if (registerBinding.radioGroup.getCheckedRadioButtonId() == -1) {
            Toasty.info(this, "" + getResources().getString(R.string.select_gender), Toast.LENGTH_SHORT).show();
            return;
        } else {
            selectedGender = findViewById(registerBinding.radioGroup.getCheckedRadioButtonId());
            if(selectedGender.getId() == R.id.male)
            {
                UserGender = "Male";
            }else{
                UserGender = "Female";
            }
        }

        try {
            // Check UserPhone Number Is Found or not
            String finalUserGender = UserGender;
            databaseReference.child(UserPhoneNumber).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    ModelUser user = snapshot.getValue(ModelUser.class);
                    // if user phoneNumber is not found
                    // passing the user object to OTPActivity to complete register
                    //
                    if (user == null) {

                        ModelUser modelUser = new ModelUser();

                        modelUser.setUserIsAdmin(false);
                        modelUser.setUserPhoneNumber(UserPhoneNumber);
                        modelUser.setUserName(UserName);
                        modelUser.setUserAge(UserAge);
                        modelUser.setUserEmail(UserEmail);
                        modelUser.setUserGender(finalUserGender);

                        Intent intent = new Intent(RegisterActivity.this, OTPActivity.class);
                        intent.putExtra("ModelUser", modelUser);
                        startActivity(intent);
                        overridePendingTransition(R.anim.down_to_up, R.anim.up_to_down);
                        finish();
                    }
                    // if user phoneNumber is found
                    //
                    else {
                        Toasty.info(RegisterActivity.this, "" + getResources().getString(R.string.already_exists), Toast.LENGTH_SHORT).show();
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
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.down_to_up, R.anim.up_to_down);
        return;
    }
}