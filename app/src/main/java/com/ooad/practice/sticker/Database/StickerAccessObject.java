package com.ooad.practice.sticker.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by fuyiru on 2017/6/8.
 */

public class StickerAccessObject implements IDataAccessObject {

    private SQLiteDatabase db;
    private String tableName = IDataAccessObject.STICKER_TABLE;
    private String stickerID = STICKER_ID;
    private String categoryID = STICKER_CATEGORY_ID;
    private String stickerTitle = STICKER_TITLE;
    private String stickerDescription = STICKER_DESCRIPTION;
    private String stickerDeadline = STICKER_DEADLINE;
    private String stickerRemindTime = STICKER_REMIND_TIME;
    private String stickerIsFinished = STICKER_IS_FINISHED;

    public StickerAccessObject(Context context){
        db = DatabaseOpener.getDatabase(context);
    }

    @Override
    public void close() {
        db.close();
    }

    @Override
    public Integer create(JSONObject cols) {
        ContentValues cv = convertJSONObjectToContentValues(cols);
        Long id = db.insert(tableName, null, cv);
        return id.intValue();
    }

    @Override
    public void updateWhere(String where, JSONObject cols) {
        ContentValues cv = convertJSONObjectToContentValues(cols);
        db.update(tableName, cv, where, null);
    }

    @Override
    public void updateOne(Integer ID, JSONObject cols) {
        String where = stickerID + " = " + ID.toString();
        ContentValues cv = convertJSONObjectToContentValues(cols);
        db.update(tableName, cv, where, null);
    }

    @Override
    public JSONArray retrieveWhere(String where) {
        String orderBy = stickerID + " " + ORDER_ASC;
        String nestQuery1 = "SELECT DISTINCT * FROM " + STICKER_TAGS_TABLE + ", " + TAG_TABLE
                + " WHERE " + STICKER_TAGS_TABLE + "." + STICKER_TAGS_TAG_ID + " = " + TAG_TABLE + "." + TAG_ID;
        String rawQuery = "SELECT DISTINCT * FROM " + STICKER_TABLE
                + " INNER JOIN " + CATEGORY_TABLE + " ON " + STICKER_TABLE + "." + STICKER_CATEGORY_ID + " = " + CATEGORY_TABLE + "." + CATEGORY_ID
                + " LEFT OUTER JOIN (" + nestQuery1 + ") AS " + NEST_TABLE1 + " ON " + STICKER_TABLE + "." + STICKER_ID + " = " + NEST_TABLE1 + "." + STICKER_TAGS_STICKER_ID
                + " WHERE " + where
                + " ORDER BY " + orderBy;
        Cursor cursor = db.rawQuery(rawQuery, null);
        //Cursor cursor = db.query(tableName, null, where, null, null, null, orderBy);
        JSONArray jArr = putRowsIntoJSONArray(cursor);
        cursor.close();
        return jArr;
    }

    @Override
    public JSONArray retrieveAll() {
        String orderBy = stickerID + " " + ORDER_ASC;
        Cursor cursor = db.query(tableName, null, null, null, null, null, orderBy);
        JSONArray jArr = putRowsIntoJSONArray(cursor);
        cursor.close();
        return jArr;
    }

    @Override
    public JSONObject retrieveOne(Integer ID) {
        String orderBy = stickerID + " " + ORDER_ASC;
        String where = stickerID + " = " + ID.toString();
        Cursor cursor = db.query(tableName, null, where, null, null, null, orderBy);
        JSONObject jObj = putRowIntoJSONObject(cursor);
        cursor.close();
        return jObj;
    }

    @Override
    public void deleteWhere(String where) {
        db.delete(tableName, where, null);
    }

    @Override
    public void deleteOne(Integer ID) {
        String where = stickerID + " = " + ID.toString();
        db.delete(tableName, where, null);
    }

    private JSONArray putRowsIntoJSONArray(Cursor cursor){
        JSONArray jArr = new JSONArray();
        int rowsNum = cursor.getCount();
        if(rowsNum > 0){
            cursor.moveToFirst();
            try{
                for(int i = 0; i < rowsNum; i++){
                    JSONObject jObj = new JSONObject();
                    jObj.put(stickerID, cursor.getInt(0));
                    jObj.put(categoryID, cursor.getInt(1));
                    jObj.put(stickerTitle, cursor.getString(2));
                    jObj.put(stickerDescription, cursor.getString(3));
                    jObj.put(stickerDeadline, cursor.getLong(4));
                    jObj.put(stickerRemindTime, cursor.getLong(5));
                    jObj.put(stickerIsFinished, (cursor.getInt(6) == 1)? true : false);
                    jArr.put(jObj);
                    cursor.moveToNext();
                }
            }
            catch (JSONException e){
                Log.e(this.getClass().toString(), e.getMessage());
                return null;
            }
        }
        return jArr;
    }

    private JSONObject putRowIntoJSONObject(Cursor cursor){
        JSONObject jObj = new JSONObject();
        int rowsNum = cursor.getCount();
        if(rowsNum > 0){
            cursor.moveToFirst();
            try{
                jObj.put(stickerID, cursor.getInt(0));
                jObj.put(categoryID, cursor.getInt(1));
                jObj.put(stickerTitle, cursor.getString(2));
                jObj.put(stickerDescription, cursor.getString(3));
                jObj.put(stickerDeadline, cursor.getLong(4));
                jObj.put(stickerRemindTime, cursor.getLong(5));
                jObj.put(stickerIsFinished, (cursor.getInt(6) == 1)? true : false);
            }
            catch (JSONException e){
                Log.e(this.getClass().toString(), e.getMessage());
                return null;
            }
        }
        return jObj;
    }

    private ContentValues convertJSONObjectToContentValues(JSONObject cols){
        ContentValues cv = new ContentValues();
        try {
            if(cols.getInt(stickerID) != 0)
                cv.put(stickerID, cols.getInt(stickerID));
            cv.put(categoryID, cols.getInt(categoryID));
            cv.put(stickerTitle, cols.getString(stickerTitle));
            cv.put(stickerDescription, cols.getString(stickerDescription));
            cv.put(stickerDeadline, cols.getLong(stickerDeadline));
            cv.put(stickerRemindTime, cols.getLong(stickerRemindTime));
            cv.put(stickerIsFinished, cols.getBoolean(stickerIsFinished));
        }
        catch (JSONException e){
            Log.e(this.getClass().toString(), e.getMessage());
            return null;
        }
        return cv;
    }
}
