package org.example;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.example.library.MyLibrary;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        System.out.println(MyLibrary.getMessage());
//        File pdf1 = new File("E:/Sample/1.pdf");
//        File pdf2 = new File("E:/Sample/3.pdf");
//
//        PDFProject pdfProject = new PDFProject(pdf1,pdf2);
//        pdfProject.setOutputPath("E:/Sample/outputImges/");
//        pdfProject.compare();
//        System.out.println(pdfProject.getDiffString());


//        List<WordInfo> wordList1 = PdfWordExtractor.getList(pdf1);
//        List<WordInfo> wordList2 = PdfWordExtractor.getList(pdf2);
////
//        List<WordInfo> diff = StringDiff.findDifference(wordList1,wordList2);
//
//        for (WordInfo wordInfo: diff){
//            System.out.println(wordInfo.getWord() + " "+ wordInfo.getOperation());
//        }
//
//        try {
//            Base.updateDocument(diff,pdf1,pdf2);
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }






    }
}