package org.example;

import java.util.List;
import org.apache.pdfbox.text.TextPosition;

public class WordInfo {

    private String word;
    private List<TextPosition> textPositions;

    public WordInfo(String word, List<TextPosition> textPositions) {
        this.word = word;
        this.textPositions = textPositions;
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
}

