package edu.temple.webbrowser;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.Log;

import java.util.ArrayList;

public class TabAdapter extends FragmentStatePagerAdapter {

    FragmentManager manager;
    ArrayList<String> array;


    public TabAdapter(FragmentManager fm, ArrayList<String> array) {
        super(fm);
        manager = fm;
        this.array = array;
    }

    @Override
    public Fragment getItem(int i) {
        return BrowserFragment.newInstance(array.get(i));
    }


    @Override
    public int getCount() {
        return array.size();
    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
    }


}
