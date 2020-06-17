package com.dcab.bugfinder;

public class ReportBean
{

    public ReportBean(String specie, String luogo, String data, int status)
    {
        this.specie = specie;
        this.luogo = luogo;
        this.data = data;
        this.status = status;
    }


    public String getSpecie() { return specie; }

    public void setSpecie(String specie) { this.specie = specie; }

    public String getLuogo() { return luogo; }

    public void setLuogo(String luogo) { this.luogo = luogo; }

    public String getData() { return data; }

    public void setData(String data) { this.data = data; }

    public int getStatus() { return status; }

    public void setStatus(int status) { this.status = status; }


    private String specie, luogo, data;
    private int status;

}
