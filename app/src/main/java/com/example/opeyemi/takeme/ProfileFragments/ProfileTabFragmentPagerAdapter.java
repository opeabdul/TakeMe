package com.example.opeyemi.takeme.ProfileFragments;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import com.example.opeyemi.takeme.ProfileFragments.BlankFragment;
import com.example.opeyemi.takeme.ProfileFragments.JobPostFragment;
import com.example.opeyemi.takeme.R;
import com.google.android.gms.common.util.Strings;

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
                return new JobPostFragment();
            case 1:
                return new BlankFragment();
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
                return mContext.getString(R.string.job_post_tab_title);
            case 1:
                return  mContext.getString(R.string.details_tab_title);
            default:
                return null;
        }
    }
}
