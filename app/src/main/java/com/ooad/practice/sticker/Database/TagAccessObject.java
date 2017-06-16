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

public class TagAccessObject implements IDataAccessObject {

    private SQLiteDatabase db;
    private String tableName = IDataAccessObject.TAG_TABLE;
    private String tagID = TAG_ID;
    private String tagTitle = TAG_TITLE;
    private String tagColorR = TAG_COLOR_R;
    private String tagColorG = TAG_COLOR_G;
    private String tagColorB = TAG_COLOR_B;

    public TagAccessObject(Context context){
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
        String where = tagID + " = " + ID.toString();
        ContentValues cv = convertJSONObjectToContentValues(cols);
        db.update(tableName, cv, where, null);
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
        String orderBy = tagID + " " + ORDER_ASC;
        String where = tagID + " = " + ID.toString();
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
        String where = tagID + " = " + ID.toString();
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
                    jObj.put(tagID, cursor.getInt(0));
                    jObj.put(tagTitle, cursor.getString(1));
                    jObj.put(tagColorR, cursor.getInt(2));
                    jObj.put(tagColorG, cursor.getInt(3));
                    jObj.put(tagColorB, cursor.getInt(4));
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
                jObj.put(tagID, cursor.getInt(0));
                jObj.put(tagTitle, cursor.getString(1));
                jObj.put(tagColorR, cursor.getInt(2));
                jObj.put(tagColorG, cursor.getInt(3));
                jObj.put(tagColorB, cursor.getInt(4));
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
            if(cols.getInt(tagID) != 0)
                cv.put(tagID, cols.getInt(tagID));
            cv.put(tagTitle, cols.getString(tagTitle));
            cv.put(tagColorR, cols.getInt(tagColorR));
            cv.put(tagColorG, cols.getInt(tagColorG));
            cv.put(tagColorB, cols.getInt(tagColorB));
        }
        catch (JSONException e){
            Log.e(this.getClass().toString(), e.getMessage());
            return null;
        }
        return cv;
    }
}
