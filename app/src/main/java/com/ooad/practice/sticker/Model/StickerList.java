package com.ooad.practice.sticker.Model;

import com.ooad.practice.sticker.Bean.Sticker;
import com.ooad.practice.sticker.Database.Database;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fuyiru on 2017/5/5.
 */

public class StickerList {
    private static StickerList instance;
    private Database db;

    private StickerList(){

    }

    public static StickerList getInstance(){
        if(instance == null){
            instance = new StickerList();
        }
        return instance;
    }

    public List<Sticker> getStickerList(String keyword){
        ArrayList<Sticker> result = new ArrayList<>();

        return result;
    }

    public void setSticker(Sticker sticker){

    }

    public void deleteSticker(Sticker sticker){

    }

    public List<Sticker> getEmergentList(){
        ArrayList<Sticker> result = new ArrayList<>();

        return result;
    }

    public List<Sticker> getLatestStickers(){
        ArrayList<Sticker> result = new ArrayList<>();

        return result;
    }

    public String calculateDate(){
        return null;
    }
}
