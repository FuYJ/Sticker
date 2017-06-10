package com.ooad.practice.sticker.Model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import com.ooad.practice.sticker.Bean.Tag;
import com.ooad.practice.sticker.Database.Database;
import com.ooad.practice.sticker.Database.IDataAccessObject;
import com.ooad.practice.sticker.Database.IDatabase;
import com.ooad.practice.sticker.Database.StickerTagsAccessObject;
import com.ooad.practice.sticker.Database.TagAccessObject;
import com.ooad.practice.sticker.MainApplication;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fuyiru on 2017/5/4.
 */

public class TagList {
    private static TagList instance;
    //private IDatabase db;
    private IDataAccessObject tagDAO;
    private IDataAccessObject stickerTagsDAO;

    public TagList() {
        Context context = MainApplication.getContext();
        tagDAO = new TagAccessObject(context);
        stickerTagsDAO = new StickerTagsAccessObject(context);
    }

    /*public static TagList getInstance(){
        if(instance == null){
            instance = new TagList();
        }
        return instance;
    }*/

    public List<Tag> getTagListByStickerId(Integer stickerId){
        List<Tag> result = new ArrayList<>();
        String where = IDataAccessObject.STICKER_TAGS_STICKER_ID  + " = " + stickerId.toString();
        JSONArray jArr = stickerTagsDAO.retrieveWhere(where);
        try{
            for(int i = 0; i < jArr.length(); i++){
                Integer tagID = jArr.getJSONObject(i).getInt(IDataAccessObject.STICKER_TAGS_TAG_ID);
                JSONObject jObj = tagDAO.retrieveOne(tagID);
                Tag tag = new Tag(jObj);
                result.add(tag);
            }
        }
        catch (JSONException e){
            Log.e(this.getClass().toString(), e.getMessage());
        }
        return result;
    }

    public List<Tag> getTagList(){
        List<Tag> result = new ArrayList<>();
        JSONArray jArr = tagDAO.retrieveAll();
        try{
            for(int i = 0; i < jArr.length(); i++){
                JSONObject jObj = jArr.getJSONObject(i);
                Tag tag = new Tag(jObj);
                result.add(tag);
            }
        }
        catch (JSONException e){
            Log.e(this.getClass().toString(), e.getMessage());
        }
        return result;
    }

    public void setTag(Tag tag){
        JSONObject jObj = tag.toJSONObject();
        if(tag.getTagID() == 0)
            tagDAO.create(jObj);
        else
            tagDAO.updateOne(tag.getTagID(), jObj);
    }

    public void deleteTag(Tag tag){
        String where = IDataAccessObject.STICKER_TAGS_TAG_ID + " = \"" + tag.getTagID().toString() + "\"";
        tagDAO.deleteOne(tag.getTagID());
        stickerTagsDAO.deleteWhere(where);
    }
}