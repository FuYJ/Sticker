package com.ooad.practice.sticker.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.test.RenamingDelegatingContext;
import android.test.mock.MockContext;
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
        //MockContext context = new MockContext();
        //Context context = InstrumentationRegistry.getInstrumentation().getContext();
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

        cv.put(Database.CATEGORY_ID, 0);
        cv.put(Database.CATEGORY_TITLE, title);
        cv.put(Database.CATEGORY_DESCRIPTION, description);
        db.create(Database.CATEGORY_TABLE, cv);

        Cursor cursor = db.retrieve(Database.CATEGORY_TABLE, 0);
        int rowsNum = cursor.getCount();
        assertEquals(1, rowsNum);
        if(rowsNum > 0){
            cursor.moveToFirst();
            for(int i = 0; i < rowsNum; i++){
                Integer categoryID = cursor.getInt(0);
                title = cursor.getString(1);
                description = cursor.getString(2);
                assertEquals(0, categoryID.longValue());
                assertEquals("Test", title);
                assertEquals("Test", description);
            }
        }

        db.delete(Database.CATEGORY_TABLE, 0);
    }

    @Test
    public void update() throws Exception {
        String title = "Test";
        String description = "Test";
        ContentValues cv = new ContentValues();

        cv.put(Database.CATEGORY_ID, 0);
        cv.put(Database.CATEGORY_TITLE, title);
        cv.put(Database.CATEGORY_DESCRIPTION, description);
        db.create(Database.CATEGORY_TABLE, cv);

        title = "new_test";
        description = "new_test";
        ContentValues new_cv = new ContentValues();
        new_cv.put(Database.CATEGORY_ID, 0);
        new_cv.put(Database.CATEGORY_TITLE, title);
        new_cv.put(Database.CATEGORY_DESCRIPTION, description);
        db.update(Database.CATEGORY_TABLE, Database.CATEGORY_TITLE + " = \"Test\"", new_cv);

        Cursor cursor = db.retrieve(Database.CATEGORY_TABLE, 0);
        int rowsNum = cursor.getCount();
        assertEquals(1, rowsNum);
        if(rowsNum > 0){
            cursor.moveToFirst();
            for(int i = 0; i < rowsNum; i++){
                Integer categoryID = cursor.getInt(0);
                title = cursor.getString(1);
                description = cursor.getString(2);
                assertEquals(0, categoryID.longValue());
                assertEquals("new_test", title);
                assertEquals("new_test", description);
            }
        }
    }

    @Test
    public void retrieve() throws Exception {
        String title = "Test";
        String description = "Test";
        ContentValues cv = new ContentValues();

        cv.put(Database.CATEGORY_ID, 0);
        cv.put(Database.CATEGORY_TITLE, title);
        cv.put(Database.CATEGORY_DESCRIPTION, description);
        db.create(Database.CATEGORY_TABLE, cv);

        Cursor cursor = db.retrieve(Database.CATEGORY_TABLE, 0);
        int rowsNum = cursor.getCount();
        assertEquals(1, rowsNum);
        if(rowsNum > 0){
            cursor.moveToFirst();
            for(int i = 0; i < rowsNum; i++){
                Integer categoryID = cursor.getInt(0);
                title = cursor.getString(1);
                description = cursor.getString(2);
                assertEquals(0, categoryID.longValue());
                assertEquals("Test", title);
                assertEquals("Test", description);

            }
        }

        db.delete(Database.CATEGORY_TABLE, 0);
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