package com.ooad.practice.sticker.Model;

import android.app.Application;
import android.content.ContentValues;
import android.database.Cursor;

import com.ooad.practice.sticker.Bean.Sticker;
import com.ooad.practice.sticker.Database.Database;
import com.ooad.practice.sticker.Database.IDatabase;
import com.ooad.practice.sticker.MainApplication;

import java.util.ArrayList;
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

    public List<Sticker> getStickerList(String keyword){
        List<Sticker> result = new ArrayList<>();

        Cursor cursor = db.retrieve(Database.STICKER_TABLE, Database.STICKER_TITLE + " LIKE %" + keyword + "%", Database.STICKER_DEADLINE);
        int rowsNum = cursor.getCount();
        if(rowsNum > 0){
            cursor.moveToFirst();
            for(int i = 0; i < rowsNum; i++){
                Integer stickerID = cursor.getInt(0);
                Integer categoryID = cursor.getInt(1);
                String title = cursor.getString(2);
                String description = cursor.getString(3);
                Integer deadline = cursor.getInt(4);
                Integer remindTime = cursor.getInt(5);
                Boolean isFinished = (cursor.getInt(6) == 1)? true : false;
                Sticker sticker = new Sticker(stickerID, categoryID, title, description, deadline, remindTime, isFinished);
                result.add(sticker);
            }
        }

        return result;
    }

    public void setSticker(Sticker sticker){
        ContentValues cv = new ContentValues();
        cv.put(Database.STICKER_CATEGORY_ID, sticker.getCategoryID());
        cv.put(Database.STICKER_TITLE, sticker.getTitle());
        cv.put(Database.STICKER_DESCRIPTION, sticker.getDescription());
        cv.put(Database.STICKER_DEADLINE, sticker.getDeadline());
        cv.put(Database.STICKER_REMIND_TIME, sticker.getRemindTime());
        cv.put(Database.STICKER_IS_FINISHED, sticker.getFinished());
        if(sticker.getStickerID() == 0)
            db.create(Database.STICKER_TABLE, cv);
        else
            db.update(Database.STICKER_TABLE, Database.STICKER_ID + "=" + sticker.getStickerID().toString(), cv);
    }

    public void deleteSticker(Sticker sticker){
        db.delete(Database.STICKER_TABLE, sticker.getStickerID());
    }

    public List<Sticker> getEmergentList(){
        ArrayList<Sticker> result = new ArrayList<>();

        return result;
    }

    public List<Sticker> getLatestStickers(){
        ArrayList<Sticker> result = new ArrayList<>();

        return result;
    }

    public String calculateDate(){
        return null;
    }
}
