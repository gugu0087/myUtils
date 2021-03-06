package com.lock.lifesensexu.myapputils.viewPagerModels;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;


import com.lock.lifesensexu.myapputils.R;

import java.util.ArrayList;
import java.util.List;


public class ViewPagerUseActivity extends FragmentActivity {
    private String[] mTitles = new String[] { "简介", "评价", "相关" };
    private ViewPager mViewPager;
    private FragmentPagerAdapter mAdapter;
    private TabFragment[] mFragments = new TabFragment[mTitles.length];
    private List<ColorTrackView> mTabs = new ArrayList<ColorTrackView>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_pager_use);
        initViews();
        initDatas();
        initEvents();
    }
    private void initEvents()
    {
        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener()
        {
            @Override
            public void onPageSelected(int position)
            {
            }

            @Override
            public void onPageScrolled(int position, float positionOffset,
                                       int positionOffsetPixels)
            {
                if (positionOffset > 0)
                {
                    ColorTrackView left = mTabs.get(position);
                    ColorTrackView right = mTabs.get(position + 1);

                    left.setDirection(1);
                    right.setDirection(0);
                    left.setProgress( 1-positionOffset);
                    right.setProgress(positionOffset);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state)
            {

            }
        });

    }

    private void initDatas()
    {

        for (int i = 0; i < mTitles.length; i++)
        {
            mFragments[i] = (TabFragment) TabFragment.newInstance(mTitles[i]);
        }

        mAdapter = new FragmentPagerAdapter(getSupportFragmentManager())
        {
            @Override
            public int getCount()
            {
                return mTitles.length;
            }

            @Override
            public Fragment getItem(int position)
            {
                return mFragments[position];
            }

        };

        mViewPager.setAdapter(mAdapter);
        mViewPager.setCurrentItem(0);
    }

    private void initViews()
    {
        mViewPager = (ViewPager) findViewById(R.id.view_pager);
        mTabs.add((ColorTrackView) findViewById(R.id.id_tab_01));
        mTabs.add((ColorTrackView) findViewById(R.id.id_tab_02));
        mTabs.add((ColorTrackView) findViewById(R.id.id_tab_03));
    }
}
