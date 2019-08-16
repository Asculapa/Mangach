package com.shakal.sources;

import com.shakal.exceptions.IncorrectURL;
import com.shakal.exceptions.ParseError;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.SocketTimeoutException;
import java.net.URL;


public abstract class Site implements ParseSite {
    URL url;
    Document document;

    protected Site(URL url) throws IncorrectURL, ParseError {
        this.url = url;
        if (!isSiteUp(url.getProtocol() + "://" + url.getHost())) {
            throw new ParseError("Site is down.");
        }
        document = connect(url);
    }

    protected Document connect(URL url) throws IncorrectURL {
        try {
            return Jsoup.connect(url.toString()).get();
        } catch (IOException e) {
            e.printStackTrace();
        }

        throw new IncorrectURL(url.toString() + " is incorrect!");
    }

    public static boolean isSiteUp(String site) {
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
    }
}
