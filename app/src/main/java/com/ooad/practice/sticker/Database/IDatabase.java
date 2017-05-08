package com.ooad.practice.sticker.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

/**
 * Created by fuyiru on 2017/5/7.
 */

public interface IDatabase {

    void close();

    void create(String tableName, ContentValues cols);

    void update(String tableName, String where, ContentValues cols);

    Cursor retrieve(String tableName, String where, String orderBy);

    Cursor retrieve(String tableName, String orderBy);

    Cursor retrieve(String tableName, Integer ID);

    void delete(String tableName, String where);

    void delete(String tableName, Integer ID);
}
