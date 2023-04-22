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
                wordInfo1.getFontSize() == wordInfo2.getFontSize() && wordInfo1.getFontStyle().equals(wordInfo2.getFontStyle());
    }
    public static List<WordInfo.Info> getFontOperation(WordInfo wordInfo1, WordInfo wordInfo2) {
        List<WordInfo.Info> list = new ArrayList<>();
        if (!wordInfo1.getFontName().equals(wordInfo2.getFontName())){
            WordInfo.Info info = new WordInfo.Info(WordInfo.Operation.FONT,wordInfo1.getFontName() + " : "+wordInfo2.getFontName());
            list.add(info);
        }
        if (wordInfo1.getFontSize() != wordInfo2.getFontSize()){
            WordInfo.Info info = new WordInfo.Info(WordInfo.Operation.SIZE,wordInfo1.getFontSize() + " : "+wordInfo2.getFontSize());
            list.add(info);
        }
        if (!wordInfo1.getFontStyle().equals(wordInfo2.getFontStyle())){
            WordInfo.Info info = new WordInfo.Info(WordInfo.Operation.STYLE,wordInfo1.getFontStyle() + " : "+wordInfo2.getFontStyle());
            list.add(info);
        }
        return list;
    }



}
