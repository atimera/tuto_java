package com.opc.tuto.zarmy.comportement;

public class CombatCouteau implements EspritCombatif {
    @Override
    public void combat() {
        System.out.println("Je me bat au couteau !");
    }
}
