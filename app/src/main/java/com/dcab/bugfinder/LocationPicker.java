package com.dcab.bugfinder;

import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.content.Intent;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import com.google.android.gms.maps.OnMapReadyCallback;
import com.mapbox.api.geocoding.v5.models.CarmenFeature;

import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.plugins.places.autocomplete.PlaceAutocomplete;
import com.mapbox.mapboxsdk.plugins.places.autocomplete.ui.PlaceAutocompleteFragment;
import com.mapbox.mapboxsdk.plugins.places.autocomplete.ui.PlaceSelectionListener;

/**
 * Use the places plugin to take advantage of Mapbox's location search ("geocoding") capabilities. The plugin
 * automatically makes geocoding requests, has built-in saved locations, includes location picker functionality,
 * and adds beautiful UI into your Android project.
 */

public class LocationPicker extends AppCompatActivity implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener{

    private static final int REQUEST_CODE_AUTOCOMPLETE = 1;
    private static final String AUTOCOMPLETE_FRAGMENT_TAG = "autocomplete_fragment_tag";
    private static final int ACTIVATE_BACKGROUND_BLUR = -1;
    private static final int DEACTIVATE_BACKGROUND_BLUR = -2;

    private FragmentTransaction transaction;
    private TextView locationInput;
    private LatLng selectedPlace;
    private Button selectButton;
    private String selectedPlaceString;
    private PlaceAutocompleteFragment autocompleteFragment;
    private RelativeLayout fragmentContainer;
    private RelativeLayout screen;
    private GoogleMap gMap;
    private SupportMapFragment mapFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.seleziona_localita);
        initSearchFab(savedInstanceState);
    }

    public void initSearchFab(Bundle savedInstanceState) {
        mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(LocationPicker.this::onMapReady);
        locationInput = findViewById(R.id.localitaSelection);
        selectButton = findViewById(R.id.selezionaSpecie);
        screen = findViewById(R.id.screen_background);
        fragmentContainer = findViewById(R.id.fragment_container);

        Mapbox.getInstance(this, getString(R.string.mapbox_access_token));
        PlaceAutocomplete.clearRecentHistory(getApplicationContext());
        locationInput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (savedInstanceState == null) {
                    autocompleteFragment = PlaceAutocompleteFragment.newInstance(getString(R.string.mapbox_access_token));

                    transaction = getSupportFragmentManager().beginTransaction();
                    transaction.add(R.id.fragment_container, autocompleteFragment,AUTOCOMPLETE_FRAGMENT_TAG);
                    transaction.commit();

                } else {
                    autocompleteFragment = (PlaceAutocompleteFragment)getSupportFragmentManager().findFragmentByTag(AUTOCOMPLETE_FRAGMENT_TAG);
                }

                autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
                    @Override
                    public void onPlaceSelected(CarmenFeature feature) {
                        selectedPlace = new LatLng(feature.center().latitude(), feature.center().longitude());
                        // this string will return to invoking activity
                        selectedPlaceString = feature.placeName();
                        locationInput.setText(selectedPlaceString);
                        locationInput.setTextSize(TypedValue.COMPLEX_UNIT_DIP,15f);

                        onMapChange(gMap);

                        transaction = getSupportFragmentManager().beginTransaction();
                        transaction.remove(autocompleteFragment).commit();
                        updateScreen(DEACTIVATE_BACKGROUND_BLUR);
                    }

                    @Override
                    public void onCancel() {
                        transaction = getSupportFragmentManager().beginTransaction();
                        transaction.remove(autocompleteFragment).commit();
                        updateScreen(DEACTIVATE_BACKGROUND_BLUR);
                    }
                });

            }
        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap)
    {
        gMap = googleMap;
        //Coordinates: SUD, OVEST - NORD, EST
        gMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
        gMap.setMinZoomPreference(5.5f);
        gMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(41.981807, 12.0819104))); //pointing Rome
    }

    public void onMapChange(GoogleMap googleMap)
    {
        googleMap.addMarker(new MarkerOptions().position(selectedPlace));
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(selectedPlace));
        googleMap.animateCamera(CameraUpdateFactory.zoomTo(13),400, null);
    }

    public void onSelectButtonClick(View v)
    {
        Intent result = new Intent();
        result.putExtra("selectedLocation", selectedPlaceString);
        setResult(RESULT_OK,result);
        finish();
    }

    public void backToPrevious (View v){
        finish();
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        return false;
    }

    public void updateScreen(int command)
    {
        if (command == ACTIVATE_BACKGROUND_BLUR) {
            screen.setAlpha(0.5f);
            fragmentContainer.setVisibility(View.VISIBLE);
        }
        else if (command == DEACTIVATE_BACKGROUND_BLUR)
        {
            screen.setAlpha(1f);
            fragmentContainer.setVisibility(View.INVISIBLE);
        }
    }
}