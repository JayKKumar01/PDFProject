package org.example;

import java.util.ArrayList;
import java.util.List;

public class StringDiff {

    public static List<WordInfo> findDifference(List<WordInfo> wordList1, List<WordInfo> wordList2) {
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
        List<WordInfo> diff = new ArrayList<>();
        while (i > 0 || j > 0) {
            if (i > 0 && j > 0 && words1[i-1].equals(words2[j-1])) {
                WordInfo wordInfo = wordList2.get(j-1);
                if (Base.isFontInfoSame(wordList1.get(i-1), wordInfo)) {
                    wordInfo.setOperation(WordInfo.Operation.EQUAL);
                } else {
                    wordInfo.setOperation(WordInfo.Operation.FONTDIFFERENCE);
                }
                diff.add(0, wordInfo);
                i--;
                j--;
            } else if (i > 0 && (j == 0 || dp[i][j-1] >= dp[i-1][j])) {
                WordInfo wordInfo = wordList1.get(i-1);
                wordInfo.setOperation(WordInfo.Operation.DELETED);
                diff.add(0, wordInfo);
                i--;
            } else if (i == 0 || dp[i][j - 1] < dp[i - 1][j]) {
                WordInfo wordInfo = wordList2.get(j-1);
                wordInfo.setOperation(WordInfo.Operation.ADDED);
                diff.add(0, wordInfo);
                j--;
            }
        }
        return diff;
    }

    private static String[] listToArr(List<WordInfo> wordList) {
        String[] list = new String[wordList.size()];
        for(int i=0; i<wordList.size(); i++){
            list[i] = wordList.get(i).getWord();
        }
        return list;
    }

}
