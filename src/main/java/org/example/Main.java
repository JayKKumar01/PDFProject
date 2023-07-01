package org.example;

import org.apache.pdfbox.pdmodel.PDDocument;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException {
        String pdf1 = "E:/Sample/1.pdf";
        String pdf2 = "E:/Sample/3.pdf";

        PDFProject pdfProject = new PDFProject(pdf1,pdf2);
//        pdfProject.setForm("Form-001");
//        pdfProject.setPagesNumberForFile1(new int[]{11});
//        pdfProject.setPagesNumberForFile2(new int[]{11});
        pdfProject.setOutputPath("E:/Sample/outputImages/");
        pdfProject.compare();
        System.out.println("Completed");





    }
}