package Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.TableRow;
import android.widget.TextView;

import com.ooad.practice.sticker.Bean.Category;
import com.ooad.practice.sticker.Bean.Sticker;
import com.ooad.practice.sticker.Bean.Tag;
import com.ooad.practice.sticker.Model.StickerList;
import com.ooad.practice.sticker.Model.TagList;
import com.ooad.practice.sticker.R;

import java.util.List;

import static android.R.attr.category;

/**
 * Created by mousecat1 on 2017/6/12.
 */

public class TagAdapter extends BaseAdapter {
    private LayoutInflater inflater;
    private List<Tag> stickerTagList;
    private List<Tag> tagList;
    private TagList tag;
    private boolean[] isChosen;
    private Context context;

    public TagAdapter(Context c, List<Tag> stickerTagList){
        inflater = LayoutInflater.from(c);
        this.stickerTagList = stickerTagList;
        this.tag = new TagList();
        tagList = tag.getTagList();
        this.context = c;
        checkChosen();
    }

    @Override
    public int getCount() {
        return tagList.size();
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
        List<Integer> color = tagList.get(i).getColor();
        CheckBox chosen;
        TextView title;
        ImageButton edit;
        chosen = (CheckBox)view.findViewById(R.id.isChosen);
        title = (TextView)view.findViewById(R.id.tagTitle);
//        edit = (ImageButton)view.findViewById(R.id.editTag);
        chosen.setBackgroundColor(android.graphics.Color.argb(255, color.get(0), color.get(1), color.get(2)));
        chosen.setChecked(isChosen[i]);
        title.setText(tagList.get(i).getTitle());
        title.setEnabled(false);
        return view;
    }

    private void checkChosen(){
        int index = 0;
        isChosen = new boolean[tagList.size()];
        for(int i = 0; i < tagList.size(); i++){
            isChosen[i] = false;
        }

        for(int i = 0; i < stickerTagList.size(); i+=0){
            if(stickerTagList.get(i).getTagID().equals(tagList.get(index).getTagID())){
                isChosen[index] = true;
                i++;
            }
            index++;
            if(index > tagList.size()){
                break;
            }
        }
    }
}
