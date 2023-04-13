package org.example;

import java.awt.Rectangle;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripperByArea;

public class FooterUtil {
    private final MyTextStripper myTextStripper;
    private final PDDocument document;

    private FooterUtil(PDDocument document) throws IOException {
        this.document = document;
        myTextStripper = new MyTextStripper(document);
    }

    public static List<String> getFooterText(PDDocument document){
        try {
            FooterUtil footerUtil = new FooterUtil(document);
            return footerUtil.extractFooterText();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    private List<String> extractFooterText() throws IOException {
        PDFTextStripperByArea stripper = new PDFTextStripperByArea();

        List<String> footerTextList = new ArrayList<>();

        int numPages = document.getNumberOfPages();
        for (int curPage = 0; curPage < numPages; curPage++) {
            float pageWidth = document.getPage(curPage).getMediaBox().getWidth();
            float pageHeight = document.getPage(curPage).getMediaBox().getHeight();

            float footerHeight = getFooterHeight(curPage);
            System.out.println(pageHeight+" "+footerHeight);

            // rectangle representing the footer region
            if (footerHeight > 0) {
                Rectangle rect = new Rectangle(0, (int) (pageHeight - footerHeight), (int) pageWidth, (int) footerHeight);

                stripper.addRegion("footer", rect);
                stripper.extractRegions(document.getPage(curPage));

                String footerText = stripper.getTextForRegion("footer").trim();
                footerTextList.add(footerText);

                stripper.removeRegion("footer");
            }
        }

        return footerTextList;
    }

    private float getFooterHeight(int curPage) {
        return myTextStripper.getFirstTextY(curPage + 1);
    }
}
