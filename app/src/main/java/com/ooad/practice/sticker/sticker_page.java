package com.ooad.practice.sticker;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.ooad.practice.sticker.Bean.Category;
import com.ooad.practice.sticker.Bean.Sticker;
import com.ooad.practice.sticker.Bean.Tag;
import com.ooad.practice.sticker.Model.CategoryList;
import com.ooad.practice.sticker.Model.Reminder;
import com.ooad.practice.sticker.Model.StickerList;
import com.ooad.practice.sticker.Model.TagList;

import java.util.ArrayList;
import java.util.List;

import Adapter.StickerAdapter;
import Adapter.TagAdapter;
import ColorPickerDialog.ColorPickerDialog;

/**
 * Created by mousecat1 on 2017/5/31.
 */

public class sticker_page extends ActionBarActivity {
    private String[] table;
    private final String CREATE = "新增";
    private final String MODIFY = "修改";
    private final String DELETE = "刪除";
    private final String CANCEL = "取消";
    private final String CONFIRM = "確認";
    private int UI_vis[] = {View.INVISIBLE,View.VISIBLE};
    private StickerList stickerList;
    private List<Category> categoryList;
    private Spinner categories;
    private TextView title;
    private TextView description;
    private TextView deadline;
    private TextView remind;
    private CheckBox isFinished;
    private Button left;
    private Button right;
    private String state;
    private Sticker sticker;
    private Category category;
    private CategoryList categoryController;
    private Dialog dialog;
    private TextView[] tags;
    private TagList tagList;
    private List<Tag> stickerTagList;
    private int selectedTagID;
    private int stickerID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sticker);
        category = (Category)getIntent().getBundleExtra("Bundle").getSerializable("Category");
        categories = (Spinner)findViewById(R.id.categoryList);
        categories.setOnItemSelectedListener(spinnerSelectedItem());
        state = (String)getIntent().getBundleExtra("Bundle").getSerializable("State");
        stickerList = new StickerList();
        tagList = new TagList();
        categoryController = new CategoryList();
        category = categoryController.getCategoryByCategoryId((int)getIntent().getBundleExtra("Bundle").getSerializable("CategoryID"));
        stickerID = (int)getIntent().getBundleExtra("Bundle").getSerializable("StickerID");
        if(stickerID > 0)
            sticker = stickerList.getStickerByStickerId(stickerID);
        else
            sticker = null;
        table = getApplicationContext().getResources().getStringArray(R.array.sticker_state);
        title = (TextView)findViewById(R.id.stickerTitle_input);
        description = (TextView)findViewById(R.id.stickerDescription_input);
        deadline = (TextView)findViewById(R.id.stickerDeadline_input);
        remind = (TextView)findViewById(R.id.stickerRemind_input);
        isFinished = (CheckBox)findViewById(R.id.finished_check);
        left = (Button)findViewById(R.id.leftButton);
        left.setOnClickListener(leftButtonListener());
        right = (Button)findViewById(R.id.rightButton);
        right.setOnClickListener(rightButtonListener());
        if(sticker != null){
            stickerTagList = tagList.getTagListByStickerId(sticker.getStickerID());
            isFinished.setChecked(sticker.getFinished());
        }
        else{
            stickerTagList = new ArrayList<Tag>();
        }
        updateView();
    }

    private void updateView(){
        updateSpinner();
        if(table[0].equals(state)){
            setUIEnable(true);
            left.setText(CREATE);
            right.setText(CANCEL);
            handleTags(true);
        }
        else if(table[1].equals(state)){
            setUIEnable(true);
            setUIText();
            left.setText(MODIFY);
            right.setText(CANCEL);
            handleTags(true);
        }
        else if(table[2].equals(state)){
            setUIEnable(false);
            setUIText();
            left.setText(MODIFY);
            right.setText(DELETE);
            handleTags(false);
        }
    }

    private void updateSpinner(){
        categoryList = new CategoryList().getCategoryList(null);
        String[] temp = new String[categoryList.size()];
        int selectedIndex = 0;
        for(int i = 0; i < categoryList.size(); i++){
            temp[i] = categoryList.get(i).getTitle();
            if(temp[i].equals(category.getTitle())){
                selectedIndex = i;
            }
        }
        ArrayAdapter<String> list = new ArrayAdapter<String>(sticker_page.this, android.R.layout.simple_list_item_activated_1, temp);
        categories.setAdapter(list);
        categories.setSelection(selectedIndex);
    }

    private void handleTags(boolean isEnable){
        int index;
        tags = new TextView[8];
        String headString = "stickerTags";
        String temp;
        for (int i = 0; i < 8; i++) {
            temp = headString + i;
            tags[i] = (TextView) findViewById(this.getResources().getIdentifier(temp, "id", getPackageName()));
            tags[i].setOnClickListener(tagListener(this.getResources().getIdentifier(temp, "id", getPackageName())));
            tags[i].setText("");
        }

        for(index = 0; index < stickerTagList.size(); index++){
            tags[index].setEnabled(isEnable);
            tags[index].setVisibility(UI_vis[1]);
            List<Integer> color = stickerTagList.get(index).getColor();
            tags[index].setBackgroundColor(android.graphics.Color.argb(255, color.get(0), color.get(1), color.get(2)));
        }
        if(index < 8 && isEnable){
            tags[index].setEnabled(isEnable);
            tags[index].setVisibility(UI_vis[1]);
            tags[index].setBackgroundColor(0xFFFFFFFF);
            tags[index].setText("+");
            index++;
        }

        for(index = index; index < 8; index++){
            tags[index].setEnabled(false);
            tags[index].setVisibility(UI_vis[0]);
        }
    }

    private void setUIEnable(boolean setValue){
        categories.setEnabled(setValue);
        title.setEnabled(setValue);
        description.setEnabled(setValue);
        deadline.setEnabled(setValue);
        remind.setEnabled(setValue);
    }

    private void setUIText(){
        title.setText(sticker.getTitle());
        description.setText(sticker.getDescription());
        deadline.setText(sticker.getDeadline());
        remind.setText(sticker.getRemindTime());
    }

    private View.OnClickListener tagListener(final int textID){
        return new View.OnClickListener() {
            public void onClick(View v) {
                selectedTagID = textID;
                showTagDialog(v.getId());
            }
        };
    }

    private void showTagDialog(int textID){
        dialog = new Dialog(this);
        dialog.setContentView(R.layout.popup_tag);
        dialog.setTitle("Tags");
        TextView color = (TextView)dialog.findViewById(R.id.tagColor_inpput);
        TextView title = (TextView)dialog.findViewById(R.id.tagTitle_input);
        color.setOnClickListener(colorListener(color));
        title.setOnKeyListener(createTagLinstener());
        showTagList();
        ListView tagList = (ListView)dialog.findViewById(R.id.tagList);
        tagList.setOnItemClickListener(listItemListener(textID));
        dialog.show();
    }

    private View.OnKeyListener createTagLinstener(){
        return new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN) {
                    TextView color = (TextView)dialog.findViewById(R.id.tagColor_inpput);
                    TextView title = (TextView)dialog.findViewById(R.id.tagTitle_input);
                    if(title.getText().toString().equals("")){

                    }
                    else{
                        ColorDrawable cd = (ColorDrawable) color.getBackground();
                        Tag temp = new Tag(0, title.getText().toString(), Color.red(cd.getColor()), Color.green(cd.getColor()), Color.blue(cd.getColor()));
                        tagList.setTag(temp);
                    }
                    showTagList();
                    return true;
                }
                return false;
            }
        };
    }

    private AdapterView.OnItemClickListener listItemListener(final int textID){
        return new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView parent, View view, int position, long id){
                TextView text = (TextView)findViewById(textID);
                List<Tag> allTagList = tagList.getTagList();
                int offset = 0;
                if(text.getText().toString().equals("+")){
                    offset = 0;
                }
                else{
                    offset = 1;
                }
                if(checkChosen(allTagList.get(position - offset).getTagID())){
                    List<Integer> color = allTagList.get(position - offset).getColor();
                    text.setBackgroundColor(android.graphics.Color.argb(255, color.get(0), color.get(1), color.get(2)));
                    Tag temp = allTagList.get(position - offset);
                    if(offset == 0)
                        stickerTagList.add(temp);
                    else{
                        stickerTagList.set(Integer.parseInt(text.getTag().toString()), temp);
                    }
                    handleTags(true);
                    dialog.dismiss();
                }
                else{
                    Toast.makeText(view.getContext(), "已經選擇過了", Toast.LENGTH_SHORT).show();
                }
            }
        };
    }

    private boolean checkChosen(int id){
        for(int i = 0; i < stickerTagList.size(); i++){
            if(stickerTagList.get(i).getTagID() == id){
                return false;
            }
        }
        return true;
    }

    public void showTagList(){
        TextView text = (TextView)findViewById(selectedTagID);
        int chooseTagState = 0;
        if(text.getText().toString().equals("+"))
            chooseTagState = 0;
        else
            chooseTagState = 1;
        ListView tagList = (ListView)dialog.findViewById(R.id.tagList);
        tagList.setAdapter(new TagAdapter(sticker_page.this, stickerTagList, chooseTagState));
    }

    public void cancelSelectedTag(){
        TextView text = (TextView)findViewById(selectedTagID);
        int index = Integer.parseInt(text.getTag().toString());
        stickerTagList.remove(index);
        handleTags(true);
        dialog.dismiss();
    }

    private View.OnClickListener colorListener(final TextView color){
        return new View.OnClickListener() {
            public void onClick(View v) {
                Dialog d;
                d = new ColorPickerDialog(v.getContext(), 0xFFFF0000, "11",
                        new ColorPickerDialog.OnColorChangedListener() {
                            public void colorChanged(int color2)
                            {
                                color.setBackgroundColor(color2);
                            }
                        });
                d.show();
            }
        };
    }

    private AdapterView.OnItemSelectedListener spinnerSelectedItem(){
        return new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                category = categoryList.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        };
    }

    private View.OnClickListener leftButtonListener(){
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(table[0].equals(state)){
                    createSticker();
                }
                else if(table[1].equals(state)){
                    modifySticker();
                    state = getApplicationContext().getResources().getStringArray(R.array.sticker_state)[2];
                    updateView();
                }
                else if(table[2].equals(state)){
                    state = getApplicationContext().getResources().getStringArray(R.array.sticker_state)[1];
                    updateView();
                }
            }
        };
    }

    private View.OnClickListener rightButtonListener(){
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(table[0].equals(state)){
                    finish();
                }
                else if(table[1].equals(state)){
                    state = getApplicationContext().getResources().getStringArray(R.array.sticker_state)[2];
                    updateView();
                }
                else if(table[2].equals(state)){
                    deleteSticker();
                }
            }
        };
    }

    private void createSticker(){
        Sticker temp = new Sticker(0, category.getCategoryID(), title.getText().toString(),
                description.getText().toString(), deadline.getText().toString(),
                remind.getText().toString(), isFinished.isChecked());
        int stickerID = stickerList.setSticker(temp);
        for(int i = 0; i < stickerTagList.size(); i++)
            stickerList.setTagToSticker(stickerList.getStickerByStickerId(stickerID), stickerTagList.get(i));
        this.finish();
    }

    private void modifySticker(){
        boolean temp;
        List<Tag> oldStickerTagList = tagList.getTagListByStickerId(sticker.getStickerID());
        sticker = new Sticker(sticker.getStickerID(), category.getCategoryID(), title.getText().toString(),
                description.getText().toString(), deadline.getText().toString(),
                remind.getText().toString(), isFinished.isChecked());
        stickerList.setSticker(sticker);
        for(int i = 0; i < oldStickerTagList.size(); i++){
            temp = true;
            int tagID = oldStickerTagList.get(i).getTagID();
            for(int j = 0; j < stickerTagList.size(); j++){
                if(tagID == stickerTagList.get(j).getTagID()){
                    temp = false;
                    break;
                }
            }
            if(temp){
                stickerList.deleteTagFromSticker(sticker, oldStickerTagList.get(i));
            }
        }
        for(int i = 0; i < stickerTagList.size(); i++){
            temp = true;
            int tagID = stickerTagList.get(i).getTagID();
            for(int j = 0; j < oldStickerTagList.size(); j++){
                if(tagID == oldStickerTagList.get(j).getTagID()){
                    temp = false;
                    break;
                }
            }
            if(temp){
                stickerList.setTagToSticker(sticker, stickerTagList.get(i));
            }
        }
    }

    private void deleteSticker(){
        dialog = new Dialog(this);
        dialog.setContentView(R.layout.popup_reconfirmation);
        dialog.setTitle(CONFIRM);
        TextView text = (TextView)dialog.findViewById(R.id.reconfirm_text);
        Button confirm = (Button)dialog.findViewById(R.id.confirm);
        Button concel = (Button)dialog.findViewById(R.id.concel);
        text.setText("確定要刪除嗎?");
        dialog.show();
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stickerList.deleteSticker(sticker);
                dialog.dismiss();
                finish();
            }
        });
        concel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }
}
