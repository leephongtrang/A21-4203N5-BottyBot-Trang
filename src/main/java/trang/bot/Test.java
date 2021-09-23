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
        String Path = "C:\\Users\\Lee Phong\\Desktop\\A21-4203N5-BottyBot-Trang";
        Document doc = Jsoup.connect("https://departement-info-cem.github.io/3N5-Prog3/testbot/index.html").get();

        String path = "https://departement-info-cem.github.io/3N5-Prog3/testbot/index.html";
        String base = "https://departement-info-cem.github.io/3N5-Prog3/testbot/";
        String relative = new File(base).toURI().relativize(new File(path).toURI()).getPath();

        FileWriter file = new FileWriter( Path + "\\" + relative);
        file.write(doc.html());
        file.close();
    }
}