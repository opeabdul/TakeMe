package com.example.opeyemi.takeme.ProfileFragments;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.opeyemi.takeme.R;

public class ProfileTabFragmentPagerAdapter extends FragmentPagerAdapter {

    private Context mContext;
    public ProfileTabFragmentPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        mContext = context;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new ProfileDetailsFragment();
            case 1:
                return new JobPostFragment();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position){
            case 0:
                return  mContext.getString(R.string.details_tab_title);
            case 1:
                return mContext.getString(R.string.job_post_tab_title);
            default:
                return null;
        }
    }
}
