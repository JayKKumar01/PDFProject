package org.example;

import java.util.List;

public class InfoList {
    List<WordInfo> list;
    List<WordInfo> deletedList;
    List<WordInfo> addedList;

    public InfoList(List<WordInfo> list, List<WordInfo> deletedList, List<WordInfo> addedList) {
        this.list = list;
        this.deletedList = deletedList;
        this.addedList = addedList;
    }

    public List<WordInfo> getList() {
        return list;
    }

    public void setList(List<WordInfo> list) {
        this.list = list;
    }

    public List<WordInfo> getDeletedList() {
        return deletedList;
    }

    public void setDeletedList(List<WordInfo> deletedList) {
        this.deletedList = deletedList;
    }

    public List<WordInfo> getAddedList() {
        return addedList;
    }

    public void setAddedList(List<WordInfo> addedList) {
        this.addedList = addedList;
    }
}
