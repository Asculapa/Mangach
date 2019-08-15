package com.shakal;

import com.shakal.exceptions.IncorrectURL;
import com.shakal.exceptions.ParseError;
import com.shakal.sources.Mangalib;
import com.shakal.sources.ParseSite;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {

    public static void main(String[] args) {
        File[] f = {new File("C:\\Users\\nadto\\Desktop\\014.png"),
                new File("C:\\Users\\nadto\\Desktop\\017.png"),
                new File("C:\\Users\\nadto\\Desktop\\018.png")};

        //PDFUtils.pngToPDDocument(f);
        try {
            // URL url = new URL("https://mangalib.me/quoc-vuong-van-tue/");
            //System.out.println(url.getPath().matches("^/"));
            // url.getProtocol() - https
            // url.getHost() - mangalib.me
            // url.getPath() - /happily-ever-after
            // url.getProtocol() + :// url.getHost() + url.getPath()
            // url.toString() - https://mangalib.me/happily-ever-after
            // (\\/{1})
            Mangalib mangalib = new Mangalib(new URL("https://mangalib.me/mahou-shoujo-site"));
            // ArrayList<Integer> integers = new ArrayList<Integer>();
            // Pattern pattern = Pattern.compile("([{].+?[}])");
            //Pattern pattern = Pattern.compile("\"p\":\\d+");
/*            Pattern pattern = Pattern.compile("\"u\":.+");
            Matcher matcher = pattern.matcher("{\"p\":1,\"u\":\"01.png\"}\n");
            while (matcher.find()) {
                System.out.println(matcher.group());
            }
            System.out.println("Volume number - " + mangalib.getVolumesCount());
            System.out.println("Chapter number - " + mangalib.getChaptersCount());
            try {
                  System.out.println("В 1 томе - " + mangalib.getChaptersCount(1) + " глав");
            } catch (Exception e) {
                e.printStackTrace();
            }
             System.out.println("В 1 томе 1 главы - " + mangalib.getChapterSize(1,1) + " страниц");
             System.out.println("Название мнги - " + mangalib.getMangaName(ParseSite.Language.RUS) + "  ==== " + mangalib.getMangaName(ParseSite.Language.ENG));
             System.out.println("Название 31 главы - " + mangalib.getChapterName(1, 31));*/
             PDFUtils.imagesToPDF(mangalib.getImages(1,30)).save("test2.pdf");
             PDFUtils.imagesToPDF(mangalib.getImages(1,31)).save("test3.pdf");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ParseError parseError) {
            parseError.printStackTrace();
        } catch (IncorrectURL incorrectURL) {
            incorrectURL.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
