package Adapter;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.ooad.practice.sticker.Bean.Category;
import com.ooad.practice.sticker.Bean.Sticker;
import com.ooad.practice.sticker.Bean.Tag;
import com.ooad.practice.sticker.Model.StickerList;
import com.ooad.practice.sticker.Model.TagList;
import com.ooad.practice.sticker.R;
import com.ooad.practice.sticker.category_list;
import com.ooad.practice.sticker.sticker_list;
import com.ooad.practice.sticker.sticker_page;

import java.util.List;

import ColorPickerDialog.ColorPickerDialog;

import static android.R.attr.category;

/**
 * Created by mousecat1 on 2017/6/12.
 */

public class TagAdapter extends BaseAdapter {
    private int UI_vis[] = {View.GONE,View.VISIBLE};
    private LayoutInflater inflater;
    private List<Tag> stickerTagList;
    private List<Tag> tagList;
    private TagList tag;
    private boolean[] isChosen;
    private Context context;
    private Dialog dialog;
    private int[] isVisible;
    private int offset;
    private int tagIndex;

    public TagAdapter(Context c, List<Tag> stickerTagList, int offset){
        inflater = LayoutInflater.from(c);
        this.stickerTagList = stickerTagList;
        this.tag = new TagList();
        tagList = tag.getTagList();
        this.context = c;
        this.offset = offset;
        this.tagIndex = tagIndex;
        checkChosen();
        chekUIVisible();
    }

    @Override
    public int getCount() {
        return tagList.size() + offset;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        view = inflater.inflate(R.layout.tag_item,viewGroup,false);
        Button remove;
        CheckBox chosen;
        TextView title;
        ImageButton edit;
        remove = (Button)view.findViewById(R.id.removeTag);
        chosen = (CheckBox)view.findViewById(R.id.isChosen);
        title = (TextView)view.findViewById(R.id.tagTitle);
        edit = (ImageButton)view.findViewById(R.id.editTag);
        if(i - offset >= 0){
            List<Integer> color = tagList.get(i - offset).getColor();
            chosen.setBackgroundColor(android.graphics.Color.argb(255, color.get(0), color.get(1), color.get(2)));
            title.setText(tagList.get(i - offset).getTitle());
            edit.setOnClickListener(editButtonListener(i - offset));
        }
        remove.setVisibility(UI_vis[(isVisible[i] + 1) % 2]);
        chosen.setVisibility(UI_vis[isVisible[i]]);
        title.setVisibility(UI_vis[isVisible[i]]);
        edit.setVisibility(UI_vis[isVisible[i]]);
        remove.setOnClickListener(cancelTagButtonListener());
        chosen.setChecked(isChosen[i]);
        chosen.setEnabled(false);
        return view;
    }

    private View.OnClickListener cancelTagButtonListener(){
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((sticker_page)context).cancelSelectedTag();
            }
        };
    }

    private View.OnClickListener editButtonListener(final int index){
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog = new Dialog(v.getContext());
                dialog.setContentView(R.layout.edit_delete_tag);
                dialog.setTitle("修改");
                List<Integer> colorList = tagList.get(index).getColor();
                TextView title = (TextView)dialog.findViewById(R.id.editTag_title);
                TextView color = (TextView)dialog.findViewById(R.id.editTag_color);
                Button editButton = (Button)dialog.findViewById(R.id.edit_Tag);
                Button deleteButton = (Button)dialog.findViewById(R.id.delete_Tag);
                title.setText(tagList.get(index).getTitle());
                color.setBackgroundColor(android.graphics.Color.argb(255, colorList.get(0), colorList.get(1), colorList.get(2)));
                color.setOnClickListener(colorListener(color, index));
                editButton.setOnClickListener(editItemListener(index));
                deleteButton.setOnClickListener(deleteItemListener(index));
                dialog.show();
            }
        };
    }

    private View.OnClickListener colorListener(final TextView color, final int index){
        return new View.OnClickListener() {
            public void onClick(View v) {
                Dialog d;
                List<Integer> colorList = tagList.get(index).getColor();
                d = new ColorPickerDialog(v.getContext(), android.graphics.Color.argb(255, colorList.get(0), colorList.get(1), colorList.get(2)), "11",
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

    private View.OnClickListener deleteItemListener(final int index){
        return new View.OnClickListener() {
            public void onClick(View v) {
                tag.deleteTag(tagList.get(index));
                dialog.dismiss();
                ((sticker_page)context).showTagList();
            }
        };
    }

    private View.OnClickListener editItemListener(final int index){
        return new View.OnClickListener() {
            public void onClick(View v) {
                TextView title = (TextView)dialog.findViewById(R.id.editTag_title);
                TextView color = (TextView)dialog.findViewById(R.id.editTag_color);
                ColorDrawable cd = (ColorDrawable) color.getBackground();
                Tag temp = new Tag(tagList.get(index).getTagID(), title.getText().toString(), Color.red(cd.getColor()), Color.green(cd.getColor()), Color.blue(cd.getColor()));
                tag.setTag(temp);
                dialog.dismiss();
                ((sticker_page)context).showTagList();
            }
        };
    }

    private void chekUIVisible(){
        isVisible = new int[tagList.size() + offset];
        for(int i = 0; i < tagList.size() + offset; i++){
            isVisible[i] = 0;
        }
        for(int i = offset; i < tagList.size() + offset; i++){
            isVisible[i] = 1;
        }
    }

    private void checkChosen(){
        int id = 0;
        isChosen = new boolean[tagList.size() + offset];
        for(int i = 0; i < tagList.size() + offset; i++){
            isChosen[i] = false;
        }

        for(int i = 0; i < stickerTagList.size(); i++){
            id = stickerTagList.get(i).getTagID();
            for(int j = 0; j < tagList.size(); j++){
                if(id == tagList.get(j).getTagID()){
                    isChosen[j + offset] = true;
                }
            }
        }
    }
}
