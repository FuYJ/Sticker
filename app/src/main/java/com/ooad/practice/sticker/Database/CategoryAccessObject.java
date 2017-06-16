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
 * Created by fuyiru on 2017/5/29.
 */

public class CategoryAccessObject implements IDataAccessObject {

    private SQLiteDatabase db;
    private String tableName = CATEGORY_TABLE;
    private String categoryID = CATEGORY_ID;
    private String categoryTitle = CATEGORY_TITLE;
    private String categoryDescription = CATEGORY_DESCRIPTION;

    public CategoryAccessObject(Context context){
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
        String where = categoryID + " = " + ID.toString();
        ContentValues cv = convertJSONObjectToContentValues(cols);
        db.update(tableName, cv, where, null);
    }

    @Override
    public JSONArray retrieveWhere(String where) {
        String orderBy = categoryID + " " + ORDER_ASC;
        Cursor cursor = db.query(tableName, null, where, null, null, null, orderBy);
        JSONArray jArr = putRowsIntoJSONArray(cursor);
        cursor.close();
        return jArr;
    }

    @Override
    public JSONArray retrieveAll() {
        String orderBy = categoryID + " " + ORDER_ASC;
        Cursor cursor = db.query(tableName, null, null, null, null, null, orderBy);
        JSONArray jArr = putRowsIntoJSONArray(cursor);
        cursor.close();
        return jArr;
    }

    @Override
    public JSONObject retrieveOne(Integer ID) {
        String orderBy = categoryID + " " + ORDER_ASC;
        String where = categoryID + " = " + ID.toString();
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
        String where = categoryID + " = " + ID.toString();
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
                    jObj.put(categoryID, cursor.getInt(0));
                    jObj.put(categoryTitle, cursor.getString(1));
                    jObj.put(categoryDescription, cursor.getString(2));
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
                jObj.put(categoryID, cursor.getInt(0));
                jObj.put(categoryTitle, cursor.getString(1));
                jObj.put(categoryDescription, cursor.getString(2));
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
            if(cols.getInt(categoryID) != 0)
                cv.put(categoryID, cols.getInt(categoryID));
            cv.put(categoryTitle, cols.getString(categoryTitle));
            cv.put(categoryDescription, cols.getString(categoryDescription));
        }
        catch (JSONException e){
            Log.e(this.getClass().toString(), e.getMessage());
            return null;
        }
        return cv;
    }
}
