package Adapter;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.ooad.practice.sticker.Bean.Category;
import com.ooad.practice.sticker.Controller.CategoryHandler;
import com.ooad.practice.sticker.R;
import com.ooad.practice.sticker.category_list;

import java.util.List;

/**
 * Created by mousecat1 on 2017/5/5.
 */

public class MyAdapter extends BaseAdapter {
    private LayoutInflater inflater;
    List<Category> categoryList;
    int[] isCreateButtonVisible;
    int[] isEditButtonVisible;
    int UI_vis[] = {View.GONE,View.VISIBLE};
    private CategoryHandler handler = new CategoryHandler();
    private Context context;

    public MyAdapter(Context c, List<Category> categoryList, int[] isCreateButtonVisible, int[] isEditButtonVisible, Context context){
        inflater = LayoutInflater.from(c);
        this.categoryList = categoryList;
        this.isCreateButtonVisible = isCreateButtonVisible;
        this.isEditButtonVisible = isEditButtonVisible;
        this.context = context;
    }
    @Override
    public int getCount() {
        return categoryList.size() + 1;
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
        TextView name;
        Button createButton, deleteButton, editButton;
        view = inflater.inflate(R.layout.category_item,viewGroup,false);
        name = (TextView) view.findViewById(R.id.category_name);
        createButton = (Button) view.findViewById(R.id.create);
        deleteButton = (Button) view.findViewById(R.id.delete);
        editButton = (Button) view.findViewById(R.id.edit);
        if(i > 0){
            name.setText(categoryList.get(i - 1).getTitle());
        }
        createButton.setVisibility(UI_vis[isCreateButtonVisible[i]]);
        name.setVisibility(UI_vis[(isCreateButtonVisible[i] + 1) % 2]);
        deleteButton.setVisibility(UI_vis[isEditButtonVisible[i]]);
        editButton.setVisibility(UI_vis[isEditButtonVisible[i]]);
        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(v.getContext());
                dialog.setContentView(R.layout.popup_create_category);
                dialog.setTitle("新增分類");
                Button categoryCreate = (Button)dialog.findViewById(R.id.category_create);
                Button categoryCancel = (Button)dialog.findViewById(R.id.category_cancel);
                dialog.show();
                categoryCreate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        TextView title = (TextView)dialog.findViewById(R.id.category_title_input);
                        TextView description = (TextView)dialog.findViewById(R.id.category_description_input);
                        if(!title.getText().toString().isEmpty()){
                            handler.addCategory(new Category(0, title.getText().toString(), description.getText().toString()));
                            dialog.dismiss();
                            ((category_list)context).updateView();
                            if(context instanceof category_list){
                                ((category_list)context).updateView();
                            }
                        }
                        else
                            Toast.makeText(v.getContext(), "Title沒有輸入", Toast.LENGTH_SHORT).show();
                    }
                });
                categoryCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
            }
        });
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handler.deleteCategory(categoryList.get(i - 1));
                ((category_list)context).updateView();
                if(context instanceof category_list){
                    ((category_list)context).updateView();
                }
            }
        });
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(v.getContext());
                dialog.setContentView(R.layout.popup_create_category);
                dialog.setTitle("編輯分類");
                Button categoryCreate = (Button)dialog.findViewById(R.id.category_create);
                Button categoryCancel = (Button)dialog.findViewById(R.id.category_cancel);
                final TextView title = (TextView)dialog.findViewById(R.id.category_title_input);
                final TextView description = (TextView)dialog.findViewById(R.id.category_description_input);
                title.setText(categoryList.get(i - 1).getTitle());
                description.setText(categoryList.get(i - 1).getDescription());
                categoryCreate.setText("編輯");
                dialog.show();
                categoryCreate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(!title.getText().toString().isEmpty()){
                            handler.editCategory(new Category(categoryList.get(i - 1).getCategoryID(), title.getText().toString(), description.getText().toString()));
                            dialog.dismiss();
                            ((category_list)context).updateView();
                            if(context instanceof category_list){
                                ((category_list)context).updateView();
                            }
                        }
                        else
                            Toast.makeText(v.getContext(), "Title沒有輸入", Toast.LENGTH_SHORT).show();
                    }
                });
                categoryCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
            }
        });
        return view;
    }

    private void updateView(){
        categoryList = handler.getCategoryList(null);

    }
}
