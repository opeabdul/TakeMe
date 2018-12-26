package com.example.opeyemi.takeme;



import android.os.Bundle;
import android.widget.TextView;
import com.example.opeyemi.takeme.bottomNavigationViewHelper.BaseActivity;


public class NewPostActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        TextView chatTitle =  findViewById(R.id.newPostActivityTitle);
        chatTitle.setText("Take Me New Post");



    }

    //overriding BaseActivity method
    public int getContentViewId(){
        return R.layout.activity_new_post;
    }

    public int getNavigationMenuItemId(){
        return R.id.navigation_add;
    }
}
