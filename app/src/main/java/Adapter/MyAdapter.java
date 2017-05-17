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
import com.ooad.practice.sticker.Model.CategoryList;
import com.ooad.practice.sticker.R;
import com.ooad.practice.sticker.category_list;

import java.util.List;

/**
 * Created by mousecat1 on 2017/5/5.
 */

public class MyAdapter extends BaseAdapter {
    private LayoutInflater inflater;
    private List<Category> categoryList;
    private int[] isCreateButtonVisible;
    private int[] isEditButtonVisible;
    private int UI_vis[] = {View.GONE,View.VISIBLE};
    private CategoryList category;
    private Context context;
    private Dialog dialog;

    public MyAdapter(Context c, List<Category> categoryList, int[] isCreateButtonVisible, int[] isEditButtonVisible, Context context){
        inflater = LayoutInflater.from(c);
        this.categoryList = categoryList;
        this.isCreateButtonVisible = isCreateButtonVisible;
        this.isEditButtonVisible = isEditButtonVisible;
        this.context = context;
        this.category = CategoryList.getInstance();
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
        createButton.setOnClickListener(createListener());
        deleteButton.setOnClickListener(reconfirmListener(i));
        editButton.setOnClickListener(editListener(i));
        return view;
    }

    private View.OnClickListener createListener(){
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog = new Dialog(v.getContext());
                dialog.setContentView(R.layout.popup_category);
                dialog.setTitle("新增分類");
                Button categoryCreate = (Button)dialog.findViewById(R.id.category_create);
                Button categoryCancel = (Button)dialog.findViewById(R.id.category_cancel);
                categoryCreate.setText("新增");
                dialog.show();
                categoryCreate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        TextView title = (TextView)dialog.findViewById(R.id.category_title_input);
                        TextView description = (TextView)dialog.findViewById(R.id.category_description_input);
                        if(!title.getText().toString().isEmpty()){
                            int number = category.setCategory(new Category(0, title.getText().toString(), description.getText().toString()));
                            if(number == -1){
                                Toast.makeText(v.getContext(), "Title已經存在", Toast.LENGTH_SHORT).show();
                            }
                            else{
                                dialog.dismiss();
                                if(context instanceof category_list){
                                    ((category_list)context).updateView();
                                }
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
        };
    }

    private View.OnClickListener editListener(final int index){
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog = new Dialog(v.getContext());
                dialog.setContentView(R.layout.popup_category);
                dialog.setTitle("編輯分類");
                Button categoryEdit = (Button)dialog.findViewById(R.id.category_create);
                Button categoryCancel = (Button)dialog.findViewById(R.id.category_cancel);
                final TextView title = (TextView)dialog.findViewById(R.id.category_title_input);
                final TextView description = (TextView)dialog.findViewById(R.id.category_description_input);
                title.setText(categoryList.get(index - 1).getTitle());
                description.setText(categoryList.get(index - 1).getDescription());
                categoryEdit.setText("編輯");
                dialog.show();
                categoryEdit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(!title.getText().toString().isEmpty()){
                            int number = category.setCategory(new Category(categoryList.get(index - 1).getCategoryID(), title.getText().toString(), description.getText().toString()));
                            if(number == -1){
                                Toast.makeText(v.getContext(), "Title已經存在", Toast.LENGTH_SHORT).show();
                            }
                            else{
                                dialog.dismiss();
                                if(context instanceof category_list){
                                    ((category_list)context).updateView();
                                }
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
        };
    }

    private View.OnClickListener reconfirmListener(final int index){
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog = new Dialog(v.getContext());
                dialog.setContentView(R.layout.popup_reconfirmation);
                dialog.setTitle("確認");
                TextView text = (TextView)dialog.findViewById(R.id.reconfirm_text);
                Button confirm = (Button)dialog.findViewById(R.id.confirm);
                Button concel = (Button)dialog.findViewById(R.id.concel);
                text.setText("確定要刪除嗎?");
                dialog.show();
                confirm.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        category.deleteCategory(categoryList.get(index - 1));
                        ((category_list)context).updateView();
                        if(context instanceof category_list){
                            ((category_list)context).updateView();
                        }
                        dialog.dismiss();
                    }
                });
                concel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
            }
        };
    }
}
