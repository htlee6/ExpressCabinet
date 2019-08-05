package com.lihaotian.kuaidi;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.app.ActionBar;
import android.view.View;
import android.content.Intent;

import com.lihaotian.config.LoginInfo;

public class MenuActivity extends AppCompatActivity {

    public final static  String EXTRA_MESSAGE = "message";
    public String transfer1 ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        Intent intent = getIntent();
        transfer1 = intent.getStringExtra(EXTRA_MESSAGE);
        /*ActionBar actionBar = getActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        显示有误，于是李昊天把这部分注释掉*/
    }

    //点击投递按钮，跳转到DeliverOneActivity
    public void onDeliverOne(View view){
        Intent intent2 = new Intent(this,DeliverOneActivity.class);
        intent2.putExtra(DeliverOneActivity.EXTRA_MESSAGE,transfer1);
        startActivity(intent2);
    }

    //点击取件按钮，跳转到GetExpActivity
    public void onGetExp(View view){
        Intent intent = new Intent(this,GetExpActivity.class);
        startActivity(intent);
    }
    public void onLogout(View view){
        this.finish();
    }
}
