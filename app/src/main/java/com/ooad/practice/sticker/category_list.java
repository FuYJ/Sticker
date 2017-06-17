package com.ooad.practice.sticker;

import android.content.Intent;
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
    private int[] isCreateButtonVisible;
    private int length;
    private String searchKey;
    private EditText searchInput;
    private Button mainButton;
    private Button categoryPageBtn;
    private Button settingAppButton;
    private Button searchButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Category List");
        setContentView(R.layout.activity_category_list);

        searchInput = (EditText)findViewById(R.id.searchInput);
        searchInput.setOnKeyListener(searchInputLinstener());

        mainButton = (Button)findViewById(R.id.MainPage);
        mainButton.setOnClickListener(mainButtonListener());
        categoryPageBtn = (Button)findViewById(R.id.Category);
        categoryPageBtn.setEnabled(false);
        settingAppButton = (Button)findViewById(R.id.Setting);
        settingAppButton.setOnClickListener(changeToSettings());
        searchButton = (Button)findViewById(R.id.Search);
        searchButton.setOnClickListener(changeToSearch());
        category = new CategoryList();

        updateView();
    }

    @Override
    public void onRestart() {
        super.onRestart();

    }

    public void updateView(){
        categoryList = category.getCategoryList(searchKey);
        length = categoryList.size();
        isCreateButtonVisible = new int[length + 1];

        for(int i = 0; i < length + 1; i++){
            if(i == 0){
                isCreateButtonVisible[i] = 1;
            }
            else{
                isCreateButtonVisible[i] = 0;
            }
        }

        listView = (ListView)findViewById(R.id.category_list);
        listView.setAdapter(new CategoryAdapter(category_list.this, categoryList, isCreateButtonVisible));
        listView.setOnItemClickListener(listItemListener());
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

    private OnItemClickListener listItemListener(){
        return new OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView parent, View view, int position, long id){
                if(position > 0){
                    Intent intent = new Intent(category_list.this, sticker_list.class);
                    Bundle bundle = new Bundle();
                    bundle.putInt("CategoryID", categoryList.get(position - 1).getCategoryID());
                    intent.putExtra("Bundle", bundle);
                    startActivity(intent);
                }
            }
        };
    }

    private View.OnClickListener mainButtonListener(){
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(category_list.this, MainActivity.class);
                Bundle bundle = new Bundle();
                intent.putExtra("Bundle", bundle);
                startActivity(intent);
            }
        };
    }

    private View.OnClickListener changeToSettings(){
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(category_list.this, Settings.class);
                Bundle bundle = new Bundle();
                intent.putExtra("Bundle", bundle);
                startActivity(intent);
            }
        };
    }

    private View.OnClickListener changeToSearch(){
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(category_list.this, search_sticker.class);
                Bundle bundle = new Bundle();
                intent.putExtra("Bundle", bundle);
                startActivity(intent);
            }
        };
    }
}
