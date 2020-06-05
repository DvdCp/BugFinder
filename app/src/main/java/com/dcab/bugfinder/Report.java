package com.dcab.bugfinder;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.maps.SupportMapFragment;

public class Report extends AppCompatActivity implements AdapterView.OnItemSelectedListener
{
    Spinner spinner;
    LinearLayout formNewReport2;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_report);

        spinner = findViewById(R.id.specie);
        formNewReport2 = findViewById(R.id.formNewReport2);

        spinner.setOnItemSelectedListener(this);
        spinner.setOnTouchListener(spinnerOnTouch);
        spinner.setOnKeyListener(spinnerOnKey);

        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.specie, R.layout.spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);
    }

    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id)
    {
        // An item was selected. You can retrieve the selected item using
        // parent.getItemAtPosition(pos)
    }

    public void onNothingSelected(AdapterView<?> parent)
    {
        // Another interface callback
    }

    public void backToPrevious(View v)
    {
        finish();
    }

    private View.OnTouchListener spinnerOnTouch = new View.OnTouchListener() {
        public boolean onTouch(View v, MotionEvent event) {
            if (event.getAction() == MotionEvent.ACTION_UP) {
                formNewReport2.setVisibility(View.INVISIBLE);
            }
            return false;
        }
    };

    private View.OnKeyListener spinnerOnKey = new View.OnKeyListener()
    {
        public boolean onKey(View v, int keyCode, KeyEvent event) {
            if (keyCode == KeyEvent.KEYCODE_DPAD_CENTER) {
                formNewReport2.setVisibility(View.INVISIBLE);
                return true;
            } else {
                return false;
            }
        }
    };

}
