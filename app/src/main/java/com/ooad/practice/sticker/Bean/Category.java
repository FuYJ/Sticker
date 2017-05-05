package com.ooad.practice.sticker.Bean;

import java.util.List;

/**
 * Created by fuyiru on 2017/5/4.
 */

public class Category {
    private int categoryID;
    private String title;
    private String description;
    private List<Integer> stickerList;

    public int getCategoryID() {
        return categoryID;
    }

    public void setCategoryID(int categoryID) {
        this.categoryID = categoryID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Integer> getStickerList() {
        return stickerList;
    }

    public void setStickerList(List<Integer> stickerList) {
        this.stickerList = stickerList;
    }
}
