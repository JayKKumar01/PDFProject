package org.example;

import java.util.List;
import org.apache.pdfbox.text.TextPosition;

public class WordInfo {

    private String word;
    private List<TextPosition> textPositions;
    private Operation operation;
    private int pageNumber = 1;

    public WordInfo(String word, List<TextPosition> textPositions) {
        this.word = word;
        this.textPositions = textPositions;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    public String getWord() {
        return word;
    }

    public List<TextPosition> getTextPositions() {
        return textPositions;
    }

    public String getFontName() {
        if (textPositions != null && textPositions.size() > 0) {
            TextPosition firstTextPosition = textPositions.get(0);
            return firstTextPosition.getFont().getName();
        }
        return null;
    }

    public float getFontSize() {
        if (textPositions != null && textPositions.size() > 0) {
            TextPosition firstTextPosition = textPositions.get(0);
            return firstTextPosition.getFontSizeInPt();
        }
        return 0;
    }

    public Operation getOperation() {
        return operation;
    }

    public void setOperation(Operation operation) {
        this.operation = operation;
    }

    public enum Operation {
        EQUAL, ADDED, DELETED, FONTDIFFERENCE
    }
}
