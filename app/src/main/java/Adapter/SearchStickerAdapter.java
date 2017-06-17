package Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TableRow;
import android.widget.TextView;

import com.ooad.practice.sticker.Bean.Category;
import com.ooad.practice.sticker.Bean.Sticker;
import com.ooad.practice.sticker.Bean.Tag;
import com.ooad.practice.sticker.Model.StickerList;
import com.ooad.practice.sticker.Model.TagList;
import com.ooad.practice.sticker.R;

import java.util.List;

/**
 * Created by mousecat1 on 2017/6/18.
 */

public class SearchStickerAdapter extends BaseAdapter {
    private LayoutInflater inflater;
    private Context context;
    private List<Sticker> stickers;
    private TagList tagList;
    private TextView[] tags;


    public SearchStickerAdapter(Context c, List<Sticker> stickerList){
        inflater = LayoutInflater.from(c);
        this.stickers = stickerList;
        this.context = c;
        tagList = new TagList();
    }

    @Override
    public int getCount() {
        return stickers.size();
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
        view = inflater.inflate(R.layout.search_sticker_item,viewGroup,false);
        TextView title, description;
        title = (TextView)view.findViewById(R.id.sticker_title);
        description = (TextView)view.findViewById(R.id.sticker_deadline);
        title.setText(stickers.get(i).getTitle());
        description.setText(stickers.get(i).getDeadline());
        handleTags(view, i);
        return view;
    }

    private void handleTags(View view, int position){
        List<Tag> tagTemp;
        tags = new TextView[8];
        tagTemp = tagList.getTagListByStickerId(stickers.get(position).getStickerID());
        String headString = "stickerTags";
        String temp;
        for (int i = 0; i < 8; i++) {
            temp = headString + i;
            tags[i] = (TextView) view.findViewById(context.getResources().getIdentifier(temp, "id", context.getPackageName()));
            tags[i].setText("");
            tags[i].setVisibility(View.INVISIBLE);
        }

        for(int i = 0; i < tagTemp.size(); i++){
            List<Integer> color = tagTemp.get(i).getColor();
            tags[i].setBackgroundColor(android.graphics.Color.argb(255, color.get(0), color.get(1), color.get(2)));
            tags[i].setVisibility(View.VISIBLE);
        }
    }
}
