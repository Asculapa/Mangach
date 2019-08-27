package com.shakal.sources;
import com.shakal.exceptions.ParseError;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

public interface ParseManga {
    enum Language {
        RUS,
        ENG
    }


    String getMangaName(Language language) throws ParseError;

    ArrayList<BufferedImage> getImages(String conn) throws ParseError, IOException;
}
