package com.ooad.practice.sticker.Model;

import com.ooad.practice.sticker.Bean.Category;
import com.ooad.practice.sticker.Database.IDataAccessObject;

import org.jmock.Expectations;
import org.jmock.Mockery;
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
    Mockery context1 = new Mockery();
    IDataAccessObject categoryDAO = context1.mock(IDataAccessObject.class);
    Mockery context2 = new Mockery();
    IDataAccessObject stickerDAO = context2.mock(IDataAccessObject.class);
    CategoryList categoryList;
    JSONArray fakeCategoryListData1;
    String fakeCategoryListData1_str = "[" +
            "{\""+ IDataAccessObject.CATEGORY_ID +"\": 1, \"" + IDataAccessObject.CATEGORY_TITLE + "\": \"1\", \"" + IDataAccessObject.CATEGORY_DESCRIPTION + "\": \"1\"}," +
            "{\""+ IDataAccessObject.CATEGORY_ID +"\": 2, \"" + IDataAccessObject.CATEGORY_TITLE + "\": \"2\", \"" + IDataAccessObject.CATEGORY_DESCRIPTION + "\": \"2\"}," +
            "{\""+ IDataAccessObject.CATEGORY_ID +"\": 3, \"" + IDataAccessObject.CATEGORY_TITLE + "\": \"3\", \"" + IDataAccessObject.CATEGORY_DESCRIPTION + "\": \"3\"}" +
            "]";
    JSONArray fakeCategoryListData2;
    String fakeCategoryListData2_str = "[" +
            "{\""+ IDataAccessObject.CATEGORY_ID +"\": 1, \"" + IDataAccessObject.CATEGORY_TITLE + "\": \"1\", \"" + IDataAccessObject.CATEGORY_DESCRIPTION + "\": \"1\"}" +
            "]";
    JSONObject fakeCategoryData1;
    String fakeCategoryData1_str = "{\""+ IDataAccessObject.CATEGORY_ID +"\": 0, \"" + IDataAccessObject.CATEGORY_TITLE + "\": \"1\", \"" + IDataAccessObject.CATEGORY_DESCRIPTION + "\": \"1\"}";
    JSONObject fakeCategoryData2;
    String fakeCategoryData2_str = "{\""+ IDataAccessObject.CATEGORY_ID +"\": 0, \"" + IDataAccessObject.CATEGORY_TITLE + "\": \"4\", \"" + IDataAccessObject.CATEGORY_DESCRIPTION + "\": \"4\"}";

    @Before
    public void setUp() throws Exception {
        categoryList = new CategoryList(categoryDAO, stickerDAO);

        //fakeCategoryListData
        fakeCategoryListData1 = new JSONArray(fakeCategoryListData1_str);
        fakeCategoryListData2 = new JSONArray(fakeCategoryListData2_str);

        //fakeCategoryData
        fakeCategoryData1 = new JSONObject(fakeCategoryData1_str);
        fakeCategoryData2 = new JSONObject(fakeCategoryData2_str);
    }

    @Test
    public void getCategoryList() throws Exception {
        context1.checking(new Expectations(){{
            oneOf(categoryDAO).retrieveAll();
            will(returnValue(fakeCategoryListData1));
        }});
        List<Category> result = categoryList.getCategoryList(null);
        context1.assertIsSatisfied();
        int num = result.size();
        assertEquals(3, num);
        assertEquals(1, (int)result.get(0).getCategoryID());
    }

    @Test
    public void setCategory_Fail_TitleRepeated() throws Exception {
        context1.checking(new Expectations(){{
            oneOf(categoryDAO).retrieveWhere(IDataAccessObject.CATEGORY_TITLE + " = \"1\"");
            will(returnValue(fakeCategoryListData2));
        }});

        int result = categoryList.setCategory(new Category(0, "1", "1"));
        context1.assertIsSatisfied();
        assertEquals(-1, result);
    }

    @Test
    public void setCategory_Success_Create() throws Exception {
        context1.checking(new Expectations(){{
            oneOf(categoryDAO).retrieveWhere(IDataAccessObject.CATEGORY_TITLE + " = \"4\"");
            will(returnValue(new JSONArray()));
            oneOf(categoryDAO).create(with(any(JSONObject.class)));
        }});

        int result = categoryList.setCategory(new Category(0, "4", "4"));
        context1.assertIsSatisfied();
        assertEquals(0, result);
    }

    @Test
    public void setCategory_Success_Edit() throws Exception {
        context1.checking(new Expectations(){{
            oneOf(categoryDAO).retrieveWhere(IDataAccessObject.CATEGORY_TITLE + " = \"4\"");
            will(returnValue(new JSONArray()));
            oneOf(categoryDAO).updateOne(with(1), with(any(JSONObject.class)));
        }});

        int result = categoryList.setCategory(new Category(1, "4", "4"));
        context1.assertIsSatisfied();
        assertEquals(0, result);
    }

    @Test
    public void deleteCategory() throws Exception {
        context1.checking(new Expectations(){{
            oneOf(categoryDAO).deleteOne(with(any(int.class)));
        }});
        context2.checking(new Expectations(){{
            oneOf(stickerDAO).deleteWhere(with(any(String.class)));
        }});

        categoryList.deleteCategory(new Category(1, "4", "4"));
        context1.assertIsSatisfied();
    }

}