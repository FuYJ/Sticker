package com.ooad.practice.sticker;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.widget.Button;

import android.widget.*;
import android.widget.AdapterView.OnItemClickListener;
import android.view.*;
import android.widget.AdapterView;

import com.ooad.practice.sticker.Bean.*;
import com.ooad.practice.sticker.Model.CategoryList;
import Adapter.*;

import java.util.List;

public class category_list extends ActionBarActivity {
    private List<Category> categoryList;
    private CategoryList category;
    private ListView listView;
    private Button settingButton;
    private int isVisible = 0;
    private int[] isCreateButtonVisible;
    private int[] isEditButtonVisible;
    private int length;
    private String searchKey;
    private EditText searchInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Category List");
        setContentView(R.layout.activity_category_list);

        settingButton = (Button)findViewById(R.id.setting);
        settingButton.setOnClickListener(settingsListener());
        searchInput = (EditText)findViewById(R.id.searchInput);
        searchInput.setOnKeyListener(searchInputLinstener());
        category = CategoryList.getInstance();
        updateView();
    }

    public void updateView(){
        categoryList = category.getCategoryList(searchKey);
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
        listView.setAdapter(new CategoryAdapter(category_list.this, categoryList, isCreateButtonVisible, isEditButtonVisible, this));
        listView.setOnItemClickListener(new OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView parent, View view, int position, long id){

            }
        });
    }

    private View.OnClickListener settingsListener(){
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isVisible = (isVisible + 1) % 2;
                updateView();
            }
        };
    }

    private View.OnKeyListener searchInputLinstener(){
        return new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_ENTER) {
                    if(searchInput.getText().toString().equals("")){
                        searchKey = null;
                    }
                    else{
                        searchKey = searchInput.getText().toString();
                    }
                    updateView();
                    return true;
                }
                return false;
            }
        };
    }
}
