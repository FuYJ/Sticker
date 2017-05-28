package com.ooad.practice.sticker.Model;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.CharArrayBuffer;
import android.database.ContentObserver;
import android.database.Cursor;
import android.database.DataSetObserver;
import android.net.Uri;
import android.os.Bundle;

import com.ooad.practice.sticker.Bean.Category;
import com.ooad.practice.sticker.Database.Database;
import com.ooad.practice.sticker.Database.DatabaseOpener;
import com.ooad.practice.sticker.Database.IDatabase;
import com.ooad.practice.sticker.Database.StubCursor;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by fuyiru on 2017/5/21.
 */
public class CategoryListTest {
    Mockery context = new Mockery();
    IDatabase database = context.mock(IDatabase.class);
    //DatabaseOpener databaseOpener = context.mock(DatabaseOpener.class);
    CategoryList categoryList;
    //List<Category> stubCategoryList = new ArrayList<>();

    @Before
    public void setUp() throws Exception {
        categoryList = CategoryList.getInstance(database);
        //Category category0 = new Category(0, "0", "0");
        //Category category1 = new Category(1, "1", "1");
        //stubCategoryList.add(category0);
        //stubCategoryList.add(category1);
    }

    @Test
    public void getCategoryList() throws Exception {
        context.checking(new Expectations(){{
            oneOf(database).retrieve(Database.CATEGORY_TABLE, Database.CATEGORY_ID + Database.ORDER_ASC);
            will(returnValue(new StubCursor(2)));
        }});

        List<Category> result = categoryList.getCategoryList(null);
        context.assertIsSatisfied();
        int num = result.size();
        assertEquals(2, num);
    }

    @Test
    public void setCategory() throws Exception {
        final StubCursor cursor = new StubCursor(2);
        final ContentValues cv = new ContentValues();
        cursor.setInt(0);
        cv.put(Database.CATEGORY_TITLE, "1");
        cv.put(Database.CATEGORY_DESCRIPTION, "1");

        context.checking(new Expectations(){{
            oneOf(database).retrieve(Database.CATEGORY_TABLE, Database.CATEGORY_ID + Database.ORDER_ASC);
            will(returnValue(cursor));
        }});
        context.checking(new Expectations(){{
            oneOf(database).create(Database.CATEGORY_TABLE, cv);
        }});

        categoryList.setCategory(new Category(null, "1", "1"));
        context.assertIsSatisfied();
    }

    @Test
    public void deleteCategory() throws Exception {

    }

}