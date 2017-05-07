package com.ooad.practice.sticker.Controller;

import com.ooad.practice.sticker.Bean.Category;
import com.ooad.practice.sticker.Model.CategoryList;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fuyiru on 2017/5/5.
 */

public class CategoryHandler {

    public CategoryHandler(){

    }

    public void addCategory(Category category){
        CategoryList.getInstance().setCategory(category);
    }

    public void editCategory(Category category){
        CategoryList.getInstance().setCategory(category);
    }

    public void deleteCategory(Category category){
        CategoryList.getInstance().deleteCategory(category);
    }

    public List<Category> getCategoryList(String keyword){
        List<Category> categoryList;
        categoryList = CategoryList.getInstance().getCategoryList(keyword);
        return categoryList;
    }
}
