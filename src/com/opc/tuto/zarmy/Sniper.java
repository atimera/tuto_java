package com.opc.tuto.zarmy;

import com.opc.tuto.zarmy.comportement.CombatPistolet;
import com.opc.tuto.zarmy.comportement.Deplacement;
import com.opc.tuto.zarmy.comportement.EspritCombatif;
import com.opc.tuto.zarmy.comportement.Soin;

public class Sniper extends Personnage {

    // constructeur par défaut
    public Sniper(){
        espritCombatif = new CombatPistolet();
    }

    public Sniper(EspritCombatif esprit, Soin soin, Deplacement dep){
        super(esprit, soin, dep);
    }
}
