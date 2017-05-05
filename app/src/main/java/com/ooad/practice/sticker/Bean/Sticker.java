package com.ooad.practice.sticker.Bean;

import java.util.Date;
import java.util.List;

/**
 * Created by fuyiru on 2017/5/4.
 */

public class Sticker {
    private int stickerID;
    private int categoryID;
    private String title;
    private Date deadline;
    private List<Integer> tagList;
    private String description;
    private Date remindTime;
    private Boolean isFinished;

    public int getStickerID() {
        return stickerID;
    }

    public void setStickerID(int stickerID) {
        this.stickerID = stickerID;
    }

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

    public Date getDeadline() {
        return deadline;
    }

    public void setDeadline(Date deadline) {
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

    public Date getRemindTime() {
        return remindTime;
    }

    public void setRemindTime(Date remindTime) {
        this.remindTime = remindTime;
    }

    public Boolean getFinished() {
        return isFinished;
    }

    public void setFinished(Boolean finished) {
        isFinished = finished;
    }
}
