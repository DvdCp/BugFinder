package com.dcab.bugfinder;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;

public class ReportBean
{

    public ReportBean(String specie, String luogo, String data,Drawable immagine, String note, String user, int status)
    {
        this.specie = specie;
        this.luogo = luogo;
        this.data = data;
        this.immagine = immagine;
        this.status = status;
    }


    public String getSpecie() { return specie; }

    public void setSpecie(String specie) { this.specie = specie; }

    public String getLuogo() { return luogo; }

    public void setLuogo(String luogo) { this.luogo = luogo; }

    public String getData() { return data; }

    public void setData(String data) { this.data = data; }

    public Drawable getImmagine() {return immagine;}

    public void setImmagine(Drawable immagine) { this.immagine = immagine; }

    public int getStatus() { return status; }

    public void setStatus(int status) { this.status = status; }


    private String specie, luogo, data, note, user;
    private int status;
    private Drawable immagine;
}
