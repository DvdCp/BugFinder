package com.dcab.bugfinder;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class ReportsAdapter extends ArrayAdapter<ReportBean>
{

    public ReportsAdapter(Context context, int resource, ArrayList<ReportBean> listaReports)
    {
        super(context, resource, listaReports);
        resourceID = resource;
        inflater = LayoutInflater.from(context);
        registroReports = listaReports;
    }


    @Override
    public View getView(int position, View v, ViewGroup parent)
    {
        if(v == null)
            v = inflater.inflate(R.layout.report_item, null);

        ReportBean report = getItem(position);

        specieTW = v.findViewById(R.id.specieInsetto);
        dataTW = v.findViewById(R.id.dataAvvistamento);
        localitaTW = v.findViewById(R.id.localitaAvvistamento);
        logo = v.findViewById(R.id.logoInsettoReport);
        statusTW = v.findViewById(R.id.statusReport);

        Log.d("DEBUG","Report= "+report);

        logo.setBackgroundResource(R.drawable.insetto1);
        specieTW.setText(report.getSpecie());
        dataTW.setText(report.getData());
        localitaTW.setText(report.getLuogo());

        if(report.getStatus() == 0)
            statusTW.setText("in esaminazione");
        else if(report.getStatus() == 1)
            statusTW.setText("confermato");
        else if(report.getStatus() == 2)
            statusTW.setText("non confermato");

        specieTW.setTag(position);
        dataTW.setTag(position);
        localitaTW.setTag(position);
        logo.setTag(position);

        return v;
    }


    public ArrayList<ReportBean> getRegistroReports() { return registroReports; }


    private int resourceID;
    private LayoutInflater inflater;
    private ArrayList<ReportBean> registroReports;
    private TextView specieTW, dataTW, localitaTW, statusTW;
    private ImageView logo;

}
