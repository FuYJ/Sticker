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

public class StickerTagsAccessObject implements IDataAccessObject {

    private SQLiteDatabase db;
    private String tableName = IDataAccessObject.STICKER_TAGS_TABLE;
    private String tagID = STICKER_TAGS_TAG_ID;
    private String stickerID = STICKER_TAGS_STICKER_ID;

    public StickerTagsAccessObject(Context context){
        db = DatabaseOpener.getDatabase(context);
    }

    @Override
    public void close() {
        db.close();
    }

    @Override
    public void create(JSONObject cols) {
        ContentValues cv = convertJSONObjectToContentValues(cols);
        db.insert(tableName, null, cv);
    }

    @Override
    public void updateWhere(String where, JSONObject cols) {
        ContentValues cv = convertJSONObjectToContentValues(cols);
        db.update(tableName, cv, where, null);
    }

    @Override
    public void updateOne(Integer ID, JSONObject cols) {
        Log.w(this.getClass().toString(), "Cannot update stickerTags table by single ID");
    }

    @Override
    public JSONArray retrieveWhere(String where) {
        String orderBy = tagID + " " + ORDER_ASC;
        Cursor cursor = db.query(tableName, null, where, null, null, null, orderBy);
        JSONArray jArr = putRowsIntoJSONArray(cursor);
        cursor.close();
        return jArr;
    }

    @Override
    public JSONArray retrieveAll() {
        String orderBy = tagID + " " + ORDER_ASC;
        Cursor cursor = db.query(tableName, null, null, null, null, null, orderBy);
        JSONArray jArr = putRowsIntoJSONArray(cursor);
        cursor.close();
        return jArr;
    }

    @Override
    public JSONObject retrieveOne(Integer ID) {
        Log.w(this.getClass().toString(), "Cannot retrieve stickerTags table by single ID");
        return null;
    }

    @Override
    public void deleteWhere(String where) {
        db.delete(tableName, where, null);
    }

    @Override
    public void deleteOne(Integer ID) {
        Log.w(this.getClass().toString(), "Cannot delete stickerTags table by single ID");
    }

    private JSONArray putRowsIntoJSONArray(Cursor cursor){
        JSONArray jArr = new JSONArray();
        int rowsNum = cursor.getCount();
        if(rowsNum > 0){
            cursor.moveToFirst();
            try{
                for(int i = 0; i < rowsNum; i++){
                    JSONObject jObj = new JSONObject();
                    jObj.put(tagID, cursor.getInt(0));
                    jObj.put(stickerID, cursor.getInt(1));
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

    private ContentValues convertJSONObjectToContentValues(JSONObject cols){
        ContentValues cv = new ContentValues();
        try {
            cv.put(tagID, cols.getInt(tagID));
            cv.put(stickerID, cols.getInt(stickerID));
        }
        catch (JSONException e){
            Log.e(this.getClass().toString(), e.getMessage());
            return null;
        }
        return cv;
    }
}
