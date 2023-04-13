package org.example;


import com.documents4j.api.DocumentType;
import com.documents4j.api.IConverter;
import com.documents4j.job.LocalConverter;

import java.io.*;

public class WordToPdfConverter {
    public static final String TEMP_DIR = System.getProperty("java.io.tmpdir");

    public static File toPDF(String docPath){
        System.out.println("Converting to PDF");
        WordToPdfConverter wordToPdfConverter = new WordToPdfConverter();
        return wordToPdfConverter.get(docPath);
    }

    public WordToPdfConverter() {
    }

    public File get(String docPath){
        if (docPath == null){
            return null;
        }
        DocumentType type = getType(docPath);
        File inputWord = new File(docPath);
        File outputPdf = new File(TEMP_DIR+"/"+System.currentTimeMillis()+".pdf");
        try {
            InputStream inputStream = new FileInputStream(inputWord);
            OutputStream outputStream = new FileOutputStream(outputPdf);
            IConverter converter = LocalConverter.builder().build();
            converter.convert(inputStream).as(type).to(outputStream)
                    .as(DocumentType.PDF).execute();
            converter.shutDown();
            outputStream.close();
            PDFProject.addTempFile(outputPdf);
            System.out.println("Converted!");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return outputPdf;
    }

    private DocumentType getType(String docPath) {
        if (docPath.endsWith(".docx")){
            return DocumentType.DOCX;
        }
        if (docPath.endsWith(".doc")){
            return DocumentType.DOC;
        }
        return null;
    }

}

