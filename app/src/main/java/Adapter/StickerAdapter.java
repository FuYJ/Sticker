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
    private Dialog dialog;
    private List<Sticker> stickerList;
    private int[] isCreateButtonVisible;
    private int[] isEditButtonVisible;
    private Context context;
    private StickerList sticker;
    private Category category;
    private int UI_vis[] = {View.GONE,View.VISIBLE};

    public StickerAdapter(Context c, List<Sticker> stickerList, int[] isCreateButtonVisible, int[] isEditButtonVisible, Category category){
        inflater = LayoutInflater.from(c);
        this.stickerList = stickerList;
        this.isCreateButtonVisible = isCreateButtonVisible;
        this.isEditButtonVisible = isEditButtonVisible;
        this.context = c;
        this.sticker = new StickerList();
        this.category = category;
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
        createButton.setOnClickListener(createListener());
        title.setVisibility(UI_vis[(isCreateButtonVisible[i] + 1) % 2]);
        description.setVisibility(UI_vis[(isCreateButtonVisible[i] + 1) % 2]);
        tags.setVisibility(UI_vis[(isCreateButtonVisible[i] + 1) % 2]);
        deleteButton.setVisibility(UI_vis[isEditButtonVisible[i]]);
        deleteButton.setOnClickListener(deleteListener(i));
        editButton.setVisibility(UI_vis[isEditButtonVisible[i]]);
        editButton.setOnClickListener(editListener(i));
        return view;
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
                bundle.putSerializable("Category", category);
                bundle.putSerializable("Sticker", stickerList.get(position - 1));
                bundle.putString("State", context.getResources().getStringArray(R.array.sticker_state)[1]);
                intent.putExtra("Bundle", bundle);
                context.startActivity(intent);
            }
        };
    }

    private View.OnClickListener deleteListener(final int position){
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog = new Dialog(v.getContext());
                dialog.setContentView(R.layout.popup_reconfirmation);
                dialog.setTitle("確認");
                TextView text = (TextView)dialog.findViewById(R.id.reconfirm_text);
                Button confirm = (Button)dialog.findViewById(R.id.confirm);
                Button concel = (Button)dialog.findViewById(R.id.concel);
                text.setText("確定要刪除嗎?");
                dialog.show();
                confirm.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        sticker.deleteSticker(stickerList.get(position - 1));
                        ((sticker_list)context).update();
                        if(context instanceof category_list){
                            ((category_list)context).updateView();
                        }
                        dialog.dismiss();
                    }
                });
                concel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
            }
        };
    }
}
