package org.example;

import org.apache.pdfbox.pdmodel.PDDocument;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException {
//        String pdf1 = "E:/Sample/1.pdf";
//        String pdf2 = "E:/Sample/3.pdf";

        String path = "E:/Sample/1.pdf";
        PDDocument document = PDDocument.load(new File(path));



        List<String> list = FooterUtil.getFooterText(document);
        for (int i=0; i<list.size(); i++){
            System.out.println((i+1)+": "+list.get(i));
        }









//        PDFProject pdfProject = new PDFProject(pdf1,pdf2);
//        pdfProject.setForm("Form-001");
////        pdfProject.setPagesNumberForPDF1(new int[]{11});
////        pdfProject.setPagesNumberForPDF2(new int[]{11});
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