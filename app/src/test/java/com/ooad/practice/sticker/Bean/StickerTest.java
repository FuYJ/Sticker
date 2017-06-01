package com.ooad.practice.sticker.Bean;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.ooad.practice.sticker.MainApplication;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.lib.legacy.ClassImposteriser;
import org.junit.Before;
import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import static org.junit.Assert.*;

/**
 * Created by fuyiru on 2017/5/28.
 */
public class StickerTest {
    Mockery context = new Mockery(){{
        setImposteriser(ClassImposteriser.INSTANCE);
    }};
    PreferenceManager preferenceManager = context.mock(PreferenceManager.class);
    SharedPreferences defaultSharedPreferences = context.mock(SharedPreferences.class);
    SimpleDateFormat sdf;
    Sticker sticker;
    String date;
    Long time;

    @Before
    public void setUp() throws Exception {
        sdf = new SimpleDateFormat("yyyy/mm/ss");
        date = "2017/05/20";
        time = sdf.parse("2017/05/20").getTime();
        sticker = new Sticker(0, 0, "Test", "Test", date, date, true);
    }

    @Test
    public void calculateDateFromStringToLong() throws Exception {
        context.checking(new Expectations(){{
            oneOf(defaultSharedPreferences).getString("@string/calendars", "西元曆");
            will(returnValue("國曆"));
        }});

        context.checking(new Expectations(){{
            oneOf(preferenceManager).getDefaultSharedPreferences(MainApplication.getContext());
            will(returnValue(defaultSharedPreferences));
        }});

        Long calculatedTime = sticker.calculateDate(sticker.getDeadline());
        Long expectedTime = sdf.parse("0106/05/20").getTime();
        assertEquals(expectedTime, calculatedTime);
    }

    @Test
    public void calculateDate1() throws Exception {

    }

}