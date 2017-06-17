package com.ooad.practice.sticker;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.ooad.practice.sticker.Bean.Sticker;
import com.ooad.practice.sticker.Database.IDataAccessObject;
import com.ooad.practice.sticker.Model.CategoryList;
import com.ooad.practice.sticker.Model.StickerList;

import java.util.ArrayList;
import java.util.List;

import Adapter.SearchStickerAdapter;
import Adapter.StickerAdapter;

/**
 * Created by mousecat1 on 2017/6/18.
 */

public class search_sticker extends ActionBarActivity {
    private ImageButton searchSettings;
    private TextView inputKey;
    private ListView listView;
    private Dialog dialog;
    private StickerList stickerList;
    private List<Sticker> sticker;
    private List<Boolean> searchCategory;
    private Boolean isFinished;
    private Button mainButton;
    private Button categoryPageBtn;
    private Button settingAppButton;
    private Button searchButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Search Sticker");
        setContentView(R.layout.activity_search_sticker);

        searchSettings = (ImageButton)findViewById(R.id.setting);
        inputKey = (TextView)findViewById(R.id.searchInput);
        listView = (ListView)findViewById(R.id.sticker_list);
        initializeSearchSettings();
        searchSettings.setOnClickListener(searchSettings());
        inputKey.setOnKeyListener(searchInputLinstener());

        mainButton = (Button)findViewById(R.id.MainPage);
        mainButton.setOnClickListener(mainButtonListener());
        categoryPageBtn = (Button)findViewById(R.id.Category);
        categoryPageBtn.setOnClickListener(changeToCategory());
        settingAppButton = (Button)findViewById(R.id.Setting);
        settingAppButton.setOnClickListener(changeToSettings());
        searchButton = (Button)findViewById(R.id.Search);
        searchButton.setEnabled(false);

        stickerList = new StickerList();
    }

    @Override
    public void onRestart() {
        super.onRestart();

    }

    private void initializeSearchSettings(){
        searchCategory = new ArrayList<>();
        isFinished = false;
        searchCategory.add(false);
        searchCategory.add(true);
        searchCategory.add(false);
    }

    public void update(){
        List<IDataAccessObject.SearchTarget> searchTargets = new ArrayList<>();
        IDataAccessObject.SearchIsFinished searchIsFinished;
        if(searchCategory.get(0))
            searchTargets.add(IDataAccessObject.SearchTarget.CATEGORY);
        if(searchCategory.get(1))
            searchTargets.add(IDataAccessObject.SearchTarget.STICKER);
        if(searchCategory.get(2))
            searchTargets.add(IDataAccessObject.SearchTarget.TAG);
        if(isFinished)
            searchIsFinished = IDataAccessObject.SearchIsFinished.FINISHED;
        else
            searchIsFinished = IDataAccessObject.SearchIsFinished.UNFINISHED;
        sticker = stickerList.getStickerList(inputKey.getText().toString(), searchTargets, searchIsFinished);

        listView.setAdapter(new SearchStickerAdapter(search_sticker.this, sticker));
        listView.setOnItemClickListener(listItemListener());
    }

    private View.OnClickListener searchSettings(){
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog = new Dialog(v.getContext());
                dialog.setContentView(R.layout.popup_search_settings);
                dialog.setTitle("Search Settings");
                CheckBox categorySettings, stickerSettings, tagSettings;
                Switch finished;
                categorySettings = (CheckBox)dialog.findViewById(R.id.settings_category);
                stickerSettings = (CheckBox)dialog.findViewById(R.id.settings_sticker);
                tagSettings = (CheckBox)dialog.findViewById(R.id.settings_tag);
                finished = (Switch)dialog.findViewById(R.id.isFinished);
                categorySettings.setChecked(searchCategory.get(0));
                stickerSettings.setChecked(searchCategory.get(1));
                tagSettings.setChecked(searchCategory.get(2));
                finished.setChecked(isFinished);
                categorySettings.setOnCheckedChangeListener(settingsChange());
                stickerSettings.setOnCheckedChangeListener(settingsChange());
                tagSettings.setOnCheckedChangeListener(settingsChange());
                finished.setOnCheckedChangeListener(switchChange());
                dialog.show();
            }
        };
    }

    private AdapterView.OnItemClickListener listItemListener(){
        return new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView parent, View view, int position, long id){
                if(position > 0){
                    Intent intent = new Intent(search_sticker.this, sticker_page.class);
                    Bundle bundle = new Bundle();
                    bundle.putInt("CategoryID", sticker.get(position).getCategoryID());
                    bundle.putInt("StickerID", sticker.get(position).getStickerID());
                    bundle.putString("State", getApplicationContext().getResources().getStringArray(R.array.sticker_state)[2]);
                    intent.putExtra("Bundle", bundle);
                    startActivity(intent);
                }
            }
        };
    }

    private CheckBox.OnCheckedChangeListener settingsChange(){
        return new CheckBox.OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                CheckBox temp = (CheckBox)dialog.findViewById(compoundButton.getId());
                searchCategory.set(Integer.parseInt(temp.getTag().toString()), b);
            }
        };
    }

    private CompoundButton.OnCheckedChangeListener switchChange(){
        return new CompoundButton.OnCheckedChangeListener(){

            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                isFinished = b;
            }
        };
    }

    private View.OnKeyListener searchInputLinstener(){
        return new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_ENTER) {
                    update();
                    return true;
                }
                return false;
            }
        };
    }

    private View.OnClickListener mainButtonListener(){
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(search_sticker.this, MainActivity.class);
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
                Intent intent = new Intent(search_sticker.this, Settings.class);
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
                Intent intent = new Intent(search_sticker.this, category_list.class);
                Bundle bundle = new Bundle();
                intent.putExtra("Bundle", bundle);
                startActivity(intent);
            }
        };
    }
}
