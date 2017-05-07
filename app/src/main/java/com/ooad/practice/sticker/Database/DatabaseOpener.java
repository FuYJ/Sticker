package com.ooad.practice.sticker.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by fuyiru on 2017/5/5.
 */

public class DatabaseOpener extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "Sticker.db";
    public static final int DB_VERSION = 1;
    private static SQLiteDatabase database;

    public DatabaseOpener(Context context, String name, SQLiteDatabase.CursorFactory factory,
                          int version) {
        super(context, name, factory, version);
    }

    public static SQLiteDatabase getDatabase(Context context) {
        if (database == null || !database.isOpen()) {
            database = new DatabaseOpener(context, DATABASE_NAME,
                    null, DB_VERSION).getWritableDatabase();
        }

        return database;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(Database.CREATE_STICKER_TABLE);
        db.execSQL(Database.CREATE_CATEGORY_TABLE);
        db.execSQL(Database.CREATE_TAG_TABLE);
        db.execSQL(Database.CREATE_STICKER_TAGS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + Database.CREATE_STICKER_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + Database.CREATE_CATEGORY_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + Database.CREATE_TAG_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + Database.CREATE_STICKER_TAGS_TABLE);

        onCreate(db);
    }
}
