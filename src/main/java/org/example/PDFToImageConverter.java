package org.example;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.PDFRenderer;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PDFToImageConverter {
    public static List<BufferedImage> createImagesFromPdf(File pdfFile) throws IOException {
        PDDocument document = null;
        try {
            document = PDDocument.load(pdfFile);
            PDFRenderer renderer = new PDFRenderer(document);
            int numPages = document.getNumberOfPages();
            List<BufferedImage> pageImages = new ArrayList<>();

            for (int i = 0; i < numPages; i++) {
                BufferedImage image = renderer.renderImageWithDPI(i, 300); // set the DPI to 300
                pageImages.add(image);
            }

            return pageImages;
        } finally {
            if (document != null) {
                document.close();
            }
        }
    }

    public static void createImage(File pdf1, File pdf2,String outputPath) throws IOException {
        // Load the first PDF and render all its pages to images
        List<BufferedImage> pdf1Images = createImagesFromPdf(pdf1);

        // Load the second PDF and render all its pages to images
        List<BufferedImage> pdf2Images = createImagesFromPdf(pdf2);

        // Determine the number of pages in the combined PDF (the larger of the two)
        int numPages = Math.max(pdf1Images.size(), pdf2Images.size());

        // Combine the images of each page side by side and write to a new image file
        for (int i = 0; i < numPages; i++) {
            BufferedImage pdf1Image = i < pdf1Images.size() ? pdf1Images.get(i) : null;
            BufferedImage pdf2Image = i < pdf2Images.size() ? pdf2Images.get(i) : null;
            int width = (pdf1Image != null ? pdf1Image.getWidth() : 0) + (pdf2Image != null ? pdf2Image.getWidth() : 0);
            int height = Math.max(pdf1Image != null ? pdf1Image.getHeight() : 0, pdf2Image != null ? pdf2Image.getHeight() : 0);
            BufferedImage combinedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

            if (pdf1Image != null) {
                combinedImage.createGraphics().drawImage(pdf1Image, 0, 0, null);
            }
            if (pdf2Image != null) {
                combinedImage.createGraphics().drawImage(pdf2Image, pdf1Image != null ? pdf1Image.getWidth() : 0, 0, null);
            }

            File combinedImageFile = new File(outputPath,"combined_page_" + (i+1) + ".png");
            ImageIO.write(combinedImage, "png", combinedImageFile);
            System.out.println("Created at: "+combinedImageFile.getAbsolutePath());
        }

        System.out.println("Combined images created at :"+outputPath);
    }

}

