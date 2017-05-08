package com.ooad.practice.sticker.Model;

import com.ooad.practice.sticker.Bean.Tag;
import com.ooad.practice.sticker.Database.Database;
import com.ooad.practice.sticker.Database.IDatabase;
import com.ooad.practice.sticker.MainApplication;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fuyiru on 2017/5/4.
 */

public class TagList {
    private static TagList instance;
    private IDatabase db;

    private TagList() {
        db = new Database(MainApplication.getContext());
    }

    public static TagList getInstance(){
        if(instance == null){
            instance = new TagList();
        }
        return instance;
    }

    public List<Tag> getTagList(){
        ArrayList<Tag> tagList = new ArrayList<>();

        return tagList;
    }

    public void setTag(Tag tag){

    }

    public void deleteTag(Tag tag){

    }
}
