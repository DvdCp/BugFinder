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

public class BugBookAdapter extends ArrayAdapter<BugBean>
{

    public BugBookAdapter(Context context, int resource, ArrayList<BugBean> listaInsetti)
    {
        super(context, resource, listaInsetti);
        resourceID = resource;
        inflater = LayoutInflater.from(context);
        registroInsetti = listaInsetti;
    }


    @Override
    public View getView(int position, View v, ViewGroup parent)
    {
        if(v == null)
            v = inflater.inflate(R.layout.insetto_layout, null);

        BugBean bug = getItem(position);

        nome = v.findViewById(R.id.nomeInsetto);
        logo = v.findViewById(R.id.logoInsetto);

        Log.d("DEBUG","Bug= "+bug);

        logo.setImageResource(R.drawable.icon_awesome_bug);
        nome.setText(bug.getNome());

        nome.setTag(position);
        logo.setTag(position);

        return v;
    }


    public ArrayList<BugBean> getRegistroInsetti() { return registroInsetti; }



    private LayoutInflater inflater;
    private int resourceID;
    private ArrayList<BugBean> registroInsetti;
    private BugBean bug;
    private TextView nome;
    private ImageView logo;

}
