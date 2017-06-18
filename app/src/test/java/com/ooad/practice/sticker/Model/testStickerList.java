package com.ooad.practice.sticker.Model;

import android.content.SharedPreferences;

import com.ooad.practice.sticker.Bean.Sticker;
import com.ooad.practice.sticker.Bean.Tag;
import com.ooad.practice.sticker.Database.IDataAccessObject;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.lib.legacy.ClassImposteriser;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by fuyiru on 2017/6/12.
 */
public class testStickerList {
    Mockery context1 = new Mockery();
    IDataAccessObject stickerDAO = context1.mock(IDataAccessObject.class);
    Mockery context2 = new Mockery();
    IDataAccessObject stickerTagsDAO = context2.mock(IDataAccessObject.class);
    Mockery context3 = new Mockery(){{
        setImposteriser(ClassImposteriser.INSTANCE);
    }};
    SharedPreferences defaultSharedPreferences = context3.mock(SharedPreferences.class);
    StickerList stickerList;
    JSONArray fakeStickerListData1;
    String fakeStickerListData1_str = "[" +
            "{\""+ IDataAccessObject.STICKER_ID +"\": 1, \"" + IDataAccessObject.STICKER_CATEGORY_ID + "\": 1, \"" + IDataAccessObject.STICKER_TITLE + "\": \"1\", \"" + IDataAccessObject.STICKER_DESCRIPTION + "\": \"1\", \"" + IDataAccessObject.STICKER_DEADLINE + "\": 1, \"" + IDataAccessObject.STICKER_REMIND_TIME + "\": 1, \"" + IDataAccessObject.STICKER_IS_FINISHED + "\": 1}," +
            "{\""+ IDataAccessObject.STICKER_ID +"\": 2, \"" + IDataAccessObject.STICKER_CATEGORY_ID + "\": 2, \"" + IDataAccessObject.STICKER_TITLE + "\": \"2\", \"" + IDataAccessObject.STICKER_DESCRIPTION + "\": \"1\", \"" + IDataAccessObject.STICKER_DEADLINE + "\": 1, \"" + IDataAccessObject.STICKER_REMIND_TIME + "\": 1, \"" + IDataAccessObject.STICKER_IS_FINISHED + "\": 1}," +
            "{\""+ IDataAccessObject.STICKER_ID +"\": 3, \"" + IDataAccessObject.STICKER_CATEGORY_ID + "\": 3, \"" + IDataAccessObject.STICKER_TITLE + "\": \"3\", \"" + IDataAccessObject.STICKER_DESCRIPTION + "\": \"1\", \"" + IDataAccessObject.STICKER_DEADLINE + "\": 1, \"" + IDataAccessObject.STICKER_REMIND_TIME + "\": 1, \"" + IDataAccessObject.STICKER_IS_FINISHED + "\": 0}," +
            "{\""+ IDataAccessObject.STICKER_ID +"\": 4, \"" + IDataAccessObject.STICKER_CATEGORY_ID + "\": 1, \"" + IDataAccessObject.STICKER_TITLE + "\": \"123\", \"" + IDataAccessObject.STICKER_DESCRIPTION + "\": \"1\", \"" + IDataAccessObject.STICKER_DEADLINE + "\": 1, \"" + IDataAccessObject.STICKER_REMIND_TIME + "\": 1, \"" + IDataAccessObject.STICKER_IS_FINISHED + "\": 1}," +
            "{\""+ IDataAccessObject.STICKER_ID +"\": 5, \"" + IDataAccessObject.STICKER_CATEGORY_ID + "\": 2, \"" + IDataAccessObject.STICKER_TITLE + "\": \"12345\", \"" + IDataAccessObject.STICKER_DESCRIPTION + "\": \"1\", \"" + IDataAccessObject.STICKER_DEADLINE + "\": 1, \"" + IDataAccessObject.STICKER_REMIND_TIME + "\": 1, \"" + IDataAccessObject.STICKER_IS_FINISHED + "\": 1}" +
            "]";
    JSONArray fakeStickerListData2;
    String fakeStickerListData2_str = "[" +
            "{\""+ IDataAccessObject.STICKER_ID +"\": 1, \"" + IDataAccessObject.STICKER_CATEGORY_ID + "\": 1, \"" + IDataAccessObject.STICKER_TITLE + "\": \"1\", \"" + IDataAccessObject.STICKER_DESCRIPTION + "\": \"1\", \"" + IDataAccessObject.STICKER_DEADLINE + "\": 1, \"" + IDataAccessObject.STICKER_REMIND_TIME + "\": 1, \"" + IDataAccessObject.STICKER_IS_FINISHED + "\": 1}" +
            "]";
    JSONArray fakeStickerListData3;
    String fakeStickerListData3_str = "[" +
            "{\""+ IDataAccessObject.STICKER_ID +"\": 1, \"" + IDataAccessObject.STICKER_CATEGORY_ID + "\": 1, \"" + IDataAccessObject.STICKER_TITLE + "\": \"1\", \"" + IDataAccessObject.STICKER_DESCRIPTION + "\": \"1\", \"" + IDataAccessObject.STICKER_DEADLINE + "\": 1, \"" + IDataAccessObject.STICKER_REMIND_TIME + "\": 1, \"" + IDataAccessObject.STICKER_IS_FINISHED + "\": 1}," +
            "{\""+ IDataAccessObject.STICKER_ID +"\": 4, \"" + IDataAccessObject.STICKER_CATEGORY_ID + "\": 1, \"" + IDataAccessObject.STICKER_TITLE + "\": \"123\", \"" + IDataAccessObject.STICKER_DESCRIPTION + "\": \"1\", \"" + IDataAccessObject.STICKER_DEADLINE + "\": 1, \"" + IDataAccessObject.STICKER_REMIND_TIME + "\": 1, \"" + IDataAccessObject.STICKER_IS_FINISHED + "\": 1}," +
            "{\""+ IDataAccessObject.STICKER_ID +"\": 5, \"" + IDataAccessObject.STICKER_CATEGORY_ID + "\": 2, \"" + IDataAccessObject.STICKER_TITLE + "\": \"12345\", \"" + IDataAccessObject.STICKER_DESCRIPTION + "\": \"1\", \"" + IDataAccessObject.STICKER_DEADLINE + "\": 1, \"" + IDataAccessObject.STICKER_REMIND_TIME + "\": 1, \"" + IDataAccessObject.STICKER_IS_FINISHED + "\": 1}" +
            "]";
    JSONObject fakeStickerTagsData1;
    String fakeStickerTagsData1_str = "{\""+ IDataAccessObject.STICKER_TAGS_STICKER_ID +"\": 1, \"" + IDataAccessObject.STICKER_TAGS_TAG_ID + "\": 1, }";
    JSONArray fakeStickerTagsListData1;
    String fakeStickerTagsListData1_str = "[" +
            "{\""+ IDataAccessObject.STICKER_TAGS_STICKER_ID +"\": 1, \"" + IDataAccessObject.STICKER_TAGS_TAG_ID + "\": 1, }" +
            "]";
    JSONObject fakeTagData1;
    String fakeTagData1_str = "{\""+ IDataAccessObject.TAG_ID +"\": 1, \"" + IDataAccessObject.TAG_TITLE + "\": \"1\", \"" + IDataAccessObject.TAG_COLOR_R + "\": 1, \"" + IDataAccessObject.TAG_COLOR_G + "\": 1, \"" + IDataAccessObject.TAG_COLOR_B + "\": 1}";
    JSONObject fakeStickerData1;
    String fakeStickerData1_str = "{\""+ IDataAccessObject.STICKER_ID +"\": 0, \"" + IDataAccessObject.STICKER_CATEGORY_ID + "\": 1, \"" + IDataAccessObject.STICKER_TITLE + "\": \"1\", \"" + IDataAccessObject.STICKER_DESCRIPTION + "\": \"1\", \"" + IDataAccessObject.STICKER_DEADLINE + "\": 1, \"" + IDataAccessObject.STICKER_REMIND_TIME + "\": 1, \"" + IDataAccessObject.STICKER_IS_FINISHED + "\": 1}";
    JSONObject fakeStickerData2;
    String fakeStickerData2_str = "{\""+ IDataAccessObject.STICKER_ID +"\": 1, \"" + IDataAccessObject.STICKER_CATEGORY_ID + "\": 1, \"" + IDataAccessObject.STICKER_TITLE + "\": \"1\", \"" + IDataAccessObject.STICKER_DESCRIPTION + "\": \"1\", \"" + IDataAccessObject.STICKER_DEADLINE + "\": 1, \"" + IDataAccessObject.STICKER_REMIND_TIME + "\": 1, \"" + IDataAccessObject.STICKER_IS_FINISHED + "\": 1}";
    Long currentTime = 1000L;

