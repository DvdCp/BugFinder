package com.dcab.bugfinder;

public class BugBean
{
    public BugBean(String nome, String type, String logo)
    {
        this.nome = nome;
        this.type = type;
        this.logo = logo;
    }

    public String getNome() { return nome; }

    public void setNome(String nome) { this.nome = nome; }

    public String getType() { return type; }

    public void setType(String type) { this.type = type; }

    public String getLogo() { return logo; }

    public void setLogo(String logo) { this.logo = logo; }

    @Override
    public String toString() { return "BugBean{" + "nome='" + nome + '\'' + '}'; }

    private String nome;
    private String type;
    private String logo;

}
