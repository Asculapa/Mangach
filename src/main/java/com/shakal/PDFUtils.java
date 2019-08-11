package com.shakal;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.graphics.image.LosslessFactory;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class PDFUtils {

    public static PDDocument pngToPDDocument(File... files) {
        PDDocument pdDocument = new PDDocument();
        PDPage pdPage;
        BufferedImage image;
        PDImageXObject pdImageXObject;
        PDPageContentStream pageContentStream;
        PDRectangle pdRectangle;

        for (File file : files) {
            try {
                pdPage = new PDPage();
                pdDocument.addPage(pdPage);
                image = ImageIO.read(file);
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

/*        try {
            pdDocument.save("test.pdf");
        } catch (IOException e) {
            e.printStackTrace();
        }*/
        return pdDocument;
    }

}
