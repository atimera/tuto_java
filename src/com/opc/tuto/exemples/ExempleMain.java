package com.opc.tuto.exemples;

import java.io.*;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.time.*;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ExempleMain {

    public static void main(String[] args) {

        String cheminFichier = "fichier.txt";
        String fichierACopier = "dictionnaire.txt";
        String fichierALire = "64_x_dictionnaire.txt";

/*
        copierFichier(cheminFichier);
        System.out.println("");
        compareLecture_FileInputStream_et_BufferedInputStream(fichierALire);
        System.out.println("");
        compareEcriture_FileOutputStream_et_BufferedOutputStream(fichierACopier);


        List<Game> mesJeux = lireListeGamesDepuisFichier("games.txt");

        for (Game game : mesJeux){
            System.out.println("===== Jeu "+ (mesJeux.indexOf(game)+1) +" =====");
            System.out.println(game);
        }

        ecrireTextDansFichier("nom,prix,style,notice", "games.csv");
        ecrireTextDansFichier("fifa 18,59.0,sport,Français", "games.csv");

        System.out.println(lireContenuTextDepuisFichier("games.csv"));

        compareLecture_BufferedOutputStream_et_BufferAvecFileChannel("2_x_dictionnaire.txt");

        // TEST les classes de java.nio.file
        Path path = Paths.get("test.txt");
        System.out.println("Chemin absolu du fichier : " + path.toAbsolutePath());
        System.out.println("Est-ce qu'il existe ? " + Files.exists(path));
        System.out.println("Nom du fichier : " + path.getFileName());
        System.out.println("Est-ce un répertoire ? " + Files.isDirectory(path));

        // Design paterne Decorator
        Patisserie patisserie = new CoucheChocolat(new CoucheBiscuit(new CoucheCaramel(new CoucheChocolat(new Gateau()))));
        System.out.println(patisserie.preparer());

        Class c = String.class;
        System.out.println(c);
        Method[] methods = c.getMethods();
        System.out.println(methods.length + " Méthodes");
        Set<String> set = new HashSet<>();
        System.out.println(c.getConstructors().length + " Constructeurs");
        System.out.println(c.getFields().length + " champs");
        System.out.println(c.getDeclaredFields().length + " champs déclaré");
        for(Method method :methods){
            //System.out.println(method.getName());
            set.add(method.getName());
        }
        System.out.println("\n"+ set.size() + " Methodes meme nom");
        int i=1;
        for (String method : set){
            System.out.print( method + " ");
            if(i%4==0) System.out.println("");
            i++;
        }

        // reflexicivité

        String nom = Paire.class.getName();
        try {
            // création d'un objet
            Class c = Class.forName(nom);
            // création d'une instance
            Object o = c.newInstance();
            // création des params du constructeur
            Class[] types = new Class[]{String.class, String.class};
            // on récupere le constructeur avec les 2 params
            Constructor cons = c.getConstructor(types);
            // on instancie l'objet avec le construteur chargé
            Object obj = cons.newInstance(new String[]{"valeur 1", "valeur 2"});

            Method m = c.getMethod("toString", null);

            System.out.println("----------------------------------------");
            System.out.println("Méthode " + m.getName() + " sur o2: " +m.invoke(obj, null));
            System.out.println("Méthode " + m.getName() + " sur o: " +m.invoke(o, null));

        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }


        List<Personne> listP = Arrays.asList(
                new Personne(1.80, 70, "A", "Nicolas", Couleur.BLEU),
                new Personne(1.56, 50, "B", "Nicole", Couleur.VERRON),
                new Personne(1.75, 65, "C", "Germain", Couleur.VERT),
                new Personne(1.68, 50, "D", "Michel", Couleur.ROUGE),
                new Personne(1.96, 65, "E", "Cyrille", Couleur.BLEU),
                new Personne(2.10, 120, "F", "Denis", Couleur.ROUGE),
                new Personne(1.90, 90, "G", "Olivier", Couleur.VERRON)
        );

        List<Personne> list = new ArrayList<>();

        // parcours
        Stream<Personne> sp = listP.stream();
        sp.forEach(System.out::println);
        System.out.println("");
        // prends 10 element et les filtre avant de les affiché
        listP.stream()
                .limit(10)
                .filter(x -> x.getPrenom().contains("a") || x.getPoids() > 70)
                .limit(3)
                .forEach(System.out::println);

        System.out.println("");
        listP.stream().filter(x -> x.getPoids() > 1.65).map(x -> (x.getPrenom() + " "+ x.getTaille() + " m")).forEach(System.out::println);

        System.out.println("");
        // somme des poids
        NumberFormat nf = new DecimalFormat("#0.00"); // pour formater les décimaux
        System.out.println(
                "Somme des poids : "+
                        listP.stream().map(Personne::getPoids).reduce ((x,y) -> (x+y)).get()
                        +"\nTaille moyenne : " +
                        nf.format(listP.stream().map(Personne::getTaille).reduce((x, y) -> (x+y)).get() / listP.size()) + "m"
        );
        // taille moyenne des plus de 75 kilos
        System.out.println("");
        System.out.println(
                "Taille moyenne des plus de 75 kilos : " +
                        nf.format (listP.parallelStream()
                                .filter(x -> x.getPoids() > 75)
                                .map(Personne::getTaille)
                                .reduce(0.0d, (x,y)->(x+y)) / listP.stream().filter(x -> x.getPoids() > 75).count())
        );
        // taille moyenne des moins de 75 kilos
        System.out.println("");
        System.out.println(
                "Taille moyenne des moins de 75 kilos : " +
                        nf.format (listP.parallelStream()
                                .filter(x -> x.getPoids() < 75)
                                .map(Personne::getTaille)
                                .reduce(0.0d, (x,y)->(x+y)) / listP.stream().filter(x -> x.getPoids() < 75).count())
        );

        double pMax = listP.parallelStream().map(Personne::getPoids).reduce((x,y) -> (x>y ? x:y)).get();
        System.out.println("Poids max "+ pMax);
        double pMin = listP.parallelStream().map(Personne::getPoids).reduce((x,y) -> (x<y ? x:y)).get();
        System.out.println("Poids min "+ pMin);

        System.out.println("");
        System.out.println("Y a-t-il quelqu'un qui pèse moins de 50 kils ?");
        double ref = 70.0;
        boolean oui = listP.stream().anyMatch(x -> x.getPoids() < ref) ;
        if(oui){
            System.out.println("OUI il y en a ! Les voici :");
            listP.parallelStream().filter(x -> x.getPoids() <= ref)  // parallelStream multiprocessor
                    .collect(Collectors.toList()).stream()
                    .skip(2) // saute 2
                    .peek(x -> System.out.println(x.toString())) // debug
                    .map(Personne::getPrenom).forEach(System.out::println);
        }else{
            System.out.println("Non il n'y en a pas !");
        }

        //Stream.iterate(2d, (x) -> x+1).limit(20).forEach(System.out::println);


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
*/



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
