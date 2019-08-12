package com.shakal.sources;

import com.shakal.exceptions.IncorrectURL;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.net.URL;


public abstract class Site implements ParseSite {
    protected URL url;
    protected Document document;
    // Уаааааа-аааа, нужно менять jsoup -> HtmlUnit, ибо он не поддерживает js(
    protected Site(URL url) throws IncorrectURL {
        this.url = url;
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
}
