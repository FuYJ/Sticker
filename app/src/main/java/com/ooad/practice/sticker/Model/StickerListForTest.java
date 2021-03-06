package com.ooad.practice.sticker.Model;

import android.content.SharedPreferences;
import android.util.Log;

import com.ooad.practice.sticker.Bean.Sticker;
import com.ooad.practice.sticker.Bean.Tag;
import com.ooad.practice.sticker.Database.IDataAccessObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fuyiru on 2017/5/5.
 */

public class StickerListForTest extends StickerList {
    private IDataAccessObject stickerDAO;
    private IDataAccessObject stickerTagsDAO;
    private SharedPreferences sharedPreferences;
    private Long currentTime;

    public StickerListForTest(IDataAccessObject stickerDAO, IDataAccessObject stickerTagsDAO, SharedPreferences sharedPreferences, Long currentTime){
        this.stickerDAO = stickerDAO;
        this.stickerTagsDAO = stickerTagsDAO;
        this.sharedPreferences = sharedPreferences;
        this.currentTime = currentTime;
    }

    public Sticker getStickerByStickerId(Integer stickerID){
        JSONObject jObj = stickerDAO.retrieveOne(stickerID);
        Sticker result = new Sticker(jObj);
        List<Tag> tagList = new TagList().getTagListByStickerId(stickerID);
        result.setTagList(tagList);
        return result;
    }

    public List<Sticker> getStickerListByCategoryId(Integer categoryID){
        List<Sticker> result = new ArrayList<>();
        String where = IDataAccessObject.STICKER_CATEGORY_ID + " = " + categoryID.toString();
        JSONArray jArr = stickerDAO.retrieveWhere(where);
        for(int i = 0; i < jArr.length(); i++){
            try {
                JSONObject jObj = jArr.getJSONObject(i);
                Sticker sticker = new Sticker(jObj, sharedPreferences);
                result.add(sticker);
            }
            catch (JSONException e){
                Log.e(this.getClass().toString(), "Error when parsing JSONArray");
            }
        }
        return result;
    }

    public List<Sticker> getStickerList(String keyword, List<IDataAccessObject.SearchTarget> searchTarget, IDataAccessObject.SearchIsFinished searchIsFinished){
        List<Sticker> result = new ArrayList<>();
        String where = "";
        where = " ( " + addWhereConstraintsAccordingToSearchTarget(where, keyword, searchTarget) + " ) ";
        where = addWhereConstraintsAccordingToSearchIsFinished(where, searchIsFinished);
        JSONArray jArr = stickerDAO.retrieveWhere(where);
        for(int i = 0; i < jArr.length(); i++){
            try {
                JSONObject jObj = jArr.getJSONObject(i);
                Sticker sticker = new Sticker(jObj, sharedPreferences);
                result.add(sticker);
            }
            catch (JSONException e){
                Log.e(this.getClass().toString(), "Error when parsing JSONArray");
            }
        }
        return result;
    }

    private String addWhereConstraintsAccordingToSearchTarget(String where, String keyword, List<IDataAccessObject.SearchTarget> searchTarget){
        for(IDataAccessObject.SearchTarget st : searchTarget){
            switch(st){
                case STICKER:
                    if(where == "")
                        where += IDataAccessObject.STICKER_TABLE + "." + IDataAccessObject.STICKER_TITLE + " LIKE \"%" + keyword + "%\"";
                    else
                        where += " OR " + IDataAccessObject.STICKER_TABLE + "." + IDataAccessObject.STICKER_TITLE + " LIKE \"%" + keyword + "%\"";
                    break;
                case CATEGORY:
                    if(where == "")
                        where += IDataAccessObject.CATEGORY_TABLE + "." + IDataAccessObject.CATEGORY_TITLE + " LIKE \"%" + keyword + "%\"";
                    else
                        where += " OR " + IDataAccessObject.CATEGORY_TABLE + "." + IDataAccessObject.CATEGORY_TITLE + " LIKE \"%" + keyword + "%\"";
                    break;
                case TAG:
                    if(where == "")
                        where += IDataAccessObject.NEST_TABLE1 + "." + IDataAccessObject.TAG_TITLE + " LIKE \"%" + keyword + "%\"";
                    else
                        where += " OR " + IDataAccessObject.NEST_TABLE1 + "." + IDataAccessObject.TAG_TITLE + " LIKE \"%" + keyword + "%\"";
                    break;
            }
        }
        return where;
    }

    private String addWhereConstraintsAccordingToSearchIsFinished(String where, IDataAccessObject.SearchIsFinished searchIsFinished){
        if(searchIsFinished == IDataAccessObject.SearchIsFinished.FINISHED){
            if(where == "")
                where += IDataAccessObject.STICKER_TABLE + "." + IDataAccessObject.STICKER_IS_FINISHED + " = " + String.valueOf(IDataAccessObject.SearchIsFinished.FINISHED.ordinal());
            else
                where += " AND " + IDataAccessObject.STICKER_TABLE + "." + IDataAccessObject.STICKER_IS_FINISHED + " = " + String.valueOf(IDataAccessObject.SearchIsFinished.FINISHED.ordinal());
        }
        else{
            if(where == "")
                where += IDataAccessObject.STICKER_TABLE + "." + IDataAccessObject.STICKER_IS_FINISHED + " = " + String.valueOf(IDataAccessObject.SearchIsFinished.UNFINISHED.ordinal());
            else
                where += " AND " + IDataAccessObject.STICKER_TABLE + "." + IDataAccessObject.STICKER_IS_FINISHED + " = " + String.valueOf(IDataAccessObject.SearchIsFinished.UNFINISHED.ordinal());
        }
        return where;
    }

    public Integer setSticker(Sticker sticker){
        Integer stickerID = 0;
        JSONObject jObj = sticker.toJSONObject();
        if(sticker.getStickerID() == 0)
            stickerID = stickerDAO.create(jObj);
        else
            stickerDAO.updateOne(sticker.getStickerID(), jObj);
        return stickerID;
    }

    public void setTagToSticker(Sticker sticker, Tag tag){
        JSONObject jObj = new JSONObject();
        try{
            jObj.put(IDataAccessObject.STICKER_TAGS_STICKER_ID, sticker.getStickerID());
            jObj.put(IDataAccessObject.STICKER_TAGS_TAG_ID, tag.getTagID());
        }
        catch (JSONException e){
            Log.e(this.getClass().toString(), e.getMessage());
        }
        stickerTagsDAO.create(jObj);
    }

    public void deleteTagFromSticker(Sticker sticker, Tag tag){
        String where = IDataAccessObject.STICKER_TAGS_STICKER_ID + " = " + sticker.getStickerID().toString() + " AND "
                + IDataAccessObject.STICKER_TAGS_TAG_ID + " = " + tag.getTagID().toString();
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
        String where = IDataAccessObject.STICKER_REMIND_TIME + " < " + currentTime + " AND " + IDataAccessObject.STICKER_IS_FINISHED + " = 0";
        JSONArray jArr = stickerDAO.retrieveWhere(where);
        try {
            for(int i = 0; i < jArr.length(); i++){
                JSONObject jObj = jArr.getJSONObject(i);
                Sticker sticker = new Sticker(jObj, sharedPreferences);
                result.add(sticker);
            }
        }
        catch (JSONException e){
            Log.e(this.getClass().toString(), "Error when parsing JSONArray");
        }
        return result;
    }
}