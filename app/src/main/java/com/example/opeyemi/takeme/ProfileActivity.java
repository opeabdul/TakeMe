package com.example.opeyemi.takeme;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.PagerTabStrip;
import android.support.v4.view.ViewPager;
import android.widget.TextView;

import com.example.opeyemi.takeme.ProfileFragments.JobPostFragment;
import com.example.opeyemi.takeme.ProfileFragments.dummy.DummyContent;
import com.example.opeyemi.takeme.bottomNavigationViewHelper.BaseActivity;
import com.example.opeyemi.takeme.ProfileFragments.ProfileTabFragmentPagerAdapter;
import com.example.opeyemi.takeme.common.Common;

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
    public void onListFragmentInteraction(DummyContent.DummyItem item) {

    }
}
