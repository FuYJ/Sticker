package com.ooad.practice.sticker.Controller;

import com.ooad.practice.sticker.Bean.Sticker;
import com.ooad.practice.sticker.Model.StickerList;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fuyiru on 2017/5/5.
 */

public class StickerHandler {

    public StickerHandler(){

    }

    public List<Sticker> getStickerList(String keyword, List<String> options){
        List<Sticker> stickerList;
        stickerList = StickerList.getInstance().getStickerList(keyword);
        return stickerList;
    }

    public void editSticker(Sticker sticker){
        StickerList.getInstance().setSticker(sticker);
    }

    public void addSticker(Sticker sticker){
        StickerList.getInstance().setSticker(sticker);
    }

    public void deleteSticker(Sticker sticker){
        StickerList.getInstance().deleteSticker(sticker);
    }

    public List<Sticker> getEmergentList(){
        List<Sticker> stickerList;
        stickerList = StickerList.getInstance().getEmergentList();
        return stickerList;
    }

    public List<Sticker> getLatestStickers(){
        List<Sticker> stickerList;
        stickerList = StickerList.getInstance().getLatestStickers();
        return stickerList;
    }

}
