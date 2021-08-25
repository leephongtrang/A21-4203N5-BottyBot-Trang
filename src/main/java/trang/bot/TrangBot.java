package trang.bot;

import org.omg.CORBA.WStringSeqHelper;

import java.util.Arrays;

public class TrangBot {
    public static void main(String[] args) {

        //System.out.println("Nom à faire");

        ValideNbParam(args);
        ValideParam(args);

    }
    public static void ValideNbParam(String[] tabl){
        if (tabl.length != 3) {
            System.out.println(
                "Le nombre de paramètres donnés n'est pas correct.\n" +
                "Il faut 3 paramètres :\n" +
                "-La profondeurs d'exploration et doit être une nombre supérieur à 0\n" +
                "-Un URL de départ valide\n" +
                "-Un répertoire pour sauvegarder les pages téléchargées");
        }
    }
    public static void ValideParam(String[] tabl){
        if (Integer.parseInt(tabl[0]) < 0){
            System.out.println(
                "Le paramètre contient un nombre invalide, il faut un entier positif égal ou supérieur à 0."
            );
        }
    }
}
