package trang.bot; /**
 * This file is implemented: replace the value of the <title> tags of all htm and html files in the specified directory with the file name (without suffix).
 */
import java.io.*;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


public class Test {
    public static void main (String [] args) throws IOException {
        Document doc = Jsoup.connect("https://departement-info-cem.github.io/3N5-Prog3/testbot/index.html").get();

        Elements links = doc.select("a[href]");
        String fileName = links.attr("href");
        //FileWriter file = new FileWriter( "C:\\Users\\2031296\\Desktop\\" + fileName);
        //file.write(doc.html());
        //file.close();
        Path pathAbsolute = Paths.get("https://departement-info-cem.github.io/3N5-Prog3/testbot/index.html");
        Path pathBase = Paths.get("https://departement-info-cem.github.io/3N5-Prog3/testbot/index.html");
        Path pathRelative = pathBase.relativize(pathAbsolute);
        System.out.println(pathRelative);


    }
}