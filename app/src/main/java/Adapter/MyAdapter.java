package Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

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
    public View getView(int i, View view, ViewGroup viewGroup) {
        TextView name;
        Button createButton;
        view = inflater.inflate(R.layout.category_item,viewGroup,false);
        name = (TextView) view.findViewById(R.id.category_name);
        createButton = (Button) view.findViewById(R.id.create);
        name.setText(category_name[i]);
        createButton.setVisibility(UI_vis[isButtonVisible[i]]);
        name.setVisibility(UI_vis[(isButtonVisible[i] + 1) % 2]);
        return view;
    }
}
