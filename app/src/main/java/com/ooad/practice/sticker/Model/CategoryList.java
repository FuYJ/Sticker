package com.ooad.practice.sticker.Model;

import com.ooad.practice.sticker.Bean.Category;
import com.ooad.practice.sticker.Database.Database;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fuyiru on 2017/5/5.
 */

public class CategoryList {
    private static CategoryList instance;
    private Database db;

    private CategoryList(){

    }

    public static CategoryList getInstance(){
        if(instance == null){
            instance = new CategoryList();
        }
        return instance;
    }

    public List<Category> getCategoryList(String keyword){
        ArrayList<Category> result = new ArrayList<>();

        return  result;
    }

    public void setCategory(Category category){

    }

    public void deleteCategory(Category category) {

    }
}
