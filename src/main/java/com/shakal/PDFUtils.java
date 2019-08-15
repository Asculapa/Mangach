package com.shakal;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.graphics.image.LosslessFactory;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class PDFUtils {

    public static PDDocument imagesToPDF(ArrayList<BufferedImage> images) {
        PDDocument pdDocument = new PDDocument();
        PDPage pdPage;
        PDImageXObject pdImageXObject;
        PDPageContentStream pageContentStream;
        PDRectangle pdRectangle;

        for (BufferedImage image : images) {
            try {
                pdPage = new PDPage();
                pdDocument.addPage(pdPage);
                pdRectangle = new PDRectangle();
                pdRectangle.setUpperRightX(image.getWidth());
                pdRectangle.setUpperRightY(image.getHeight());
                pdPage.setMediaBox(pdRectangle);
                pdImageXObject = LosslessFactory.createFromImage(pdDocument, image);
                pageContentStream = new PDPageContentStream(pdDocument, pdPage, PDPageContentStream.AppendMode.APPEND, false);
                pageContentStream.drawImage(pdImageXObject, 0, 0);
                pageContentStream.close();
            } catch (Exception io) {
                io.printStackTrace();
            }
        }
        return pdDocument;
    }
}
