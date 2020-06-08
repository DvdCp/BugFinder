package com.dcab.bugfinder;

public class BugUIBean
{

    public BugUIBean(){}

    public BugUIBean(String nome, String type, String description, String ordine, String provenienza, String difese)
    {
        this.nome = nome;
        this.type = type;
        this.description = description;
        this.ordine = ordine;
        this.provenienza = provenienza;
        this.difese = difese;
    }

    String nome, type, description, ordine, provenienza, difese;

    public String getNome() { return nome; }

    public void setNome(String nome) { this.nome = nome; }

    public String getType() { return type; }

    public void setType(String type) { this.type = type; }

    public String getDescription() { return description; }

    public void setDescription(String description) { this.description = description; }

    public String getOrdine() { return ordine; }

    public void setOrdine(String ordine) { this.ordine = ordine; }

    public String getProvenienza() { return provenienza; }

    public void setProvenienza(String provenienza) { this.provenienza = provenienza; }

    public String getDifese() { return difese; }

    public void setDifese(String difese) { this.difese = difese; }

}
