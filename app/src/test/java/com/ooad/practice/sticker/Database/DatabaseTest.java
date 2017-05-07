package com.ooad.practice.sticker.Database;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.test.AndroidTestCase;
import android.test.RenamingDelegatingContext;
import android.test.mock.MockContext;

import com.ooad.practice.sticker.MainApplication;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

/**
 * Created by fuyiru on 2017/5/6.
 */
public class DatabaseTest{
    Database db;

    @Before
    public void setUp() throws Exception {
        MockContext context = new MockContext();
        //Context context = MainApplication.getContext();
        assertNotNull(context);
        db = new Database(context);
    }

    @Test
    public void tearDown() throws Exception {
        db.close();
    }

    @Test
    public void create() throws Exception {
        ContentValues cv = new ContentValues();
        cv.put(Database.CATEGORY_TITLE, "Test");
        cv.put(Database.CATEGORY_DESCRIPTION, "Test");
        db.create(Database.CATEGORY_TABLE, cv);

        Cursor cursor = db.retrieve(Database.CATEGORY_TABLE, 0);
        int rowsNum = cursor.getCount();
        assertEquals(1, rowsNum);
        if(rowsNum > 0){
            cursor.moveToFirst();
            for(int i = 0; i < rowsNum; i++){
                Integer categoryID = cursor.getInt(0);
                String title = cursor.getString(1);
                String description = cursor.getString(2);
                assertEquals(0, categoryID.longValue());
                assertEquals("Test", title);
                assertEquals("Test", description);

            }
        }
    }

    @Test
    public void update() throws Exception {

    }

    @Test
    public void retrieve() throws Exception {
        db.retrieve(Database.CATEGORY_TABLE, 0);
    }

    @Test
    public void retrieve1() throws Exception {

    }

    @Test
    public void delete() throws Exception {

    }

    @Test
    public void delete1() throws Exception {

    }

}