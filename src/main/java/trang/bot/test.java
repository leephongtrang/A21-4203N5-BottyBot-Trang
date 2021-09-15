package trang.bot;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

public class test {
    public static void main(String[] args) throws IOException {
        Document document;
        try {
            //Get Document object after parsing the html from given url.
            document = Jsoup.connect("https://jsoup.org/cookbook/extracting-data//").get();

            //Get links from document object.
            Elements links = document.select("a[href]");

            //Iterate links and print link attributes.
            for (Element link : links) {
                System.out.println("Link: " + link.attr("href"));
                System.out.println("Text: " + link.text());
                System.out.println("");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
