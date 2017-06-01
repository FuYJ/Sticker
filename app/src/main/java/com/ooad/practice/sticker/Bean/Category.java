package com.ooad.practice.sticker.Bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by fuyiru on 2017/5/4.
 */

public class Category implements Serializable {
    private static final long serialVersionUID = 1L;
    private Integer categoryID;
    private String title;
    private String description;
    private List<Integer> stickerList;

    public Category(Integer categoryID, String title, String description){
        this.categoryID = categoryID;
        this.title = title;
        this.description = description;
    }

    public Integer getCategoryID() {
        return categoryID;
    }

    public void setCategoryID(Integer categoryID) {
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
