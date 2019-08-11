package com.shakal.sources;

import java.io.File;

public interface ParseSite {
    int getVolumesCount();
    int getChaptersCount(int volume);
    int getChaptersCount();
    int getChapterSize(int volume, int chapter);
    String getMangaName();
    String getVolumeName(int volume);
    String getChapterName(int volume, int chapter);
    File getImage(int volume, int chapter, int page);
}
