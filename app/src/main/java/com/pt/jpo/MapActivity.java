package com.pt.jpo;

import android.content.Context;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;




import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import android.location.LocationListener;
import android.widget.Toast;

import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
//import com.google.android.maps.MapView;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
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

public class MapActivity extends AppCompatActivity implements  View.OnClickListener,
        OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener {


    public final static String LAYOUT_MESSAGE = "com.pt.JPO.MESSAGE";

    LocationRequest mLocationRequest;
    GoogleApiClient mGoogleApiClient;

    LatLng latLng;
    GoogleMap mGoogleMap;
    SupportMapFragment mFragment;
    Marker currLocationMarker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.localisation);
        initButton();
        initMarginAllLayout(findViewById(R.id.layoutLocalisation));

        mFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap gMap) {
        mGoogleMap = gMap;
        try {
            mGoogleMap.setMyLocationEnabled(true);
        }catch(SecurityException s){

        }

        buildGoogleApiClient();

        mGoogleApiClient.connect();

    }

    protected synchronized void buildGoogleApiClient() {
        Toast.makeText(this,"buildGoogleApiClient",Toast.LENGTH_SHORT).show();
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        Toast.makeText(this,"apres le build",Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onConnected(Bundle bundle) {
        Toast.makeText(this,"onConnected",Toast.LENGTH_SHORT).show();
        Location mLastLocation = null;
        try {
            mLastLocation = LocationServices.FusedLocationApi.getLastLocation(
                    mGoogleApiClient);
        }catch(SecurityException s){

        }
        if (mLastLocation != null) {
            //place marker at current position
            //mGoogleMap.clear();
            latLng = new LatLng(mLastLocation.getLatitude(), mLastLocation.getLongitude());
            MarkerOptions markerOptions = new MarkerOptions();
            markerOptions.position(latLng);
            markerOptions.title("Current Position");
            markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA));
            currLocationMarker = mGoogleMap.addMarker(markerOptions);
        }
        else{
            Toast.makeText(this,"getLastLocation est Null !",Toast.LENGTH_SHORT).show();
        }

        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(5000); //5 seconds
        mLocationRequest.setFastestInterval(3000); //3 seconds
        mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        try {
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient,
                    mLocationRequest, locationListener);
        }catch(SecurityException s){

        }

    }

    @Override
    public void onConnectionSuspended(int i) {
        Toast.makeText(this,"onConnectionSuspended",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Toast.makeText(this,"onConnectionFailed",Toast.LENGTH_SHORT).show();
    }

    com.google.android.gms.location.LocationListener locationListener = new com.google.android.gms.location.LocationListener() {
        public void onLocationChanged(Location location) {
            //place marker at current position
            //mGoogleMap.clear();
            if (currLocationMarker != null) {
                currLocationMarker.remove();
            }
            latLng = new LatLng(location.getLatitude(), location.getLongitude());
            MarkerOptions markerOptions = new MarkerOptions();
            markerOptions.position(latLng);
            markerOptions.title("Current Position");
            markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA));
            currLocationMarker = mGoogleMap.addMarker(markerOptions);

            //zoom to current position:
            CameraPosition cameraPosition = new CameraPosition.Builder()
                    .target(latLng).zoom(14).build();

            mGoogleMap.animateCamera(CameraUpdateFactory
                    .newCameraPosition(cameraPosition));

            //If you only need one location, unregister the listener
            //LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);

        }
    };


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
