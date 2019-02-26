package com.example.opeyemi.takeme;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.PagerTabStrip;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.opeyemi.takeme.ProfileFragments.JobPostFragment;
import com.example.opeyemi.takeme.bottomNavigationViewHelper.BaseActivity;
import com.example.opeyemi.takeme.ProfileFragments.ProfileTabFragmentPagerAdapter;
import com.example.opeyemi.takeme.common.Common;
import com.example.opeyemi.takeme.model.Job;
import com.onesignal.OneSignal;

public class ProfileActivity extends BaseActivity implements JobPostFragment.OnListFragmentInteractionListener {

    private ViewPager mProfileViewpager;
    private ProfileTabFragmentPagerAdapter mPagerAdapter;
    private TabLayout mProfileTab;

    private TextView mProfileNameOfUserTextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //instantiate viewpager
        mProfileViewpager = findViewById(R.id.pager);
        mPagerAdapter = new ProfileTabFragmentPagerAdapter(getSupportFragmentManager(), this);
        mProfileViewpager.setAdapter(mPagerAdapter);
        mProfileTab = findViewById(R.id.profile_tab);
        mProfileTab.setupWithViewPager(mProfileViewpager);



        //set the name of the user on profile
        mProfileNameOfUserTextView = findViewById(R.id.profileUsernameTextView);
        mProfileNameOfUserTextView.setText(Common.currentUser.getName());

    }

    //overriding BaseActivity method
    public int getContentViewId(){
        return R.layout.activity_profile;
    }

    public int getNavigationMenuItemId(){
        return R.id.navigation_profile;
    }

    @Override
    public void onListFragmentInteraction(Job job) {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.profile_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();

        switch (itemId){
            case R.id.profile_logout_menu:
                OneSignal.setSubscription(false);
                Common.logout(ProfileActivity.this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
