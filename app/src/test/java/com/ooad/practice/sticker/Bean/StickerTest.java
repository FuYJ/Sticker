package com.ooad.practice.sticker.Bean;

import android.content.SharedPreferences;

import com.ooad.practice.sticker.Database.IDataAccessObject;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.lib.legacy.ClassImposteriser;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;

import java.text.SimpleDateFormat;

import static org.junit.Assert.*;

/**
 * Created by fuyiru on 2017/5/28.
 */
public class StickerTest {
    Mockery context = new Mockery(){{
        setImposteriser(ClassImposteriser.INSTANCE);
    }};
    SharedPreferences defaultSharedPreferences = context.mock(SharedPreferences.class);
    SimpleDateFormat sdf;
    Sticker sticker1;
    Sticker sticker2;
    Sticker sticker3;
    String date1;
    String date2;
    Long time;
    JSONObject jObj;

    @Before
    public void setUp() throws Exception {
        sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm");
        date1 = "2017/05/20 12:00";
        date2 = "0106/05/20 12:00";
        time = sdf.parse(date1).getTime();
        jObj = new JSONObject();
        jObj.put(IDataAccessObject.STICKER_ID, 1);
        jObj.put(IDataAccessObject.STICKER_CATEGORY_ID, 1);
        jObj.put(IDataAccessObject.STICKER_TITLE, "Test3");
        jObj.put(IDataAccessObject.STICKER_DESCRIPTION, "Test3");
        jObj.put(IDataAccessObject.STICKER_DEADLINE, time);
        jObj.put(IDataAccessObject.STICKER_REMIND_TIME, time);
        jObj.put(IDataAccessObject.STICKER_IS_FINISHED, true);
        sticker1 = new Sticker(0, 0, "Test1", "Test1", date1, date1, true, defaultSharedPreferences);
        sticker2 = new Sticker(0, 0, "Test2", "Test2", date2, date2, true, defaultSharedPreferences);
    }

    @Test
    public void calculateDate_FromStringToLong_CE() throws Exception {
        context.checking(new Expectations(){{
            oneOf(defaultSharedPreferences).getString(with(any(String.class)), with(any(String.class)));
            will(returnValue("西元曆"));
        }});
        Long calculatedTime = sticker1.calculateDate(sticker1.getDeadline());
        Long expectedTime = sdf.parse(date1).getTime();
        assertEquals(expectedTime, calculatedTime);
    }

    @Test
    public void calculateDate_FromStringToLong_ROC() throws Exception {
        context.checking(new Expectations(){{
            oneOf(defaultSharedPreferences).getString(with(any(String.class)), with(any(String.class)));
            will(returnValue("國曆"));
        }});
        Long calculatedTime = sticker2.calculateDate(sticker2.getDeadline());
        Long expectedTime = sdf.parse(date1).getTime();
        assertEquals(expectedTime, calculatedTime);
    }

    @Test
    public void calculateDate_FromLongToString_CE() throws Exception {
        context.checking(new Expectations(){{
            oneOf(defaultSharedPreferences).getString(with(any(String.class)), with(any(String.class)));
            will(returnValue("西元曆"));
            oneOf(defaultSharedPreferences).getString(with(any(String.class)), with(any(String.class)));
            will(returnValue("西元曆"));
        }});
        sticker3 = new Sticker(jObj, defaultSharedPreferences);
        String calculatedTime = sticker3.getDeadline();
        String expectedTime = date1;
        assertEquals(expectedTime, calculatedTime);
    }

    @Test
    public void calculateDate_FromLongToString_ROC() throws Exception {
        context.checking(new Expectations(){{
            oneOf(defaultSharedPreferences).getString(with(any(String.class)), with(any(String.class)));
            will(returnValue("國曆"));
            oneOf(defaultSharedPreferences).getString(with(any(String.class)), with(any(String.class)));
            will(returnValue("國曆"));
        }});
        sticker3 = new Sticker(jObj, defaultSharedPreferences);
        String calculatedTime = sticker3.getDeadline();
        String expectedTime = date2;
        assertEquals(expectedTime, calculatedTime);
    }

}