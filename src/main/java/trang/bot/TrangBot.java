package trang.bot;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TrangBot {
    static ArrayList<Page> m_PageAExplorer = new ArrayList<>();
    static ArrayList<Page> m_PageDejaFait = new ArrayList<>();
    static Set<String> m_Email = new HashSet<>();
    static int PageExplorer = 0;
    static String Path = "";

    public static void main(String[] args) {
        //Paramètre pour debug
        {
            args = new String[3];
            args[0] = "1";
            args[1] = "https://departement-info-cem.github.io/3N5-Prog3/testbot/index.html";
            args[2] = "C:\\Users\\2031296\\Desktop";
        }

        Page PageDeBase = new Page(args[1], Integer.parseInt(args[0]));
        Path = args[1];

        AfficherDebut();
        Validation(args);

        m_PageAExplorer.add(PageDeBase);

        while (!m_PageAExplorer.isEmpty()){
            try {
                ExtraireLiens(m_PageAExplorer.get(0));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        AfficherMessageFin();
        AfficherCouriel();
    }

    public static void AfficherMessageExploration(Page pPage){
        System.out.println("Exploration de >> " + pPage.URL);
    }
    public static void AfficherDebut(){
        System.out.println("Bonjour le correcteur ?" + "\n\n" + "Tout va bien, explorons");
    }
    public static void AfficherMessageFin(){
        System.out.println("\nNombre de pages explorées : " + PageExplorer);
    }

    public static void AfficherCouriel(){
        List<String> e = new ArrayList<>(m_Email);
        Collections.sort(e);
        System.out.println("Nombre de courriels extrait (en ordre alphabétique) : " + m_Email.size());
        for (String s : e) {
            System.out.println("\t"+ s);
        }
    }
    public static void ExtraireCouriels(Page page) throws IOException {
        Document doc = Jsoup.connect(page.URL).get();

        Pattern p = Pattern.compile("[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+");
        Matcher matcher = p.matcher(doc.text());
        while (matcher.find()) {
            m_Email.add(matcher.group());
        }
    }

    public static void SauvegardeFichier(Page pPage) throws IOException {
        Document doc = Jsoup.connect(pPage.URL).get();
        Elements links = doc.select("a[href]");

        //https://stackoverflow.com/questions/204784/how-to-construct-a-relative-path-in-java-from-two-absolute-paths-or-urls



        //Pattern p = Pattern.compile("[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+");
        //Matcher matcher = p.matcher(doc.text());
        //while (matcher.find()) {

        //    m_Email.add(matcher.group());
        //}
        //String master = doc.html();
        //String target = String.valueOf(matcher);
        //String replacement = "2031296@cegepmontpetit.ca";
        //String processed = master.replace(target, replacement);

        FileWriter file = new FileWriter( Path + "\\" + links.attr(":href"));
        file.write(doc.html());
        file.close();
    }

    //A CUSTOM LA REPONSE ERREUR
    public static void ExtraireLiens(Page pPage) throws IOException {
        Document doc = null;
        try {
            doc = Jsoup.connect(pPage.URL).get();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException i) {
            System.out.println("Url mal formée " + pPage.URL);
            SauvegardePageExplorer(pPage);
        }
        PageExplorer++;
        ExtraireCouriels(pPage);
        AfficherMessageExploration(m_PageAExplorer.get(0));

        SauvegardeFichier(pPage);

        if (pPage.Profondeur > 0){
            Elements links = doc.select("a[href]");
            for (Element link : links) {
                String Url = link.attr("abs:href");
                AjoutPageAExplorer(Url, pPage);
                //System.out.println("Link: " + Url);
            }
        }
        SauvegardePageExplorer(pPage);
    }

    public static void AjoutPageAExplorer(String pURL, Page pPagePrecedent){
        if (pPagePrecedent.Profondeur != 0){
            Page page = new Page(pURL, pPagePrecedent.Profondeur-1);
            if (m_PageDejaFait.isEmpty()){
                m_PageAExplorer.add(page);
            }
            else {
                for (Page euh : m_PageDejaFait){
                    if (!euh.URL.equals(page.URL) | euh.URL == null){
                        m_PageAExplorer.add(page);
                    }
                }
            }
        }
    }
    public static void SauvegardePageExplorer(Page pPage){
        m_PageDejaFait.add(pPage);
        m_PageAExplorer.remove(pPage);
    }

    public static void Validation(String[] tabl){
        ValideNbParam(tabl);
        ValideParam(tabl);
    }
    public static void ValideNbParam(String[] tabl){
        if (tabl.length != 3) {
            System.out.println(
                    "Le nombre de paramètres donnés n'est pas correct.\n" +
                            "Il faut 3 paramètres :\n" +
                            "-La profondeur d'exploration doit être un nombre entier positif.\n" +
                            "-L' URL de départ doit être valide.\n" +
                            "-Le répertoire où écrire les copies locales des fichiers explorés, le dossier doit être accessible et on doit pouvoir y écrire.");
            System.exit(0);
        }
    }
    public static void ValideParam(String[] tabl){
        //Assignation des valeurs
        Page page = new Page(tabl[1], Integer.parseInt(tabl[0]));
        File Dossier = new File(tabl[2]);

        //Check la profondeur//
        if (page.Profondeur < 0){
            System.out.println(
                    "Le paramètre 1 contient un nombre invalide, il faut un entier positif égal ou supérieur à 0."
            );
        }
        //Check l'URL
        {
            try {
                Jsoup.connect(page.URL).get();
            } catch (IllegalArgumentException a) {
                System.out.println("Le paramètre 2, l'URL n'est pas valide.");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        //Check le dossier
        try {
            FileWriter Yo = new FileWriter(Dossier + "\\capoute.txt");
            Yo.write("Salut\nBye");
            Yo.close();
            File Degage = new File(Dossier + "\\capoute.txt");
            Degage.delete();
        } catch (IOException e) {
            System.out.println("Le paramètre 3, l'emplacement n'est pas valide, il est inaccessible ou il est impossible d'écrire dedans.");
        }
    }
}

