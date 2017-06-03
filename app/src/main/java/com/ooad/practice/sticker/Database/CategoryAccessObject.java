package com.ooad.practice.sticker.Database;

import android.content.ContentValues;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by fuyiru on 2017/5/29.
 */

public class CategoryAccessObject implements IDataAccessObject {
    @Override
    public void close() {

    }

    @Override
    public void create(String tableName, ContentValues cols) {

    }

    @Override
    public void update(String tableName, String where, ContentValues cols) {

    }

    @Override
    public JSONArray retrieve(String tableName, String where) {
        return null;
    }

    @Override
    public JSONArray retrieve(String tableName) {
        return null;
    }

    @Override
    public JSONObject retrieve(String tableName, Integer ID) {
        return null;
    }

    @Override
    public void delete(String tableName, String where) {

    }

    @Override
    public void delete(String tableName, Integer ID) {

    }
}
