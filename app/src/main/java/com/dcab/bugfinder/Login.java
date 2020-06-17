package com.dcab.bugfinder;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
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
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Login extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        //Riferimenti
        registerButton = findViewById(R.id.registerButton);
        loginButton = findViewById(R.id.loginButton);
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        leftArrow = findViewById(R.id.left_arrow);
        errorLL = findViewById(R.id.errorLL);

        sp = getSharedPreferences("login", MODE_PRIVATE);

        if(sp.getBoolean("logged", false))
            goToUserArea();


        //Listeners
        registerButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                registerAccount();
            }
        });
        loginButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                login(v);
            }
        });
        leftArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                onBackPressed();
            }
        });

        leftArrow.clearFocus();

        //Database Part
        dbHelper = new DatabaseHelper(this);

        db = dbHelper.getReadableDatabase();
    }


    public void registerAccount()
    {
        Intent intent = new Intent(getApplicationContext(), Register.class);

        startActivity(intent);
    }


    public Cursor login(View v)
    {
        username.clearFocus();
        password.clearFocus();

        hideKeyboardFrom(getApplicationContext(), v);

        String valore = username.getText().toString();
        String valore2 = password.getText().toString();
        Cursor cursor = db.rawQuery("SELECT * FROM users WHERE username = ? AND password = ?", new String[] {valore, valore2});

        if(cursor != null && cursor.moveToFirst())
        {
            String nome = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.columns[1]));
            String cognome = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.columns[2]));
            String email = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.columns[3]));
            String username = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.columns[4]));
            String password = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.columns[5]));

            final Dialog dialog = new Dialog(Login.this);
            dialog.setContentView(R.layout.dialog_ok);
            okTW = dialog.findViewById(R.id.okTW);
            okTW.setText("Login effettuato");
            dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
            dialog.setTitle("");
            dialog.setCancelable(true);

            new CountDownTimer(3000, 1000)
            {
                @Override
                public void onTick(long millisUntilFinished) { }

                @Override
                public void onFinish()
                {
                    sp.edit().putBoolean("logged", true).apply();
                    sp.edit().putString("name", nome).apply();
                    sp.edit().putString("surname", cognome).apply();
                    sp.edit().putString("email", email).apply();
                    sp.edit().putString("username", username).apply();
                    sp.edit().putInt("id", cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.columns[0]))).apply();

                    dialog.dismiss();
                    onBackPressed();
                }
            }.start();

            //now that the dialog is set up, it's time to show it
            dialog.show();

            return cursor;
        } else {
            errorLL.setVisibility(View.VISIBLE);
            return null;
        }
    }


    public void goToUserArea()
    {
        return;
    }


    public static void hideKeyboardFrom(Context context, View view)
    {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }



    private Button registerButton, loginButton;
    private EditText username, password;
    private TextView okTW;
    private ImageView leftArrow;
    private LinearLayout errorLL;
    private SQLiteDatabase db;
    private DatabaseHelper dbHelper;
    private SharedPreferences sp;

}
