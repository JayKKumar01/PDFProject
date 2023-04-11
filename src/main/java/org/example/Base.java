package org.example;

import java.awt.Color;
import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.text.TextPosition;

public class Base {

    public static boolean isFontInfoSame(WordInfo wordInfo1, WordInfo wordInfo2) {
        return wordInfo1.getFontName().equals(wordInfo2.getFontName()) &&
                wordInfo1.getFontSize() == wordInfo2.getFontSize();
    }

    public static void updateDocument(List<WordInfo> wordList, File pdf1, File pdf2) throws IOException {
        PDDocument document1 = PDDocument.load(pdf1);
        PDDocument document2 = PDDocument.load(pdf2);
        for (WordInfo wordInfo : wordList) {
            if (wordInfo.getOperation() == WordInfo.Operation.ADDED) {
                addRect(wordInfo, document2, Color.GREEN);
            } else if (wordInfo.getOperation() == WordInfo.Operation.FONTDIFFERENCE) {
                addRect(wordInfo, document2, Color.YELLOW);
            } else if (wordInfo.getOperation() == WordInfo.Operation.DELETED){
                addRect(wordInfo,document1,Color.RED);
            }
        }
        String tempDir = System.getProperty("java.io.tmpdir");
        String path1 = tempDir+"/old.pdf";
        String path2 = tempDir+"/new.pdf";
        document1.save(path1);
        document2.save(path2);
        document1.close();
        document2.close();
        System.out.println("Navigate to Document to see Result");
        PDFToImageConverter.createImage(new File(path1), new File(path2));


//        PDFToImageConverter converter = new PDFToImageConverter(new File(path));
//        String imgPath = System.getProperty("user.dir");
//        converter.converToImage(imgPath);
//        System.out.println("Updated image saved at: " + imgPath);

    }

    private static void addRect(WordInfo wordInfo, PDDocument document, Color color) throws IOException {
        List<TextPosition> textPositions = wordInfo.getTextPositions();
        TextPosition firstTextPosition = textPositions.get(0);
        TextPosition lastTextPosition = textPositions.get(textPositions.size() - 1);
        int pageIndex = wordInfo.getPageNumber() - 1;
        PDPage page = document.getPage(pageIndex);
        try (PDPageContentStream contentStream = new PDPageContentStream(document, page, PDPageContentStream.AppendMode.APPEND, true, true)) {
            contentStream.setLineWidth(2);
            contentStream.setStrokingColor(color);
            float padding = 2f;
            float x = firstTextPosition.getX() - padding;
            float y = page.getMediaBox().getHeight() - lastTextPosition.getY()  - padding;
            float width = lastTextPosition.getX() + lastTextPosition.getWidth() - firstTextPosition.getX() + padding * 2;
            float height = lastTextPosition.getHeight() + padding * 2;
            contentStream.addRect(x, y, width, height);
            contentStream.stroke();
        }
    }


}
