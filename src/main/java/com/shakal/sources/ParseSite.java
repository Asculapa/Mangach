package com.shakal.sources;

import com.shakal.exceptions.IncorrectURL;
import com.shakal.exceptions.ParseError;

import java.io.File;

public interface ParseSite {
    enum Language {
        RUS,
        ENG
    }

    int getVolumesCount();

    int getChaptersCount(int volume) throws Exception;

    int getChaptersCount();

    int getChapterSize(int volume, int chapter) throws IncorrectURL;

    String getMangaName(Language language);

    String getChapterName(int volume, int chapter, Language language);

    File getImage(int volume, int chapter, int page);
}
