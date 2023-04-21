package org.example;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.graphics.color.PDColor;
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

        List<String> wordList = new ArrayList<>();
        List<PDColor> colorList = new ArrayList<>();

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
                    infoText.append("[").append(operation).append(": (").append(operation.getInfo()).append(")] ");
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



            if ((!wordList.isEmpty() && !prevInfo.equals(curInfo)) || (!wordList.isEmpty() && prevPosition != curPosition)){
                Info info = new Info(wordList, prevInfo, colorList, prevColor, prevFont);
                infoList.add(info);
                wordList = new ArrayList<>();
                colorList = new ArrayList<>();
            }
            wordList.add(wordInfo.getWord());
            colorList.add(wordInfo.getColor());
            prevInfo = curInfo;
            prevPosition = curPosition;
            prevColor = color;
            prevFont = curFont;
        }
        if (!wordList.isEmpty()){
            Info info = new Info(wordList, prevInfo, colorList, prevColor, prevFont);
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


                contentStream.beginText();

                contentStream.setTextMatrix(Matrix.getTranslateInstance(20,yLimit));
                List<String> inWordList = in.getWordList();
                List<PDColor> inColorList = in.getColorList();

                for (int i=0; i<inWordList.size(); i++){
                    contentStream.setNonStrokingColor(inColorList.get(i));
                    contentStream.setFont(in.getFont(),12);
                    contentStream.showText(inWordList.get(i));
                    contentStream.setNonStrokingColor(Color.BLACK);
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

    private static class Info{
        List<String> wordList;
        String info;
        List<PDColor> colorList;
        Color color;
        PDFont font;

        public Info(List<String> wordList, String info, List<PDColor> colorList, Color color, PDFont font) {
            this.wordList = wordList;
            this.info = info;
            this.colorList = colorList;
            this.color = color;
            this.font = font;
        }

        public List<String> getWordList() {
            return wordList;
        }

        public void setWordList(List<String> wordList) {
            this.wordList = wordList;
        }

        public String getInfo() {
            return info;
        }

        public void setInfo(String info) {
            this.info = info;
        }

        public List<PDColor> getColorList() {
            return colorList;
        }

        public void setColorList(List<PDColor> colorList) {
            this.colorList = colorList;
        }

        public Color getColor() {
            return color;
        }

        public void setColor(Color color) {
            this.color = color;
        }

        public PDFont getFont() {
            return font;
        }

        public void setFont(PDFont font) {
            this.font = font;
        }
    }

}
