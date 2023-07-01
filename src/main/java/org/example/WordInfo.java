package org.example;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.graphics.color.PDColor;
import org.apache.pdfbox.text.TextPosition;

public class WordInfo {

    private final String word;
    private final List<TextPosition> textPositions;
    private int pageNumber = 1;
    private PDColor color;
    private List<Info> infoList;

    public WordInfo(String word, List<TextPosition> textPositions) {
        this.word = word;
        this.textPositions = textPositions;
    }

    public List<Info> getInfoList() {
        return infoList;
    }

    public void setInfoList(List<Info> infoList) {
        this.infoList = infoList;
    }

    public PDColor getColor() {
        return color;
    }

    public void setColor(PDColor color) {
        this.color = color;
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

    public PDFont getPDFont() {
        if (textPositions != null && textPositions.size() > 0) {
            TextPosition firstTextPosition = textPositions.get(0);
            return firstTextPosition.getFont();
        }
        return null;
    }
    public String getFont() {
        if (textPositions != null && textPositions.size() > 0) {
            TextPosition firstTextPosition = textPositions.get(0);
            return firstTextPosition.getFont().getName();
        }
        return null;
    }

    public String getFontStyle() {
        String font = getFont().toLowerCase().replace("mt","");
        if (font != null){
            if (font.contains("-")){
                return (font.substring(font.lastIndexOf("-")+1)).toLowerCase();
            }else if (font.contains(",")){
                return (font.substring(font.lastIndexOf(",")+1));
            }

        }
        return "regular";
    }

    public int getFontSize() {
        if (textPositions != null && textPositions.size() > 0) {
            TextPosition firstTextPosition = textPositions.get(0);
            float fontSize = firstTextPosition.getFontSize();
            return Math.round(fontSize);
        }
        return 0;
    }

    public int getPosition(){
        TextPosition textPosition = textPositions.get(0);
        return (int) textPosition.getY();
    }




    public enum Operation {
        EQUAL, ADDED, DELETED, SIZE, FONT, STYLE;
    }

    public void setInfoList(Operation operation, String info) {
        List<Info> list = new ArrayList<>();
        list.add(new Info(operation,info));
        this.infoList = list;
    }
    public static class Info{
        Operation operation;
        String info;

        public Info(Operation operation, String info) {
            this.operation = operation;
            this.info = info;
        }


        public Color getColor() {
            if (operation == Operation.DELETED){
                return Color.RED;
            }
            if (operation == Operation.ADDED){
                return Color.GREEN;
            }
            if (operation == Operation.SIZE){
                return Color.BLUE;
            }
            if (operation == Operation.FONT){
                return Color.magenta;
            }
            if (operation == Operation.STYLE){
                return Color.CYAN;
            }
            return Color.BLACK;
        }

        public Operation getOperation() {
            return operation;
        }

        public void setOperation(Operation operation) {
            this.operation = operation;
        }

        public String getInfo() {
            return info;
        }

        public void setInfo(String info) {
            this.info = info;
        }
    }




}
