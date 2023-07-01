package org.example;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
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

    public void setUp(List<WordInfo> wordList, PDDocument document) throws IOException {
//        document.addPage(new PDPage());
//        float f = document.getPage(0).getMediaBox().getHeight();
//        float g = document.getPage(0).getMediaBox().getWidth();
//        System.out.println("height: "+f+ " "+g);
        addText(wordList, document);
    }
    public void addText(List<WordInfo> wordInfoList, PDDocument document) throws IOException {

        String prevInfo = "";
        Color prevColor = Color.BLACK;
        float prevPosition = 0;
        PDFont prevFont = null;
        int prevPage = -1;

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
            int curPage = wordInfo.getPageNumber();


            List<WordInfo.Info> opList = wordInfo.getInfoList();

            StringBuilder infoText = new StringBuilder();
            boolean accept = false;

            for (WordInfo.Info opInfo: wordInfo.getInfoList()){
                WordInfo.Operation operation = opInfo.getOperation();
                if (operation != WordInfo.Operation.EQUAL){
                    infoText.append("[").append(operation).append(": (").append(opInfo.getInfo()).append(")]");
                    accept = true;

                }
                if (opList.size() == 1 && accept){
                    color = opInfo.getColor();
                }
            }

            if (accept){
                curInfo = infoText.toString();
                curPosition = firstPosition.getY();
            }else{
                continue;
            }



            if ((!wordList.isEmpty() && !(prevPage == curPage)) || (!wordList.isEmpty() && !prevInfo.equals(curInfo)) || (!wordList.isEmpty() && prevPosition != curPosition)){
                Info info = new Info(wordList, prevInfo, colorList, prevColor, prevFont);
                info.setPageNum(prevPage);
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
            prevPage = curPage;
        }
        if (!wordList.isEmpty()){
            Info info = new Info(wordList, prevInfo, colorList, prevColor, prevFont);
            info.setPageNum(prevPage);
            infoList.add(info);
        }




        List<List<Info>> masterList = new ArrayList<>();

        // Finding the maximum page number
        int maxPageNum = 0;
        for (Info info : infoList) {
            int pageNum = info.getPageNum();
            maxPageNum = Math.max(maxPageNum, pageNum);
        }

        // Adding sublists based on page numbers
        for (int i = 0; i < maxPageNum; i++) {
            masterList.add(new ArrayList<>());
        }

        // Populating the sublists
        for (Info info : infoList) {
            int pageNum = info.getPageNum();
            masterList.get(pageNum - 1).add(info);
        }


        int margin = 60;
        for (List<Info> infos: masterList){
            int pageHeight = Math.max(margin + infos.size() * (2+20),792);
            document.addPage(new PDPage());
            PDPage page = document.getPage(document.getNumberOfPages()-1);
            PDRectangle mediaBox = page.getMediaBox();
            mediaBox.setUpperRightY(pageHeight);
            page.setMediaBox(mediaBox);

            float wordHeight = 0f;
            for (Info in: infos){
                float yLimit = page.getMediaBox().getHeight() - wordHeight - margin;

                try(PDPageContentStream contentStream = new PDPageContentStream(document,page,PDPageContentStream.AppendMode.APPEND,true,true)){


                    contentStream.beginText();

                    contentStream.setTextMatrix(Matrix.getTranslateInstance(20,yLimit));
                    List<String> inWordList = in.getWordList();
                    List<PDColor> inColorList = in.getColorList();

                    for (int i=0; i<inWordList.size(); i++){
                        contentStream.setNonStrokingColor(inColorList.get(i));

                        try {
                            contentStream.setFont(in.getFont(),12);
                            contentStream.showText(inWordList.get(i));
                        } catch (IOException e) {

                            try {
                                contentStream.setFont(PDType1Font.TIMES_ROMAN,12);
                                contentStream.showText(inWordList.get(i));
                            } catch (IOException ex) {
                                contentStream.setNonStrokingColor(Color.DARK_GRAY);
                                contentStream.setFont(PDType1Font.TIMES_ROMAN,12);
                                contentStream.showText("Font not found!");
                            }

                        }


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

            }





        }

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
        int pageNum;

        public Info(List<String> wordList, String info, List<PDColor> colorList, Color color, PDFont font) {
            this.wordList = wordList;
            this.info = info;
            this.colorList = colorList;
            this.color = color;
            this.font = font;
        }

        public int getPageNum() {
            return pageNum;
        }

        public void setPageNum(int pageNum) {
            this.pageNum = pageNum;
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

    private class WordDetails {
        String word;
        int pageNum;

        public WordDetails(String word, int pageNum) {
            this.word = word;
            this.pageNum = pageNum;
        }

        public String getWord() {
            return word;
        }

        public void setWord(String word) {
            this.word = word;
        }

        public int getPageNum() {
            return pageNum;
        }

        public void setPageNum(int pageNum) {
            this.pageNum = pageNum;
        }
    }
}
