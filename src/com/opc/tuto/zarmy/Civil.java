package com.opc.tuto.zarmy;

import com.opc.tuto.zarmy.comportement.Deplacement;
import com.opc.tuto.zarmy.comportement.EspritCombatif;
import com.opc.tuto.zarmy.comportement.Soin;

public class Civil extends Personnage {

    // constructeur par défaut
    public Civil(){}

    public Civil(EspritCombatif esprit, Soin soin, Deplacement dep){
        super(esprit, soin, dep);
    }
}
