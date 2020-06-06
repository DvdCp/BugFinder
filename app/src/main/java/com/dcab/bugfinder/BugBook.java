package com.dcab.bugfinder;

import android.os.Bundle;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class BugBook extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registro_insetti);

        listaInsetti = new ArrayList<BugBean>();

        listaInsetti.add(new BugBean("Bagarozzo Luigi", null, null));
        listaInsetti.add(new BugBean("Bagarozzo Luigi2", null, null));
        listaInsetti.add(new BugBean("Bagarozzo Luigi3", null, null));
        listaInsetti.add(new BugBean("Bagarozzo Luigi4", null, null));
        listaInsetti.add(new BugBean("Bagarozzo Luigi5", null, null));
        listaInsetti.add(new BugBean("Bagarozzo Luigi6", null, null));
        listaInsetti.add(new BugBean("Bagarozzo Luigi7", null, null));
        listaInsetti.add(new BugBean("Bagarozzo Luigi8", null, null));

        registroInsetti = findViewById(R.id.registroInsetti);

        bugBookAdapter = new BugBookAdapter(getApplicationContext(), R.layout.insetto_layout, listaInsetti);

        registroInsetti.setAdapter(bugBookAdapter);
    }


    private ListView registroInsetti;
    private BugBookAdapter bugBookAdapter;
    private ArrayList<BugBean> listaInsetti;
}
