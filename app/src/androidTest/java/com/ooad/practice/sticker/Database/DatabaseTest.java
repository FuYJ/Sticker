package com.ooad.practice.sticker.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.support.test.runner.AndroidJUnit4;
import android.test.RenamingDelegatingContext;
import android.test.suitebuilder.annotation.LargeTest;

import com.ooad.practice.sticker.MainApplication;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

/**
 * Created by fuyiru on 2017/5/7.
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class DatabaseTest {
    IDatabase db;

    @Before
    public void setUp() throws Exception {
        Context context = new RenamingDelegatingContext(MainApplication.getContext(), "test_");
        assertNotNull(context);
        db = new Database(context);
    }

    @After
    public void tearDown() throws Exception {
        db.close();
    }

    @Test
    public void create() throws Exception {
        String title = "Test";
        String description = "Test";
        ContentValues cv = new ContentValues();

        cv.put(Database.CATEGORY_TITLE, title);
        cv.put(Database.CATEGORY_DESCRIPTION, description);
        db.create(Database.CATEGORY_TABLE, cv);

        Cursor cursor = db.retrieve(Database.CATEGORY_TABLE, 1);
        int rowsNum = cursor.getCount();
        assertEquals(1, rowsNum);
        if(rowsNum > 0){
            cursor.moveToFirst();
            for(int i = 0; i < rowsNum; i++){
                Integer categoryID = cursor.getInt(0);
                title = cursor.getString(1);
                description = cursor.getString(2);
                assertEquals(1, categoryID.longValue());
                assertEquals("Test", title);
                assertEquals("Test", description);
            }
        }
        cursor.close();

        db.delete(Database.CATEGORY_TABLE, 0);
    }

    @Test
    public void update() throws Exception {
        String title = "Test";
        String description = "Test";
        ContentValues cv = new ContentValues();

        cv.put(Database.CATEGORY_TITLE, title);
        cv.put(Database.CATEGORY_DESCRIPTION, description);
        db.create(Database.CATEGORY_TABLE, cv);

        title = "new_test";
        description = "new_test";
        ContentValues new_cv = new ContentValues();
        new_cv.put(Database.CATEGORY_TITLE, title);
        new_cv.put(Database.CATEGORY_DESCRIPTION, description);
        db.update(Database.CATEGORY_TABLE, Database.CATEGORY_TITLE + " = \"Test\"", new_cv);

        Cursor cursor = db.retrieve(Database.CATEGORY_TABLE, 1);
        int rowsNum = cursor.getCount();
        assertEquals(1, rowsNum);
        if(rowsNum > 0){
            cursor.moveToFirst();
            for(int i = 0; i < rowsNum; i++){
                //Integer categoryID = cursor.getInt(0);
                title = cursor.getString(1);
                description = cursor.getString(2);
                //assertEquals(1, categoryID.longValue());
                assertEquals("new_test", title);
                assertEquals("new_test", description);
            }
        }
        cursor.close();

        db.delete(Database.CATEGORY_TABLE, 0);
    }

    @Test
    public void retrieve() throws Exception {
        String title = "Test";
        String description = "Test";
        ContentValues cv = new ContentValues();

        cv.put(Database.CATEGORY_TITLE, title);
        cv.put(Database.CATEGORY_DESCRIPTION, description);
        db.create(Database.CATEGORY_TABLE, cv);

        Cursor cursor = db.retrieve(Database.CATEGORY_TABLE, 1);
        int rowsNum = cursor.getCount();
        assertEquals(1, rowsNum);
        if(rowsNum > 0){
            cursor.moveToFirst();
            for(int i = 0; i < rowsNum; i++){
                Integer categoryID = cursor.getInt(0);
                title = cursor.getString(1);
                description = cursor.getString(2);
                assertEquals(1, categoryID.longValue());
                assertEquals("Test", title);
                assertEquals("Test", description);

            }
        }
        cursor.close();

        db.delete(Database.CATEGORY_TABLE, 0);
    }

    @Test
    public void retrieve1() throws Exception {
        String title = "Test";
        String description = "Test";
        ContentValues cv = new ContentValues();

        for(int i = 0; i < 10; i++) {
            cv.clear();
            cv.put(Database.CATEGORY_TITLE, title + String.valueOf(i));
            cv.put(Database.CATEGORY_DESCRIPTION, description);
            db.create(Database.CATEGORY_TABLE, cv);
        }

        Cursor cursor = db.retrieve(Database.CATEGORY_TABLE, Database.CATEGORY_TITLE + " LIKE \"%Test%\"", Database.CATEGORY_ID + Database.ORDER_ASC);
        int rowsNum = cursor.getCount();
        assertEquals(10, rowsNum);
        if(rowsNum > 0){
            cursor.moveToFirst();
            for(int i = 0; i < rowsNum; i++){
                Integer categoryID = cursor.getInt(0);
                title = cursor.getString(1);
                description = cursor.getString(2);
                assertEquals(i + 1, categoryID.longValue());
                assertEquals("Test" + String.valueOf(i), title);
                assertEquals("Test", description);
                cursor.moveToNext();
            }
        }

        cursor = db.retrieve(Database.CATEGORY_TABLE, Database.CATEGORY_TITLE + " = \"Test0\"", Database.CATEGORY_ID + Database.ORDER_ASC);
        rowsNum = cursor.getCount();
        assertEquals(1, rowsNum);
        if(rowsNum > 0){
            cursor.moveToFirst();
            Integer categoryID = cursor.getInt(0);
            title = cursor.getString(1);
            description = cursor.getString(2);
            assertEquals(1, categoryID.longValue());
            assertEquals("Test0", title);
            assertEquals("Test", description);
            cursor.moveToNext();
        }

        cursor.close();

        db.delete(Database.CATEGORY_TABLE, Database.CATEGORY_TITLE + " LIKE \"%Test%\"");
    }

    @Test
    public void delete() throws Exception {
        String title = "Test";
        String description = "Test";
        ContentValues cv = new ContentValues();

        cv.clear();
        cv.put(Database.CATEGORY_TITLE, title);
        cv.put(Database.CATEGORY_DESCRIPTION, description);
        db.create(Database.CATEGORY_TABLE, cv);

        Cursor cursor = db.retrieve(Database.CATEGORY_TABLE, 1);
        int rowsNum = cursor.getCount();
        assertEquals(1, rowsNum);
        cursor.close();

        db.delete(Database.CATEGORY_TABLE, 1);

        cursor = db.retrieve(Database.CATEGORY_TABLE, 1);
        rowsNum = cursor.getCount();
        assertEquals(0, rowsNum);
        cursor.close();
    }

    @Test
    public void delete1() throws Exception {
        String title = "Test";
        String description = "Test";
        ContentValues cv = new ContentValues();

        for(int i = 0; i < 10; i++) {
            cv.clear();
            cv.put(Database.CATEGORY_TITLE, title + String.valueOf(i));
            cv.put(Database.CATEGORY_DESCRIPTION, description);
            db.create(Database.CATEGORY_TABLE, cv);
        }

        Cursor cursor = db.retrieve(Database.CATEGORY_TABLE, Database.CATEGORY_TITLE + " LIKE \"%Test%\"", Database.CATEGORY_ID + Database.ORDER_ASC);
        int rowsNum = cursor.getCount();
        assertEquals(10, rowsNum);
        cursor.close();

        db.delete(Database.CATEGORY_TABLE, Database.CATEGORY_TITLE + " LIKE \"%Test%\"");

        cursor = db.retrieve(Database.CATEGORY_TABLE, Database.CATEGORY_TITLE + " LIKE \"%Test%\"", Database.CATEGORY_ID + Database.ORDER_ASC);
        rowsNum = cursor.getCount();
        assertEquals(0, rowsNum);
        cursor.close();
    }
}