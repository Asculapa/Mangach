package com.shakal.sources;

import java.io.File;

public interface ParseSite {
    int getVolumesCount();
    int getChapterSize(int volume);
    String getMangaName();
    String getVolumeName(int volume);
    String getChapterName(int chapter);
    File getImage(int volume, int page);
}
