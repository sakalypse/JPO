package com.pt.jpo;

import android.content.Context;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;


import com.google.android.gms.common.api.GoogleApiClient;
import android.location.LocationListener;
import android.widget.Toast;

import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
//import com.google.android.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.MyLocationOverlay;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sakalypse on 16/11/16.
 */

public class MapActivity extends AppCompatActivity implements OnMapReadyCallback, View.OnClickListener {

    public final static String LAYOUT_MESSAGE = "com.pt.JPO.MESSAGE";
    private LocationManager locationManager;
    private double latitude;
    private double longitude;
    private SupportMapFragment mapFragment;
    protected GoogleApiClient mGoogleApiClient;
    private Location lastLocation;
    private GoogleMap googleMap;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.localisation);
        initButton();
        initMarginAllLayout(findViewById(R.id.layoutLocalisation));

        mapFragment =
                (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        //locationManager = (LocationManager)getApplicationContext().getSystemService(LOCATION_SERVICE);

        locationManager = (LocationManager) this.getSystemService(LOCATION_SERVICE);
        lastLocation = this.getLastKnownLocation();

    }

    @Override
    public void onMapReady(GoogleMap map) {
        try {
            googleMap = map;
            googleMap.setMyLocationEnabled(true);
            lastLocation = this.getLastKnownLocation();



        }catch (SecurityException se){

        }
    }

    private Location getLastKnownLocation() {
        List<String> providers = locationManager.getProviders(true);
        Location bestLocation = null;
        for (String provider : providers) {
            Location l = null;
            try {
                locationManager.requestLocationUpdates(provider, 1000, 10, mLocationListener);
                l = locationManager.getLastKnownLocation(provider);
            }catch(SecurityException s){
                Toast toast = Toast.makeText(getApplicationContext(), "probleme getLastLocation", Toast.LENGTH_SHORT);
                toast.show();
            }
            if (l == null) {
                continue;
            }
            if (bestLocation == null || l.getAccuracy() < bestLocation.getAccuracy()) {
                bestLocation = l;
            }
        }
        return bestLocation;
    }



    private final LocationListener mLocationListener = new LocationListener() {
        @Override
        public void onLocationChanged(final Location location) {
                try {

                    Toast toast = Toast.makeText(getApplicationContext(), "marche", Toast.LENGTH_SHORT);
                    toast.show();

                    latitude = location.getLatitude();
                    longitude = location.getLongitude();
                    LatLng loc = new LatLng(latitude, longitude);
                    googleMap.addMarker(new MarkerOptions().position(loc).title("New Marker"));
                    googleMap.moveCamera(CameraUpdateFactory.newLatLng(loc));
                } catch (SecurityException se) {
                    Toast toast = Toast.makeText(getApplicationContext(), "marche pas", Toast.LENGTH_SHORT);
                    toast.show();
                }

/*
        GeoPoint p = new GeoPoint((int) (latitude * 1E6), (int) (longitude * 1E6));
        mapController.animateTo(p);
        mapController.setCenter(p);*/
        }

        @Override
        public void onStatusChanged(String s, int i, Bundle bundle) {

        }

        @Override
        public void onProviderEnabled(String s) {

        }

        @Override
        public void onProviderDisabled(String s) {

        }
    };

    @Override
    public void onPause(){
        super.onPause();
        try {
            locationManager.removeUpdates(mLocationListener);
        }catch(SecurityException s){

        }

    }
    @Override
    public void onResume(){
        super.onResume();
        try {
            Toast toast = Toast.makeText(getApplicationContext(), "onResume marche ", Toast.LENGTH_SHORT);
            toast.show();

            lastLocation = this.getLastKnownLocation();

            if (lastLocation!=null) {
                LatLng loc = new LatLng(lastLocation.getLatitude(), lastLocation.getLongitude());
                googleMap.addMarker(new MarkerOptions().position(loc).title("New Marker"));
                googleMap.moveCamera(CameraUpdateFactory.newLatLng(loc));
            }

        }catch(SecurityException s){
            Toast toast = Toast.makeText(getApplicationContext(), "onResume marche pas", Toast.LENGTH_SHORT);
            toast.show();
        }

    }


    public void initButton() {
        Button buttonFormulaire = (Button) findViewById(R.id.formulaire);
        buttonFormulaire.setOnClickListener(this);
        Button buttonPresentationmmi = (Button) findViewById(R.id.presentationmmi);
        buttonPresentationmmi.setOnClickListener(this);
        Button buttonProfSalle = (Button) findViewById(R.id.profsalle);
        buttonProfSalle.setOnClickListener(this);
        Button buttonVideos = (Button) findViewById(R.id.videos);
        buttonVideos.setOnClickListener(this);
        Button buttoGeolocalisation = (Button) findViewById(R.id.localisation);
        buttoGeolocalisation.setOnClickListener(this);
    }

    /*
    * Initialiste le marginBottom de tous les layouts pour que le menu ne se positionne pas par dessus
    * N'est pas le même pour tous les écrans
     */
    private void initMarginAllLayout(View layout) {
        //recupere le height du menu
        LinearLayout layoutMenu = (LinearLayout)findViewById(R.id.layoutMenu);
        layoutMenu.measure(0,0);
        int heightMenu = layoutMenu.getMeasuredHeight();

        //ajoute un padding bottom a la vue
        LinearLayout linearLayout = (LinearLayout) layout;
        linearLayout.setPadding(0,0,0,heightMenu);

    }

    public void onClick(View v) {
        switch (v.getId()) {
            case  R.id.formulaire: {
                Intent intent = new Intent(this, MainActivity.class);
                intent.putExtra(LAYOUT_MESSAGE, "layoutFormulaire");
                startActivity(intent);
                break;
            }
            case  R.id.presentationmmi: {
                Intent intent = new Intent(this, MainActivity.class);
                intent.putExtra(LAYOUT_MESSAGE, "layoutPresentationMMI");
                startActivity(intent);
                break;
            }
            case  R.id.profsalle: {
                Intent intent = new Intent(this, MainActivity.class);
                intent.putExtra(LAYOUT_MESSAGE, "layoutProfSalle");
                startActivity(intent);
                break;
            }
            case  R.id.videos: {
                Intent intent = new Intent(this, MainActivity.class);
                intent.putExtra(LAYOUT_MESSAGE, "layoutVideos");
                startActivity(intent);
                break;
            }
        }
    }
}
