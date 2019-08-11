package com.shakal;

import java.io.File;

public class Main {

    public static void main(String[] args) {
        File [] f = {new File("C:\\Users\\nadto\\Desktop\\014.png"),
        new File("C:\\Users\\nadto\\Desktop\\017.png"),
        new File("C:\\Users\\nadto\\Desktop\\018.png")};
        PDFUtils.pngToPDDocument(f);
    }
}
