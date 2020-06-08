package com.dcab.bugfinder;

import android.app.Dialog;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class Register extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registrazione);

        //Riferimenti
        registerNome = findViewById(R.id.registerName);
        registerCognome = findViewById(R.id.registerSurname);
        registerEmail = findViewById(R.id.registerEmail);
        registerUsername= findViewById(R.id.registerUsername);
        registerPassword = findViewById(R.id.registerPassword);
        registratiButton = findViewById(R.id.registratiButton);

        //Listeners
        registratiButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                register();
            }
        });

        dbHelper = new DatabaseHelper(this);

        db = dbHelper.getWritableDatabase();

    }


    public void register()
    {
        ContentValues values = new ContentValues();

        values.put(SchemaDB.Tavola.COLUMN_NAME, registerNome.getText().toString());
        values.put(SchemaDB.Tavola.COLUMN_SURNAME, registerCognome.getText().toString());
        values.put(SchemaDB.Tavola.COLUMN_EMAIL, registerEmail.getText().toString());
        values.put(SchemaDB.Tavola.COLUMN_USERNAME, registerUsername.getText().toString());
        values.put(SchemaDB.Tavola.COLUMN_PASSWORD, registerPassword.getText().toString());

        db.insert(SchemaDB.Tavola.TABLE_NAME, null, values);

        final Dialog dialog = new Dialog(Register.this);
        dialog.setContentView(R.layout.dialog_ok);
        okTW = dialog.findViewById(R.id.okTW);
        okTW.setText("Registrazione effettuata");
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.setTitle("");
        dialog.setCancelable(true);

        new CountDownTimer(5000, 1000)
        {
            @Override
            public void onTick(long millisUntilFinished) { }

            @Override
            public void onFinish() { dialog.dismiss(); }
        }.start();

        //now that the dialog is set up, it's time to show it
        dialog.show();
    }

    private SQLiteDatabase db;
    private DatabaseHelper dbHelper;
    private EditText registerNome, registerCognome, registerEmail, registerUsername, registerPassword;
    private Button registratiButton;
    private TextView okTW;

}
