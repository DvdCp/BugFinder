package com.dcab.bugfinder;

import android.app.Activity;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.dcab.bugfinder.database.DatabaseHelper;
import com.dcab.bugfinder.database.SchemaDB;

public class Register extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registrazione);

        sp = getSharedPreferences("login", MODE_PRIVATE);

        //Riferimenti
        registerNome = findViewById(R.id.registerName);
        registerCognome = findViewById(R.id.registerSurname);
        registerEmail = findViewById(R.id.registerEmail);
        registerUsername= findViewById(R.id.registerUsername);
        registerPassword = findViewById(R.id.registerPassword);
        registratiButton = findViewById(R.id.registratiButton);
        leftArrow = findViewById(R.id.left_arrow);
        errorLL = findViewById(R.id.errorLL);
        errorTW3 = findViewById(R.id.errorTW3);
        accediTW = findViewById(R.id.accediTW);


        //Listeners
        registratiButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                register(v);
            }
        });

        leftArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                onBackPressed();
            }
        });

        accediTW.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        leftArrow.clearFocus();

        dbHelper = new DatabaseHelper(this);

        db = dbHelper.getWritableDatabase();

    }


    public void register(View v)
    {
        hideKeyboardFrom(getApplicationContext(), v);

        ContentValues values = new ContentValues();

        nome = registerNome.getText().toString();
        cognome = registerCognome.getText().toString();
        email = registerEmail.getText().toString();
        username = registerUsername.getText().toString();
        password = registerPassword.getText().toString();

        values.put(SchemaDB.Tavola.COLUMN_NAME, nome);
        values.put(SchemaDB.Tavola.COLUMN_SURNAME, cognome);
        values.put(SchemaDB.Tavola.COLUMN_EMAIL, email);
        values.put(SchemaDB.Tavola.COLUMN_USERNAME, username);
        values.put(SchemaDB.Tavola.COLUMN_PASSWORD, password);

        cursor = db.rawQuery("SELECT * FROM users WHERE username = ?", new String[] {username});

        if(cursor != null && cursor.moveToFirst())
        {
            errorLL.setVisibility(View.VISIBLE);
            errorTW3.setText("Username già in uso");
            return;
        }

        cursor = db.rawQuery("SELECT * FROM users WHERE email = ?", new String[] {email});

        if(cursor != null && cursor.moveToFirst())
        {
            Log.d("DEBUG", "EMAIL GIA IN USO "+cursor.moveToFirst());
            errorLL.setVisibility(View.VISIBLE);
            errorTW3.setText("Email già in uso");
            return;
        }

        if(db.insert(SchemaDB.Tavola.TABLE_NAME, null, values) != -1)
        {
            final Dialog dialog = new Dialog(Register.this);
            dialog.setContentView(R.layout.dialog_ok);
            okTW = dialog.findViewById(R.id.okTW);
            okTW.setText("Registrazione effettuata");
            dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
            dialog.setTitle("");
            dialog.setCancelable(true);

            new CountDownTimer(5000, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                }

                @Override
                public void onFinish() {
                    dialog.dismiss();
                    onBackPressed();
                }
            }.start();

            //now that the dialog is set up, it's time to show it
            dialog.show();
        } else {
            final Dialog dialog = new Dialog(Register.this);
            dialog.setContentView(R.layout.dialog_error);
            notOkTW = dialog.findViewById(R.id.errorTW1);
            notOkTW2 = dialog.findViewById(R.id.errorTW2);
            retryButton = dialog.findViewById(R.id.retryButton);
            notOkTW.setText("Errore durante la registrazione");
            notOkTW2.setText("Assicurati che la tua connessione sia funzionante e riprova in seguito");
            dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
            dialog.setTitle("");
            dialog.setCancelable(true);

            retryButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });

            //now that the dialog is set up, it's time to show it
            dialog.show();
        }
    }


    public static void hideKeyboardFrom(Context context, View view)
    {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }


    private ImageView leftArrow;
    private EditText registerNome, registerCognome, registerEmail, registerUsername, registerPassword;
    private Button registratiButton, retryButton;
    private LinearLayout errorLL;
    private TextView okTW, notOkTW, notOkTW2, errorTW3, accediTW;
    private Cursor cursor;
    private SQLiteDatabase db;
    private DatabaseHelper dbHelper;
    private String nome, cognome, email, username, password;
    private SharedPreferences sp;

}
