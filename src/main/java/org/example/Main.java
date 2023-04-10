package org.example;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException {
        File pdf1 = new File("E:/Sample/1.pdf");
        File pdf2 = new File("E:/Sample/2.pdf");
        List<WordInfo> wordList1 = (new PdfWordExtractor(pdf1)).getWordInfoList();
        List<WordInfo> wordList2 = (new PdfWordExtractor(pdf2)).getWordInfoList();

        StringBuilder sb1 = new StringBuilder();
        for (WordInfo wordInfo : wordList1) {
            sb1.append(wordInfo.getWord()).append(" ");
        }
        String str1 = sb1.toString().trim();

        StringBuilder sb2 = new StringBuilder();
        for (WordInfo wordInfo : wordList2) {
            sb2.append(wordInfo.getWord()).append(" ");
        }
        String str2 = sb2.toString().trim();

        Compare.getDiff(str1, str2);





    }
}