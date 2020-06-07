package com.dcab.bugfinder;

import android.app.Dialog;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class Report extends AppCompatActivity
{
    private ArrayAdapter arrayAdapter;
    private ListView listView;
    private ArrayList bugList;
    private Button specieButton, reportButton;
    private LinearLayout formNewReportBottom;
    private ImageView sparisciTutto;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_report);

        // ----- lista di prova
        listView = findViewById(R.id.bug_selection_list);
        bugList = new ArrayList();
        bugList.add("prova1");
        bugList.add("prova2");
        bugList.add("prova3");
        bugList.add("prova4");
        bugList.add("prova5");
        bugList.add("prova6");

        arrayAdapter = new ArrayAdapter(Report.this, R.layout.bug_list_item,R.id.item_tw,bugList);
        listView.setAdapter(arrayAdapter);

        specieButton = findViewById(R.id.showSpecie);
        formNewReportBottom = findViewById(R.id.formNewReportBottom);
        sparisciTutto = findViewById(R.id.sparisciTutto);
        reportButton = findViewById(R.id.reportButton);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                TextView text = (TextView) view.findViewById(R.id.item_tw);
                specieButton.setText(text.getText());
                sparisciTutto.callOnClick();
            }
        });

        specieButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                specieButton.setBackground(getDrawable(R.drawable.circle_shape3_no_border));
                listView.setVisibility(View.VISIBLE);
                formNewReportBottom.setVisibility(View.GONE);
            }
        });

        sparisciTutto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                specieButton.setBackground(getDrawable(R.drawable.circle_shape3));
                formNewReportBottom.setVisibility(View.VISIBLE);
                listView.setVisibility(View.GONE);
            }
        });

        reportButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                final Dialog dialog = new Dialog(Report.this);
                dialog.setContentView(R.layout.dialog_ok);
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
        });
    }

    public void backToPrevious(View v)
    {
        finish();
    }

}
