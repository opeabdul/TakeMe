package com.example.opeyemi.takeme;


import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.example.opeyemi.takeme.BottomNavigationViewHelper.BaseActivity;


public class ChatActivity extends BaseActivity {

    private BottomNavigationView mBottomNavigationView;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        TextView chatTitle =  findViewById(R.id.chatActivityTitle);
        chatTitle.setText("Take Me Chat");

    }

    //overriding BaseActivity method
    public int getContentViewId(){
        return R.layout.activity_chat;
    }

    public int getNavigationMenuItemId(){
        return R.id.navigation_chat;
    }


}
