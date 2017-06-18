package com.ooad.practice.sticker.Bean;

import android.util.Log;

import com.ooad.practice.sticker.Database.IDataAccessObject;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

/**
 * Created by fuyiru on 2017/5/4.
 */

public class Category implements Serializable {
    private static final long serialVersionUID = 1L;
    private Integer categoryID;
    private String title;
    private String description;

    public Category(Integer categoryID, String title, String description){
        this.categoryID = categoryID;
        this.title = title;
        this.description = description;
    }

    public Category(JSONObject jObj){
        try {
            this.categoryID = jObj.getInt(IDataAccessObject.CATEGORY_ID);
            this.title = jObj.getString(IDataAccessObject.CATEGORY_TITLE);
            this.description = jObj.getString(IDataAccessObject.CATEGORY_DESCRIPTION);
        }
        catch (JSONException e){
            Log.e(this.getClass().toString(), e.getMessage());
        }
    }

    public JSONObject toJSONObject(){
        JSONObject jObj = new JSONObject();
        try{
            jObj.put(IDataAccessObject.CATEGORY_ID, this.categoryID);
            jObj.put(IDataAccessObject.CATEGORY_TITLE, this.title);
            jObj.put(IDataAccessObject.CATEGORY_DESCRIPTION, this.description);
        }
        catch (JSONException e){
            Log.e(this.getClass().toString(), e.getMessage());
            return null;
        }
        return jObj;
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
}