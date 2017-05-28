package com.ooad.practice.sticker.Database;

import android.content.ContentValues;

/**
 * Created by fuyiru on 2017/5/28.
 */

public interface IDatabaseAccessObject {

    void close();

    void create(String tableName, ContentValues cols);

    void update(String tableName, String where, ContentValues cols);

    String retrieve(String tableName, String where, String orderBy);

    String retrieve(String tableName, String orderBy);

    String retrieve(String tableName, Integer ID);

    void delete(String tableName, String where);

    void delete(String tableName, Integer ID);
}
