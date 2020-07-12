package com.dcab.bugfinder;

import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.dcab.bugfinder.database.DatabaseHelperSegnalazioni;

import java.util.ArrayList;

public class ListReports extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_reports);

        sp = getSharedPreferences("login", MODE_PRIVATE);

        dbHelper = new DatabaseHelperSegnalazioni(this);

        db = dbHelper.getWritableDatabase();

        listaReport = new ArrayList<ReportBean>();

        //listaReport.add(new ReportBean("non identificata", "Montoro, Avellino, Italy", "23/11/2019", null,"notenotenote","1", 1));
        //listaReport.add(new ReportBean("Coleottero marino", "Mercato San Severino, Salerno, Italy", "10/02/2020", null,"notenotenote","1", 0));
        //listaReport.add(new ReportBean("di montagna raro", "Siano, Salerno, Italy", "03/04/2020",null,"notenotenote","1",2));

        // retrieving from DB
        int user_id = sp.getInt("id", -1);
        Cursor cursor = db.rawQuery("SELECT * FROM segnalazioni WHERE user = ?", new String[]{ Integer.toString(user_id) } );

        while(cursor.moveToNext())
        {
            byte[] byteArray = cursor.getBlob(cursor.getColumnIndexOrThrow(DatabaseHelperSegnalazioni.columns[1]));
            Bitmap bmp = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
            Drawable immagine = new BitmapDrawable(getResources(), bmp);

            String specie =  cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelperSegnalazioni.columns[2]));
            String luogo =  cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelperSegnalazioni.columns[3]));
            String data =  cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelperSegnalazioni.columns[4]));
            String note =  cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelperSegnalazioni.columns[5]));
            String user =  cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelperSegnalazioni.columns[6]));

            // status 0 = non confermato
            listaReport.add(new ReportBean(specie, luogo, data, immagine, note, user,0));

        }

        registroReports = findViewById(R.id.reports);
        leftArrow = findViewById(R.id.left_arrow);

        reportsAdapter = new ReportsAdapter(getApplicationContext(), R.layout.insetto_layout, listaReport);

        registroReports.setAdapter(reportsAdapter);

        leftArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                onBackPressed();
            }
        });

    }

    private ListView registroReports;
    private ImageView leftArrow;
    private ReportsAdapter reportsAdapter;
    private ArrayList<ReportBean> listaReport;
    private SharedPreferences sp;
    private SQLiteDatabase db;
    private DatabaseHelperSegnalazioni dbHelper;

}
