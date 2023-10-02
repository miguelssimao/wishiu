package com.example.wishiu;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class PagesAdapter extends FragmentPagerAdapter {
    public PagesAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int index) {

        switch (index) {
            case 0:
                // Wishes
                return new FragmentWishes();
            case 1:
                // Achieved
                return new FragmentAchieved();
            case 2:
                // Scheduled
                return new FragmentScheduled();
        }

        return null;
    }

    @Override
    public int getCount() {
        return 3;
    }
}
