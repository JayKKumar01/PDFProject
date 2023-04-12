package org.example;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

public class PDFProject {
    File pdf1;
    File pdf2;
    String outputPath = System.getProperty("user.dir")+"/output";
    String diffString = "";

    public PDFProject(File pdf1, File pdf2) {
        this.pdf1 = pdf1;
        this.pdf2 = pdf2;
        File outputFolder = new File(outputPath);
        if (!outputFolder.exists()){
            outputFolder.mkdirs();
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
        List<WordInfo> wordList1 = PdfWordExtractor.getList(pdf1);
        List<WordInfo> wordList2 = PdfWordExtractor.getList(pdf2);
//
        List<WordInfo> diff = StringDiff.findDifference(wordList1,wordList2);

        for (WordInfo wordInfo: diff){
            diffString += "\""+wordInfo.getWord()+"\"" + ": "+ wordInfo.getOperation()+"\n";
        }

        try {
            Base.updateDocument(diff,pdf1,pdf2,outputPath);
        } catch (IOException e) {
            System.out.println("Couldn't create images");
            throw new RuntimeException(e);
        }
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

    public void setDiffString(String diffString) {
        this.diffString = diffString;
    }
}
