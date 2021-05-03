package com.shata.helpmyapp.Fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.shata.helpmyapp.Activity.AddDiseasesActivity;
import com.shata.helpmyapp.Activity.HomeActivity;
import com.shata.helpmyapp.Activity.OTPActivity;
import com.shata.helpmyapp.Activity.RegisterActivity;
import com.shata.helpmyapp.Adapter.AdapterDiseases;
import com.shata.helpmyapp.Model.ModelDisease;
import com.shata.helpmyapp.Model.ModelTitleDisease;
import com.shata.helpmyapp.Model.ModelUser;
import com.shata.helpmyapp.R;

import java.util.ArrayList;
import java.util.List;

public class InstructionsFragment extends Fragment {

    private Spinner spinner;
    private ProgressBar progressBar;
    private TextView titleDiseases, descriptionDiseases, treatmentsDiseases;

    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference;

    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
    DatabaseReference referenceUser;

    List<ModelTitleDisease> titleDiseasesList;
    AdapterDiseases adapterDiseases;

    String IDItemSelected = "";

    boolean userIsAdmin;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_instructions, container, false);

        progressBar = view.findViewById(R.id.progressDiseases);

        referenceUser = firebaseDatabase.getReference("Users");
        userIsAdmin();

        spinner = view.findViewById(R.id.spinner);
        titleDiseases = view.findViewById(R.id.titleDiseases);
        descriptionDiseases = view.findViewById(R.id.descriptionDiseases);
        treatmentsDiseases = view.findViewById(R.id.treatmentsDiseases);
        titleDiseasesList = new ArrayList<>();

        databaseReference = firebaseDatabase.getReference("Diseases");

        getAllTitle();
        adapterDiseases = new AdapterDiseases(getActivity().getApplicationContext(), R.layout.style_spinner, titleDiseasesList);
        spinner.setAdapter(adapterDiseases);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String idSelectItem = titleDiseasesList.get(position).getdID();
                IDItemSelected = idSelectItem;
                getObjectDisease(idSelectItem);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        final Handler actionHandler = new Handler();
        final Runnable runnable = new Runnable() {
            @Override
            public void run() {
                if (HomeActivity.USERisADMIN) {
                    progressBar.setVisibility(View.VISIBLE);
                    databaseReference.child(IDItemSelected).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            ModelDisease value = snapshot.getValue(ModelDisease.class);
                            if (value != null) {
                                Intent intent = new Intent(getActivity().getApplicationContext(), AddDiseasesActivity.class);
                                intent.putExtra("DISEASE_SELECT", value);
                                startActivity(intent);
                                getActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                                progressBar.setVisibility(View.GONE);
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            progressBar.setVisibility(View.GONE);
                        }
                    });
                }
            }
        };

        spinner.setOnTouchListener((v, event) -> {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                actionHandler.postDelayed(runnable, 500);
            } else if (event.getAction() == MotionEvent.ACTION_UP) {
                actionHandler.removeCallbacks(runnable);
            }
            return false;
        });
        return view;
    }


    private void getAllTitle() {
        progressBar.setVisibility(View.VISIBLE);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                titleDiseasesList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    ModelDisease disease = snapshot.getValue(ModelDisease.class);
                    ModelTitleDisease titleDisease = new ModelTitleDisease(disease.getdID(), disease.getTitle());
                    titleDiseasesList.add(titleDisease);
                }
                adapterDiseases.notifyDataSetChanged();
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                progressBar.setVisibility(View.GONE);
                }
        });
    }

    private void getObjectDisease(String ID) {
        progressBar.setVisibility(View.VISIBLE);
        databaseReference.child(ID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ModelDisease value = snapshot.getValue(ModelDisease.class);
                if (value != null) {
                    titleDiseases.setText(value.getTitle().trim());
                    descriptionDiseases.setText(value.getDescription().trim());
                    treatmentsDiseases.setText(value.getTreatments().trim());
                    progressBar.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                progressBar.setVisibility(View.GONE);
            }
        });
    }

    private void userIsAdmin() {
        progressBar.setVisibility(View.VISIBLE);
        referenceUser.child(firebaseUser.getPhoneNumber()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ModelUser value = snapshot.getValue(ModelUser.class);
                if (value != null) {
                    progressBar.setVisibility(View.GONE);
                    userIsAdmin = value.isUserIsAdmin();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        if (HomeActivity.USERisADMIN||userIsAdmin)
            inflater.inflate(R.menu.instructions_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.diseases:
                diseases();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void diseases() {
        startActivity(new Intent(getActivity(), AddDiseasesActivity.class));
        getActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }
}