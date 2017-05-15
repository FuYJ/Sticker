package com.ooad.practice.sticker;

import android.content.Context;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.widget.Button;

import android.widget.*;
import android.widget.AdapterView.OnItemClickListener;
import android.view.*;
import android.widget.AdapterView;

import com.ooad.practice.sticker.Bean.*;
import com.ooad.practice.sticker.Controller.CategoryHandler;

import java.util.List;

import Adapter.MyAdapter;

public class category_list extends ActionBarActivity {
    private List<Category> categoryList;
    private CategoryHandler handler = new CategoryHandler();
    private ListView listView;
    private int isVisible = 0;
    private int[] isCreateButtonVisible;
    private int[] isEditButtonVisible;
    private int length;
    private String searchKey;
    private EditText searchInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_list);

        Button settingButton = (Button)findViewById(R.id.setting);
        searchInput = (EditText)findViewById(R.id.searchInput);
        searchInput.setOnKeyListener(searchInputLinstener());
//        Toast.makeText(this, searchInput.getText().toString(), Toast.LENGTH_SHORT).show();
        updateView();

        settingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isVisible = (isVisible + 1) % 2;
                updateView();
            }
        });

    }

    public void updateView(){
        categoryList = handler.getCategoryList(null);
        length = categoryList.size();
        isCreateButtonVisible = new int[length + 1];
        isEditButtonVisible = new int[length + 1];

        for(int i = 0; i < length + 1; i++){
            if(i == 0){
                isCreateButtonVisible[i] = 1;
                isEditButtonVisible[i] = 0;
            }
            else{
                isCreateButtonVisible[i] = 0;
                isEditButtonVisible[i] = isVisible;
            }
        }

        listView = (ListView)findViewById(R.id.listView);
        listView.setAdapter(new MyAdapter(category_list.this, categoryList, isCreateButtonVisible, isEditButtonVisible, this));
        listView.setOnItemClickListener(new OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView parent, View view, int position, long id){

            }
        });
    }

    private View.OnKeyListener searchInputLinstener(){
        return new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_ENTER) {
                    Toast.makeText(v.getContext(), "Test", Toast.LENGTH_SHORT);
                    return true;
                }
                return false;
            }
        };
    }
}
