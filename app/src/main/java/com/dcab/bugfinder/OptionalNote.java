package com.dcab.bugfinder;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class OptionalNote extends AppCompatActivity {

    private EditText editText;
    private String notes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.note_aggiuntive);

        editText = findViewById(R.id.note_aggiuntive);
        notes = getIntent().getStringExtra("optionalNotes");

        if(!notes.equals(null))
            editText.setText(notes);
    }

    public void onConfirmButtonClick(View v)
    {
        notes = editText.getText().toString();
        Intent result = new Intent();
        result.putExtra("optionalNotes", notes);
        setResult(RESULT_OK,result);
        finish();
    }

}