package org.example.pdfprojectlib;

import org.apache.pdfbox.pdmodel.PDDocument;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PDFProject {
    PDDocument pdf1;
    PDDocument pdf2;
    String outputPath = System.getProperty("user.dir")+"/output";
    String diffString = "";
    List<Integer> pagesPDF1 = new ArrayList<>();
    List<Integer> pagesPDF2 = new ArrayList<>();

    public PDFProject(File pdf1, File pdf2) {
        this.pdf1 = getDoc(pdf1);
        this.pdf2 = getDoc(pdf2);


        File outputFolder = new File(outputPath);
        if (!outputFolder.exists()){
            outputFolder.mkdirs();
        }
    }

    private PDDocument getDoc(File pdf){
        try {
            return PDDocument.load(pdf);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void compare(){
        if (pdf1 == null || pdf2 == null){
            System.out.println("Please provide valid files");
            return;
        }
        File output = new File(outputPath);
        if (!output.exists()){
            if (!output.mkdirs()){
                System.out.println("Couldn't create a folder");
                return ;
            }
        } else if (!output.isDirectory()){
            System.out.println("Please provide a valid path");
            return ;
        }
        if (!areValidPages(pagesPDF1,pagesPDF2)){
            System.out.println("Please provide valid pages");
            return;
        }

        //modify(pagesPDF1,pagesPDF2);

        List<WordInfo> wordList1 = PdfWordExtractor.getList(pdf1,pagesPDF1);
        List<WordInfo> wordList2 = PdfWordExtractor.getList(pdf2,pagesPDF1);
//
        List<WordInfo> diff = StringDiff.findDifference(wordList1,wordList2);

        for (WordInfo wordInfo: diff){
            String opList = wordInfo.getOperationsList().toString();
            diffString += "\""+wordInfo.getWord()+"\"" + ": "+ opList+"\n";
        }

        try {
            Base.updateDocument(diff,pdf1,pdf2,outputPath,pagesPDF1,pagesPDF2);
        } catch (IOException e) {
            System.out.println("Couldn't create images");
            throw new RuntimeException(e);
        }
    }



    private boolean areValidPages(List<Integer> pagesPDF1, List<Integer> pagesPDF2) {
        for(int i: pagesPDF1){
            if (i<1 || i>pdf1.getNumberOfPages()){
                return false;
            }
        }
        for(int i: pagesPDF2){
            if (i<1 || i>pdf2.getNumberOfPages()){
                return false;
            }
        }
        return true;
    }

    public String getOutputPath() {
        return outputPath;
    }

    public void setOutputPath(String outputPath) {
        this.outputPath = outputPath;
    }

    public String getDiffString() {
        return diffString;
    }

    private List<Integer> getPagesPDF1() {
        return pagesPDF1;
    }

    private void setPagesPDF1(List<Integer> pagesPDF1) {
        this.pagesPDF1 = pagesPDF1;
    }

    private List<Integer> getPagesPDF2() {
        return pagesPDF2;
    }

    private void setPagesPDF2(List<Integer> pagesPDF2) {
        this.pagesPDF2 = pagesPDF2;
    }




    public void setPageRangeForPDF1(int startPage, int endPage) {
        if (!pagesPDF1.isEmpty()){
            pagesPDF1.clear();
        }
        for (int i=startPage; i<=endPage; i++){
            pagesPDF1.add(i);
        }
    }
    public void setPageRangeForPDF2(int startPage, int endPage) {
        if (!pagesPDF2.isEmpty()){
            pagesPDF2.clear();
        }
        for (int i=startPage; i<=endPage; i++){
            pagesPDF2.add(i);
        }
    }

    public void setPagesNumberForPDF1(int[] pages) {
        if (!pagesPDF1.isEmpty()){
            pagesPDF1.clear();
        }
        for (int i: pages){
            pagesPDF1.add(i);
        }
    }

    public void setPagesNumberForPDF2(int[] pages) {
        if (!pagesPDF2.isEmpty()){
            pagesPDF2.clear();
        }
        for (int i: pages){
            pagesPDF2.add(i);
        }
    }
}
