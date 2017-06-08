package com.ooad.practice.sticker.Model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import com.ooad.practice.sticker.Bean.Sticker;
import com.ooad.practice.sticker.Bean.Tag;
import com.ooad.practice.sticker.Database.Database;
import com.ooad.practice.sticker.Database.IDataAccessObject;
import com.ooad.practice.sticker.Database.StickerAccessObject;
import com.ooad.practice.sticker.Database.StickerTagsAccessObject;
import com.ooad.practice.sticker.MainApplication;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fuyiru on 2017/5/5.
 */

public class StickerList {
    private static StickerList instance;
    private IDataAccessObject stickerDAO;
    private IDataAccessObject stickerTagsDAO;

    private StickerList(){
        Context context = MainApplication.getContext();
        stickerDAO = new StickerAccessObject(context);
        stickerTagsDAO = new StickerTagsAccessObject(context);
    }

    public static StickerList getInstance(){
        if(instance == null){
            instance = new StickerList();
        }
        return instance;
    }

    public List<Sticker> getStickerListByCategoryId(Integer categoryId){
        List<Sticker> result = new ArrayList<>();
        String where = IDataAccessObject.STICKER_CATEGORY_ID + " = " + categoryId.toString();
        JSONArray jArr = stickerDAO.retrieveWhere(where);
        for(int i = 0; i < jArr.length(); i++){
            try {
                JSONObject jObj = jArr.getJSONObject(i);
                Sticker sticker = new Sticker(jObj);
                List<Tag> tagList = TagList.getInstance().getTagListByStickerId(sticker.getStickerID());
                sticker.setTagList(tagList);
                result.add(sticker);
            }
            catch (JSONException e){
                Log.e(this.getClass().toString(), "Error when parsing JSONArray");
            }
        }
        return result;
    }

    public List<Sticker> getStickerList(String keyword, List<Database.SearchTarget> searchTarget, Database.SearchIsFinished searchIsFinished){
        List<Sticker> result = new ArrayList<>();
        String where = "";
        where = addWhereConstraintsAccordingToSearchTarget(where, keyword, searchTarget);
        where = addWhereConstraintsAccordingToSearchIsFinished(where, searchIsFinished);
        JSONArray jArr = stickerDAO.retrieveWhere(where);
        for(int i = 0; i < jArr.length(); i++){
            try {
                JSONObject jObj = jArr.getJSONObject(i);
                Sticker sticker = new Sticker(jObj);
                List<Tag> tagList = TagList.getInstance().getTagListByStickerId(sticker.getStickerID());
                sticker.setTagList(tagList);
                result.add(sticker);
            }
            catch (JSONException e){
                Log.e(this.getClass().toString(), "Error when parsing JSONArray");
            }
        }
        return result;
    }

    private String addWhereConstraintsAccordingToSearchTarget(String where, String keyword, List<Database.SearchTarget> searchTarget){
        for(Database.SearchTarget st : searchTarget){
            switch(st){
                case STICKER:
                    if(where == "")
                        where += Database.STICKER_TITLE + " LIKE \"%" + keyword + "%\"";
                    else
                        where += "AND" + Database.STICKER_TITLE + " LIKE \"%" + keyword + "%\"";
                    break;
                case CATEGORY:
                    if(where == "")
                        where += Database.CATEGORY_TITLE + " LIKE \"%" + keyword + "%\"";
                    else
                        where += "AND" + Database.CATEGORY_TITLE + " LIKE \"%" + keyword + "%\"";
                    break;
                case TAG:
                    if(where == "")
                        where += Database.TAG_TITLE + " LIKE \"%" + keyword + "%\"";
                    else
                        where += "AND" + Database.TAG_TITLE + " LIKE \"%" + keyword + "%\"";
                    break;
            }
        }
        return where;
    }

    private String addWhereConstraintsAccordingToSearchIsFinished(String where, Database.SearchIsFinished searchIsFinished){
        if(searchIsFinished == Database.SearchIsFinished.FINISHED){
            if(where == "")
                where += Database.STICKER_IS_FINISHED + " = " + String.valueOf(Database.SearchIsFinished.FINISHED.ordinal());
            else
                where += "AND" + Database.STICKER_IS_FINISHED + " = " + String.valueOf(Database.SearchIsFinished.FINISHED.ordinal());
        }
        else{
            if(where == "")
                where += Database.STICKER_IS_FINISHED + " = " + String.valueOf(Database.SearchIsFinished.UNFINISHED.ordinal());
            else
                where += "AND" + Database.STICKER_IS_FINISHED + " = " + String.valueOf(Database.SearchIsFinished.UNFINISHED.ordinal());
        }
        return where;
    }

    public void setSticker(Sticker sticker){
        JSONObject jObj = sticker.toJSONObject();
        if(sticker.getStickerID() == 0)
            stickerDAO.create(jObj);
        else
            stickerDAO.updateOne(sticker.getCategoryID(), jObj);
    }

    public void setTagToSticker(Sticker sticker, Tag tag){
        JSONObject jObj = new JSONObject();
        try{
            jObj.put("\"" + IDataAccessObject.STICKER_TAGS_STICKER_ID + "\"", sticker.getStickerID());
            jObj.put("\"" + IDataAccessObject.STICKER_TAGS_TAG_ID + "\"", tag.getTagID());
        }
        catch (JSONException e){
            Log.e(this.getClass().toString(), e.getMessage());
        }
        stickerTagsDAO.create(jObj);
    }

    public void deleteTagFromSticker(Sticker sticker, Tag tag){
        String where = Database.STICKER_TAGS_STICKER_ID + " = \"" + sticker.getStickerID().toString() + "\" AND"
                + Database.STICKER_TAGS_TAG_ID + " = \"" + tag.getTagID().toString() + "\"";
        JSONArray jArr = stickerTagsDAO.retrieveWhere(where);
        int count = jArr.length();
        if(count > 0){
            stickerTagsDAO.deleteWhere(where);
        }
    }

    public void deleteSticker(Sticker sticker){
        stickerDAO.deleteOne(sticker.getStickerID());
    }

    public List<Sticker> getEmergentList(){
        List<Sticker> result = new ArrayList<>();
        String where = Database.STICKER_REMIND_TIME + " < " + System.currentTimeMillis();
        JSONArray jArr = stickerDAO.retrieveWhere(where);
        try {
            for(int i = 0; i < jArr.length(); i++){
                JSONObject jObj = jArr.getJSONObject(i);
                Sticker sticker = new Sticker(jObj);
                List<Tag> tagList = TagList.getInstance().getTagListByStickerId(sticker.getStickerID());
                sticker.setTagList(tagList);
                result.add(sticker);
            }
        }
        catch (JSONException e){
            Log.e(this.getClass().toString(), "Error when parsing JSONArray");
        }
        return result;
    }

    public List<Sticker> getLatestStickers(){
        ArrayList<Sticker> result = new ArrayList<>();

        return result;
    }
}