package com.dcab.bugfinder;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class UserArea extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_area);

        sp = getSharedPreferences("login", MODE_PRIVATE);

        nome = sp.getString("name", "");
        cognome = sp.getString("surname", "");
        email = sp.getString("email", "");
        username = sp.getString("username", "");

        userNome = findViewById(R.id.userNome);
        userEmail = findViewById(R.id.userEmail);
        userUsername = findViewById(R.id.userUsername);
        userPassword = findViewById(R.id.userPassword);
        reportsButton = findViewById(R.id.reportsButton);
        leftArrow = findViewById(R.id.left_arrow);

        userNome.setText(nome+" "+cognome);
        userEmail.setText(email);
        userUsername.setText(username);

        reportsButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                toMyReports();
            }
        });

        leftArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                onBackPressed();
            }
        });
    }


    public void toMyReports()
    {
        Intent intent = new Intent(getApplicationContext(), ListReports.class);

        startActivity(intent);
    }


    private TextView userNome, userEmail, userUsername, userPassword;
    private ImageView leftArrow;
    private Button reportsButton;
    private SharedPreferences sp;
    private String nome, cognome, email, username;

}
