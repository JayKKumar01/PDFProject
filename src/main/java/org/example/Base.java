package org.example;

import java.awt.Color;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.text.TextPosition;

public class Base {

    public static boolean isFontInfoSame(WordInfo wordInfo1, WordInfo wordInfo2) {
        if (wordInfo1.getFont() == null){
            return false;
        }
        return wordInfo1.getFontName().equals(wordInfo2.getFontName()) &&
                wordInfo1.getFontSize() == wordInfo2.getFontSize();
    }
    public static List<WordInfo.Operation> getFontOperation(WordInfo wordInfo1, WordInfo wordInfo2) {
        List<WordInfo.Operation> list = new ArrayList<>();
        if (!wordInfo1.getFontName().equals(wordInfo2.getFontName())){
            list.add(WordInfo.Operation.FONTNAMEDIFF);
        }
        if (wordInfo1.getFontSize() != wordInfo2.getFontSize()){
            list.add(WordInfo.Operation.FONTSIZEDIFF);
        }
        if (!wordInfo1.getFontStyle().equals(wordInfo2.getFontStyle())){
            list.add(WordInfo.Operation.FONTSTYLEDIFF);
        }
        return list;
    }

    public static void updateDocument(List<WordInfo> wordList, PDDocument document1, PDDocument document2, String outputPath, List<Integer> pagesPDF1, List<Integer> pagesPDF2) throws IOException {

        for (WordInfo wordInfo : wordList) {
            List<WordInfo.Operation> opList = wordInfo.getOperationsList();
            if (opList.size() == 1){
                if (opList.get(0) == WordInfo.Operation.ADDED) {
                    addRect(wordInfo, document2, Color.GREEN);
                } else if (opList.get(0) == WordInfo.Operation.FONTNAMEDIFF) {
                    addRect(wordInfo, document2, Color.YELLOW);
                } else if (opList.get(0) == WordInfo.Operation.FONTSIZEDIFF) {
                    addRect(wordInfo, document2, Color.BLUE);
                } else if (opList.get(0) == WordInfo.Operation.FONTSTYLEDIFF) {
                    addRect(wordInfo, document2, Color.CYAN);
                } else if (opList.get(0) == WordInfo.Operation.DELETED){
                    addRect(wordInfo,document1,Color.RED);
                }
            }else{
                addRect(wordInfo, document2, Color.BLACK);
            }
        }
        String tempDir = System.getProperty("java.io.tmpdir");
        String path1 = tempDir+"/old.pdf";
        String path2 = tempDir+"/new.pdf";
        document1.save(path1);
        document2.save(path2);
        document1.close();
        document2.close();
        //System.out.println("Navigate to Document to see Result");
        PDFToImageConverter.createImage(new File(path1), new File(path2),outputPath,pagesPDF1,pagesPDF2);


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
