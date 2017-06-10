package com.ooad.practice.sticker;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
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
import com.ooad.practice.sticker.Model.StickerList;

import java.util.List;

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
    private Dialog dialog;
    private TextView[] tags;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sticker);
        category = (Category)getIntent().getBundleExtra("Bundle").getSerializable("Category");
        sticker = (Sticker)getIntent().getBundleExtra("Bundle").getSerializable("Sticker");
        categories = (Spinner)findViewById(R.id.categoryList);
        categories.setOnItemSelectedListener(spinnerSelectedItem());
        state = (String)getIntent().getBundleExtra("Bundle").getSerializable("State");
        stickerList = StickerList.getInstance();
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
        handleTags();
        updateView();
    }

    private void updateView(){
        updateSpinner();
        if(table[0].equals(state)){
            setUIEnable(true);
            left.setText(CREATE);
            right.setText(CANCEL);
        }
        else if(table[1].equals(state)){
            setUIEnable(true);
            setUIText();
            left.setText(MODIFY);
            right.setText(CANCEL);
        }
        else if(table[2].equals(state)){
            setUIEnable(false);
            setUIText();
            left.setText(MODIFY);
            right.setText(DELETE);
        }
    }

    private void updateSpinner(){
        categoryList = CategoryList.getInstance().getCategoryList(null);
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

    private void handleTags(){
        tags = new TextView[8];
        String headString = "stickerTags";
        String temp;
        List<Tag> tagList = sticker.getTagList();
        for (int i = 0; i < 2; i++) {
            temp = headString + i;
            tags[i] = (TextView) findViewById(this.getResources().getIdentifier(temp, "id", getPackageName()));
            tags[i].setBackgroundColor(0xFFFF0000);
            tags[i].setOnClickListener(tagListener());
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

    private View.OnClickListener tagListener(){
        return new View.OnClickListener() {
            public void onClick(View v) {
                final TextView t = (TextView)findViewById(v.getId());
                showTagDialog(t);
            }
        };
    }

    private void showTagDialog(TextView text){
        dialog = new Dialog(this);
        dialog.setContentView(R.layout.popup_tag);
        dialog.setTitle("Tags");
        TextView color = (TextView)dialog.findViewById(R.id.tagColor_inpput);
        TextView title = (TextView)dialog.findViewById(R.id.tagTitle_input);
        ListView tagList = (ListView)dialog.findViewById(R.id.tagList);
        color.setOnClickListener(colorListener(color));
        dialog.show();
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
        stickerList.setSticker(temp);
        this.finish();
    }

    private void modifySticker(){
        sticker = new Sticker(sticker.getStickerID(), category.getCategoryID(), title.getText().toString(),
                description.getText().toString(), deadline.getText().toString(),
                remind.getText().toString(), isFinished.isChecked());
        stickerList.setSticker(sticker);
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
