package com.modori.kwonkiseokee.AUto.ViewPager;

import com.modori.kwonkiseokee.AUto.tab1_frag;
import com.modori.kwonkiseokee.AUto.tab2_frag;
import com.modori.kwonkiseokee.AUto.tab3_frag;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

public class SplashViewpagerAdapter extends FragmentStatePagerAdapter {

    public SplashViewpagerAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }


    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                splashView_tab1 tab1 = new splashView_tab1();
                return tab1;

            case 1:
                splashView_tab2 tab2 = new splashView_tab2();
                return tab2;

//            case 2:
//                splashView_tab3 tab3 = new splashView_tab3();
//                return tab3;

            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return 2;
    }
}
