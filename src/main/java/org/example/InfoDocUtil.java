package org.example;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.text.TextPosition;
import org.apache.pdfbox.util.Matrix;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class InfoDocUtil {

    public void addText(List<WordInfo> wordInfoList, PDDocument document) throws IOException {

        String prevInfo = "";
        Color prevColor = Color.BLACK;
        float prevPosition = 0;
        PDFont prevFont = null;

        StringBuilder word = new StringBuilder();
        wordInfoList.sort(new SortingUtil());

        List<Info> infoList = new ArrayList<>();

        for (WordInfo wordInfo: wordInfoList){
            List<TextPosition> positions = wordInfo.getTextPositions();
            TextPosition firstPosition = positions.get(0);


            Color color = Color.BLACK;
            String curInfo = null;
            float curPosition = 0;
            PDFont curFont = firstPosition.getFont();

            List<WordInfo.Operation> opList = wordInfo.getOperationsList();

            boolean accept = false;

            StringBuilder infoText = new StringBuilder();

            for (WordInfo.Operation operation: opList){
                if (operation == WordInfo.Operation.SIZE || operation == WordInfo.Operation.STYLE || operation == WordInfo.Operation.FONT){
                    infoText.append("[").append(operation).append(": (").append(operation.getInfo()).append("] ");
                    accept = true;
                }
                else if (operation == WordInfo.Operation.DELETED){
                    infoText.append("[").append(operation).append("] (").append(operation.getInfo()).append(")");
                    accept = true;
                } else if (operation == WordInfo.Operation.ADDED) {
                    infoText.append("[").append(operation).append("] (").append(operation.getInfo()).append(")");
                    accept = true;
                }
                if (opList.size() == 1 && accept){
                    color = setColor(operation);
                }
            }

            if (accept){
                curInfo = infoText.toString();
                curPosition = firstPosition.getY();
            }else{
                continue;
            }



            if ((!word.toString().isEmpty() && !prevInfo.equals(curInfo)) || (!word.toString().isEmpty() && prevPosition != curPosition)){
                Info info = new Info(word.toString(),prevInfo,prevColor,prevFont);
                infoList.add(info);
                word = new StringBuilder();
            }
            word.append(wordInfo.getWord()).append(" ");
            prevInfo = curInfo;
            prevPosition = curPosition;
            prevColor = color;
            prevFont = curFont;
        }
        if (!word.toString().isEmpty()){
            Info info = new Info(word.toString(),prevInfo,prevColor,prevFont);
            infoList.add(info);
        }




        int margin = 60;
        float wordHeight = 0f;

        for (Info in: infoList){



            int pageIndex = document.getNumberOfPages() -1;
            PDPage page = document.getPage(pageIndex);

            float yLimit = page.getMediaBox().getHeight() - wordHeight - margin;
            if (yLimit <= margin){
                document.addPage(new PDPage());
                page = document.getPage(++pageIndex);
                wordHeight = 0;
                yLimit = page.getMediaBox().getHeight() - wordHeight - margin;
            }

            try(PDPageContentStream contentStream = new PDPageContentStream(document,page,PDPageContentStream.AppendMode.APPEND,true,true)){


                contentStream.setNonStrokingColor(Color.BLACK);
                contentStream.beginText();

                contentStream.setTextMatrix(Matrix.getTranslateInstance(20,yLimit));
                String[] wList = in.getWord().trim().split(" ");
                for (String w: wList){
                    contentStream.setFont(in.getFont(),12);
                    contentStream.showText(w);
                    contentStream.setFont(PDType1Font.TIMES_ROMAN,12);
                    contentStream.showText(" ");
                }




                contentStream.setNonStrokingColor(in.getColor());
                contentStream.setFont(PDType1Font.TIMES_ROMAN,12);

                contentStream.setTextMatrix(Matrix.getTranslateInstance(20,yLimit-20));
                contentStream.showText(in.getInfo());
                contentStream.endText();
                wordHeight += 2*20;
            }














//            System.out.println(in.getWord());
//            System.out.println(in.getInfo());
//            System.out.println(in.getColor());
//            System.out.println("---------------------------------------");
        }

    }





    private Color setColor(WordInfo.Operation operation) {
        if (operation == WordInfo.Operation.ADDED){
            return Color.GREEN;
        }else if (operation == WordInfo.Operation.DELETED){
            return Color.RED;
        }else if (operation == WordInfo.Operation.STYLE){
            return Color.CYAN;
        }else if (operation == WordInfo.Operation.FONT){
            return Color.YELLOW;
        }else if (operation == WordInfo.Operation.SIZE){
            return Color.BLUE;
        }
        return Color.BLACK;
    }


    private String getOperationString(WordInfo.Operation operation) {
        return " ";
    }

    private class Info{
        String word;
        String info;
        Color color;

        PDFont font;

        public Info(String word, String info, Color color, PDFont font) {
            this.word = word;
            this.info = info;
            this.color = color;
            this.font = font;
        }

        public PDFont getFont() {
            return font;
        }

        public void setFont(PDFont font) {
            this.font = font;
        }

        public String getWord() {
            return word;
        }

        public void setWord(String word) {
            this.word = word;
        }

        public String getInfo() {
            return info;
        }

        public void setInfo(String info) {
            this.info = info;
        }

        public Color getColor() {
            return color;
        }

        public void setColor(Color color) {
            this.color = color;
        }
    }

}
