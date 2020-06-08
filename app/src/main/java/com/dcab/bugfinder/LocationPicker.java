package com.dcab.bugfinder;

import android.os.Bundle;

import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import com.mapbox.api.geocoding.v5.models.CarmenFeature;
import com.mapbox.mapboxsdk.plugins.places.autocomplete.PlaceAutocomplete;
import com.mapbox.mapboxsdk.plugins.places.autocomplete.model.PlaceOptions;
import com.mapbox.mapboxsdk.plugins.places.autocomplete.ui.PlaceAutocompleteFragment;
import com.mapbox.mapboxsdk.plugins.places.autocomplete.ui.PlaceSelectionListener;

public class LocationPicker extends AppCompatActivity {

    private static String FRAGMENT_TAG = "autocompletePlaceTag";
    private EditText locationEditText;
    private PlaceAutocompleteFragment autocompleteFragment;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.seleziona_localita);

        locationEditText = findViewById(R.id.localitaSelection);

        String query = locationEditText.getText().toString();

        if (savedInstanceState == null) {
            PlaceAutocomplete.clearRecentHistory(getApplicationContext());
            PlaceOptions placeOptions = PlaceOptions.builder()
                    .build(PlaceOptions.MODE_CARDS);
            autocompleteFragment = PlaceAutocompleteFragment.newInstance(getString(R.string.mapbox_access_token),placeOptions);

            final FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.add(R.id.RL1, autocompleteFragment,FRAGMENT_TAG);
            transaction.commit();

        } else {
            autocompleteFragment = (PlaceAutocompleteFragment)
                    getSupportFragmentManager().findFragmentByTag(FRAGMENT_TAG);
        }

        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(CarmenFeature carmenFeature) {
                Toast.makeText(getApplicationContext(),carmenFeature.text(), Toast.LENGTH_LONG).show();
                finish();
            }

            @Override
            public void onCancel() {
                finish();
            }
        });

    }

    public void backToPrevious (View v){
        finish();
    }
}
