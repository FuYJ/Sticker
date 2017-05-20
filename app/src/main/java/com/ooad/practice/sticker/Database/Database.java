package com.ooad.practice.sticker.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by fuyiru on 2017/5/5.
 */

public class Database implements IDatabase {
    public static final String STICKER_TABLE = "STICKER";
    public static final String CATEGORY_TABLE = "CATEGORY";
    public static final String TAG_TABLE = "TAG";
    public static final String STICKER_TAGS_TABLE = "STICKER_TAGS";

    //Sticker
    public static final String STICKER_ID = "_id";
    public static final String STICKER_CATEGORY_ID = "categoryID";
    public static final String STICKER_TITLE = "title";
    public static final String STICKER_DESCRIPTION = "description";
    public static final String STICKER_DEADLINE = "deadline";
    public static final String STICKER_REMIND_TIME = "remindTime";
    public static final String STICKER_IS_FINISHED = "isFinished";
    public static final String CREATE_STICKER_TABLE =
            "CREATE TABLE " + STICKER_TABLE + " (" +
                    STICKER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    STICKER_CATEGORY_ID + " INTEGER NOT NULL, " +
                    STICKER_TITLE + " TEXT NOT NULL, " +
                    STICKER_DESCRIPTION + " TEXT, " +
                    STICKER_DEADLINE + " DATE, " +
                    STICKER_REMIND_TIME + " DATE, " +
                    STICKER_IS_FINISHED + " INTEGER NOT NULL)";

    //Category
    public static final String CATEGORY_ID = "_id";
    public static final String CATEGORY_TITLE = "title";
    public static final String CATEGORY_DESCRIPTION = "description";
    public static final String CREATE_CATEGORY_TABLE =
            "CREATE TABLE " + CATEGORY_TABLE + " (" +
                    CATEGORY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    CATEGORY_TITLE + " TEXT NOT NULL, " +
                    CATEGORY_DESCRIPTION + " TEXT)";

    //Tag
    public static final String TAG_ID = "_id";
    public static final String TAG_TITLE = "title";
    public static final String TAG_COLOR_R = "colorR";
    public static final String TAG_COLOR_G = "colorG";
    public static final String TAG_COLOR_B = "colorB";
    public static final String CREATE_TAG_TABLE =
            "CREATE TABLE " + TAG_TABLE + " (" +
                    TAG_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    TAG_TITLE + " TEXT, " +
                    TAG_COLOR_R + " INTEGER NOT NULL, " +
                    TAG_COLOR_G + " INTEGER NOT NULL, " +
                    TAG_COLOR_B + " INTEGER NOT NULL)";

    //Sticker_tag_association
    public static final String STICKER_TAGS_TAG_ID = "tagID";
    public static final String STICKER_TAGS_STICKER_ID = "stickerID";
    public static final String CREATE_STICKER_TAGS_TABLE =
            "CREATE TABLE " + STICKER_TAGS_TABLE + " (" +
                    STICKER_TAGS_TAG_ID + " INTEGER NOT NULL, " +
                    STICKER_TAGS_STICKER_ID + " INTEGER NOT NULL)";

    public static final String ORDER_ASC = " ASC";
    public static final String ORDER_DESC = " DESC";

    public enum OrderBy{
        ORDER_ASC,
        ORDER_DESC
    }

    public enum SearchTarget{
        STICKER,
        CATEGORY,
        TAG
    }

    public enum SearchIsFinished{
        UNFINISHED,
        FINISHED
    }

    private SQLiteDatabase db;

    public Database(Context context){
        db = DatabaseOpener.getDatabase(context);
    }

    public void close(){
        db.close();
    }

    public void create(String tableName, ContentValues cols){
        db.insert(tableName, null, cols);
    }

    public void update(String tableName, String where, ContentValues cols){
        db.update(tableName, cols, where, null);
    }

    public Cursor retrieve(String tableName, String where, String orderBy){
        Cursor result = db.query(tableName, null, where, null, null, null, orderBy);
        return result;
    }

    public Cursor retrieve(String tableName, String orderBy){
        Cursor result = db.query(tableName, null, null, null, null, null, orderBy);
        return result;
    }

    public Cursor retrieve(String tableName, Integer ID){
        String where = "_id = " + ID.toString();
        Cursor result = db.query(tableName, null, where, null, null, null, null);
        return result;
    }

    public void delete(String tableName, String where){
        db.delete(tableName, where, null);
    }

    public void delete(String tableName, Integer ID){
        String where = "_id = " + ID.toString();
        db.delete(tableName, where, null);
    }
}
