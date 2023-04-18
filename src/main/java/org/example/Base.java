package org.example;

import java.util.ArrayList;
import java.util.List;

public class Base {
    public static final String SAMPLEPATH = "E:/Sample/";


    public static boolean isFontInfoSame(WordInfo wordInfo1, WordInfo wordInfo2) {
        if (wordInfo1.getFont() == null){
            return false;
        }
        return wordInfo1.getFontName().equals(wordInfo2.getFontName()) &&
                wordInfo1.getFontSize() == wordInfo2.getFontSize();
    }
    public static List<WordInfo.Operation> getFontOperation(WordInfo wordInfo1, WordInfo wordInfo2) {
        List<WordInfo.Operation> list = new ArrayList<>();
        if (!wordInfo1.getFontName().equals(wordInfo2.getFontName())){
            WordInfo.Operation operation = WordInfo.Operation.FONTNAMEDIFF;
            operation.setInfo(wordInfo1.getFontName() + " : "+wordInfo2.getFontName());
            list.add(operation);
        }
        if (wordInfo1.getFontSize() != wordInfo2.getFontSize()){
            WordInfo.Operation operation = WordInfo.Operation.FONTSIZEDIFF;
            operation.setInfo(wordInfo1.getFontSize() + " : "+wordInfo2.getFontSize());
            list.add(operation);
        }
        if (!wordInfo1.getFontStyle().equals(wordInfo2.getFontStyle())){
            WordInfo.Operation operation = WordInfo.Operation.FONTSTYLEDIFF;
            operation.setInfo(wordInfo1.getFontStyle() + " : "+wordInfo2.getFontStyle());
            list.add(operation);
        }
        return list;
    }



}
