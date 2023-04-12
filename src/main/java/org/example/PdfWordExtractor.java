package org.example;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.pdfbox.text.TextPosition;

public class PdfWordExtractor extends PDFTextStripper {

    private final File pdfFile;
    private List<WordInfo> wordInfoList = new ArrayList<>();

    public PdfWordExtractor(File pdfFile) throws IOException {
        super();
        this.pdfFile = pdfFile;
        PDDocument doc = PDDocument.load(pdfFile);
        this.setSortByPosition(true);
        this.getText(doc);
    }

    public static List<WordInfo> getList(File pdf) {
        try {
            PdfWordExtractor pdfWordExtractor = new PdfWordExtractor(pdf);
            return pdfWordExtractor.getWordInfoList();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void writeString(String string, List<TextPosition> textPositions) throws IOException {
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
                wordInfoList.add(wordInfo);
            }
            i += word.length() + 1;
        }
    }


    public List<WordInfo> getWordInfoList() {
        return wordInfoList;
    }

}
