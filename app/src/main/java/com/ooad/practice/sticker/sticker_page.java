package com.ooad.practice.sticker;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.ooad.practice.sticker.Bean.Category;
import com.ooad.practice.sticker.Model.StickerList;

/**
 * Created by mousecat1 on 2017/5/31.
 */

public class sticker_page extends ActionBarActivity {
    private TextView title;
    private TextView description;
    private TextView deadline;
    private TextView remind;
    private CheckBox isFinished;
    private Button left;
    private Button right;
    private String state;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sticker);

    }
}
