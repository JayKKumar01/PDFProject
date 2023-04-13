package org.example;

import org.apache.pdfbox.pdmodel.PDDocument;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        String pdf1 = "E:/Sample/1.pdf";
        String pdf2 = "E:/Sample/3.pdf";
//        File pdf1 = new File(Base.SAMPLEPATH+"SamplePdf_1.73Mb_87_Page.pdf");
        //File pdf2 = pdf1;

        PDFProject pdfProject = new PDFProject(pdf1,pdf2);
//        pdfProject.setPagesNumberForPDF1(new int[]{11});
//        pdfProject.setPagesNumberForPDF2(new int[]{11});
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