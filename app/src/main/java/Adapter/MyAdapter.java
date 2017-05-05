package Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.ooad.practice.sticker.R;

/**
 * Created by mousecat1 on 2017/5/5.
 */

public class MyAdapter extends BaseAdapter {
    private LayoutInflater inflater;
    String [] key;
    String [] value;
    public MyAdapter(Context c, String [] key, String [] value){
        inflater = LayoutInflater.from(c);
        this.key = key;
        this.value = value;
    }
    @Override
    public int getCount() {
        return key.length;
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
        view = inflater.inflate(R.layout.category_item,viewGroup,false);
        TextView key2,value2;
        key2 = (TextView) view.findViewById(R.id.key);
        value2 = (TextView) view.findViewById(R.id.value);
        key2.setText(key[i]);
        value2.setText(value[i]);
        return view;
    }
}
