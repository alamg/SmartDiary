package com.cou.cse.alamgir.smartdiary.Fragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

/**
 * Created by Alamgir on 5/20/2017.
 */

public class SelectionPageAdapter extends FragmentPagerAdapter {
    private ArrayList<Fragment> fragmentlist=new ArrayList<>();
    private ArrayList<String> fragmenttitlelist=new ArrayList<>();

    public void addFragment(Fragment fragment, String title)
    {
        fragmentlist.add(fragment);
        fragmenttitlelist.add(title);
    }

    public SelectionPageAdapter(FragmentManager fm) {
        super(fm);
    }
    public CharSequence getPageTitle(int position)
    {
        return fragmenttitlelist.get(position);
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentlist.get(position);
    }

    @Override
    public int getCount() {
        return fragmentlist.size();
    }
}
