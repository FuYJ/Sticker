package com.ooad.practice.sticker.Model;

import android.content.ContentValues;
import android.database.Cursor;

import com.ooad.practice.sticker.Bean.Category;
import com.ooad.practice.sticker.Database.Database;
import com.ooad.practice.sticker.Database.IDatabase;
import com.ooad.practice.sticker.MainApplication;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fuyiru on 2017/5/5.
 */

public class CategoryList {
    private static CategoryList instance;
    private IDatabase db;

    private CategoryList(){
        db = new Database(MainApplication.getContext());
    }

    public static CategoryList getInstance(){
        if(instance == null){
            instance = new CategoryList();
        }
        return instance;
    }

    public List<Category> getCategoryList(String keyword){
        List<Category> result = new ArrayList<>();
        Cursor cursor;

        if(keyword == null)
            cursor = db.retrieve(Database.CATEGORY_TABLE, Database.CATEGORY_ID + Database.ORDER_ASC);
        else
            cursor = db.retrieve(Database.CATEGORY_TABLE, Database.CATEGORY_TITLE + " LIKE %" + keyword + "%", Database.CATEGORY_ID + Database.ORDER_ASC);
        int rowsNum = cursor.getCount();
        if(rowsNum > 0){
            cursor.moveToFirst();
            for(int i = 0; i < rowsNum; i++){
                Integer categoryID = cursor.getInt(0);
                String title = cursor.getString(1);
                String description = cursor.getString(2);
                Category category = new Category(categoryID, title, description);
                result.add(category);
            }
        }

        return result;
    }

    public void setCategory(Category category){
        ContentValues cv = new ContentValues();
        cv.put(Database.CATEGORY_TITLE, category.getTitle());
        cv.put(Database.CATEGORY_DESCRIPTION, category.getDescription());
        if(category.getCategoryID() == 0)
            db.create(Database.CATEGORY_TABLE, cv);
        else
            db.update(Database.CATEGORY_TABLE, Database.CATEGORY_ID + "=" + category.getCategoryID().toString(),cv);
    }

    public void deleteCategory(Category category) {
        db.delete(Database.CATEGORY_TABLE, category.getCategoryID());
    }
}
