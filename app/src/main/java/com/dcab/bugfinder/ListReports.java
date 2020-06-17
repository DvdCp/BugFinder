package com.dcab.bugfinder;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class ListReports extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_reports);

        listaReport = new ArrayList<ReportBean>();

        listaReport.add(new ReportBean("non identificata", "Montoro, Avellino, Italy", "23/11/2019", 1));
        listaReport.add(new ReportBean("Coleottero marino", "Mercato San Severino, Salerno, Italy", "10/02/2020", 0));
        listaReport.add(new ReportBean("di montagna raro", "Siano, Salerno, Italy", "03/04/2020", 2));

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

}
