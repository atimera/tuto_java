package com.opc.tuto.zarmy;

import com.opc.tuto.zarmy.comportement.Deplacement;
import com.opc.tuto.zarmy.comportement.EspritCombatif;
import com.opc.tuto.zarmy.comportement.PremierSoin;
import com.opc.tuto.zarmy.comportement.Soin;

public class Medecin extends Personnage {

    // constructeur par défaut
    public Medecin(){
        soin = new PremierSoin();
    }

    public Medecin(EspritCombatif esprit, Soin soin, Deplacement dep){
        super(esprit, soin, dep);
    }
}
