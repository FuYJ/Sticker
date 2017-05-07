package com.ooad.practice.sticker.Bean;

import java.util.Date;
import java.util.List;

/**
 * Created by fuyiru on 2017/5/4.
 */

public class Sticker {
    private Integer stickerID;
    private Integer categoryID;
    private String title;
    private String description;
    private Integer deadline;
    private Integer remindTime;
    private List<Integer> tagList;
    private Boolean isFinished;

    public Sticker(Integer stickerID, Integer categoryID, String title, String description, Integer deadline, Integer remindTime, Boolean isFinished){
        this.stickerID = stickerID;
        this.categoryID = categoryID;
        this.title = title;
        this.description = description;
        this.deadline = deadline;
        this.remindTime = remindTime;
        this.isFinished = isFinished;
    }

    public Integer getStickerID() {
        return stickerID;
    }

    public void setStickerID(Integer stickerID) {
        this.stickerID = stickerID;
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

    public Integer getDeadline() {
        return deadline;
    }

    public void setDeadline(Integer deadline) {
        this.deadline = deadline;
    }

    public List<Integer> getTagList() {
        return tagList;
    }

    public void setTagList(List<Integer> tagList) {
        this.tagList = tagList;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getRemindTime() {
        return remindTime;
    }

    public void setRemindTime(Integer remindTime) {
        this.remindTime = remindTime;
    }

    public Boolean getFinished() {
        return isFinished;
    }

    public void setFinished(Boolean finished) {
        isFinished = finished;
    }
}
