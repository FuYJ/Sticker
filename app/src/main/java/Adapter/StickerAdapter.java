package Adapter;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.ooad.practice.sticker.Bean.Category;
import com.ooad.practice.sticker.Bean.Sticker;
import com.ooad.practice.sticker.Bean.Tag;
import com.ooad.practice.sticker.Model.CategoryList;
import com.ooad.practice.sticker.Model.StickerList;
import com.ooad.practice.sticker.Model.TagList;
import com.ooad.practice.sticker.R;
import com.ooad.practice.sticker.category_list;
import com.ooad.practice.sticker.sticker_list;
import com.ooad.practice.sticker.sticker_page;

import java.util.List;

/**
 * Created by mousecat1 on 2017/5/23.
 */

public class StickerAdapter extends BaseAdapter {
    private LayoutInflater inflater;
    private Dialog dialog;
    private TextView[] tags;
    private List<Sticker> stickerList;
    private List<Tag> tagList;
    private int[] isCreateButtonVisible;
    private Context context;
    private StickerList sticker;
    private TagList tag;
    private Category category;
    private int UI_vis[] = {View.GONE, View.VISIBLE, View.INVISIBLE};

    public StickerAdapter(Context c, List<Sticker> stickerList, int[] isCreateButtonVisible, Category category){
        inflater = LayoutInflater.from(c);
        this.stickerList = stickerList;
        this.isCreateButtonVisible = isCreateButtonVisible;
        this.context = c;
        this.sticker = new StickerList();
        this.category = category;
        tag = new TagList();
    }

    @Override
    public int getCount() {
        return stickerList.size() + 1;
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
        view = inflater.inflate(R.layout.sticker_item,viewGroup,false);
        TextView title, description;
        ImageButton editButton;
        Button createButton;
        TableRow tagRow;
        title = (TextView)view.findViewById(R.id.sticker_title);
        description = (TextView)view.findViewById(R.id.sticker_deadline);
        createButton = (Button)view.findViewById(R.id.create_sticker);
        editButton = (ImageButton)view.findViewById(R.id.edit_sticker);
        tagRow = (TableRow)view.findViewById(R.id.stickerTagRow);
        if(i > 0){
            title.setText(stickerList.get(i - 1).getTitle());
            description.setText(stickerList.get(i - 1).getDeadline());
            handleTags(view, i);
        }
        createButton.setVisibility(UI_vis[isCreateButtonVisible[i]]);
        createButton.setOnClickListener(createListener());
        title.setVisibility(UI_vis[(isCreateButtonVisible[i] + 1) % 2]);
        description.setVisibility(UI_vis[(isCreateButtonVisible[i] + 1) % 2]);
        tagRow.setVisibility(UI_vis[(isCreateButtonVisible[i] + 1) % 2]);
        editButton.setVisibility(UI_vis[(isCreateButtonVisible[i] + 1) % 2]);
        editButton.setOnClickListener(editListener(i));
        return view;
    }

    private void handleTags(View view, int position){
        tags = new TextView[8];
        tagList = tag.getTagListByStickerId(stickerList.get(position - 1).getStickerID());
        String headString = "stickerTags";
        String temp;
        for (int i = 0; i < 8; i++) {
            temp = headString + i;
            tags[i] = (TextView) view.findViewById(context.getResources().getIdentifier(temp, "id", context.getPackageName()));
            tags[i].setText("");
            tags[i].setVisibility(UI_vis[2]);
        }

        for(int i = 0; i < tagList.size(); i++){
            List<Integer> color = tagList.get(i).getColor();
            tags[i].setBackgroundColor(android.graphics.Color.argb(255, color.get(0), color.get(1), color.get(2)));
            tags[i].setVisibility(UI_vis[1]);
        }
    }

    private View.OnClickListener createListener(){
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, sticker_page.class);
                Bundle bundle = new Bundle();
                bundle.putInt("CategoryID", category.getCategoryID());
                bundle.putInt("StickerID", -1);
                bundle.putString("State", context.getResources().getStringArray(R.array.sticker_state)[0]);
                intent.putExtra("Bundle", bundle);
                context.startActivity(intent);
            }
        };
    }

    private View.OnClickListener editListener(final int position){
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, sticker_page.class);
                Bundle bundle = new Bundle();
                bundle.putInt("CategoryID", category.getCategoryID());
                bundle.putInt("StickerID", stickerList.get(position - 1).getStickerID());
                bundle.putString("State", context.getResources().getStringArray(R.array.sticker_state)[2]);
                intent.putExtra("Bundle", bundle);
                context.startActivity(intent);
            }
        };
    }
}
