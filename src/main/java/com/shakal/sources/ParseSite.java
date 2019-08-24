package com.shakal.sources;
import com.shakal.exceptions.ParseError;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

public interface ParseSite {
    enum Language {
        RUS,
        ENG
    }

  /*  int getVolumesCount() throws ParseError;

    int getChaptersCount(int volume) throws Exception;

    int getChaptersCount() throws ParseError;

    int getChapterSize(int volume, int chapter) throws ParseError;

    String getMangaName(Language language) throws ParseError;

    String getChapterName(int volume, int chapter) throws ParseError;

    ArrayList<BufferedImage> getImages(int volume, int chapter) throws Exception;*/
}
