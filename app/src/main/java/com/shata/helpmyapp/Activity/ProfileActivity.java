package com.shata.helpmyapp.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
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
import com.shata.helpmyapp.databinding.ActivityProfileBinding;

import es.dmoral.toasty.Toasty;

public class ProfileActivity extends AppCompatActivity {
    ActivityProfileBinding profileBinding;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;

    String UserPhoneNumber = "", UserName = "", UserAge = "", UserEmail = "";
    ModelUser modelUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        profileBinding = ActivityProfileBinding.inflate(getLayoutInflater());
        setContentView(profileBinding.getRoot());

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Users");

        setSupportActionBar(profileBinding.toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("");

        profileBinding.progressCircle.setVisibility(View.VISIBLE);
        databaseReference.child(firebaseUser.getPhoneNumber()).addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ModelUser user = snapshot.getValue(ModelUser.class);
                modelUser = user;
                profileBinding.progressCircle.setVisibility(View.GONE);
                profileBinding.userName.setText(user.getUserName().trim());
                profileBinding.userAge.setText(user.getUserAge().trim());
                profileBinding.userEmail.setText(user.getUserEmail().trim());
                profileBinding.userPhoneNumber.setText(user.getUserPhoneNumber().trim());
                profileBinding.userPhoneNumber.setEnabled(false);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        profileBinding.continueRegister.setOnClickListener(v -> {
            profileBinding.progressCircle.setVisibility(View.VISIBLE);
            UserPhoneNumber = profileBinding.userPhoneNumber.getText().toString().trim();
            UserName = profileBinding.userName.getText().toString().trim();
            UserAge = profileBinding.userAge.getText().toString().trim();
            UserEmail = profileBinding.userEmail.getText().toString().trim();

            if (UserName.isEmpty()) {
                profileBinding.userName.setError(""+getResources().getString(R.string.username_is_required));
                profileBinding.userName.setFocusable(true);
                profileBinding.userName.requestFocus();
                profileBinding.progressCircle.setVisibility(View.GONE);
                return;
            }

            if (UserAge.isEmpty()) {
                profileBinding.userAge.setError(""+getResources().getString(R.string.userage_required));
                profileBinding.userAge.setFocusable(true);
                profileBinding.userAge.requestFocus();
                profileBinding.progressCircle.setVisibility(View.GONE);
                return;
            }

            if (UserEmail.isEmpty()) {
                profileBinding.userEmail.setError(""+getResources().getString(R.string.email_required));
                profileBinding.userEmail.setFocusable(true);
                profileBinding.userEmail.requestFocus();
                profileBinding.progressCircle.setVisibility(View.GONE);
                return;
            }

            if (!Patterns.EMAIL_ADDRESS.matcher(UserEmail).matches()) {
                profileBinding.userEmail.setError(""+getResources().getString(R.string.please_enter_email));
                profileBinding.userEmail.setFocusable(true);
                profileBinding.userEmail.requestFocus();
                profileBinding.progressCircle.setVisibility(View.GONE);
                return;
            }

            if (modelUser != null) {
                ModelUser modelUserUpdata = new ModelUser();
                modelUserUpdata.setUserIsAdmin(modelUser.isUserIsAdmin());
                modelUserUpdata.setUserID(modelUser.getUserID());
                modelUserUpdata.setUserPhoneNumber(modelUser.getUserPhoneNumber());
                modelUserUpdata.setUserName(UserName);
                modelUserUpdata.setUserAge(UserAge);
                modelUserUpdata.setUserEmail(UserEmail);
                modelUserUpdata.setUserGender(modelUser.getUserGender());

                databaseReference.child(modelUserUpdata.getUserPhoneNumber()).setValue(modelUserUpdata)
                        .addOnSuccessListener(aVoid -> {
                            Toasty.success(ProfileActivity.this, ""+getResources().getString(R.string.update_succec), Toast.LENGTH_SHORT).show();
                            profileBinding.progressCircle.setVisibility(View.GONE);
                        })
                        .addOnFailureListener(e -> {
                            Toasty.error(ProfileActivity.this, ""+getResources().getString(R.string.failure_update), Toast.LENGTH_SHORT).show();
                            profileBinding.progressCircle.setVisibility(View.GONE);
                        });
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.profile_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            case R.id.logout:
                logout();
                break;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        return;
    }

    private void logout() {
        firebaseAuth.signOut();
        startActivity(new Intent(ProfileActivity.this, LoginActivity.class));
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        finish();
    }
}