package com.opc.tuto.zarmy;

import com.opc.tuto.zarmy.comportement.CombatPistolet;
import com.opc.tuto.zarmy.comportement.Deplacement;
import com.opc.tuto.zarmy.comportement.EspritCombatif;
import com.opc.tuto.zarmy.comportement.Soin;

public class Guerrier extends Personnage {

    public Guerrier(){
        this.espritCombatif = new CombatPistolet();
    }

    public Guerrier(EspritCombatif esprit, Soin soin, Deplacement dep){
        super(esprit, soin, dep);
    }
}
