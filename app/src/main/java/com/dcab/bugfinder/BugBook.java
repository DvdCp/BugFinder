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

        listaInsetti = new ArrayList<BugUIBean>();

        listaInsetti.add(new BugUIBean("Ciao1", "Ciao1", "Ciao1", "Ciao1", "Ciao1", "Ciao1"));
        listaInsetti.add(new BugUIBean("Ciao2", "Ciao2", "Ciao2", "Ciao2", "Ciao2", "Ciao2"));
        listaInsetti.add(new BugUIBean("Ciao3", "Ciao3", "Ciao1", "Ciao3", "Ciao3", "Ciao3"));
        listaInsetti.add(new BugUIBean("Ciao4", "Ciao4", "Ciao4", "Ciao4", "Ciao4", "Ciao4"));
        listaInsetti.add(new BugUIBean("Ciao5", "Ciao5", "Ciao5", "Ciao5", "Ciao5", "Ciao5"));
        listaInsetti.add(new BugUIBean("Ciao6", "Ciao6", "Ciao6", "Ciao6", "Ciao6", "Ciao6"));
        listaInsetti.add(new BugUIBean("Ciao7", "Ciao7", "Ciao7", "Ciao7", "Ciao7", "Ciao7"));

        registroInsetti = findViewById(R.id.registroInsetti);
        leftArrow = findViewById(R.id.left_arrow);

        bugBookAdapter = new BugBookAdapter(getApplicationContext(), R.layout.insetto_layout, listaInsetti);

        registroInsetti.setAdapter(bugBookAdapter);

        registroInsetti.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                parent.getItemAtPosition(position);
                showBug(position);
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


    public void showBug(int position)
    {
        final Dialog dialog = new Dialog(BugBook.this);
        dialog.setContentView(R.layout.insetto_page);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        dialog.setTitle("");
        dialog.setCancelable(true);

        bugTypeFULL = dialog.findViewById(R.id.bugTypeFULL);
        bugName = dialog.findViewById(R.id.bugName);
        bugOrdine = dialog.findViewById(R.id.bugOrdine);
        bugProvenienza = dialog.findViewById(R.id.bugProvenienza);
        bugDescription = dialog.findViewById(R.id.bugDescription);
        bugDifese = dialog.findViewById(R.id.bugDifese);

        bugTypeFULL.setText(listaInsetti.get(position).getType());
        bugName.setText(listaInsetti.get(position).getNome());
        bugOrdine.setText(listaInsetti.get(position).getOrdine());
        bugProvenienza.setText(listaInsetti.get(position).getProvenienza());
        bugDescription.setText(listaInsetti.get(position).getDescription());
        bugDifese.setText(listaInsetti.get(position).getDifese());

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
    private TextView bugTypeFULL, bugName, bugOrdine, bugProvenienza, bugDescription, bugDifese;
    private ImageView leftArrow;
    private BugBookAdapter bugBookAdapter;
    private ArrayList<BugUIBean> listaInsetti;

}
