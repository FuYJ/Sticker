package com.ooad.practice.sticker.Model;

import android.content.ContentValues;
import android.database.Cursor;

import com.ooad.practice.sticker.Bean.Sticker;
import com.ooad.practice.sticker.Bean.Tag;
import com.ooad.practice.sticker.Database.Database;
import com.ooad.practice.sticker.Database.IDatabase;
import com.ooad.practice.sticker.MainApplication;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by fuyiru on 2017/5/5.
 */

public class StickerList {
    private static StickerList instance;
    private IDatabase db;

    private StickerList(){
        db = new Database(MainApplication.getContext());
    }

    public static StickerList getInstance(){
        if(instance == null){
            instance = new StickerList();
        }
        return instance;
    }

    public List<Sticker> getStickerListByCategoryId(Integer categoryId){
        List<Sticker> result = new ArrayList<>();
        String where = Database.STICKER_CATEGORY_ID + " = " + categoryId.toString();

        Cursor cursor = db.retrieve(Database.STICKER_TABLE, where, Database.STICKER_ID + " " + Database.ORDER_ASC);
        int rowsNum = cursor.getCount();
        if(rowsNum > 0){
            cursor.moveToFirst();
            for(int i = 0; i < rowsNum; i++){
                Integer stickerID = cursor.getInt(0);
                Integer categoryID = cursor.getInt(1);
                String title = cursor.getString(2);
                String description = cursor.getString(3);
                Long deadline = cursor.getLong(4);
                Long remindTime = cursor.getLong(5);
                Boolean isFinished = (cursor.getInt(6) == 1)? true : false;
                Sticker sticker = new Sticker(stickerID, categoryID, title, description, deadline, remindTime, isFinished);
                List<Tag> tagList = TagList.getInstance().getTagListByStickerId(sticker.getStickerID());
                sticker.setTagList(tagList);
                result.add(sticker);
                cursor.moveToNext();
            }
            cursor.close();
        }
        return result;
    }

    public List<Sticker> getStickerList(String keyword, List<Database.SearchTarget> searchTarget, Database.SearchIsFinished searchIsFinished){
        List<Sticker> result = new ArrayList<>();
        String where = "";
        addWhereConstraintsAccordingToSearchTarget(where, keyword, searchTarget);
        addWhereConstraintsAccordingToSearchIsFinished(where, searchIsFinished);

        Cursor cursor = db.retrieve(Database.STICKER_TABLE, where, Database.STICKER_DEADLINE);
        int rowsNum = cursor.getCount();
        if(rowsNum > 0){
            cursor.moveToFirst();
            for(int i = 0; i < rowsNum; i++){
                Integer stickerID = cursor.getInt(0);
                Integer categoryID = cursor.getInt(1);
                String title = cursor.getString(2);
                String description = cursor.getString(3);
                Long deadline = cursor.getLong(4);
                Long remindTime = cursor.getLong(5);
                Boolean isFinished = (cursor.getInt(6) == 1)? true : false;
                Sticker sticker = new Sticker(stickerID, categoryID, title, description, deadline, remindTime, isFinished);
                List<Tag> tagList = TagList.getInstance().getTagListByStickerId(sticker.getStickerID());
                sticker.setTagList(tagList);
                result.add(sticker);
                cursor.moveToNext();
            }
            cursor.close();
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
        ContentValues cv = new ContentValues();
        cv.put(Database.STICKER_CATEGORY_ID, sticker.getCategoryID());
        cv.put(Database.STICKER_TITLE, sticker.getTitle());
        cv.put(Database.STICKER_DESCRIPTION, sticker.getDescription());
        cv.put(Database.STICKER_DEADLINE, sticker.calculateDate(sticker.getDeadline()));
        cv.put(Database.STICKER_REMIND_TIME, sticker.calculateDate(sticker.getRemindTime()));
        cv.put(Database.STICKER_IS_FINISHED, sticker.getFinished());
        if(sticker.getStickerID() == 0)
            db.create(Database.STICKER_TABLE, cv);
        else
            db.update(Database.STICKER_TABLE, Database.STICKER_ID + " = " + sticker.getStickerID().toString(), cv);
    }

    public void setTagToSticker(Sticker sticker, Tag tag){
        ContentValues cv = new ContentValues();
        cv.put(Database.STICKER_TAGS_STICKER_ID, sticker.getStickerID());
        cv.put(Database.STICKER_TAGS_TAG_ID, tag.getTagID());
        db.create(Database.STICKER_TAGS_TABLE, cv);
    }

    public void deleteTagFromSticker(Sticker sticker, Tag tag){
        ContentValues cv = new ContentValues();
        cv.put(Database.STICKER_TAGS_STICKER_ID, sticker.getStickerID());
        cv.put(Database.STICKER_TAGS_TAG_ID, tag.getTagID());
        String where = Database.STICKER_TAGS_STICKER_ID + " = \"" + sticker.getStickerID().toString() + "\" AND"
                + Database.STICKER_TAGS_TAG_ID + " = \"" + tag.getTagID().toString() + "\"";
        Cursor cursor = db.retrieve(Database.STICKER_TAGS_TABLE, where, Database.STICKER_ID + Database.ORDER_ASC);
        int count = cursor.getCount();
        if(count > 0){
            db.delete(Database.STICKER_TAGS_TABLE, where);
        }
    }

    public void deleteSticker(Sticker sticker){
        db.delete(Database.STICKER_TABLE, sticker.getStickerID());
    }

    public List<Sticker> getEmergentList(){
        List<Sticker> result = new ArrayList<>();
        String where = Database.STICKER_REMIND_TIME + " < " + System.currentTimeMillis();

        Cursor cursor = db.retrieve(Database.STICKER_TABLE, where, Database.STICKER_DEADLINE);
        int rowsNum = cursor.getCount();
        if(rowsNum > 0){
            cursor.moveToFirst();
            for(int i = 0; i < rowsNum; i++){
                Integer stickerID = cursor.getInt(0);
                Integer categoryID = cursor.getInt(1);
                String title = cursor.getString(2);
                String description = cursor.getString(3);
                Long deadline = cursor.getLong(4);
                Long remindTime = cursor.getLong(5);
                Boolean isFinished = (cursor.getInt(6) == 1)? true : false;
                Sticker sticker = new Sticker(stickerID, categoryID, title, description, deadline, remindTime, isFinished);
                List<Tag> tagList = TagList.getInstance().getTagListByStickerId(sticker.getStickerID());
                sticker.setTagList(tagList);
                result.add(sticker);
                cursor.moveToNext();
            }
            cursor.close();
        }
        return result;
    }

    public List<Sticker> getLatestStickers(){
        ArrayList<Sticker> result = new ArrayList<>();

        return result;
    }
}