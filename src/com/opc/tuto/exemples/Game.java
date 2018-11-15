package com.opc.tuto.exemples;

import java.io.Serializable;

public class Game implements Serializable{
    private String nom, style;
    private double prix;

    //private transient Notice notice; // si on ne veux pas sauvegarder cette info
    private Notice notice;

    public Game(String nom, String style, double prix) {
        this.nom = nom;
        this.style = style;
        this.prix = prix;
        this.notice = new Notice();
    }

    public String toString(){
        return "Nom du jeu : " + this.nom + "\n" +
                "Style de jeu : " + this.style + "\n" +
                "Prix du jeu : " + this.prix + "\n" +
                this.notice.getLangue();
    }

    public Notice getNotice() {
        return notice;
    }
}