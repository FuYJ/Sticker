package Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.ooad.practice.sticker.R;

/**
 * Created by mousecat1 on 2017/5/5.
 */

public class MyAdapter extends BaseAdapter {
    private LayoutInflater inflater;
    String [] category_name;
    int[] isButtonVisible;
    int UI_vis[] = {View.GONE,View.VISIBLE};
    public MyAdapter(Context c, String [] category_name, int[] isButtonVisible){
        inflater = LayoutInflater.from(c);
        this.category_name = category_name;
        this.isButtonVisible = isButtonVisible;
    }
    @Override
    public int getCount() {
        return category_name.length;
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
        name.setText(category_name[i]);
        createButton.setVisibility(UI_vis[isButtonVisible[i]]);
        name.setVisibility(UI_vis[(isButtonVisible[i] + 1) % 2]);
//        deleteButton.setOnClickListener();
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
