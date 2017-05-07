package com.ooad.practice.sticker.Bean;

import java.util.List;

/**
 * Created by fuyiru on 2017/5/4.
 */

public class Tag {
    private String title;
    private List<Integer> color;
    private int tagID;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Integer> getColor() {
        return color;
    }

    public void setColor(List<Integer> color) {
        this.color = color;
    }

    public int getTagID() {
        return tagID;
    }

    public void setTagID(int tagID) {
        this.tagID = tagID;
    }
}
