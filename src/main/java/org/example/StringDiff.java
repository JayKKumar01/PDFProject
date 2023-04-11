package org.example;

import java.util.List;

public class StringDiff {

    public static String findDifference(List<WordInfo> wordList1, List<WordInfo> wordList2) {
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
    StringBuilder diff = new StringBuilder();
    while (i > 0 || j > 0) {
        if (i > 0 && j > 0 && words1[i-1].equals(words2[j-1])) {
            diff.insert(0, words1[i-1] + " ");
            i--;
            j--;
        } else if (i > 0 && (j == 0 || dp[i][j-1] >= dp[i-1][j])) {
            diff.insert(0, "-"+words1[i-1] + " ");
            i--;
        } else if (j > 0 && (i == 0 || dp[i][j-1] < dp[i-1][j])) {
            diff.insert(0, "+"+words2[j-1] + " ");
            j--;
        }
    }
    return diff.toString().trim();
}

    private static String[] listToArr(List<WordInfo> wordList) {
        String[] list = new String[wordList.size()];
        for(int i=0; i<wordList.size(); i++){
            list[i] = wordList.get(i).getWord();
        }
        return list;
    }


//    public static void main(String[] args) {
//        String s1 = "The quick brown fox jumps over the lazy dog";
//        String s2 = "The quick red jumps over lazy";
//        String diff = findDifference(s1, s2);
//        System.out.println(diff);
//    }
}
