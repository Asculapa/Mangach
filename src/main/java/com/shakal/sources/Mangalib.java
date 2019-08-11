package com.shakal.sources;

import java.io.File;
import java.net.URL;

public class Mangalib extends Site {
    public Mangalib(URL url) {
        super(url);
    }

    public int getVolumesCount() {
        return 0;
    }

    public int getChapterSize(int volume) {
        return 0;
    }

    public String getMangaName() {
        return null;
    }

    public String getVolumeName(int volume) {
        return null;
    }

    public String getChapterName(int chapter) {
        return null;
    }

    public File getImage(int volume, int page) {
        return null;
    }
}
