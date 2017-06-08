package com.ooad.practice.sticker.Model;

import android.content.ContentValues;
import android.database.Cursor;
import android.util.Log;

import com.ooad.practice.sticker.Bean.Category;
import com.ooad.practice.sticker.Database.CategoryAccessObject;
import com.ooad.practice.sticker.Database.Database;
import com.ooad.practice.sticker.Database.IDataAccessObject;
import com.ooad.practice.sticker.Database.IDatabase;
import com.ooad.practice.sticker.MainApplication;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fuyiru on 2017/5/5.
 */

public class CategoryList {
    private static CategoryList instance;
    private IDataAccessObject categoryDAO;

    private CategoryList(){
        categoryDAO = new CategoryAccessObject(MainApplication.getContext());
    }

    private CategoryList(IDataAccessObject dao){
        this.categoryDAO = dao;
    }

    public static CategoryList getInstance(){
        if(instance == null){
            instance = new CategoryList();
        }
        return instance;
    }

    public static CategoryList getInstance(IDataAccessObject dao){
        if(instance == null){
            instance = new CategoryList(dao);
        }
        return instance;
    }

    public List<Category> getCategoryList(String keyword){
        List<Category> result = new ArrayList<>();
        JSONArray jArr;

        if(keyword == null){
            jArr = categoryDAO.retrieveAll();
        }
        else{
            String where = Database.CATEGORY_TITLE + " LIKE \"%" + keyword + "%\"";
            jArr = categoryDAO.retrieveWhere(where);
        }

        try{
            for(int i = 0; i < jArr.length(); i++){
                JSONObject jObj = jArr.getJSONObject(i);
                Category category = new Category(jObj);
                result.add(category);
            }
        }
        catch (JSONException e){
            Log.e(this.getClass().toString(), e.getMessage());
        }

        return result;
    }

    public int setCategory(Category category){
        JSONObject jObj = category.toJSONObject();
        String where = Database.CATEGORY_TITLE + " = \"" + category.getTitle() + "\"";
        if(categoryDAO.retrieveWhere(where).length() > 0)
            return -1;

        if(category.getCategoryID() == 0){
            categoryDAO.create(jObj);
        }
        else{
            categoryDAO.updateOne(category.getCategoryID(), jObj);
        }
        return 0;
    }

    public void deleteCategory(Category category) {
        categoryDAO.deleteOne(category.getCategoryID());
    }
}
