package org.example;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.pdfbox.text.TextPosition;

import java.io.IOException;
import java.util.List;

public class MyTextStripper extends PDFTextStripper {
    private float firstTextY;
    private boolean foundFirstText = false;
    private final PDDocument document;


    public MyTextStripper(PDDocument document) throws IOException {
        super();
        this.document = document;
    }

    @Override
    protected void writeString(String string, List<TextPosition> textPositions){
        if (!foundFirstText) {
            firstTextY = textPositions.get(0).getY();
            foundFirstText = true;
        }
    }


    public float getFirstTextY(int page) {
        foundFirstText = false;
        this.setStartPage(page);
        this.setEndPage(page);
        try {
            this.getText(document);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return firstTextY;
    }
}
