package com.shakal.sources;

import com.shakal.exceptions.IncorrectURL;
import com.shakal.exceptions.ParseError;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import static com.shakal.sources.Utils.getIntsFromStr;

public class Mangalib extends Site {

    private ArrayList<int[]> volAndChap;
    private HashMap<Integer, String> chapName;

    public Mangalib(URL url) throws IncorrectURL {
        // Не пускать далее, если не верный url или нет доступа к сайту или нет глав
        super(url);
        setVolumesAndChapters();
    }


    public int getVolumesCount() {
        return volAndChap.get(0)[0];
    }

    public int getChaptersCount(int volume) throws Exception {
        if (volume > getVolumesCount()) {
            throw new Exception("Incorrect volume");
        }

        int chapteCount = 0;
        for (int[] arr : volAndChap) {
            if (arr[0] == volume) {
                chapteCount++;
            }
        }
        return chapteCount;
    }

    public int getChaptersCount() {
        return volAndChap.get(0)[1];
    }

    public int getChapterSize(int volume, int chapter) throws ParseError {
        ArrayList<String> pages = getStringsWithPages(volume, chapter);
        if (pages != null) {
            return pages.size();
        }
        throw new ParseError("Unknown chapter size");
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

    public String getChapterName(int volume, int chapter) {
        return chapName.get(chapter);
    }


    public ArrayList<BufferedImage> getImages(int volume, int chapter) throws ParseError, IOException {
        ArrayList<String> pages = getStringsWithPages(volume, chapter);
        ArrayList<BufferedImage> images = new ArrayList<BufferedImage>();
        Map<Integer, String> urls = new TreeMap<Integer, String>();
        String pageRgex = "\"p\":";
        String urlRgex = "\"u\":.+";
        if (pages == null) {
            throw new ParseError("Can't get images");
        }
        // Не умею использовать регулярные выражения =(
        for (String str : pages) {
            System.out.println(str);
            int page = Integer.parseInt(Utils.parseString(pageRgex + "\\d+", str).get(0).substring(pageRgex.length()));
            String url = Utils.parseString(urlRgex, str).get(0);
            url = url.substring(urlRgex.length() - 1, url.length() - 2);
            System.out.println("key - " + page + " value - " + url);
            urls.put(page, url);
        }
        for (Integer page: urls.keySet()){
            String url = "https://img3.mangalib.me/manga" + this.url.getPath() + "/chapters/" + volume + "-" + chapter + "/" + urls.get(page);
            System.out.println(url);
            URL imgUrl = new URL(url);
            BufferedImage img = ImageIO.read(imgUrl);
            images.add(img);
        }
        return images;
    }


    private void setVolumesAndChapters() {
        Elements elements = document.body().getElementsByClass("chapter-item");
        volAndChap = new ArrayList<int[]>();
        chapName = new HashMap<Integer, String>();
        ArrayList<Integer> volumesAndChapters;
        String chapterName;
        for (Element element : elements) {
            volumesAndChapters = getIntsFromStr(element.getElementsByClass("link-default").text());
            int[] arr = new int[2];
            arr[0] = volumesAndChapters.get(0);
            arr[1] = volumesAndChapters.get(1);
            chapterName = element.getElementsByClass("link-default").attr("title");
            volAndChap.add(arr);
            chapName.put(arr[1], chapterName);
        }
    }

    private ArrayList<String> getStringsWithPages(int volume, int chapter) {
        String conn = this.url.getProtocol() + "://" + url.getHost() + url.getPath() + "/v" + volume + "/c" + chapter;
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
        if (document == null) {
            return null;
        }
        Element element = document
                .getElementsByClass("reader-next hidden")
                .first().getElementById("pg");
        return Utils.parseString("([{].+?[}])", element.html());
    }

}
