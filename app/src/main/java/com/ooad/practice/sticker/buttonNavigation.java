package com.ooad.practice.sticker;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;
import android.view.View;


/**
 * Created by mousecat1 on 2017/5/5.
 */

public class buttonNavigation extends Activity implements View.OnClickListener{
    Button btn = (Button)findViewById(R.id.Category);

    @Override
    public void onClick(View view) {
/*        Button btn = (Button) view;

        Intent intent = new Intent();
        intent.setClass(MainActivity.this  , category_list.class);
        startActivity(intent);*/
    }
}
