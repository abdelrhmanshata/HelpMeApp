package com.shata.helpmyapp.Activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentTransaction;

import com.etebarian.meowbottomnavigation.MeowBottomNavigation;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.shata.helpmyapp.Fragment.CoronaFragment;
import com.shata.helpmyapp.Fragment.InstructionsFragment;
import com.shata.helpmyapp.Fragment.MapWebViewFragment;
import com.shata.helpmyapp.Model.ModelUser;
import com.shata.helpmyapp.R;
import com.shata.helpmyapp.databinding.ActivityHomeBinding;

import es.dmoral.toasty.Toasty;

public class HomeActivity extends AppCompatActivity {

    public static boolean USERisADMIN = false;

    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    DatabaseReference referenceUser;

    ActivityHomeBinding homeBinding;

    private static final int ID_Corona = 1;
    private static final int ID_GoogleMap = 2;
    private static final int ID_Instructions = 3;

    private MeowBottomNavigation buttomNavigation;
    private Toolbar toolbar;

    private long backPressedTime;
    private Toast backToast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        homeBinding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(homeBinding.getRoot());

        referenceUser = firebaseDatabase.getReference("Users");
        userIsAdmin();

        toolbar = findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);

        buttomNavigation = findViewById(R.id.buttomNavigation);

        buttomNavigation.add(new MeowBottomNavigation.Model(ID_Corona, R.drawable.ic_baseline_coronavirus_24));
        buttomNavigation.add(new MeowBottomNavigation.Model(ID_GoogleMap, R.drawable.ic_baseline_explore_24));
        buttomNavigation.add(new MeowBottomNavigation.Model(ID_Instructions, R.drawable.ic_baseline_info_24));

        homeBinding.buttomNavigation.setOnShowListener(item -> {
        });

        buttomNavigation.setOnClickMenuListener(item -> {

            switch (item.getId()) {
                case ID_Corona:
                    Corona();
                    break;
                case ID_GoogleMap:
                    GoogleMap();
                    break;
                case ID_Instructions:
                    Instructions();
                    break;
                default:
                    GoogleMap();
            }
        });

        buttomNavigation.setOnReselectListener(item -> {
            switch (item.getId()) {
                case ID_Corona:
                    Corona();
                    break;
                case ID_GoogleMap:
                    GoogleMap();
                    break;
                case ID_Instructions:
                    Instructions();
                    break;
                default:
                    GoogleMap();
            }
        });
    }

    private void Corona() {
        toolbar.setTitle(getResources().getString(R.string.cronacases));
        setSupportActionBar(toolbar);
        buttomNavigation.show(ID_Corona, true);

        CoronaFragment coronaFragment = new CoronaFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, coronaFragment, "");
        transaction.commit();
    }

    private void GoogleMap() {
        toolbar.setTitle(getResources().getString(R.string.google_map));
        setSupportActionBar(toolbar);
        buttomNavigation.show(ID_GoogleMap, true);

        MapWebViewFragment mapWebViewFragment = new MapWebViewFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, mapWebViewFragment, "");
        transaction.commit();
    }

    private void Instructions() {

        toolbar.setTitle(getResources().getString(R.string.instructions));
        setSupportActionBar(toolbar);
        buttomNavigation.show(ID_Instructions, true);

        InstructionsFragment instructionsFragment = new InstructionsFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, instructionsFragment, "");
        transaction.commit();
    }

    private void userIsAdmin() {
        referenceUser.child(firebaseUser.getPhoneNumber()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ModelUser value = snapshot.getValue(ModelUser.class);
                if (value != null) {
                    USERisADMIN = value.isUserIsAdmin();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        GoogleMap();
    }

    @Override
    public void onBackPressed() {

        if (MapWebViewFragment.myWebView.canGoBack()) {
            MapWebViewFragment.myWebView.goBack();
        } else {
            if (backPressedTime + 2000 > System.currentTimeMillis()) {
                backToast.cancel();
                super.onBackPressed();
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                return;
            } else {
                backToast = Toasty.info(getBaseContext(), "" + getResources().getString(R.string.press_again), Toast.LENGTH_SHORT);
                backToast.show();
            }
            backPressedTime = System.currentTimeMillis();
        }
    }
}