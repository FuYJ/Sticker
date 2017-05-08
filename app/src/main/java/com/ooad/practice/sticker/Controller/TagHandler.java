package com.ooad.practice.sticker.Controller;

import com.ooad.practice.sticker.Bean.Tag;
import com.ooad.practice.sticker.Model.TagList;

import java.util.List;

/**
 * Created by fuyiru on 2017/5/5.
 */

public class TagHandler {

    public List<Tag> getTagList(){
        List<Tag> tagList;
        tagList = TagList.getInstance().getTagList();
        return tagList;
    }

    public void addTag(Tag tag){
        TagList.getInstance().setTag(tag);
    }

    public void editTag(Tag tag){
        TagList.getInstance().setTag(tag);
    }

    public void deleteTag(Tag tag){
        TagList.getInstance().deleteTag(tag);
    }
}
