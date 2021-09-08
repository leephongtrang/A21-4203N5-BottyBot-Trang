package trang.bot;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TrangBot {

    public static void main(String[] args) {
        //Paramètre pour debug
        {
            args = new String[3];
            args[0] = "0";
            args[1] = "https://jsoup.org/cookbook/input/load-document-from-url";
            args[2] = "C:\\Users\\2031296\\Desktop\\Nouveau dossier";
        }


        Page PageDeBase = new Page(args[1], Integer.parseInt(args[0]));
        Validation(args);

        ArrayList<Page> m_PageAExplorer = new ArrayList<Page>();
        ArrayList<Page> m_PageDejaFait = new ArrayList<Page>();
        m_PageAExplorer.add(PageDeBase);

        if (!m_PageAExplorer.isEmpty()){

        }
    }



    public static void EcrireFichier(Page pPage){
        throw new UnsupportedOperationException();
    }

    public static void ExtraireCouriels(Page pPage){
        throw new UnsupportedOperationException();
    }

    public static void ExtraireLiens(Page pPage){
        throw new UnsupportedOperationException();
    }

    public static void SauvegardePageExplorer(Page pPage){

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
                            "-La profondeurs d'exploration et doit être une nombre supérieur à 0.\n" +
                            "-Un URL de départ valide.\n" +
                            "-Un répertoire pour sauvegarder les pages téléchargées.");
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
            System.out.println("Le paramètre 3, l'emplacement n'est pas valide, il n'est pas accessible ou il est impossible d'écrire dedans.");
        }
    }
}

