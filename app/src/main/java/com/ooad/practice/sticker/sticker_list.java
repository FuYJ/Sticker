package com.ooad.practice.sticker;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.ooad.practice.sticker.Bean.Category;
import com.ooad.practice.sticker.Bean.Sticker;
import com.ooad.practice.sticker.Model.CategoryList;
import com.ooad.practice.sticker.Model.StickerList;

import java.util.List;

import Adapter.CategoryAdapter;
import Adapter.StickerAdapter;

public class sticker_list extends ActionBarActivity {
    private List<Sticker> stickerList;
    private Category category;
    private ListView listView;
    private Button settingButton;
    private EditText searchInput;
    private StickerList sticker;
    private CategoryList categoryList;
    private int isVisible = 0;
    private String keyword;
    private int length;
    private int[] isCreateButtonVisible;
    private int[] isEditButtonVisible;
    private Button mainButton;
    private Button categoryPageBtn;
    private Button settingAppButton;
    private Button searchButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sticker_list);
        categoryList = new CategoryList();
        category = categoryList.getCategoryByCategoryId((int)getIntent().getBundleExtra("Bundle").getSerializable("CategoryID"));
        setTitle(category.getTitle());

        settingButton = (Button)findViewById(R.id.setting);
        settingButton.setOnClickListener(settingsListener());
        searchInput = (EditText)findViewById(R.id.searchInput);

        mainButton = (Button)findViewById(R.id.MainPage);
        mainButton.setOnClickListener(mainButtonListener());
        categoryPageBtn = (Button)findViewById(R.id.Category);
        categoryPageBtn.setOnClickListener(changeToCategory());
        settingAppButton = (Button)findViewById(R.id.Setting);
        settingAppButton.setOnClickListener(changeToSettings());
        searchButton = (Button)findViewById(R.id.Search);
        searchButton.setOnClickListener(changeToSearch());

        sticker = new StickerList();
        update();
    }

    @Override
    public void onRestart() {
        super.onRestart();
        update();
    }

    public void update(){
        stickerList = sticker.getStickerListByCategoryId(category.getCategoryID());
        length = stickerList.size();
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

        listView = (ListView)findViewById(R.id.sticker_list);
        listView.setAdapter(new StickerAdapter(sticker_list.this, stickerList, isCreateButtonVisible, isEditButtonVisible, category));
        listView.setOnItemClickListener(listItemListener());
    }

    private View.OnClickListener settingsListener(){
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isVisible = (isVisible + 1) % 2;
                update();
            }
        };
    }

    private AdapterView.OnItemClickListener listItemListener(){
        return new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView parent, View view, int position, long id){
                if(position > 0){
                    Intent intent = new Intent(sticker_list.this, sticker_page.class);
                    Bundle bundle = new Bundle();
                    bundle.putInt("CategoryID", category.getCategoryID());
                    bundle.putInt("StickerID", stickerList.get(position - 1).getStickerID());
                    bundle.putString("State", getApplicationContext().getResources().getStringArray(R.array.sticker_state)[2]);
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
                Intent intent = new Intent(sticker_list.this, MainActivity.class);
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
                Intent intent = new Intent(sticker_list.this, Settings.class);
                Bundle bundle = new Bundle();
                intent.putExtra("Bundle", bundle);
                startActivity(intent);
            }
        };
    }

    private View.OnClickListener changeToCategory(){
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(sticker_list.this, category_list.class);
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
                Intent intent = new Intent(sticker_list.this, search_sticker.class);
                Bundle bundle = new Bundle();
                intent.putExtra("Bundle", bundle);
                startActivity(intent);
            }
        };
    }
}
