package com.dcab.bugfinder;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

public class Report extends AppCompatActivity
{
    // using to identify request in LocationPicker.java
    public static int LOCATION_REQUEST_CODE = 2;

    private ArrayAdapter arrayAdapter;
    private ListView listView;
    private ArrayList bugList;
    private Button specieButton;
    private Button locationButton;
    private Button dateButton;
    private LinearLayout formNewReportBottom;
    private ImageView sparisciTutto;
    private LatLng selectedPlace;
    private String selectedPlaceString;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_report);

        // ----- lista di prova
        //---- bisogno implementare il retrieve da una BD
        listView = findViewById(R.id.bug_selection_list);
        locationButton = findViewById(R.id.locationSelectorButton);
        bugList = new ArrayList();
        bugList.add("prova1");
        bugList.add("prova2");
        bugList.add("prova3");
        bugList.add("prova4");
        bugList.add("prova5");
        bugList.add("prova6");
        bugList.add("prova6");
        bugList.add("prova6");
        bugList.add("prova6");
        bugList.add("prova6");
        bugList.add("prova6");
        bugList.add("prova6");
        bugList.add("prova6");
        bugList.add("prova6");
        bugList.add("prova6");

        arrayAdapter = new ArrayAdapter(Report.this, R.layout.bug_list_item,R.id.item_tw,bugList);
        listView.setAdapter(arrayAdapter);

        specieButton = findViewById(R.id.showSpecie);
        formNewReportBottom = findViewById(R.id.formNewReportBottom);
        sparisciTutto = findViewById(R.id.sparisciTutto);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                TextView text = (TextView) view.findViewById(R.id.item_tw);
                specieButton.setText(text.getText());
                sparisciTutto.callOnClick();
            }
        });

        specieButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                specieButton.setBackground(getDrawable(R.drawable.circle_shape3_no_border));
                listView.setVisibility(View.VISIBLE);
                formNewReportBottom.setVisibility(View.GONE);
            }
        });

        sparisciTutto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                specieButton.setBackground(getDrawable(R.drawable.circle_shape3));
                formNewReportBottom.setVisibility(View.VISIBLE);
                listView.setVisibility(View.GONE);
            }
        });
    }

    public void onLocationSelectorButtonClick(View view)
    {
        Intent intent = new Intent(getApplicationContext(), LocationPicker.class);

        intent.putExtra("selectedPlaceString",selectedPlaceString);
        intent.putExtra("selectedPlace",selectedPlace);

        startActivityForResult(intent,LOCATION_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == LOCATION_REQUEST_CODE && resultCode == RESULT_OK)
        {
            selectedPlaceString = data.getStringExtra("selectedPlaceString");
            locationButton.setText(selectedPlaceString);
            selectedPlace = data.getParcelableExtra("selectedPlace");
        }
    }

    @Override
    public void onBackPressed() {
        if(listView.getVisibility() == View.VISIBLE)
            sparisciTutto.callOnClick();
        else
            super.onBackPressed();
    }

    public void backToPrevious(View v) { finish(); }
}
