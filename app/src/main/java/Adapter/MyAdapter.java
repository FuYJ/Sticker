package Adapter;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.ooad.practice.sticker.Bean.Category;
import com.ooad.practice.sticker.R;

import java.util.List;

/**
 * Created by mousecat1 on 2017/5/5.
 */

public class MyAdapter extends BaseAdapter {
    private LayoutInflater inflater;
    List<Category> categoryList;
    int[] isCreateButtonVisible;
    int[] isEditButtonVisible;
    int UI_vis[] = {View.GONE,View.VISIBLE};
    public MyAdapter(Context c, List<Category> categoryList, int[] isCreateButtonVisible, int[] isEditButtonVisible){
        inflater = LayoutInflater.from(c);
        this.categoryList = categoryList;
        this.isCreateButtonVisible = isCreateButtonVisible;
        this.isEditButtonVisible = isEditButtonVisible;
    }
    @Override
    public int getCount() {
        return categoryList.size() + 1;
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
        TextView name;
        Button createButton, deleteButton, editButton;
        view = inflater.inflate(R.layout.category_item,viewGroup,false);
        name = (TextView) view.findViewById(R.id.category_name);
        createButton = (Button) view.findViewById(R.id.create);
        deleteButton = (Button) view.findViewById(R.id.delete);
        editButton = (Button) view.findViewById(R.id.edit);
        if(i > 0){
            name.setText(categoryList.get(i - 1).getTitle());
        }
        createButton.setVisibility(UI_vis[isCreateButtonVisible[i]]);
        name.setVisibility(UI_vis[(isCreateButtonVisible[i] + 1) % 2]);
        deleteButton.setVisibility(UI_vis[isEditButtonVisible[i]]);
        editButton.setVisibility(UI_vis[isEditButtonVisible[i]]);
        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(v.getContext());
                dialog.setContentView(R.layout.popup_create_category);
                dialog.setTitle("新增分類");
                Button categoryCreate = (Button)dialog.findViewById(R.id.category_create);
                Button categoryCancel = (Button)dialog.findViewById(R.id.category_cancel);
                dialog.show();
                categoryCreate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        TextView title = (TextView)dialog.findViewById(R.id.category_title_input);
                        Toast.makeText(v.getContext(), "Create " + title.getText().toString(), Toast.LENGTH_SHORT).show();
                    }
                });
                categoryCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
            }
        });
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(), "Delete " + i, Toast.LENGTH_SHORT).show();
            }
        });
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(), "Edir " + i, Toast.LENGTH_SHORT).show();
            }
        });
        return view;
    }
}
