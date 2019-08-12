package com.shakal.sources;

import com.shakal.exceptions.IncorrectURL;
import com.shakal.exceptions.ParseError;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

import static com.shakal.sources.Utils.getIntsFromStr;

public class Mangalib extends Site {

    private ArrayList<int[]> volAndChap;
    private HashMap<Integer, String> chapName;

    public Mangalib(URL url) throws ParseError, IncorrectURL {
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

    public int getChapterSize(int volume, int chapter) throws IncorrectURL {
        String conn =  this.url.getProtocol() + "://" + url.getHost() + url.getPath() + "/v" + volume + "/c" + chapter;
        URL url;
        try {
            url = new URL(conn);
        } catch (MalformedURLException e) {
            e.printStackTrace();
            throw new IncorrectURL(conn + " is not valid!");
        }
        Document document = connect(url);
        System.out.println(document.body().html());
        Elements elements = document.getElementsByClass("reader-footer");
        System.out.println(elements.html());
        return 0;
    }

    public String getMangaName(Language language) {
        return null;
    }

    public String getChapterName(int volume, int chapter, Language language) {
        return null;
    }


    public File getImage(int volume, int chapter, int page) {
        return null;
    }


    private void setVolumesAndChapters() throws ParseError {
        Elements elements = document.body().getElementsByClass("chapter-item");
        volAndChap = new ArrayList<int[]>();
        chapName = new HashMap<Integer, String>();
        ArrayList<Integer> volumesAndChapters;
        String chaptername;
        for (Element element : elements) {
            volumesAndChapters = getIntsFromStr(element.getElementsByClass("link-default").text());
            int[] arr = new int[2];
            arr[0] = volumesAndChapters.get(0);
            arr[1] = volumesAndChapters.get(1);
            chaptername = element.getElementsByClass("link-default").attr("title");
            volAndChap.add(arr);
            chapName.put(arr[1], chaptername);
        }

/*        for (Map.Entry<Integer, String> entry: chapName.entrySet()){
            System.out.println("Chapter " + entry.getKey() + " - " + entry.getValue());
        }*/
    }

}
