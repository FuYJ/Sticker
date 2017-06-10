package com.ooad.practice.sticker.Database;

import android.content.ContentValues;
import android.content.Context;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by fuyiru on 2017/5/28.
 */

public interface IDataAccessObject {
    String STICKER_TABLE = "STICKER";
    String CATEGORY_TABLE = "CATEGORY";
    String TAG_TABLE = "TAG";
    String STICKER_TAGS_TABLE = "STICKER_TAGS";

    //Sticker
    String STICKER_ID = "_id";
    String STICKER_CATEGORY_ID = "categoryID";
    String STICKER_TITLE = "title";
    String STICKER_DESCRIPTION = "description";
    String STICKER_DEADLINE = "deadline";
    String STICKER_REMIND_TIME = "remindTime";
    String STICKER_IS_FINISHED = "isFinished";
    String CREATE_STICKER_TABLE =
            "CREATE TABLE " + STICKER_TABLE + " (" +
                    STICKER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    STICKER_CATEGORY_ID + " INTEGER NOT NULL, " +
                    STICKER_TITLE + " TEXT NOT NULL, " +
                    STICKER_DESCRIPTION + " TEXT, " +
                    STICKER_DEADLINE + " DATE, " +
                    STICKER_REMIND_TIME + " DATE, " +
                    STICKER_IS_FINISHED + " INTEGER NOT NULL)";

    //Category
    String CATEGORY_ID = "_id";
    String CATEGORY_TITLE = "title";
    String CATEGORY_DESCRIPTION = "description";
    String CREATE_CATEGORY_TABLE =
            "CREATE TABLE " + CATEGORY_TABLE + " (" +
                    CATEGORY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    CATEGORY_TITLE + " TEXT NOT NULL, " +
                    CATEGORY_DESCRIPTION + " TEXT)";

    //Tag
    String TAG_ID = "_id";
    String TAG_TITLE = "title";
    String TAG_COLOR_R = "colorR";
    String TAG_COLOR_G = "colorG";
    String TAG_COLOR_B = "colorB";
    String CREATE_TAG_TABLE =
            "CREATE TABLE " + TAG_TABLE + " (" +
                    TAG_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    TAG_TITLE + " TEXT, " +
                    TAG_COLOR_R + " INTEGER NOT NULL, " +
                    TAG_COLOR_G + " INTEGER NOT NULL, " +
                    TAG_COLOR_B + " INTEGER NOT NULL)";

    //Sticker_tag_association
    String STICKER_TAGS_TAG_ID = "tagID";
    String STICKER_TAGS_STICKER_ID = "stickerID";
    String CREATE_STICKER_TAGS_TABLE =
            "CREATE TABLE " + STICKER_TAGS_TABLE + " (" +
                    STICKER_TAGS_TAG_ID + " INTEGER NOT NULL, " +
                    STICKER_TAGS_STICKER_ID + " INTEGER NOT NULL)";

    String ORDER_ASC = " ASC";
    String ORDER_DESC = " DESC";

    enum OrderBy{
        ORDER_ASC,
        ORDER_DESC
    }

    enum SearchTarget{
        STICKER,
        CATEGORY,
        TAG
    }

    enum SearchIsFinished{
        UNFINISHED,
        FINISHED
    }

    void close();

    void create(JSONObject cols);

    void updateWhere(String where, JSONObject cols);

    void updateOne(Integer ID, JSONObject cols);

    JSONArray retrieveWhere(String where);

    JSONArray retrieveAll();

    JSONObject retrieveOne(Integer ID);

    void deleteWhere(String where);

    void deleteOne(Integer ID);
}
