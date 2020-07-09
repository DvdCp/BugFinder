package com.dcab.bugfinder;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.provider.MediaStore;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.dcab.bugfinder.database.DatabaseHelper;
import com.dcab.bugfinder.database.DatabaseHelperSegnalazioni;
import com.dcab.bugfinder.database.SchemaDB;
import com.dcab.bugfinder.database.SchemaDBSegnalazioni;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class Report extends AppCompatActivity
{
    // using to identify request in LocationPicker.java
    public static int LOCATION_REQUEST_CODE = 2;
    private static final int CAMERA_REQUEST_CODE = 3;
    private static final int STORAGE_REQUEST_CODE = 4;
    private static final int NOTE_ACTIVITY_REQUEST_CODE = 5;

    private ArrayAdapter arrayAdapter;
    private ListView listView;
    private ArrayList bugList;
    private Button specieButton;
    private Button locationButton;
    private Button dateButton;
    private ImageView takePhotoImageView;
    private Button selectImagerFromGalleryButton;
    private LinearLayout formNewReportBottom;
    private ImageView sparisciTutto;
    private RelativeLayout screen;
    private LatLng selectedPlace;
    private String selectedPlaceString;
    private MaterialDatePicker materialDatePicker;
    private LinearLayout imageMissingError;
    private LinearLayout locationMissingError;
    private LinearLayout dateMissingError;
    private boolean isPhotoSelected;
    private boolean isLocationSelected;
    private boolean isDateSelected;
    private TextView optionalNotesTW;
    private SharedPreferences sp;
    private SQLiteDatabase db;
    private DatabaseHelperSegnalazioni dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_report);

        sp = getSharedPreferences("login", MODE_PRIVATE);

        dbHelper = new DatabaseHelperSegnalazioni(this);

        db = dbHelper.getWritableDatabase();

        // ----- lista di prova
        //---- bisogno implementare il retrieve da una BD
        specieButton = findViewById(R.id.showSpecie);
        dateButton = findViewById(R.id.datePickerButton);
        formNewReportBottom = findViewById(R.id.formNewReportBottom);
        sparisciTutto = findViewById(R.id.sparisciTutto);
        screen = findViewById(R.id.RL1);
        listView = findViewById(R.id.bug_selection_list);
        locationButton = findViewById(R.id.locationSelectorButton);
        takePhotoImageView = findViewById(R.id.takePhoto);
        selectImagerFromGalleryButton = findViewById(R.id.selectImageFromGallery);
        imageMissingError = findViewById(R.id.ImageMissingErrorLL);
        locationMissingError = findViewById(R.id.LocationMissingErrorLL);
        dateMissingError = findViewById(R.id.DateMissingErrorLL);
        optionalNotesTW = findViewById(R.id.optionalNotesTW);
        isPhotoSelected = false;
        isLocationSelected = false;
        isDateSelected = false;

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

        //MaterialDesign DatePicker
        MaterialDatePicker.Builder builder = MaterialDatePicker.Builder.datePicker();
        builder.setTitleText("Scegli una data");
        builder.setTheme(R.style.DatePicker);
        materialDatePicker = builder.build();

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

        dateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideErrors();
                materialDatePicker.show(getSupportFragmentManager(),"DATE_PICKER");
                screen.setAlpha(0.5f);
            }
        });

        materialDatePicker.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener() {
            @Override
            public void onPositiveButtonClick(Object selection) {
                dateButton.setText(materialDatePicker.getHeaderText());
                screen.setAlpha(1f);
                isDateSelected = true;
                dateMissingError.setVisibility(View.GONE);
            }
        });

        materialDatePicker.addOnNegativeButtonClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            screen.setAlpha(1f);
            }
        });

        materialDatePicker.addOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                    screen.setAlpha(1f);
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

        takePhotoImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ContextCompat.checkSelfPermission(Report.this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED)
                {
                    dispatchTakePictureIntent();
                }
                else // user tap for the first the button
                {
                    requestCameraPermission();
                }

                hideErrors();
            }
        });

        selectImagerFromGalleryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                {
                    // checking the permission
                    if(ContextCompat.checkSelfPermission(Report.this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)
                    {
                        dispatchSelectPictureIntent();
                    }
                    else // user tap for the first the button
                    {
                        requestStoragePermission();
                    }

                    hideErrors();
                }
            }
        });

        //******** SCROLLABLE TEXT VIEW SETTINGS FOR "OPTIONAL NOTES" ********
        optionalNotesTW.setMovementMethod(new ScrollingMovementMethod());
        optionalNotesTW.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                optionalNotesTW.getParent().requestDisallowInterceptTouchEvent(true);
                return false;
            }
        });
}

    public void onLocationSelectorButtonClick(View view)
    {
        Intent intent = new Intent(getApplicationContext(), LocationPicker.class);

        intent.putExtra("selectedPlaceString",selectedPlaceString);
        intent.putExtra("selectedPlace",selectedPlace);

        startActivityForResult(intent,LOCATION_REQUEST_CODE);
        hideErrors();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == LOCATION_REQUEST_CODE && resultCode == RESULT_OK)
        {
            selectedPlaceString = data.getStringExtra("selectedPlaceString");
            locationButton.setText(selectedPlaceString);
            selectedPlace = data.getParcelableExtra("selectedPlace");
            isLocationSelected = true;
            locationMissingError.setVisibility(View.GONE);
        }
        else if(requestCode == CAMERA_REQUEST_CODE && resultCode == RESULT_OK)
        {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            takePhotoImageView.setImageBitmap(imageBitmap);
            takePhotoImageView.setBackground(getDrawable(R.drawable.group_3));
            isPhotoSelected = true;
            imageMissingError.setVisibility(View.GONE);
        }
        else if(requestCode == STORAGE_REQUEST_CODE && resultCode == RESULT_OK)
        {
            Uri selectedImage = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), selectedImage);
                takePhotoImageView.setImageBitmap(bitmap);
                isPhotoSelected = true;
                imageMissingError.setVisibility(View.GONE);
            } catch (IOException e) {
                Log.i("TAG", "Some exception " + e);
            }
        }
        else if(requestCode == NOTE_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK)
        {
                String notes = data.getStringExtra("optionalNotes");
                optionalNotesTW.setText(notes);
        }
    }

    @Override
    public void onBackPressed()
    {
        if(listView.getVisibility() == View.VISIBLE)
            sparisciTutto.callOnClick();
        else
            super.onBackPressed();
    }

    public void backToPrevious(View v)
    {
        finish();
    }

    public void sendReport(View v)
    {
        if(!isPhotoSelected)
            imageMissingError.setVisibility(View.VISIBLE);
        if(!isLocationSelected)
            locationMissingError.setVisibility(View.VISIBLE);
        if(!isDateSelected)
            dateMissingError.setVisibility(View.VISIBLE);
        if(isPhotoSelected && isLocationSelected && isDateSelected)
        {
            // catching user_id and compressing image
            int user_id = sp.getInt("id", 0);

            Bitmap pic = ((BitmapDrawable) takePhotoImageView.getDrawable()).getBitmap();
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            pic.compress(Bitmap.CompressFormat.PNG, 100, stream);
            byte[] byteArray = stream.toByteArray();

            // saving the report in the DB
            ContentValues values = new ContentValues();

            values.put(SchemaDBSegnalazioni.Tavola.COLUMN_IMAGE, byteArray);
            values.put(SchemaDBSegnalazioni.Tavola.COLUMN_TYPE, specieButton.getText().toString());
            values.put(SchemaDBSegnalazioni.Tavola.COLUMN_LOCATION, locationButton.getText().toString());
            values.put(SchemaDBSegnalazioni.Tavola.COLUMN_DATE, dateButton.getText().toString());
            values.put(SchemaDBSegnalazioni.Tavola.COLUMN_NOTE, optionalNotesTW.getText().toString());
            values.put(SchemaDBSegnalazioni.Tavola.COLUMN_STATUS, "0");
            values.put(SchemaDBSegnalazioni.Tavola.COLUMN_USER, user_id);

            if( db.insert(SchemaDBSegnalazioni.Tavola.TABLE_NAME, null, values) != -1)
            {
                final Dialog dialog = new Dialog(Report.this);
                dialog.setContentView(R.layout.dialog_ok);
                final TextView okTW = dialog.findViewById(R.id.okTW);
                okTW.setText("Segnalazione Inviata");
                dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                dialog.setTitle("");
                dialog.setCancelable(true);
                dialog.show();

                new CountDownTimer(2000, 1000)
                {
                    @Override
                    public void onTick(long millisUntilFinished) { }

                    @Override
                    public void onFinish()
                    {
                        dialog.dismiss();
                        onBackPressed();
                    }
                }.start();
            }
            else
            {
                Log.e("ErrorDB","ERROR DURING SAVING REPORT IN DB");
            }

        }
    }

    public void dispatchNoteActivity(View v)
    {
        Intent noteActivityIntent = new Intent(getApplicationContext(), OptionalNote.class);
        String notes = optionalNotesTW.getText().toString();
        noteActivityIntent.putExtra("optionalNotes",notes);
        startActivityForResult(noteActivityIntent,NOTE_ACTIVITY_REQUEST_CODE);
        hideErrors();
    }

    private void hideErrors()
    {
        locationMissingError.setVisibility(View.GONE);
        imageMissingError.setVisibility(View.GONE);
        dateMissingError.setVisibility(View.GONE);
    }

    //*************** PERMISSION REQUEST METHODS ***************
    private void requestCameraPermission()        //ask user to accept or decline the permission
    {
        if(ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CAMERA))
        {
            new AlertDialog.Builder(this)
                    .setTitle("Permesso Richiesto")
                    .setMessage("Per poter continuare, bisogna dare il permesso di accesso alla fotocamera")
                    // setting positive and negative button
                    .setPositiveButton("OK", new DialogInterface.OnClickListener()
                    {
                        @Override
                        public void onClick(DialogInterface dialog, int which)
                        {
                            ActivityCompat.requestPermissions(Report.this, new String[]{Manifest.permission.CAMERA},CAMERA_REQUEST_CODE);
                        }
                    })
                    .setNegativeButton("Annulla", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    })
                    // creating and showing the dialog box
                    .create()
                    .show();
        }
        else // this is the case which user tap for the first time the button to gain the permission
        {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, CAMERA_REQUEST_CODE);
        }
    }

    private void requestStoragePermission()        //ask user to accept or decline the permission
    {
        if(ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.READ_EXTERNAL_STORAGE))
        {
            new AlertDialog.Builder(this)
                    .setTitle("Permesso Richiesto")
                    .setMessage("Per poter continuare, bisogna dare il permesso di accesso alla galleria")
                    // setting positive and negative button
                    .setPositiveButton("OK", new DialogInterface.OnClickListener()
                    {
                        @Override
                        public void onClick(DialogInterface dialog, int which)
                        {
                            ActivityCompat.requestPermissions(Report.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},STORAGE_REQUEST_CODE);
                        }
                    })
                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    })
                    // creating and showing the dialog box
                    .create()
                    .show();
        }
        else // this is the case which user tap for the first time the button to gain the permission
        {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, STORAGE_REQUEST_CODE);
        }
    }

    //*************** DISPATCHING INTENTS METHODS ***************
    private void dispatchSelectPictureIntent()
    {
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
        photoPickerIntent.setType("image/*");

        if (photoPickerIntent.resolveActivity(getPackageManager()) != null)
        {
            startActivityForResult(photoPickerIntent, STORAGE_REQUEST_CODE);
        }

    }

    private void dispatchTakePictureIntent()
    {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, CAMERA_REQUEST_CODE);
        }
    }
}
