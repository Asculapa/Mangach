package com.shakal.sources;

import java.net.URL;

public abstract class Site implements ParseSite {
    protected URL url;

    public Site(URL url) {
        this.url = url;
    }
}
