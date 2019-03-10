package com.modori.kwonkiseokee.AUto.ViewPager;

import com.modori.kwonkiseokee.AUto.tab1_frag;
import com.modori.kwonkiseokee.AUto.tab2_frag;
import com.modori.kwonkiseokee.AUto.tab3_frag;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

public class ViewPagerAdapter extends FragmentStatePagerAdapter {

    public ViewPagerAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                tab1_frag tab1 = new tab1_frag();
                return tab1;

            case 1:
                tab2_frag tab2 = new tab2_frag();
                return tab2;

            case 2:
                tab3_frag tab3 = new tab3_frag();
                return tab3;

            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return 3;
    }
}
