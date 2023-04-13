package org.example;

import org.apache.pdfbox.io.RandomAccessBuffer;
import org.apache.pdfbox.io.RandomAccessRead;
import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.pdmodel.PDDocument;

import java.io.*;
import java.nio.file.Files;

public class Parser {
    public static PDDocument parse(File pdf, int[] pages) {
        PDDocument targetDoc = new PDDocument();
        try {
            byte[] pdfBytes = Files.readAllBytes(pdf.toPath());
            RandomAccessRead input = new RandomAccessBuffer(pdfBytes);
            PDFParser parser = new PDFParser(input);
            parser.parse();
            PDDocument sourceDoc = parser.getPDDocument();

            for (int pageNumber : pages) {
                if (pageNumber < 1 || pageNumber > sourceDoc.getNumberOfPages()) {
                    throw new IllegalArgumentException("Invalid page number: " + pageNumber);
                }
                targetDoc.addPage(sourceDoc.getPage(pageNumber - 1));
            }
            // do something with the new document containing only the selected pages
            sourceDoc.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return targetDoc;
    }

    public static void main(String[] args) throws IOException {
        File pdf = new File(Base.SAMPLEPATH+"SamplePdf_1.73Mb_87_Page.pdf");
        int[] pageNumbers = {1, 3, 5}; // specify the page numbers to load

        long start1 = System.nanoTime();
        PDDocument doc1 = parse(pdf,pageNumbers);
        long end1 = System.nanoTime();

        long start2 = System.nanoTime();
        PDDocument doc2 = PDDocument.load(pdf);
        long end2 = System.nanoTime();

        System.out.println("Time taken to load doc1: " + (end1 - start1) + " ns");
        System.out.println("Time taken to load doc2: " + (end2 - start2) + " ns");
    }
}
