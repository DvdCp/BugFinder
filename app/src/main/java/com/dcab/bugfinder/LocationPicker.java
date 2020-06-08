package com.dcab.bugfinder;

import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.content.Intent;
import android.graphics.Color;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import com.google.android.gms.maps.OnMapReadyCallback;
import com.mapbox.api.geocoding.v5.models.CarmenFeature;
import com.mapbox.geojson.Point;

import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.plugins.places.autocomplete.PlaceAutocomplete;
import com.mapbox.mapboxsdk.plugins.places.autocomplete.model.PlaceOptions;

/**
 * Use the places plugin to take advantage of Mapbox's location search ("geocoding") capabilities. The plugin
 * automatically makes geocoding requests, has built-in saved locations, includes location picker functionality,
 * and adds beautiful UI into your Android project.
 */

public class LocationPicker extends AppCompatActivity implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener{

    private static final int REQUEST_CODE_AUTOCOMPLETE = 1;

    private MapView mapView;
    private TextView locationInput;
    private LatLng selectedPlace;
    private Button selectButton;
    private String selectedPlaceString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.seleziona_localita);
        initSearchFab();

    }

    private void initSearchFab() {
        locationInput = findViewById(R.id.localitaSelection);
        selectButton = findViewById(R.id.selezionaSpecie);

        Mapbox.getInstance(this, getString(R.string.mapbox_access_token));
        PlaceAutocomplete.clearRecentHistory(getApplicationContext());
        locationInput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new PlaceAutocomplete.IntentBuilder()
                        .accessToken(Mapbox.getAccessToken() != null ? Mapbox.getAccessToken() : getString(R.string.mapbox_access_token))
                        .placeOptions(PlaceOptions.builder()
                                .country("IT")
                                .backgroundColor(Color.parseColor("#EEEEEE"))
                                .limit(10)
                                .proximity(Point.fromLngLat(40.9397284,3.7186683))
                                .build(PlaceOptions.MODE_CARDS))
                        .build(LocationPicker.this);
                startActivityForResult(intent, REQUEST_CODE_AUTOCOMPLETE);
            }
        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap)
    {
        //Coordinates: SUD, OVEST - NORD, EST
        LatLngBounds boundsItaly = new LatLngBounds(new LatLng(40.666397,10.172663), new LatLng(42.0914, 15.120292));
        googleMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
        googleMap.setMinZoomPreference(2f);
        googleMap.addMarker(new MarkerOptions().position(selectedPlace));
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(selectedPlace));
        googleMap.animateCamera(CameraUpdateFactory.zoomTo(20),400, null);

        // Constrain the camera target to the Adelaide bounds.
        googleMap.setLatLngBoundsForCameraTarget(boundsItaly);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE_AUTOCOMPLETE) {
            // Retrieve selected location's CarmenFeature
            CarmenFeature feature = PlaceAutocomplete.getPlace(data);
            //getting coordinares of selected place
            selectedPlace = new LatLng(feature.center().latitude(), feature.center().longitude());
            // this string will return to invoking activity
            selectedPlaceString = feature.placeName();
            locationInput.setText(selectedPlaceString);
            locationInput.setTextSize(TypedValue.COMPLEX_UNIT_DIP,15f);

            SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
            mapFragment.getMapAsync(this::onMapReady);
        }
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
}