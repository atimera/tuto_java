package com.opc.tuto.zarmy.comportement;

public class PremierSoin implements Soin{
    @Override
    public void soigner() {
        System.out.println("Je donne les premiers soins !");
    }
}
