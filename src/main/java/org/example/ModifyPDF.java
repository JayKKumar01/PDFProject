package org.example;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.text.TextPosition;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class ModifyPDF {
    public static final String TEMP_DIR = System.getProperty("java.io.tmpdir");
    public static void updateDocument(List<WordInfo> wordList, PDDocument document1, PDDocument document2, String outputPath, List<Integer> pagesPDF1, List<Integer> pagesPDF2) throws IOException {

        PDDocument document3 = new PDDocument();
        document3.addPage(new PDPage());
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

        String path1 = TEMP_DIR +"/old.pdf";
        String path2 = TEMP_DIR +"/new.pdf";
        String path3 = TEMP_DIR +"/edited.pdf";
        decrypt(document1);
        decrypt(document2);
        decrypt(document3);

        document1.save(path1);
        document2.save(path2);
        document3.save(path3);
        document1.close();
        document2.close();
        document3.close();
        //System.out.println("Navigate to Document to see Result");
        File file1 = new File(path1);
        File file2 = new File(path2);
        File file3 = new File(path3);
        PDFProject.addTempFile(file1);
        PDFProject.addTempFile(file2);
        PDFProject.addTempFile(file3);
        PDFToImageConverter.createImage(file1, file2,file3,outputPath,pagesPDF1,pagesPDF2);

    }

    private static void decrypt(PDDocument document) {
        if (document.isEncrypted()){
            document.setAllSecurityToBeRemoved(true);
        }
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
