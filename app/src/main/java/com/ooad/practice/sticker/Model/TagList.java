package com.ooad.practice.sticker.Model;

import android.content.ContentValues;
import android.database.Cursor;

import com.ooad.practice.sticker.Bean.Tag;
import com.ooad.practice.sticker.Database.Database;
import com.ooad.practice.sticker.Database.IDatabase;
import com.ooad.practice.sticker.MainApplication;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fuyiru on 2017/5/4.
 */

public class TagList {
    private static TagList instance;
    private IDatabase db;

    private TagList() {
        db = new Database(MainApplication.getContext());
    }

    public static TagList getInstance(){
        if(instance == null){
            instance = new TagList();
        }
        return instance;
    }

    public List<Tag> getTagList(){
        List<Tag> result = new ArrayList<>();
        Cursor cursor = db.retrieve(Database.TAG_TABLE, Database.TAG_ID + Database.ORDER_ASC);
        int rowsNum = cursor.getCount();
        if(rowsNum > 0){
            cursor.moveToFirst();
            for(int i = 0; i < rowsNum; i++){
                Integer tagID = cursor.getInt(0);
                String tagTitle = cursor.getString(1);
                Integer tagColorR = cursor.getInt(2);
                Integer tagColorG = cursor.getInt(3);
                Integer tagColorB = cursor.getInt(4);
                Tag tag = new Tag(tagID, tagTitle, tagColorR, tagColorG, tagColorB);
                result.add(tag);
                cursor.moveToNext();
            }
            cursor.close();
        }
        return result;
    }

    public void setTag(Tag tag){
        ContentValues cv = new ContentValues();
        cv.put(Database.TAG_ID, tag.getTagID());
        cv.put(Database.TAG_TITLE, tag.getTitle());
        cv.put(Database.TAG_COLOR_R, tag.getColor().get(0));
        cv.put(Database.TAG_COLOR_G, tag.getColor().get(1));
        cv.put(Database.TAG_COLOR_B, tag.getColor().get(2));
        if(tag.getTagID() == 0)
            db.create(Database.TAG_TABLE, cv);
        else
            db.update(Database.TAG_TABLE, tag.getTagID().toString() + " = " + Database.TAG_ID, cv);
    }

    public void deleteTag(Tag tag){
        db.delete(Database.TAG_TABLE, tag.getTagID());
    }
}