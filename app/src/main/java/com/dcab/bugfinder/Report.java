package com.dcab.bugfinder;


import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;


import androidx.appcompat.app.AppCompatActivity;


public class Report extends AppCompatActivity
{
    Button specieButton;
    LinearLayout formNewReport2;
    ImageView sparisciTutto;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_report);

        specieButton = findViewById(R.id.showSpecie);
        formNewReport2 = findViewById(R.id.formNewReport2);
        sparisciTutto = findViewById(R.id.sparisciTutto);

        specieButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                //Creating the instance of PopupMenu
                PopupMenu popup = new PopupMenu(getApplicationContext(), specieButton);
                //Inflating the Popup using xml file
                popup.getMenuInflater().inflate(R.menu.specie_menu, popup.getMenu());
                formNewReport2.setVisibility(View.GONE);
                //registering popup with OnMenuItemClickListener
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener()
                {
                    public boolean onMenuItemClick(MenuItem item) {
                        specieButton.setText(item.getTitle().toString());
                        formNewReport2.setVisibility(View.VISIBLE);
                        return true;
                    }
                });

                popup.show(); //showing popup menu
            }
        }); //closing the setOnClickListener method


        sparisciTutto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                formNewReport2.setVisibility(View.VISIBLE);
            }
        });
    }

    public void backToPrevious(View v)
    {
        finish();
    }

}
