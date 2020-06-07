package com.dcab.bugfinder;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

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

        registroInsetti.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                showBug();
            }
        });
    }


    /*public void showBug()
    {
        Intent intent = new Intent(getApplicationContext(), Bug.class);
        startActivity(intent);
    }*/


    public void showBug()
    {
        final Dialog dialog = new Dialog(BugBook.this);
        dialog.setContentView(R.layout.insetto_page);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        dialog.setTitle("");
        dialog.setCancelable(true);

        //set up button
        ImageView backArrow = (ImageView) dialog.findViewById(R.id.backArrow);
        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                dialog.dismiss();
            }
        });
        //now that the dialog is set up, it's time to show it
        dialog.show();
    }


    private ListView registroInsetti;
    private BugBookAdapter bugBookAdapter;
    private ArrayList<BugBean> listaInsetti;

}
