package com.ooad.practice.sticker.Bean;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.ooad.practice.sticker.Database.IDataAccessObject;
import com.ooad.practice.sticker.MainActivity;
import com.ooad.practice.sticker.MainApplication;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by fuyiru on 2017/5/4.
 */

public class Sticker implements Serializable {
    private static final long serialVersionUID = 1L;
    private Integer stickerID;
    private Integer categoryID;
    private String title;
    private String description;
    private String deadline;
    private String remindTime;
    private List<Tag> tagList;
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

    public Sticker(JSONObject jObj){
        try {
            this.stickerID = jObj.getInt(IDataAccessObject.STICKER_ID);
            this.categoryID = jObj.getInt(IDataAccessObject.STICKER_CATEGORY_ID);
            this.title = jObj.getString(IDataAccessObject.STICKER_TITLE);
            this.description = jObj.getString(IDataAccessObject.STICKER_DESCRIPTION);
            this.deadline = calculateDate(jObj.getLong(IDataAccessObject.STICKER_DEADLINE));
            this.remindTime = calculateDate(jObj.getLong(IDataAccessObject.STICKER_REMIND_TIME));
            this.isFinished = jObj.getBoolean(IDataAccessObject.STICKER_IS_FINISHED);
        }
        catch (JSONException e){
            Log.e(this.getClass().toString(), e.getMessage());
        }
    }

    public JSONObject toJSONObject(){
        JSONObject jObj = new JSONObject();
        try{
            jObj.put(IDataAccessObject.STICKER_ID, this.stickerID);
            jObj.put(IDataAccessObject.STICKER_CATEGORY_ID, this.categoryID);
            jObj.put(IDataAccessObject.STICKER_TITLE, this.title);
            jObj.put(IDataAccessObject.STICKER_DESCRIPTION, this.description);
            jObj.put(IDataAccessObject.STICKER_DEADLINE, calculateDate(this.deadline));
            jObj.put(IDataAccessObject.STICKER_REMIND_TIME, calculateDate(this.remindTime));
            jObj.put(IDataAccessObject.STICKER_IS_FINISHED, this.isFinished);
        }
        catch (JSONException e){
            Log.e(this.getClass().toString(), e.getMessage());
            return null;
        }
        return jObj;
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

    public List<Tag> getTagList() {
        return tagList;
    }

    public void setTagList(List<Tag> tagList){
        this.tagList = tagList;
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
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm");
        formattedDate = sdf.format(date);
        return formattedDate;
    }

    public Long calculateDate(String date){
        Long dateTime = 0L;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm");
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
