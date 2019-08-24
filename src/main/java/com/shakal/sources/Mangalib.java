package com.shakal.sources;

import com.shakal.exceptions.IncorrectURL;
import com.shakal.exceptions.ParseError;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

public class Mangalib extends Site {


    public Mangalib(URL url) throws IncorrectURL, ParseError {
        super(url);
        Elements elements = document.getElementsByClass("empty nf");
        if (!elements.isEmpty()) {
            throw new ParseError(elements.text());
        }
        setVolsAndUrls();
        for (Chapter chapter : chapters) {
            System.out.println(chapter.getVolAndChap() + " - " + chapter.getName() + " URL - " + chapter.getUrl());
        }
    }

    private void setVolsAndUrls() {
        Elements chapList = document
                .getElementsByClass("chapters-list")
                .first()
                .getElementsByClass("chapter-item");
        for (Element element : chapList) {
            Chapter chapter = new Chapter();
            Element infoElement = element
                    .getElementsByClass("chapter-item__name")
                    .first()
                    .getElementsByClass("link-default")
                    .first();
            chapter.setName(infoElement.attr("title"));
            chapter.setVolAndChap(infoElement.ownText());
            chapter.setUrl(infoElement.attr("href"));
            chapters.add(chapter);
        }
    }

    public ArrayList<BufferedImage> getImages(String conn) throws ParseError, IOException {
        ArrayList<String> pages = getStringsWithPages(conn);
        if (pages == null) {
            throw new ParseError("Can't get images");
        }
        ArrayList<BufferedImage> images = new ArrayList<BufferedImage>();
        Map<Integer, String> urls = getPagesAndUrls(pages);
        String chapterUrl = getChapUrl(conn);

        for (String pageUrl : urls.values()) {
            String url = "https://img3.mangalib.me" + chapterUrl + "/" + pageUrl;
            System.out.println(url);
            URL imgUrl = new URL(url);
            BufferedImage img = ImageIO.read(imgUrl);
            images.add(img);
        }
        return images;
    }

    private Map<Integer, String> getPagesAndUrls(ArrayList<String> pages) {
        Map<Integer, String> urls = new TreeMap<Integer, String>();
        String pageRgex = "\"p\":";
        String urlRgex = "\"u\":.+";

        // Не умею использовать регулярные выражения =(
        for (String str : pages) {
            System.out.println(str);
            int page = Integer.parseInt(Utils.parseString(pageRgex + "\\d+", str).get(0).substring(pageRgex.length()));
            String url = Utils.parseString(urlRgex, str).get(0);
            url = url.substring(urlRgex.length() - 1, url.length() - 2);
            System.out.println("key - " + page + " value - " + url);
            urls.put(page, url);
        }
        return urls;
    }

    public String getMangaName(Language language) {
        Element elements = document.getElementsByClass("page__wrapper page__wrapper_left")
                .first()
                .getElementsByClass("section paper clearfix manga__section-info")
                .first();
        switch (language) {
            case RUS:
                return elements.getElementsByAttributeValue("itemprop", "name").attr("content");
            case ENG:
                return elements.getElementsByAttributeValue("itemprop", "alternativeHeadline").attr("content");
        }
        return "";
    }


    private ArrayList<String> getStringsWithPages(String conn) {
        Document document = getDocument(conn);
        if (document == null) {
            return null;
        }

        Element element = document
                .getElementsByClass("reader-next hidden")
                .first().getElementById("pg");
        return Utils.parseString("([{].+?[}])", element.html());
    }

    private String getChapUrl(String conn) throws ParseError {
        Document document = getDocument(conn);
        Elements elements = document.head().getElementsByTag("script");

        for (Element element : elements) {
            if (element.html().contains("window.__info")) {
                ArrayList<String> list = Utils.parseString("([{].+[}])", element.html());
                if (list.size() == 1) {
                    try {
                        JSONObject jsonObject = new JSONObject(list.get(0));
                        return jsonObject.get("imgUrl").toString();
                    } catch (JSONException ex) {
                        throw new ParseError("Can't convert " + list.get(0) + " to JSON");
                    }
                } else {
                    throw new ParseError("Incorrect script parse!");
                }
            }
        }
        throw new ParseError("Script not found!");
    }

    private Document getDocument(String conn) {
        URL url;
        Document document = null;
        try {
            url = new URL(conn);
            document = connect(url);
        } catch (IncorrectURL incorrectURL) {
            incorrectURL.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return document;
    }
}
