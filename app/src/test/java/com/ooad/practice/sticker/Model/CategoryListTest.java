package com.ooad.practice.sticker.Model;

import android.content.ContentValues;
import android.test.mock.MockContext;

import com.ooad.practice.sticker.Bean.Category;
import com.ooad.practice.sticker.Database.Database;
import com.ooad.practice.sticker.Database.DatabaseOpener;
import com.ooad.practice.sticker.Database.IDataAccessObject;
import com.ooad.practice.sticker.Database.StubCursor;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.lib.legacy.ClassImposteriser;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by fuyiru on 2017/5/21.
 */
public class CategoryListTest {
    Mockery context = new Mockery();
    Mockery context1 = new Mockery(){{
        setImposteriser(ClassImposteriser.INSTANCE);
    }};
    IDataAccessObject dao = context.mock(IDataAccessObject.class);
    DatabaseOpener dbOpener = context1.mock(DatabaseOpener.class);
    CategoryList categoryList;
    JSONArray fakeCategoryListData1;
    String fakeCategoryListData1_str = "[" +
            "{\""+ IDataAccessObject.CATEGORY_ID +"\": 1, \"" + IDataAccessObject.CATEGORY_TITLE + "\": \"1\", \"" + IDataAccessObject.CATEGORY_DESCRIPTION + "\": \"1\"}" +
            "{\""+ IDataAccessObject.CATEGORY_ID +"\": 2, \"" + IDataAccessObject.CATEGORY_TITLE + "\": \"2\", \"" + IDataAccessObject.CATEGORY_DESCRIPTION + "\": \"2\"}" +
            "{\""+ IDataAccessObject.CATEGORY_ID +"\": 3, \"" + IDataAccessObject.CATEGORY_TITLE + "\": \"3\", \"" + IDataAccessObject.CATEGORY_DESCRIPTION + "\": \"3\"}" +
            "]";
    JSONObject fakeCategoryData1;
    String fakeCategoryData1_str = "{\""+ IDataAccessObject.CATEGORY_ID +"\": 0, \"" + IDataAccessObject.CATEGORY_TITLE + "\": \"1\", \"" + IDataAccessObject.CATEGORY_DESCRIPTION + "\": \"1\"}";

    @Before
    public void setUp() throws Exception {
        categoryList = CategoryList.getInstance(dao);

        //fakeCategoryListData1
        fakeCategoryListData1 = new JSONArray(fakeCategoryListData1_str);

        //fakeCategoryData1
        fakeCategoryData1 = new JSONObject(fakeCategoryData1_str);
    }

    @Test
    public void getCategoryList() throws Exception {
        context.checking(new Expectations(){{
            oneOf(dao).retrieveAll();
            will(returnValue(fakeCategoryListData1));
        }});

        List<Category> result = categoryList.getCategoryList(null);
        context.assertIsSatisfied();
        int num = result.size();
        assertEquals(3, num);
        assertEquals(1, (int)result.get(0).getCategoryID());
    }

    @Test
    public void setCategory() throws Exception {
        final StubCursor cursor = new StubCursor(2);
        final ContentValues cv = new ContentValues();
        cursor.setInt(0);
        cv.put(Database.CATEGORY_TITLE, "1");
        cv.put(Database.CATEGORY_DESCRIPTION, "1");

        context.checking(new Expectations(){{
            oneOf(dao).retrieveAll();
            will(returnValue(fakeCategoryListData1));
        }});
        context.checking(new Expectations(){{
            oneOf(dao).create(fakeCategoryData1);
            will(returnValue(-1));
        }});

        categoryList.setCategory(new Category(null, "1", "1"));
        context.assertIsSatisfied();
    }

    @Test
    public void deleteCategory() throws Exception {

    }

}