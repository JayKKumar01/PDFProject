package org.example;

import java.util.ArrayList;
import java.util.List;
import org.apache.pdfbox.text.TextPosition;
import org.apache.poi.sl.draw.geom.GuideIf;

public class WordInfo {

    private String word;
    private List<TextPosition> textPositions;
    private List<Operation> operationsList = new ArrayList<>();
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
        String font = getFont();
        if (font != null && font.contains("+")){
            font = font.substring(font.indexOf("+")+1);
        }
        if (font != null && font.contains("-")){
            String f = font.substring(font.lastIndexOf("-"));
            return font.replace(f,"");
        }
        return font;
    }
    public String getFont() {
        if (textPositions != null && textPositions.size() > 0) {
            TextPosition firstTextPosition = textPositions.get(0);
            return firstTextPosition.getFont().getName();
        }
        return null;
    }

    public String getFontStyle() {
        String font = getFont();
        if (font != null){
            if (font.contains("-")){
                return (font.substring(font.lastIndexOf("-")+1)).toLowerCase();
            }

        }
        return "normal";
    }

    public int getFontSize() {
        if (textPositions != null && textPositions.size() > 0) {
            TextPosition firstTextPosition = textPositions.get(0);
            float fontSize = firstTextPosition.getFontSize();
            return Math.round(fontSize);
        }
        return 0;
    }




    public List<Operation> getOperationsList() {
        return operationsList;
    }

    public void setOperation(List<Operation> operationsList) {
        this.operationsList = operationsList;
    }
    public void setOperation(Operation operation) {
        operationsList.add(operation);
    }

    public enum Operation {
        EQUAL, ADDED, DELETED, FONTSIZEDIFF,FONTNAMEDIFF,FONTSTYLEDIFF;
    }
    public enum FontStyle{
        NORMAL,BOLD,ITELIC,BOLD_ITELIC;
    }
}
