package com.ooad.practice.sticker.Bean;

import android.util.Log;

import com.ooad.practice.sticker.Database.IDataAccessObject;

import org.json.JSONException;
import org.json.JSONObject;

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

    public Tag(JSONObject jObj){
        try{
            this.tagID = jObj.getInt(IDataAccessObject.TAG_ID);
            this.title = jObj.getString(IDataAccessObject.TAG_TITLE);
            List<Integer> color = new ArrayList<>();
            color.add(jObj.getInt(IDataAccessObject.TAG_COLOR_R));
            color.add(jObj.getInt(IDataAccessObject.TAG_COLOR_G));
            color.add(jObj.getInt(IDataAccessObject.TAG_COLOR_B));
            this.color = color;
        }
        catch (JSONException e){
            Log.e(this.getClass().toString(), e.getMessage());
        }
    }

    public JSONObject toJSONObject(){
        JSONObject jObj = new JSONObject();
        try{
            jObj.put(IDataAccessObject.TAG_ID, this.tagID);
            jObj.put(IDataAccessObject.TAG_TITLE, this.title);
            jObj.put(IDataAccessObject.TAG_COLOR_R, this.color.get(0));
            jObj.put(IDataAccessObject.TAG_COLOR_G, this.color.get(1));
            jObj.put(IDataAccessObject.TAG_COLOR_B, this.color.get(2));
        }
        catch (JSONException e){
            Log.e(this.getClass().toString(), e.getMessage());
            return null;
        }
        return jObj;
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