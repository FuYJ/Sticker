package com.ooad.practice.sticker.Bean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fuyiru on 2017/5/4.
 */

public class Tag {
    private String title;
    private List<Integer> color;
    private Integer tagID;

    public Tag(Integer tagID, String title, int colorR, int colorG, int colorB){
        this.tagID = tagID;
        this.title = title;
        List<Integer> color = new ArrayList<>();
        color.add(colorR);
        color.add(colorG);
        color.add(colorB);
        this.color = color;
    }

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

    public Integer getTagID() {
        return tagID;
    }

    public void setTagID(Integer tagID) {
        this.tagID = tagID;
    }
}
