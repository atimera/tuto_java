package com.opc.tuto.zarmy.comportement;

import com.opc.tuto.zarmy.comportement.EspritCombatif;

public class Pacifiste implements EspritCombatif {
    @Override
    public void combat() {
        System.out.println("Je ne combat pas !");
    }
}
