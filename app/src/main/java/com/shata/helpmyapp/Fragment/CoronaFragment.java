package com.shata.helpmyapp.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.ismaeldivita.chipnavigation.ChipNavigationBar;
import com.shata.helpmyapp.Activity.ProfileActivity;
import com.shata.helpmyapp.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class CoronaFragment extends Fragment {

    TextView tvCountry, tvDate, tvDeaths, tvRecovered, tvConfirmed;
    ProgressBar progressBar;
    private ChipNavigationBar chipNavigationBar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_corona, container, false);

        progressBar = view.findViewById(R.id.progressCircle);
        tvCountry = view.findViewById(R.id.country);
        tvDate = view.findViewById(R.id.date);
        tvDeaths = view.findViewById(R.id.deaths);
        tvRecovered = view.findViewById(R.id.recovered);
        tvConfirmed = view.findViewById(R.id.confirmed);

        chipNavigationBar = view.findViewById(R.id.chipNavigation);

        chipNavigationBar.setItemSelected(R.id.total, true);
        getTotal();
        chipNavigationBar.setOnItemSelectedListener(i -> {
            switch (i) {
                case R.id.total:
                    getTotal();
                    break;
                case R.id.today:
                    getToday();
                    break;
                case R.id.yesterday:
                    getYesterday();
                    break;
            }
        });
        return view;
    }

    public void getTotal() {
        progressBar.setVisibility(View.VISIBLE);
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        String Url = "https://pomber.github.io/covid19/timeseries.json";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, Url, null, response -> {
                    progressBar.setVisibility(View.VISIBLE);
                    //
                    String country = "Egypt";
                    try {
                        JSONArray jsonArray = response.getJSONArray(country);
                        JSONObject toDay = jsonArray.getJSONObject((jsonArray.length()) - 1);
                        String date = toDay.getString("date");
                        String confirmed = toDay.getString("confirmed");
                        String deaths = toDay.getString("deaths");
                        String recovered = toDay.getString("recovered");

                        tvCountry.setText("" + getResources().getString(R.string.country_egypt));
                        tvDate.setText("" + date);
                        tvDeaths.setText("" + deaths);
                        tvRecovered.setText("" + recovered);
                        tvConfirmed.setText("" + confirmed);
                        progressBar.setVisibility(View.INVISIBLE);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }, error -> {
                    // TODO: Handle error
                });
        requestQueue.add(jsonObjectRequest);
    }

    public void getToday() {
        progressBar.setVisibility(View.VISIBLE);
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        String Url = "https://pomber.github.io/covid19/timeseries.json";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, Url, null, response -> {
                    progressBar.setVisibility(View.VISIBLE);
                    //
                    String country = "Egypt";
                    try {
                        JSONArray jsonArray = response.getJSONArray(country);
                        JSONObject toDay = jsonArray.getJSONObject((jsonArray.length()) - 1);
                        String date1 = toDay.getString("date");
                        String confirmed1 = toDay.getString("confirmed");
                        String deaths1 = toDay.getString("deaths");
                        String recovered1 = toDay.getString("recovered");

                        JSONObject yesterDay = jsonArray.getJSONObject((jsonArray.length()) - 2);
                        String date2 = yesterDay.getString("date");
                        String confirmed2 = yesterDay.getString("confirmed");
                        String deaths2 = yesterDay.getString("deaths");
                        String recovered2 = yesterDay.getString("recovered");

                        tvCountry.setText("" + getResources().getString(R.string.country_egypt));
                        tvDate.setText("" + date1);
                        tvDeaths.setText("" + (Integer.parseInt(deaths1) - Integer.parseInt(deaths2)));
                        tvRecovered.setText("" + (Integer.parseInt(recovered1) - Integer.parseInt(recovered2)));
                        tvConfirmed.setText("" + (Integer.parseInt(confirmed1) - Integer.parseInt(confirmed2)));
                        progressBar.setVisibility(View.INVISIBLE);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }, error -> {
                    // TODO: Handle error
                });
        requestQueue.add(jsonObjectRequest);
    }

    public void getYesterday() {
        progressBar.setVisibility(View.VISIBLE);
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        String Url = "https://pomber.github.io/covid19/timeseries.json";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, Url, null, response -> {
                    progressBar.setVisibility(View.VISIBLE);
                    String country = "Egypt";
                    try {
                        JSONArray jsonArray = response.getJSONArray(country);
                        JSONObject toDay = jsonArray.getJSONObject((jsonArray.length()) - 2);
                        String date1 = toDay.getString("date");
                        String confirmed1 = toDay.getString("confirmed");
                        String deaths1 = toDay.getString("deaths");
                        String recovered1 = toDay.getString("recovered");

                        JSONObject yesterDay = jsonArray.getJSONObject((jsonArray.length()) - 3);
                        String confirmed2 = yesterDay.getString("confirmed");
                        String deaths2 = yesterDay.getString("deaths");
                        String recovered2 = yesterDay.getString("recovered");

                        tvCountry.setText("" + getResources().getString(R.string.country_egypt));
                        tvDate.setText("" + date1);
                        tvDeaths.setText("" + (Integer.parseInt(deaths1) - Integer.parseInt(deaths2)));
                        tvRecovered.setText("" + (Integer.parseInt(recovered1) - Integer.parseInt(recovered2)));
                        tvConfirmed.setText("" + (Integer.parseInt(confirmed1) - Integer.parseInt(confirmed2)));
                        progressBar.setVisibility(View.INVISIBLE);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }, error -> {
                    // TODO: Handle error
                });
        requestQueue.add(jsonObjectRequest);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.corona_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.profile:
                profile();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void profile() {
        startActivity(new Intent(getActivity(), ProfileActivity.class));
        getActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }
}