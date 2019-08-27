package com.shakal.sources;

import com.shakal.exceptions.IncorrectURL;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;


public abstract class Site implements ParseManga {
    URL url;
    Document document;
    ArrayList<Chapter> chapters;


    protected Site(URL url) throws IncorrectURL {
        this.url = url;
        chapters = new ArrayList<Chapter>();
        document = connect(url);
    }

    protected Document connect(URL url) throws IncorrectURL {
        try {
            return Jsoup.connect(url.toString()).get();
        } catch (IOException e) {
            e.printStackTrace();
        }

        throw new IncorrectURL(url.toString() + " is incorrect or site is down!");
    }

    public ArrayList<Chapter> getChapters() {
        return chapters;
    }

    /*    public static boolean isSiteUp(String site) {
        try {
            URL url = new URL(site);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.getContent();
            if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                return true;
            }
            return false;
        } catch (SocketTimeoutException tout) {
            return false;
        } catch (IOException ioex) {
            // You may decide on more specific behaviour...
            return false;
        }
    }*/
}
