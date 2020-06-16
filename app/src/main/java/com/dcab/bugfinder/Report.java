package com.dcab.bugfinder;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
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

import com.google.android.gms.maps.model.LatLng;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;

import java.io.IOException;
import java.util.ArrayList;

public class Report extends AppCompatActivity
{
    // using to identify request in LocationPicker.java
    public static int LOCATION_REQUEST_CODE = 2;
    private static final int CAMERA_REQUEST_CODE = 3;
    private static final int STORAGE_REQUEST_CODE = 4;
    private static final int GALLERY_REQUEST_CODE = 5;

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

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_report);

        // ----- lista di prova
        //---- bisogno implementare il retrieve da una BD
        listView = findViewById(R.id.bug_selection_list);
        locationButton = findViewById(R.id.locationSelectorButton);
        takePhotoImageView = findViewById(R.id.takePhoto);
        selectImagerFromGalleryButton = findViewById(R.id.selectImageFromGallery);
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
        dateButton = findViewById(R.id.datePickerButton);
        formNewReportBottom = findViewById(R.id.formNewReportBottom);
        sparisciTutto = findViewById(R.id.sparisciTutto);
        screen = findViewById(R.id.RL1);

        //MaterialDesign DatePicker
        MaterialDatePicker.Builder builder = MaterialDatePicker.Builder.datePicker();
        builder.setTitleText("Scegli una data");
        builder.setTheme(R.style.DatePicker1);
        final MaterialDatePicker materialDatePicker = builder.build();

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
                materialDatePicker.show(getSupportFragmentManager(),"DATE_PICKER");
                screen.setAlpha(0.5f);
            }
        });

        materialDatePicker.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener() {
            @Override
            public void onPositiveButtonClick(Object selection) {
                dateButton.setText(materialDatePicker.getHeaderText());
                screen.setAlpha(1f);
            }
        });

        materialDatePicker.addOnNegativeButtonClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            screen.setAlpha(1f);
            }
        });

        sparisciTutto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                specieButton.setBackground(getDrawable(R.drawable.circle_shape3));
                formNewReportBottom.setVisibility(View.VISIBLE);
                listView.setVisibility(View.GONE);
                screen.setAlpha(1f);
                getSupportFragmentManager().beginTransaction().remove(materialDatePicker).commit();
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
                }
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

    private void dispatchSelectPictureIntent()
    {
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
        photoPickerIntent.setType("image/*");

        if (photoPickerIntent.resolveActivity(getPackageManager()) != null)
        {
            startActivityForResult(photoPickerIntent, STORAGE_REQUEST_CODE);
        }

    }

    private void dispatchTakePictureIntent() {

        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, CAMERA_REQUEST_CODE);
        }
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
        else if(requestCode == CAMERA_REQUEST_CODE && resultCode == RESULT_OK)
        {
                Bundle extras = data.getExtras();
                Bitmap imageBitmap = (Bitmap) extras.get("data");
                takePhotoImageView.setImageBitmap(imageBitmap);
                takePhotoImageView.setBackground(getDrawable(R.drawable.group_3));
        }
        else if(requestCode == STORAGE_REQUEST_CODE && resultCode == RESULT_OK)
        {
            Uri selectedImage = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), selectedImage);
                takePhotoImageView.setImageBitmap(bitmap);
            } catch (IOException e) {
                Log.i("TAG", "Some exception " + e);
            }
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
}
