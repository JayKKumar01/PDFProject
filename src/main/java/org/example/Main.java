package org.example;

import org.apache.pdfbox.pdmodel.PDDocument;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        File pdf1 = new File("E:/Sample/1.pdf");
        File pdf2 = new File("E:/Sample/3.pdf");

        PDFProject pdfProject = new PDFProject(pdf1,pdf2);
        pdfProject.setPageRangeForPDF1(1,1);
        pdfProject.setPageRangeForPDF2(1,1);
        int[] pages = {1};
        pdfProject.setPagesNumberForPDF1(pages);
        pdfProject.setPagesNumberForPDF2(pages);
//        pdfProject.setPagesPDF1("d","d");
//        pdfProject.setSecondPdfPageRange(0,2);
        pdfProject.setOutputPath("E:/Sample/outputImges/");
        pdfProject.compare();
        System.out.println(pdfProject.getDiffString());


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