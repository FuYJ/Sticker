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
    private IDataAccessObject categoryDAO;

    public CategoryList(){
        categoryDAO = new CategoryAccessObject(MainApplication.getContext());
    }

    public CategoryList(IDataAccessObject dao){
        this.categoryDAO = dao;
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
        try{
            JSONArray categoriesWithSameTitle = categoryDAO.retrieveWhere(where);
            int categoryIDWithSameTitle = categoriesWithSameTitle.getJSONObject(0).getInt(IDataAccessObject.CATEGORY_ID);
            if(categoriesWithSameTitle.length() > 0 &&  categoryIDWithSameTitle != category.getCategoryID());
                return -1;
        } catch (JSONException e){
            Log.e(this.getClass().toString(), e.getMessage());
        }

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
