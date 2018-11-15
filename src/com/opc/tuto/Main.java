package com.opc.tuto;

import com.opc.tuto.exemples.Game;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.time.*;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class Main {

    public static void main(String[] args) {

        System.out.println("\nGestion du temps humain\n");
        // Get the current date and time
        LocalDateTime currentTime = LocalDateTime.now();
        System.out.println("Date et heure courante : " + currentTime);

        LocalDate date1 = currentTime.toLocalDate();
        System.out.println("Date courante : " + date1);

        Month mois = currentTime.getMonth();
        int jour = currentTime.getDayOfMonth();
        int heure = currentTime.getHour();

        System.out.println("Mois : " + mois +", jour : " + jour +", heure : " + heure);

        //Avoir le 25 décembre 2023
        LocalDateTime date2 = currentTime.withDayOfMonth(25).withYear(2023).withMonth(12);
        System.out.println("Date modifiée : " + date2);

        //une autre façon
        LocalDate date3 = LocalDate.of(2023, Month.DECEMBER, 25).minusYears(3);
        System.out.println("Autre façon de faire : " + date3);

        //On peut même parser une date depuis un String
        LocalTime parsed = LocalTime.parse("20:15:30").plusHours(5).plusMinutes(17).plusSeconds(100);
        System.out.println("Date parsée : " + parsed);

        //Le 25 Décembre 2018 a 13H37
        LocalDateTime ldt = LocalDateTime.of(2018, Month.DECEMBER, 25, 13, 37, 0);
        System.out.println("Date de référence : " + ldt);
        //Utilisation de l'objet ChronoUnit pour changer l'objet
        System.out.println("+1 semaine : " + ldt.plus(1, ChronoUnit.WEEKS));
        System.out.println("+2 mois : " + ldt.plus(2, ChronoUnit.MONTHS));
        System.out.println("+3 ans : " + ldt.plus(3, ChronoUnit.YEARS));
        System.out.println("+4 heures : " + ldt.plus(4, ChronoUnit.HOURS));
        System.out.println("-5 secondes : " + ldt.minus(5, ChronoUnit.SECONDS));
        System.out.println("-38 minutes : " + ldt.minusMinutes(38));

        System.out.println("\nDuration et Period\n");
        //Toujours notre 25 Décembre 2018 a 13H37
        //LocalDateTime ldt = LocalDateTime.of(2018, Month.DECEMBER, 25, 13, 37, 0);
        LocalDateTime ldt2 = ldt.plus(3, ChronoUnit.YEARS);
        LocalDateTime ldt3 = ldt.minusMinutes(1337);

        Period p = Period.between(ldt.toLocalDate(), ldt2.toLocalDate());
        Duration d = Duration.between(ldt.toLocalTime(), ldt3.toLocalTime());
        System.out.println("Période : " + p);
        System.out.println("Durée : " + d.getSeconds());
        System.out.println("Ecart en jour : " + ChronoUnit.DAYS.between(ldt, ldt2));

        System.out.println("\nTemporal Ajuster\n");
        //Le prochain samedi
        //Toujours notre 25 Décembre 2018 a 13H37
        LocalDate ld = LocalDate.of(2018, Month.DECEMBER, 25);
        LocalDate prochainSamedi = ld.with(TemporalAdjusters.next(DayOfWeek.SATURDAY));
        System.out.println(prochainSamedi);

        //Le troisième mardi du mois suivant
        //On ajoute un mois à notre date
        ld = ld.plus(1, ChronoUnit.MONTHS);
        //On en créer une nouvelle au premier jour du mois
        LocalDate ld2 = LocalDate.of(ld.getYear(), ld.getMonth(), 1);
        //On avance de trois mardi
        LocalDate troisiemeMardi = ld2	.with(TemporalAdjusters.next(DayOfWeek.TUESDAY))
                .with(TemporalAdjusters.next(DayOfWeek.TUESDAY))
                .with(TemporalAdjusters.next(DayOfWeek.TUESDAY));
        System.out.println(troisiemeMardi);

        System.out.println("\nZoneId et ZoneDateTime\n");
        Map<String, String> maps = ZoneId.SHORT_IDS;
        maps.values().stream().forEach((x) -> {System.out.println(x + " -- " + ZoneId.of(x).getRules());});

        //Et connaître notre fuseau
        System.out.println("");
        System.out.println("Fuseau horaire courant : "+ZoneId.systemDefault());
        System.out.println("Règle appliquer aux heures : " + ZoneId.systemDefault().getRules());

        LocalDateTime localDateTime = LocalDateTime.parse("2018-01-01T01:33:00");
        List<ZoneId> lzi = Arrays.asList(
                ZoneId.of("Europe/Paris"),
                ZoneId.of("Asia/Tokyo"),
                ZoneId.of("America/Anchorage")
        );

        lzi	.stream()
                .forEach((x) -> {
                    System.out.println(x + " : \t" + localDateTime.atZone(x).toInstant());
                });

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
