package com.ooad.practice.sticker.Database;

import android.content.ContentValues;
import android.database.Cursor;

/**
 * Created by fuyiru on 2017/5/7.
 */

public class MockDatabase implements IDatabase {

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
    public Cursor retrieve(String tableName, String where, String orderBy) {
        return null;
    }

    @Override
    public Cursor retrieve(String tableName, Integer ID) {
        return null;
    }

    @Override
    public void delete(String tableName, String where) {

    }

    @Override
    public void delete(String tableName, Integer ID) {

    }
}
