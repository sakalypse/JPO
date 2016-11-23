package com.pt.jpo;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;


import com.google.android.gms.location.LocationListener;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.maps.MapController;
import com.google.android.maps.MyLocationOverlay;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sakalypse on 16/11/16.
 */

public class MapActivity extends AppCompatActivity implements OnMapReadyCallback, View.OnClickListener, LocationListener  {

    public final static String LAYOUT_MESSAGE = "com.pt.JPO.MESSAGE";
    private LocationManager locationManager;
    private MapController mapController;
    private GoogleMap map;
    private MapView mapView;
    private MyLocationOverlay myLocation;
    private double latitude;
    private double longitude;
    private double altitude;
    private double accuracy;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.localisation);
        initButton();
        initMarginAllLayout(findViewById(R.id.layoutLocalisation));

        SupportMapFragment mapFragment =
                (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        map = mapFragment.getMap();

        mapView = (MapView) this.findViewById(R.id.map);


        mapController = mapView.getController();

        locationManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
        ArrayList<LocationProvider> providers = new ArrayList<LocationProvider>();
        List<String> names = locationManager.getProviders(true);

        for(String name : names)
            providers.add(locationManager.getProvider(name));


        map.setMyLocationEnabled(true);

        myLocation = new MyLocationOverlay(getApplicationContext(), mapView);
        mapView.getOverlays().add(myLocation);
        myLocation.enableMyLocation();
    }

    @Override
    public void onMapReady(GoogleMap map) {
    }

    @Override
    public void onPause(){
        super.onPause();
        //locationManager.removeUpdates(this);
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

    @Override
    public void onLocationChanged(Location location) {
        latitude = location.getLatitude();
        longitude = location.getLongitude();


    }
}
