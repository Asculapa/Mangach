package com.shakal;

import com.shakal.exceptions.IncorrectURL;
import com.shakal.exceptions.ParseError;
import com.shakal.sources.Mangalib;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

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
            Mangalib mangalib = new Mangalib(new URL("https://mangalib.me/hwangje-ui-oedongttal"));
           /* System.out.println("Volume number - " + mangalib.getVolumesCount());
            System.out.println("Chapter number - " + mangalib.getChaptersCount());*/
            try {
              //  System.out.print("В 1 томе - " + mangalib.getChaptersCount(1));
            } catch (Exception e) {
                e.printStackTrace();
            }
            System.out.println(mangalib.getChapterSize(1,0));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ParseError parseError) {
            parseError.printStackTrace();
        } catch (IncorrectURL incorrectURL) {
            incorrectURL.printStackTrace();
        }
    }
}
