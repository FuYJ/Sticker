package com.ooad.practice.sticker.Bean;

import com.ooad.practice.sticker.Database.IDataAccessObject;

import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by fuyiru on 2017/6/17.
 */
public class testCategory {
    private Category category1;
    private Category category2;
    private Integer categoryID = 0;
    private String categoryTitle = "Test1";
    private String categoryDescription = "Test1";
    private JSONObject jObj1;

    @Before
    public void setUp() throws Exception {
        category1 = new Category(categoryID, categoryTitle, categoryDescription);
        jObj1 = new JSONObject();
        jObj1.put(IDataAccessObject.CATEGORY_ID, categoryID);
        jObj1.put(IDataAccessObject.CATEGORY_TITLE, categoryTitle);
        jObj1.put(IDataAccessObject.CATEGORY_DESCRIPTION, categoryDescription);
        category2 = new Category(jObj1);
        assertEquals(category1.getCategoryID(), category2.getCategoryID());
    }

    @Test
    public void toJSONObject() throws Exception {
        JSONObject jObj2 = category1.toJSONObject();
        JSONObject expected = new JSONObject();
        expected.put(IDataAccessObject.CATEGORY_ID, category1.getCategoryID());
        expected.put(IDataAccessObject.CATEGORY_TITLE, category1.getTitle());
        expected.put(IDataAccessObject.CATEGORY_DESCRIPTION, category1.getDescription());
        assertEquals(expected.getInt(IDataAccessObject.CATEGORY_ID), jObj2.getInt(IDataAccessObject.CATEGORY_ID));
        assertEquals(expected.getString(IDataAccessObject.CATEGORY_TITLE), jObj2.getString(IDataAccessObject.CATEGORY_TITLE));
        assertEquals(expected.getString(IDataAccessObject.CATEGORY_DESCRIPTION), jObj2.getString(IDataAccessObject.CATEGORY_DESCRIPTION));
    }
}