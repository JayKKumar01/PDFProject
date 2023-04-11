package org.example;

import org.apache.pdfbox.pdmodel.PDDocument;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        File pdf1 = new File("E:/Sample/1.pdf");
        File pdf2 = new File("E:/Sample/2.pdf");
        List<WordInfo> wordList1 = PdfWordExtractor.getList(pdf1);
        List<WordInfo> wordList2 = PdfWordExtractor.getList(pdf2);
//
        List<WordInfo> diff = StringDiff.findDifference(wordList1,wordList2);

        for (WordInfo wordInfo: diff){
            System.out.println(wordInfo.getWord() + " "+ wordInfo.getOperation());
        }

        try {
            Base.updateDocument(diff,pdf1,pdf2);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }






    }
}