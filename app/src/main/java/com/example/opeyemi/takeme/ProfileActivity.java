package com.example.opeyemi.takeme;

import android.os.Bundle;
import android.widget.TextView;
import com.example.opeyemi.takeme.BottomNavigationViewHelper.BaseActivity;

public class ProfileActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        TextView chatTitle =  findViewById(R.id.profileActivityTitle);
        chatTitle.setText("Take Me Profile");

    }

    //overriding BaseActivity method
    public int getContentViewId(){
        return R.layout.activity_profile;
    }

    public int getNavigationMenuItemId(){
        return R.id.navigation_profile;
    }
}
