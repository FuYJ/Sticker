package com.ooad.practice.sticker.Model;

import com.ooad.practice.sticker.Database.IDataAccessObject;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by fuyiru on 2017/6/12.
 */
public class TagListTest {

    Mockery context = new Mockery();
    IDataAccessObject tagDAO = context.mock(IDataAccessObject.class);
    IDataAccessObject stickerTagsDAO = context.mock(IDataAccessObject.class);
    TagList tagList;
    JSONArray fakeTagListData1;
    String fakeTagListData1_str = "[" +
            "{\""+ IDataAccessObject.TAG_ID +"\": 1, \"" + IDataAccessObject.TAG_TITLE + "\": \"1\", \"" + IDataAccessObject.TAG_COLOR_R + "\": 1, \"" + IDataAccessObject.TAG_COLOR_G + "\": 1, \"" + IDataAccessObject.TAG_COLOR_B + "\": 1}," +
            "{\""+ IDataAccessObject.TAG_ID +"\": 2, \"" + IDataAccessObject.TAG_TITLE + "\": \"2\", \"" + IDataAccessObject.TAG_COLOR_R + "\": 2, \"" + IDataAccessObject.TAG_COLOR_G + "\": 2, \"" + IDataAccessObject.TAG_COLOR_B + "\": 2}," +
            "{\""+ IDataAccessObject.TAG_ID +"\": 3, \"" + IDataAccessObject.TAG_TITLE + "\": \"3\", \"" + IDataAccessObject.TAG_COLOR_R + "\": 3, \"" + IDataAccessObject.TAG_COLOR_G + "\": 3, \"" + IDataAccessObject.TAG_COLOR_B + "\": 3}" +
            "]";

    JSONArray fakeStickerTagsListData1;
    String fakeStickerTagsListData1_str = "[" +
            "{\""+ IDataAccessObject.STICKER_TAGS_TAG_ID +"\": 1, \"" + IDataAccessObject.STICKER_TAGS_STICKER_ID + "\": 1}" +
            "]";
    JSONObject fakeTagData1;
    String fakeTagData1_str = "{\""+ IDataAccessObject.TAG_ID +"\": 0, \"" + IDataAccessObject.TAG_TITLE + "\": \"4\", \"" + IDataAccessObject.TAG_COLOR_R + "\": 4, \"" + IDataAccessObject.TAG_COLOR_G + "\": 4, \"" + IDataAccessObject.TAG_COLOR_B + "\": 4}";

    @Before
    public void setUp() throws Exception {
        tagList = new TagList(tagDAO, stickerTagsDAO);

        //fakeCategoryListData
        fakeTagListData1 = new JSONArray(fakeTagListData1_str);

        //fakeStickerTagsListData
        fakeStickerTagsListData1 = new JSONArray(fakeStickerTagsListData1_str);

        //fakeCategoryData
        fakeTagData1 = new JSONObject(fakeTagData1_str);
    }

    @Test
    public void getTagListByStickerId() throws Exception {
        context.checking(new Expectations(){{
            oneOf(stickerTagsDAO).retrieveWhere(with(IDataAccessObject.STICKER_TAGS_STICKER_ID + " = 1"));
            will(returnValue(fakeStickerTagsListData1));
            oneOf(tagDAO).retrieveOne(with(1));
            will(returnValue(fakeTagData1));
        }});

    }

    @Test
    public void getTagList() throws Exception {

    }

    @Test
    public void setTag() throws Exception {

    }

    @Test
    public void deleteTag() throws Exception {

    }

}