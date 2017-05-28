package com.ooad.practice.sticker.Bean;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.ooad.practice.sticker.MainActivity;
import com.ooad.practice.sticker.MainApplication;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
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
    private String deadline;
    private String remindTime;
    private List<Integer> tagList;
    private Boolean isFinished;

    public Sticker(Integer stickerID, Integer categoryID, String title, String description, Long deadline, Long remindTime, Boolean isFinished){
        this.stickerID = stickerID;
        this.categoryID = categoryID;
        this.title = title;
        this.description = description;
        this.deadline = calculateDate(deadline);
        this.remindTime = calculateDate(remindTime);
        this.isFinished = isFinished;
    }

    public Sticker(Integer stickerID, Integer categoryID, String title, String description, String deadline, String remindTime, Boolean isFinished){
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

    public Integer getCategoryID() {
        return categoryID;
    }

    public String getTitle() {
        return title;
    }

    public String getDeadline() {
        return deadline;
    }

    public List<Integer> getTagList() {
        return tagList;
    }

    public String getDescription() {
        return description;
    }

    public String getRemindTime() {
        return remindTime;
    }

    public Boolean getFinished() {
        return isFinished;
    }

    public String calculateDate(Long date){
        String formattedDate;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        formattedDate = sdf.format(date);
        return formattedDate;
    }

    public Long calculateDate(String date){
        Long dateTime = 0L;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        try {
            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(MainApplication.getContext());
            Calendar calendar = Calendar.getInstance();
            dateTime = sdf.parse(date).getTime();
            if(sharedPreferences.getString("@string/calendars", "西元曆").equals("國曆")){
                calendar.setTimeInMillis(dateTime);
                calendar.add(Calendar.YEAR, -1911);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return dateTime;
    }
}
