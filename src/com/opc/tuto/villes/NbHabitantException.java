package com.opc.tuto.villes;

public class NbHabitantException extends Exception {
    public NbHabitantException(int nbre){
        System.out.println("Le nombre d'habitant ne peux pas être négatif");
        System.out.println("\t => " + nbre);
    }
}
