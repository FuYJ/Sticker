package com.ooad.practice.sticker.Bean;

import com.ooad.practice.sticker.Database.IDataAccessObject;

import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by fuyiru on 2017/6/17.
 */
public class testTag {
    private Tag tag1;
    private Tag tag2;
    private Integer tagID = 0;
    private String tagTitle = "Test1";
    private List<Integer> color;
    private JSONObject jObj1;

    @Before
    public void setUp() throws Exception {
        color = new ArrayList<>();
        color.add(1);
        color.add(2);
        color.add(3);
        tag1 = new Tag(tagID, tagTitle, color.get(0), color.get(1), color.get(2));
        jObj1 = new JSONObject();
        jObj1.put(IDataAccessObject.TAG_ID, tagID);
        jObj1.put(IDataAccessObject.TAG_TITLE, tagTitle);
        jObj1.put(IDataAccessObject.TAG_COLOR_R, color.get(0));
        jObj1.put(IDataAccessObject.TAG_COLOR_G, color.get(1));
        jObj1.put(IDataAccessObject.TAG_COLOR_B, color.get(2));
        tag2 = new Tag(jObj1);
        assertEquals(tag1.getTagID(), tag2.getTagID());
    }

    @Test
    public void toJSONObject() throws Exception {
        JSONObject jObj2 = tag1.toJSONObject();
        JSONObject expected = new JSONObject();
        expected.put(IDataAccessObject.TAG_ID, tag1.getTagID());
        expected.put(IDataAccessObject.TAG_TITLE, tag1.getTitle());
        expected.put(IDataAccessObject.TAG_COLOR_R, tag1.getColor().get(0));
        expected.put(IDataAccessObject.TAG_COLOR_G, tag1.getColor().get(1));
        expected.put(IDataAccessObject.TAG_COLOR_B, tag1.getColor().get(2));
        assertEquals(expected.getInt(IDataAccessObject.TAG_ID), jObj2.getInt(IDataAccessObject.TAG_ID));
        assertEquals(expected.getString(IDataAccessObject.TAG_TITLE), jObj2.getString(IDataAccessObject.TAG_TITLE));
        assertEquals(expected.getInt(IDataAccessObject.TAG_COLOR_R), jObj2.getInt(IDataAccessObject.TAG_COLOR_R));
        assertEquals(expected.getInt(IDataAccessObject.TAG_COLOR_G), jObj2.getInt(IDataAccessObject.TAG_COLOR_G));
        assertEquals(expected.getInt(IDataAccessObject.TAG_COLOR_B), jObj2.getInt(IDataAccessObject.TAG_COLOR_B));
    }
}