    @Before
    public void setUp() throws Exception {
        stickerList = new StickerListForTest(stickerDAO, stickerTagsDAO, defaultSharedPreferences, currentTime);

        //fakeStickerListData
        fakeStickerListData1 = new JSONArray(fakeStickerListData1_str);
        fakeStickerListData2 = new JSONArray(fakeStickerListData2_str);
        fakeStickerListData3 = new JSONArray(fakeStickerListData3_str);

        //fakeStickerTagsData
        fakeStickerTagsData1 = new JSONObject(fakeStickerTagsData1_str);

        //fakeStickerTagsListData
        fakeStickerTagsListData1 = new JSONArray(fakeStickerTagsListData1_str);

        //fakeCategoryData
        fakeTagData1 = new JSONObject(fakeTagData1_str);

        //fakeStickerData
        fakeStickerData1 = new JSONObject(fakeStickerData1_str);
        fakeStickerData2 = new JSONObject(fakeStickerData2_str);
    }

    @Test
    public void getStickerListByCategoryId() throws Exception {
        context1.checking(new Expectations(){{
            oneOf(stickerDAO).retrieveWhere(with(any(String.class)));
            will(returnValue(fakeStickerListData2));
        }});
        context3.checking(new Expectations(){{
            ignoring(defaultSharedPreferences);
        }});
        List<Sticker> result = stickerList.getStickerListByCategoryId(1);
        context1.assertIsSatisfied();
        int num = result.size();
        assertEquals(1, num);
        assertEquals(1, (int)result.get(0).getCategoryID());
    }

