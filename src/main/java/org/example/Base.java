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
            list.add(WordInfo.Operation.FONTNAMEDIFF);
        }
        if (wordInfo1.getFontSize() != wordInfo2.getFontSize()){
            list.add(WordInfo.Operation.FONTSIZEDIFF);
        }
        if (!wordInfo1.getFontStyle().equals(wordInfo2.getFontStyle())){
            list.add(WordInfo.Operation.FONTSTYLEDIFF);
        }
        return list;
    }



}
