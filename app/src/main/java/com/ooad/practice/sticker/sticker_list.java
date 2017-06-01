package com.ooad.practice.sticker;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.ooad.practice.sticker.Bean.Category;
import com.ooad.practice.sticker.Bean.Sticker;
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
    private int isVisible = 0;
    private String keyword;
    private int length;
    private int[] isCreateButtonVisible;
    private int[] isEditButtonVisible;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sticker_list);
        category = (Category)getIntent().getBundleExtra("Bundle").getSerializable("Category");
        setTitle(category.getTitle());

        settingButton = (Button)findViewById(R.id.setting);
        searchInput = (EditText)findViewById(R.id.searchInput);
        sticker = StickerList.getInstance();
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
        listView.setAdapter(new StickerAdapter(sticker_list.this, stickerList, isCreateButtonVisible, isEditButtonVisible));
    }

}
