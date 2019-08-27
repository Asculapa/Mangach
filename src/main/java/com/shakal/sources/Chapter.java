package com.shakal.sources;

public class Chapter {
    private String volAndChap;
    private int volume;
    private String name;
    private String url;

    public String getVolAndChap() {
        return volAndChap;
    }

    public void setVolAndChap(String volAndChap) {
        this.volAndChap = volAndChap;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getVolume() {
        return volume;
    }

    public void setVolume(int volume) {
        this.volume = volume;
    }

    @Override
    public String toString() {
        return volAndChap;
    }
}
