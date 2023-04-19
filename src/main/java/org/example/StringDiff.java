package org.example;

import java.util.ArrayList;
import java.util.List;

public class StringDiff {
    public static List<WordInfo> find(List<WordInfo> wordList1, List<WordInfo> wordList2){
        InfoList list = findDifference(wordList1,wordList2,true);
        InfoList newList = findDifference(list.getDeletedList(),list.getAddedList(),false);

        List<WordInfo> finalList = list.getList();
        finalList.addAll(newList.getList());
        finalList.addAll(newList.getDeletedList());
        finalList.addAll(newList.getAddedList());
        return finalList;
    }


    public static InfoList findDifference(List<WordInfo> wordList1, List<WordInfo> wordList2,boolean reset) {
        String[] words1 = listToArr(wordList1);
        String[] words2 = listToArr(wordList2);
        int n = words1.length;
        int m = words2.length;
        int[][] dp = new int[n+1][m+1];
        for (int i = 0; i <= n; i++) {
            for (int j = 0; j <= m; j++) {
                if (i == 0) {
                    dp[i][j] = j;
                } else if (j == 0) {
                    dp[i][j] = i;
                } else if (words1[i-1].equals(words2[j-1])) {
                    dp[i][j] = dp[i-1][j-1];
                } else {
                    int delCost = dp[i-1][j] + 1;
                    int insCost = dp[i][j-1] + 1;
                    int subCost = dp[i-1][j-1] + 1;
                    dp[i][j] = Math.min(delCost, Math.min(insCost, subCost));
                }
            }
        }
        int i = n;
        int j = m;
        List<WordInfo> list = new ArrayList<>();
        List<WordInfo> deletedList = new ArrayList<>();
        List<WordInfo> addedList = new ArrayList<>();
        while (i > 0 || j > 0) {
            if (i > 0 && j > 0 && words1[i-1].equals(words2[j-1])) {
                WordInfo wordInfo = wordList2.get(j-1);

                if (Base.isFontInfoSame(wordList1.get(i-1), wordInfo)) {
                    wordInfo.setOperation(WordInfo.Operation.EQUAL);
                } else {
                    List<WordInfo.Operation> opList = Base.getFontOperation(wordList1.get(i-1), wordInfo);
                    if (opList.size() == 1){
                        wordInfo.setOperation(opList.get(0));
                    }else {
                        wordInfo.setOperation(opList);
                    }
                }
                list.add(0, wordInfo);
                i--;
                j--;
            } else if (i > 0 && (j == 0 || dp[i][j-1] >= dp[i-1][j])) {

                if (j ==0){
                    WordInfo wordInfo = wordList1.get(i - 1);
                    if (!reset) {
                        wordInfo.setOperation(WordInfo.Operation.DELETED);
                    }
                    deletedList.add(0, wordInfo);
                }
                if (j>0 && confirmDel(words1,words2[j-1],i)) {
                    WordInfo wordInfo = wordList1.get(i - 1);
                    if (!reset) {
                        wordInfo.setOperation(WordInfo.Operation.DELETED);
                    }
                    deletedList.add(0, wordInfo);
                }
                i--;
            } else if (i == 0 || dp[i][j - 1] < dp[i - 1][j]) {
                if (confirmAdd(words1,words2[j-1],i)) {
                    WordInfo wordInfo = wordList2.get(j - 1);
                    if (!reset) {
                        wordInfo.setOperation(WordInfo.Operation.ADDED);
                    }
                    addedList.add(0, wordInfo);
                }
                j--;
            }
        }
        return new InfoList(list,deletedList,addedList);
    }

    private static boolean confirmAdd(String[] words1, String word, int i) {
        if (words1.length >  i+1){
            return !word.equals(words1[i] + words1[i + 1]);
        }
        return true;
    }

    private static boolean confirmDel(String[] words1, String word, int i) {
        if (words1.length > i){
            if ((words1[i-1] +words1[i]).equals(word)){
                return false;
            }
        }
        if (i-2 > -1){
            return !(words1[i - 2] + words1[i - 1]).equals(word);
        }
        return true;
    }

    private static String[] listToArr(List<WordInfo> wordList) {
        String[] list = new String[wordList.size()];
        for(int i=0; i<wordList.size(); i++){
            list[i] = wordList.get(i).getWord();
        }
        return list;
    }

}
