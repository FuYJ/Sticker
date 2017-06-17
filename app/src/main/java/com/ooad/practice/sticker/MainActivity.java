package com.ooad.practice.sticker;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.preference.Preference;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.ooad.practice.sticker.Bean.Sticker;
import com.ooad.practice.sticker.Model.StickerList;
import com.ooad.practice.sticker.Model.TagList;

import java.util.List;
import java.util.Map;

import Adapter.SearchStickerAdapter;


public class MainActivity extends ActionBarActivity{
    private Button mainButton;
    private Button nextPageBtn;
    private Button settingButton;
    private Button searchButton;
    private Button emergentButton;
    private Dialog dialog;
    private StickerList stickerList;
    private List<Sticker> stickers;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Main");
        setContentView(R.layout.activity_main);
        stickerList = new StickerList();
        stickers = stickerList.getEmergentList();

        mainButton = (Button)findViewById(R.id.MainPage);
        mainButton.setEnabled(false);

        nextPageBtn = (Button)findViewById(R.id.Category);
        nextPageBtn.setOnClickListener(changeToCategory());

        settingButton = (Button)findViewById(R.id.Setting);
        settingButton.setOnClickListener(changeToSettings());

        searchButton = (Button)findViewById(R.id.Search);
        searchButton.setOnClickListener(changeToSearch());

        emergentButton = (Button)findViewById(R.id.emergentStickers);
        emergentButton.setText("緊急的Stickers   |   " + stickers.size());
        emergentButton.setOnClickListener(showEmergentSticker());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public void onRestart() {
        super.onRestart();
        stickers = stickerList.getEmergentList();
        emergentButton.setText("緊急的Stickers   |   " + stickers.size());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private View.OnClickListener changeToCategory(){
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this  , category_list.class);
                startActivity(intent);
            }
        };
    }

    private View.OnClickListener changeToSettings(){
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this  , Settings.class);
                startActivity(intent);
            }
        };
    }

    private View.OnClickListener changeToSearch(){
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this  , search_sticker.class);
                startActivity(intent);
            }
        };
    }

    private View.OnClickListener showEmergentSticker(){
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog = new Dialog(v.getContext());
                dialog.setContentView(R.layout.popup_show_emergent_sticker);
                dialog.setTitle("緊急的Stickers ");
                ListView listView = (ListView) dialog.findViewById(R.id.listView);
                listView.setAdapter(new SearchStickerAdapter(MainActivity.this, stickers));
                listView.setOnItemClickListener(listItemListener());
                dialog.show();
            }
        };
    }

    private AdapterView.OnItemClickListener listItemListener(){
        return new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView parent, View view, int position, long id){
                Intent intent = new Intent(MainActivity.this, sticker_page.class);
                Bundle bundle = new Bundle();
                bundle.putInt("CategoryID", stickers.get(position).getCategoryID());
                bundle.putInt("StickerID", stickers.get(position).getStickerID());
                bundle.putString("State", getApplicationContext().getResources().getStringArray(R.array.sticker_state)[2]);
                intent.putExtra("Bundle", bundle);
                startActivity(intent);
            }
        };
    }
}
