package com.opc.tuto.villes;

public class Ville {
    private String nom;
    private String pays;
    private String codePostal;
    private int nbHabitant;

    public Ville(){}

    public Ville(String nom, String codePostal){
        this.nom = nom;
        this.codePostal = codePostal;
    }

    //===== GETTERS ======//

    public String getNom() {
        return nom;
    }

    public String getPays() {
        return pays;
    }

    public String getCodePostal() {
        return codePostal;
    }

    public int getNbHabitant(){
        return nbHabitant;
    }

    //===== SETTERS =====/

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setPays(String pays) {
        this.pays = pays;
    }

    public void setCodePostal(String codePostal) {
        this.codePostal = codePostal;
    }

    public void setNbHabitant (int nbHabitant) throws NbHabitantException{
        if (nbHabitant < 0) throw new NbHabitantException(nbHabitant);
        this.nbHabitant = nbHabitant;
    }
}
