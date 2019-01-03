package com.example.opeyemi.takeme;



import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.opeyemi.takeme.bottomNavigationViewHelper.BaseActivity;


public class NewPostActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        ImageView jobImageView = findViewById(R.id.new_job_image_view);

    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.new_post_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int menuID = item.getItemId();
        switch (menuID){
            case R.id.create_job_post:
                createNewPost();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void createNewPost() {
    }

    //overriding BaseActivity method
    public int getContentViewId(){
        return R.layout.activity_new_post;
    }

    public int getNavigationMenuItemId(){
        return R.id.navigation_add;
    }
}