    @Test
    public void getStickerList() throws Exception {
        context1.checking(new Expectations(){{
            oneOf(stickerDAO).retrieveWhere(with(any(String.class)));
            will(returnValue(fakeStickerListData3));
        }});
        context3.checking(new Expectations(){{
            ignoring(defaultSharedPreferences);
        }});
        List<IDataAccessObject.SearchTarget> searchTargets = new ArrayList<>();
        searchTargets.add(IDataAccessObject.SearchTarget.STICKER);
        IDataAccessObject.SearchIsFinished searchIsFinished = IDataAccessObject.SearchIsFinished.FINISHED;
        List<Sticker> result = stickerList.getStickerList("1", searchTargets, searchIsFinished);
        context1.assertIsSatisfied();
        int num = result.size();
        assertEquals(3, num);
        assertEquals("1", result.get(0).getTitle());
        assertEquals("123", result.get(1).getTitle());
        assertEquals("12345", result.get(2).getTitle());
    }

    @Test
    public void setSticker_Success_Create() throws Exception {
        context1.checking(new Expectations(){{
            oneOf(stickerDAO).create(with(any(JSONObject.class)));
            will(returnValue(1));
        }});
        context3.checking(new Expectations(){{
            ignoring(defaultSharedPreferences);
        }});
        Sticker sticker = new Sticker(fakeStickerData1, defaultSharedPreferences);
        int id = stickerList.setSticker(sticker);
        context1.assertIsSatisfied();
        assertEquals(1, id);
    }

    @Test
    public void setSticker_Success_Edit() throws Exception {
        context1.checking(new Expectations(){{
            oneOf(stickerDAO).updateOne(with(1), with(any(JSONObject.class)));
        }});
        context3.checking(new Expectations(){{
            ignoring(defaultSharedPreferences);
        }});
        Sticker sticker = new Sticker(fakeStickerData2, defaultSharedPreferences);
        int id = stickerList.setSticker(sticker);
        context1.assertIsSatisfied();
        assertEquals(0, id);
    }

    @Test
    public void setTagToSticker() throws Exception {
        context2.checking(new Expectations(){{
            oneOf(stickerTagsDAO).create(with(any(JSONObject.class)));
        }});
        context3.checking(new Expectations(){{
            ignoring(defaultSharedPreferences);
        }});
        Sticker sticker = new Sticker(fakeStickerData2, defaultSharedPreferences);
        Tag tag = new Tag(fakeStickerTagsData1);
        stickerList.setTagToSticker(sticker, tag);
        context2.assertIsSatisfied();
    }

    @Test
    public void deleteTagFromSticker() throws Exception {
        context2.checking(new Expectations(){{
            oneOf(stickerTagsDAO).retrieveWhere(with(any(String.class)));
            will(returnValue(fakeStickerTagsListData1));
            oneOf(stickerTagsDAO).deleteWhere(with(any(String.class)));
        }});
        context3.checking(new Expectations(){{
            ignoring(defaultSharedPreferences);
        }});
        Sticker sticker = new Sticker(fakeStickerData2, defaultSharedPreferences);
        Tag tag = new Tag(fakeTagData1);
        stickerList.deleteTagFromSticker(sticker, tag);
        context2.assertIsSatisfied();
    }

    @Test
    public void deleteSticker() throws Exception {
        context1.checking(new Expectations(){{
            oneOf(stickerDAO).deleteOne(with(1));
        }});
        context3.checking(new Expectations(){{
            ignoring(defaultSharedPreferences);
        }});
        Sticker sticker = new Sticker(fakeStickerData2, defaultSharedPreferences);
        stickerList.deleteSticker(sticker);
        context1.assertIsSatisfied();
    }

    @Test
    public void getEmergentList() throws Exception {
        context1.checking(new Expectations(){{
            oneOf(stickerDAO).retrieveWhere(with(any(String.class)));
            will(returnValue(fakeStickerListData1));
        }});
        context3.checking(new Expectations(){{
            ignoring(defaultSharedPreferences);
        }});
        List<Sticker> result = stickerList.getEmergentList();
        context1.assertIsSatisfied();
        int num = result.size();
        assertEquals(5, num);
        assertEquals(1, (int)result.get(0).getStickerID());
        assertEquals(5, (int)result.get(4).getStickerID());
    }
}