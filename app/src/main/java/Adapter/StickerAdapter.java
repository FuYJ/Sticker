package Adapter;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.ooad.practice.sticker.Bean.Category;
import com.ooad.practice.sticker.Bean.Sticker;
import com.ooad.practice.sticker.Model.CategoryList;
import com.ooad.practice.sticker.Model.StickerList;
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
    private List<Sticker> stickerList;
    private int[] isCreateButtonVisible;
    private int[] isEditButtonVisible;
    private Context context;
    private StickerList sticker;
    private int UI_vis[] = {View.GONE,View.VISIBLE};

    public StickerAdapter(Context c, List<Sticker> stickerList, int[] isCreateButtonVisible, int[] isEditButtonVisible){
        inflater = LayoutInflater.from(c);
        this.stickerList = stickerList;
        this.isCreateButtonVisible = isCreateButtonVisible;
        this.isEditButtonVisible = isEditButtonVisible;
        this.context = c;
        this.sticker = StickerList.getInstance();
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
        Button createButton, deleteButton, editButton;
        TableRow tags;
        title = (TextView)view.findViewById(R.id.sticker_title);
        description = (TextView)view.findViewById(R.id.sticker_deadline);
        createButton = (Button)view.findViewById(R.id.create_sticker);
        deleteButton = (Button)view.findViewById(R.id.delete_sticker);
        editButton = (Button)view.findViewById(R.id.edit_sticker);
        tags = (TableRow)view.findViewById(R.id.sticker_tags);
        if(i > 0){
            title.setText(stickerList.get(i - 1).getTitle());
            description.setText(stickerList.get(i - 1).getDeadline());
        }
        createButton.setVisibility(UI_vis[isCreateButtonVisible[i]]);
        title.setVisibility(UI_vis[(isCreateButtonVisible[i] + 1) % 2]);
        description.setVisibility(UI_vis[(isCreateButtonVisible[i] + 1) % 2]);
        tags.setVisibility(UI_vis[(isCreateButtonVisible[i] + 1) % 2]);
        deleteButton.setVisibility(UI_vis[isEditButtonVisible[i]]);
        editButton.setVisibility(UI_vis[isEditButtonVisible[i]]);
        createButton.setOnClickListener(createListener());
        return view;
    }

    private View.OnClickListener createListener(){
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, sticker_page.class);
                context.startActivity(intent);
            }
        };
    }
}
