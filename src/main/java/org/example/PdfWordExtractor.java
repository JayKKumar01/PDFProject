package org.example;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.pdfbox.contentstream.operator.color.*;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.graphics.color.PDColor;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.pdfbox.text.TextPosition;

public class PdfWordExtractor extends PDFTextStripper {

    private final List<WordInfo> wordInfoList = new ArrayList<>();
    private List<PDColor> colorList = new ArrayList<>();

    int colorIndex = 0;

    public PdfWordExtractor(PDDocument doc, List<Integer> pagesToExtract) throws IOException {
        addOperator(new SetStrokingColorSpace());
        addOperator(new SetNonStrokingColorSpace());
        addOperator(new SetStrokingDeviceCMYKColor());
        addOperator(new SetNonStrokingDeviceCMYKColor());
        addOperator(new SetNonStrokingDeviceRGBColor());
        addOperator(new SetStrokingDeviceRGBColor());
        addOperator(new SetNonStrokingDeviceGrayColor());
        addOperator(new SetStrokingDeviceGrayColor());
        addOperator(new SetStrokingColor());
        addOperator(new SetStrokingColorN());
        addOperator(new SetNonStrokingColor());
        addOperator(new SetNonStrokingColorN());
        //super();
        //this.setSortByPosition(true);
        if (pagesToExtract.isEmpty()){
            this.getText(doc);
        }else {
            for (int pageNum : pagesToExtract) {
                this.setStartPage(pageNum);
                this.setEndPage(pageNum);
                this.getText(doc);
            }
        }

    }

    public static List<WordInfo> getList(PDDocument pdf, List<Integer> pagesToExtract) {
        try {
            PdfWordExtractor pdfWordExtractor = new PdfWordExtractor(pdf,pagesToExtract);
            return pdfWordExtractor.getWordInfoList();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void processTextPosition(TextPosition text) {
        super.processTextPosition(text);
        PDColor color = getGraphicsState().getNonStrokingColor();
        colorList.add(color);
    }

    @Override
    protected void writeString(String string, List<TextPosition> textPositions) {


        String[] words = string.split(getWordSeparator());
        int i = 0;

        for (String word : words) {
            if (!word.isEmpty()) {
                List<TextPosition> positions = new ArrayList<>();
                for (int j = i; j < i + word.length(); j++) {
                    positions.add(textPositions.get(j));
                }
                WordInfo wordInfo = new WordInfo(word, positions);
                wordInfo.setPageNumber(this.getCurrentPageNo());
                wordInfo.setColor(colorList.get(i+colorIndex));
                wordInfoList.add(wordInfo);
            }
            i += word.length() + 1;
        }
        colorIndex += textPositions.size();
    }



    public List<WordInfo> getWordInfoList() {
        return wordInfoList;
    }


}
