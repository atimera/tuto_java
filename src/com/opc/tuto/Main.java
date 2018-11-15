package com.opc.tuto;

import com.opc.tuto.exemples.Game;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {


    }



    // ====== Java.nio ========//

    // lecture de fichier encore plus rapide
    private static void compareLecture_BufferedOutputStream_et_BufferAvecFileChannel(String fichierACopier){

        try (
                BufferedInputStream bis = new BufferedInputStream(new FileInputStream(fichierACopier));
                //BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(new File(fichierACopier)));
                FileChannel fc = new FileInputStream(new File(fichierACopier)).getChannel();
            ){

            int size = (int)fc.size();
            ByteBuffer bBuffer = ByteBuffer.allocate(size);

            int bufSize = 8;
            byte[] buf = new byte[bufSize];

            System.out.println("Début Copie avec FileOutputStream");
            long debut = System.currentTimeMillis();
            while (bis.read(buf) >= 0);
            long duree1 = System.currentTimeMillis() - debut;
            System.out.println("L'écriture du fichier s'est effectuée en "+ (duree1) +" ms");
            System.out.println("Fin Copie avec FileOutputStream\n");

            System.out.println("Début lecture avec FileChannel");
            debut = System.currentTimeMillis();
            // Démarrage de la lecture;
            fc.read(bBuffer);
            // On prépare la lécture
            bBuffer.flip();
            long duree2 = System.currentTimeMillis() - debut;
            System.out.println("Lecture du fichier s'est effectuée en "+ (duree2) +" ms");
            System.out.println("Fin lecture avec FileChannel");


            if(duree2 > 0)
                System.out.println("\tMorale : \nEcrire avec FileChannel est "+(duree1/duree2)+" fois plus rapide qu'avec un BufferedInputStream.");

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    //====== Pour les Streams (texte) ========//

    private static void ecrireTextDansFichier(String text, String fichierOuEcrire){
        FileWriter fw;
        try {
            fw = new FileWriter(fichierOuEcrire);
            fw.append(text);
            //fw.write(text);

            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String lireContenuTextDepuisFichier(String fichierALire){
        FileReader fr;
        String contenu = "";
        try {
            fr = new FileReader(fichierALire);
            int i;
            while ((i = fr.read()) != -1){
                contenu += (char)i;
            }

            fr.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return contenu;
    }



    //====== Pous les Streams (binaire) ======//

    private static List<Game> lireListeGamesDepuisFichier(String games) {
        ObjectInputStream ois;
        List<Game> gameList = new ArrayList<>();

        try {
            ois = new ObjectInputStream(new BufferedInputStream(new FileInputStream(new File(games))));
            try {
                gameList = (List<Game>)ois.readObject();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            ois.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return gameList;
    }

    private static void sauvegarderListeGame(String fileName, List<Game> games) {
        ObjectOutputStream oos;

        try {
            oos = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(new File(fileName))));
            oos.writeObject(games);
            oos.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private static void compareEcriture_FileOutputStream_et_BufferedOutputStream(String fichierACopier) {
        FileInputStream fis;
        FileOutputStream fos;
        BufferedInputStream bis;
        BufferedOutputStream bos;


        try {
            fis = new FileInputStream(new File(fichierACopier));
            fos = new FileOutputStream(new File(fichierACopier.substring(0, fichierACopier.lastIndexOf('.')) + "CopieFOS.txt"));

            bis = new BufferedInputStream(new FileInputStream(new File(fichierACopier)));
            bos = new BufferedOutputStream(new FileOutputStream(new File(fichierACopier.substring(0, fichierACopier.lastIndexOf('.')) + "CopieBOS.txt")));

            int bufSize = 8;
            byte[] buf = new byte[bufSize];

            System.out.println("Début Copie avec FileOutputStream");
            long debut = System.currentTimeMillis();
            while (fis.read(buf) >= 0){
                fos.write(buf);
                buf = new byte[bufSize];
            }
            long duree1 = System.currentTimeMillis() - debut;
            System.out.println("L'écriture du fichier s'est effectuée en "+ (duree1) +" ms");
            System.out.println("Fin Copie avec FileOutputStream\n");

            System.out.println("Début Copie avec BufferedOutputStream");
            debut = System.currentTimeMillis();
            while (bis.read(buf) >= 0){
                bos.write(buf);
                buf = new byte[bufSize];
            }
            long duree2 = System.currentTimeMillis() - debut;
            System.out.println("L'ériture du fichier s'est effectuée en "+ (duree2) +" ms");
            System.out.println("Fin Copie avec BufferedOutputStream");

            if(duree2 > 0)
                System.out.println("\tMorale : \nEcrire avec BuffredInputStream est "+(duree1/duree2)+" fois plus rapide qu'avec un FileInputStream.");

            // fermeture des streams
            fis.close();
            bis.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private static void compareLecture_FileInputStream_et_BufferedInputStream(String fichierALire){
        FileInputStream fis = null;
        BufferedInputStream bis = null;

        try {
            fis = new FileInputStream(new File(fichierALire));
            bis = new BufferedInputStream(new FileInputStream(new File(fichierALire)));

            byte[] buf = new byte[8];
            System.out.println("Début Lecture avec FileOutputStream");
            long debut = System.currentTimeMillis();
            while (fis.read(buf) >= 0);
            long duree1 = System.currentTimeMillis() - debut;
            System.out.println("La lecture du fichier s'est effectuée en "+ (duree1) +" ms");
            System.out.println("Fin Lecture avec FileOutputStream");

            System.out.println("Début copie avec BufferedOutputStream");
            debut = System.currentTimeMillis();
            while (bis.read(buf) >= 0);
            long duree2 = System.currentTimeMillis() - debut;
            System.out.println("La lecture du fichier s'est effectuée en "+ (duree2) +" ms");
            System.out.println("Fin Lecture avec BufferedOutputStream");
            if(duree2 > 0)
                System.out.println("\tMorale : \nLire avec BuffredInputStream est "+(duree1/duree2)+" fois plus rapide qu'avec un FileInputStream.");

            // fermeture des streams
            fis.close();
            bis.close();

        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    private static void copierFichier(String cheminVersFichier, String cheminVersCopie){

        FileInputStream fis = null;
        FileOutputStream fos = null;

        try {
            File file = new File("fichier.txt");
            fis = new FileInputStream(file);
            fos = new FileOutputStream(new File("copie.txt"));

            int bufSize = 8;
            // buffer
            byte[] buf = new byte[bufSize];

            // le resultat de la lecture
            int n = 0;

            System.out.println("===== Début de la copie ======");
            while ((n = fis.read(buf)) >= 0){
                // ecriture de ce qui lu dans la copie
                fos.write(buf);

                // affichichage des bits lu et leurs equivalent en caractere
//                for(byte bit : buf){
//                    // System.out.print(" " + bit + "=" +(char)bit +"  ");
//                    // System.out.print(bit + " ");
//                    System.out.print((char)bit + " ");
//                }
//                System.out.println("");

                // vider le buffer
                buf = new byte[bufSize];
            }
            System.out.println("===== Fin de la copie ======");

        } catch (IOException ioe) {
            ioe.printStackTrace();
        } finally {
            if ( fis != null){
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (fos != null){
                try {
                    fos.close();
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
        }
    }

    private static void copierFichier(String cheminVersFichier){

        FileInputStream fis = null;
        FileOutputStream fos = null;

        try {
            File file = new File(cheminVersFichier);
            fis = new FileInputStream(file);
            fos = new FileOutputStream(new File(cheminVersFichier.substring(0, cheminVersFichier.lastIndexOf('.')) +"Copie.txt"));

            int bufSize = 8;
            // buffer
            byte[] buf = new byte[bufSize];


            System.out.println("===== Début de la copie ======");
            while ((fis.read(buf)) >= 0){
                // ecriture de ce qui lu dans la copie
                fos.write(buf);

                // affichichage des bits lu et leurs equivalent en caractere
//                for(byte bit : buf){
//                    // System.out.print(" " + bit + "=" +(char)bit +"  ");
//                    // System.out.print(bit + " ");
//                    System.out.print((char)bit + " ");
//                }
//                System.out.println("");

                // vider le buffer
                buf = new byte[bufSize];
            }
            System.out.println("===== Fin de la copie ======");

        } catch (IOException ioe) {
            ioe.printStackTrace();
        } finally {
            if ( fis != null){
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (fos != null){
                try {
                    fos.close();
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
        }
    }


}
