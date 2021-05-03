package com.shata.helpmyapp.Fragment;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.UnderlineSpan;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.GeolocationPermissions;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.Task;
import com.shata.helpmyapp.R;

import es.dmoral.toasty.Toasty;

public class MapWebViewFragment extends Fragment {

    public static WebView myWebView;
    private static final int REQUEST_CODE = 101;
    public FusedLocationProviderClient fusedLocationProviderClient;

    ProgressBar setRefreshing;

    public static String The_Place = "hospital";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_map_web_view, container, false);

        setRefreshing = view.findViewById(R.id.progressBar);

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(view.getContext());
        myWebView = view.findViewById(R.id.webview);

        // Get Current Location
        getCurrentLocation(The_Place);

        myWebView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onGeolocationPermissionsShowPrompt(String origin, GeolocationPermissions.Callback callback) {
                callback.invoke(origin, true, false);
            }
        });
        WebSettings settings = myWebView.getSettings();
        settings.setJavaScriptEnabled(true);

        return view;
    }

    public void getCurrentLocation(String place) {

        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_CODE);
            return;
        }
        Task<Location> task = fusedLocationProviderClient.getLastLocation();
        task.addOnSuccessListener(location -> {
            if (location != null) {
                myWebView.loadUrl("https://www.google.com/maps/search/?api=1&map_action=map&center=" + location.getLatitude() + "," + location.getLongitude() + "&z=25&query=" + place);
            } else {
                Drawable myIcon = AppCompatResources.getDrawable(getActivity().getApplicationContext(), R.drawable.ic_baseline_location_off_24);
                Toasty.normal(getActivity(), "" + getResources().getString(R.string.enable_gps), myIcon).show();
                showDialogGPS();
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    getCurrentLocation(The_Place);
                }
                break;
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.home_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.refresh:
                refresh();
                break;
            case R.id.hospital:
                hospital();
                break;
            case R.id.pharmacies:
                pharmacies();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void refresh() {
        setRefreshing.setVisibility(View.VISIBLE);
        new Handler().postDelayed(() -> {
            setRefreshing.setVisibility(View.GONE);
            getCurrentLocation(The_Place);
        }, 1000);
    }

    private void hospital() {
        The_Place = "hospital";
        getCurrentLocation(The_Place);
    }

    private void pharmacies() {
        The_Place = "pharmacy";
        getCurrentLocation(The_Place);
    }

    public void showDialogGPS() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.layout_gps, null);
        dialogBuilder.setView(dialogView);
        //
        TextView tvGoogleMaps = dialogView.findViewById(R.id.googleChrome);

        String text = "https://maps.google.com.eg/";

        SpannableString spannableString = new SpannableString(text);
        UnderlineSpan underlineSpan = new UnderlineSpan();
        spannableString.setSpan(underlineSpan, 0, spannableString.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        tvGoogleMaps.setText(spannableString);

        tvGoogleMaps.setOnClickListener(v -> {
            String uriText = "https://maps.google.com.eg/";
            Uri uri = Uri.parse(uriText);
            startActivity(new Intent(Intent.ACTION_VIEW, uri));
        });
        AlertDialog alertDialog = dialogBuilder.create();
        alertDialog.show();
    }

}