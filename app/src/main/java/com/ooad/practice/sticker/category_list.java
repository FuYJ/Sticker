package com.ooad.practice.sticker;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.widget.Button;

import android.widget.*;
import android.widget.AdapterView.OnItemClickListener;
import android.view.*;
import android.widget.AdapterView;

import Adapter.MyAdapter;

public class category_list extends ActionBarActivity {
    private ListView listView01;
    private String[] show_text = {"未帶鑰匙","網路問題","電腦故障","商店","診所","未帶鑰匙","網路問題","電腦故障","商店","診所"};
    private ArrayAdapter listAdapter;
    private TextView textView01;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_list);

        listView01 = (ListView)findViewById(R.id.listView1);
//        listAdapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1,show_text);
        listView01.setAdapter(new MyAdapter(category_list.this, show_text, show_text));
        listView01.setAdapter(new MyAdapter(category_list.this, show_text, show_text));
    }
}
