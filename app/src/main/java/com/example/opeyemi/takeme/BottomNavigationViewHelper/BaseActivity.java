
package com.example.opeyemi.takeme.BottomNavigationViewHelper;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.example.opeyemi.takeme.MainActivity;
import com.example.opeyemi.takeme.R;

public abstract class BaseActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    protected BottomNavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getContentViewId());

        navigationView = findViewById(R.id.navigation);
        BottomNavigationViewHelper.removeShiftMode(navigationView);
        navigationView.setOnNavigationItemSelectedListener(this);


    }

    @Override
    protected void onStart() {
        super.onStart();
        updateNavigationBarState();
    }

    // Remove inter-activity transition to avoid screen tossing on tapping bottom navigation items
    @Override
    protected void onPause() {
        super.onPause();
        overridePendingTransition(0, 0);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull final MenuItem item) {

        navigationView.postDelayed(new Runnable() {
            @Override
            public void run() {

                switch (item.getItemId()) {
                    case R.id.navigation_home:
                        startActivity(new Intent (getApplicationContext(), MainActivity.class));
                        break;

                    case R.id.navigation_add:
                        startActivity( new Intent (getApplicationContext(), com.example.opeyemi.takeme.NewPostActivity.class));
                        break;

                    case R.id.navigation_chat:
                        startActivity(new Intent (getApplicationContext(), com.example.opeyemi.takeme.ChatActivity.class));
                        break;

                    case R.id.navigation_profile:
                        startActivity(new Intent (getApplicationContext(), com.example.opeyemi.takeme.ProfileActivity.class));
                        break;
                }

                finish();

            }
        },300);
        return true;
    }

    private void updateNavigationBarState(){
        int actionId = getNavigationMenuItemId();
        selectBottomNavigationBarItem(actionId);
    }

    void selectBottomNavigationBarItem(int itemId) {
        Menu menu = navigationView.getMenu();
        for (int i = 0, size = menu.size(); i < size; i++) {
            MenuItem item = menu.getItem(i);
            boolean shouldBeChecked = item.getItemId() == itemId;
            if (shouldBeChecked) {
                item.setChecked(true);
                break;
            }
        }
    }

    public abstract int getContentViewId();

    public abstract int getNavigationMenuItemId();

}


