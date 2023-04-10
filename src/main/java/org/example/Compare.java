package org.example;

import java.util.LinkedList;
import java.util.List;

public class Compare {
    public static void getDiff(String str1, String str2) {
        DiffMatchPatch dmp = new DiffMatchPatch();
        LinkedList<DiffMatchPatch.Diff> diffs = dmp.diff_main(str1,str2);
        dmp.diff_cleanupSemantic(diffs);
        for (DiffMatchPatch.Diff diff : diffs) {
            System.out.println(diff.operation.toString() + ": " + diff.text);
        }
    }
}

