package com.opc.tuto.zarmy;

import com.opc.tuto.zarmy.comportement.*;

public abstract class Personnage {

    // Des instance de comportement avec des valeurs par défaut
    protected EspritCombatif espritCombatif = new Pacifiste();
    protected Deplacement deplacement = new Marcher();
    protected Soin soin = new AucunSoin();

    // constructeur par défaut
    public Personnage() {}

    public Personnage(EspritCombatif espritCombatif, Soin soin, Deplacement deplacement) {
        this.espritCombatif = espritCombatif;
        this.soin = soin;
        this.deplacement = deplacement;
    }

    //======= METHODES =======//

    public void seDeplacer(){
        deplacement.deplacer();
    }

    public void soigner(){
        soin.soigner();
    }

    public void coombattre(){
        espritCombatif.combat();
    }

    @Override
    public String toString() {
        return super.toString()+ " : "+ this.getClass().getSimpleName();
    }

    //====== SETTERS ======//

    public void setEspritCombatif(EspritCombatif espritCombatif) {
        this.espritCombatif = espritCombatif;
    }

    public void setDeplacement(Deplacement deplacement) {
        this.deplacement = deplacement;
    }

    public void setSoin(Soin soin) {
        this.soin = soin;
    }
}
