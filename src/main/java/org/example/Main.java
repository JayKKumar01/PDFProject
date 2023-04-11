package org.example;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException {
        File pdf1 = new File("E:/Sample/1.pdf");
        File pdf2 = new File("E:/Sample/2.pdf");
        List<WordInfo> wordList1 = (new PdfWordExtractor(pdf1)).getWordInfoList();
        List<WordInfo> wordList2 = (new PdfWordExtractor(pdf2)).getWordInfoList();

        String diff = StringDiff.findDifference(wordList1,wordList2);
        System.out.println(diff);





    }
}