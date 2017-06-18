package com.ooad.practice.sticker.Model;

import com.ooad.practice.sticker.Bean.Tag;
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
 * Created by fuyiru on 2017/6/12.
 */
public class testTagList {
    Mockery context1 = new Mockery();
    Mockery context2 = new Mockery();
    IDataAccessObject tagDAO = context1.mock(IDataAccessObject.class);
    IDataAccessObject stickerTagsDAO = context2.mock(IDataAccessObject.class);
    TagList tagList;
    JSONArray fakeTagListData1;
    //fakeCategoryListData
    String fakeTagListData1_str = "[" +
            "{\""+ IDataAccessObject.TAG_ID +"\": 1, \"" + IDataAccessObject.TAG_TITLE + "\": \"1\", \"" + IDataAccessObject.TAG_COLOR_R + "\": 1, \"" + IDataAccessObject.TAG_COLOR_G + "\": 1, \"" + IDataAccessObject.TAG_COLOR_B + "\": 1}," +
            "{\""+ IDataAccessObject.TAG_ID +"\": 2, \"" + IDataAccessObject.TAG_TITLE + "\": \"2\", \"" + IDataAccessObject.TAG_COLOR_R + "\": 2, \"" + IDataAccessObject.TAG_COLOR_G + "\": 2, \"" + IDataAccessObject.TAG_COLOR_B + "\": 2}," +
            "{\""+ IDataAccessObject.TAG_ID +"\": 3, \"" + IDataAccessObject.TAG_TITLE + "\": \"3\", \"" + IDataAccessObject.TAG_COLOR_R + "\": 3, \"" + IDataAccessObject.TAG_COLOR_G + "\": 3, \"" + IDataAccessObject.TAG_COLOR_B + "\": 3}" +
            "]";
    //fakeStickerTagsListData
    JSONArray fakeStickerTagsListData1;
    String fakeStickerTagsListData1_str = "[" +
            "{\""+ IDataAccessObject.STICKER_TAGS_TAG_ID +"\": 1, \"" + IDataAccessObject.STICKER_TAGS_STICKER_ID + "\": 1}" +
            "]";
    JSONArray fakeStickerTagsListData2;
    String fakeStickerTagsListData2_str = "[" +
            "]";
    //fakeCategoryData
    JSONObject fakeTagData1;
    String fakeTagData1_str = "{\""+ IDataAccessObject.TAG_ID +"\": 1, \"" + IDataAccessObject.TAG_TITLE + "\": \"1\", \"" + IDataAccessObject.TAG_COLOR_R + "\": 1, \"" + IDataAccessObject.TAG_COLOR_G + "\": 1, \"" + IDataAccessObject.TAG_COLOR_B + "\": 1}";
    JSONObject fakeTagData2;
    String fakeTagData2_str = "{\""+ IDataAccessObject.TAG_ID +"\": 0, \"" + IDataAccessObject.TAG_TITLE + "\": \"4\", \"" + IDataAccessObject.TAG_COLOR_R + "\": 4, \"" + IDataAccessObject.TAG_COLOR_G + "\": 4, \"" + IDataAccessObject.TAG_COLOR_B + "\": 4}";
    JSONObject fakeTagData3;
    String fakeTagData3_str = "{\""+ IDataAccessObject.TAG_ID +"\": 1, \"" + IDataAccessObject.TAG_TITLE + "\": \"5\", \"" + IDataAccessObject.TAG_COLOR_R + "\": 5, \"" + IDataAccessObject.TAG_COLOR_G + "\": 5, \"" + IDataAccessObject.TAG_COLOR_B + "\": 5}";

    @Before
    public void setUp() throws Exception {
        tagList = new TagList(tagDAO, stickerTagsDAO);

        //fakeCategoryListData
        fakeTagListData1 = new JSONArray(fakeTagListData1_str);

        //fakeStickerTagsListData
        fakeStickerTagsListData1 = new JSONArray(fakeStickerTagsListData1_str);
        fakeStickerTagsListData2 = new JSONArray(fakeStickerTagsListData2_str);

        //fakeCategoryData
        fakeTagData1 = new JSONObject(fakeTagData1_str);
        fakeTagData2 = new JSONObject(fakeTagData2_str);
        fakeTagData3 = new JSONObject(fakeTagData3_str);
    }

    @Test
    public void getTagListByStickerId() throws Exception {
        context2.checking(new Expectations(){{
            oneOf(stickerTagsDAO).retrieveWhere(with(IDataAccessObject.STICKER_TAGS_STICKER_ID + " = 1"));
            will(returnValue(fakeStickerTagsListData1));
        }});
        context1.checking(new Expectations(){{
            oneOf(tagDAO).retrieveOne(with(1));
            will(returnValue(fakeTagData1));
        }});
        List<Tag> result = tagList.getTagListByStickerId(1);
        int num = result.size();
        context1.assertIsSatisfied();
        context2.assertIsSatisfied();
        assertEquals(1, num);
        assertEquals(1, (int)result.get(0).getTagID());
    }

    @Test
    public void getTagList() throws Exception {
        context1.checking(new Expectations(){{
            oneOf(tagDAO).retrieveAll();
            will(returnValue(fakeTagListData1));
        }});
        List<Tag> result = tagList.getTagList();
        int num = result.size();
        context1.assertIsSatisfied();
        assertEquals(3, num);
        assertEquals(1, (int)result.get(0).getTagID());
        assertEquals(2, (int)result.get(1).getTagID());
        assertEquals(3, (int)result.get(2).getTagID());
    }

    @Test
    public void setTag_Success_Create() throws Exception {
        context1.checking(new Expectations(){{
            oneOf(tagDAO).create(with(any(JSONObject.class)));
        }});
        Tag tag = new Tag(0, "4", 4, 4, 4);
        tagList.setTag(tag);
        context1.assertIsSatisfied();
    }

    @Test
    public void setTag_Success_Edit() throws Exception {
        context1.checking(new Expectations(){{
            oneOf(tagDAO).updateOne(with(1), with(any(JSONObject.class)));
        }});
        Tag tag = new Tag(1, "5", 5, 5, 5);
        tagList.setTag(tag);
        context1.assertIsSatisfied();
    }

    @Test
    public void deleteTag() throws Exception {
        context1.checking(new Expectations(){{
            oneOf(tagDAO).deleteOne(with(1));
        }});
        context2.checking(new Expectations(){{
            oneOf(stickerTagsDAO).deleteWhere(with(equal("tagID = \"1\"")));
        }});
        Tag tag = new Tag(1, "5", 5, 5, 5);
        tagList.deleteTag(tag);
        context1.assertIsSatisfied();
        context2.assertIsSatisfied();
    }
}