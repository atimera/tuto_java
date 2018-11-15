package com.opc.tuto.zarmy;

import com.opc.tuto.zarmy.comportement.*;

public class Chirurgien extends Personnage {

    // constructeur par défaut
    public Chirurgien(){
        soin = new Operation();
        espritCombatif = new CombatCouteau();
    }

    public Chirurgien(EspritCombatif esprit, Soin soin, Deplacement dep){
        super(esprit, soin, dep);
    }
}
