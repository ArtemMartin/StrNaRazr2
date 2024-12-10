package com.example.strnarazr2;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class PageAdapter extends FragmentPagerAdapter {
    private final int numOfTabs;

    public PageAdapter(FragmentManager fm, int numOfTabs) {
        super(fm);
        this.numOfTabs = numOfTabs;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return new OZFragment();
        } else if (position == 1) {
            return new OZFragment();
        } else if (position == 2) {
            return new OZFragment();
        } else if (position == 3) {
            return new OZFragment();
        }else {
            return null;
        }
    }


    @Override
    public int getCount() {
        return numOfTabs;
    }
}
