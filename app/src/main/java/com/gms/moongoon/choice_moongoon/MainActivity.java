package com.gms.moongoon.choice_moongoon;

import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.gms.moongoon.choice_moongoon.guillotine.animation.GuillotineAnimation;

import it.neokree.materialtabs.MaterialTab;
import it.neokree.materialtabs.MaterialTabHost;
import it.neokree.materialtabs.MaterialTabListener;

public class MainActivity extends AppCompatActivity implements MaterialTabListener {

    Toolbar toolbar;

    FrameLayout root;

    View contentHamburger;

    ViewPager pager;
    ViewpagerAdapter pagerAdapter;
    MaterialTabHost tabHost;
    Resources res;

    TextView title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        res = getResources();

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        root = (FrameLayout) findViewById(R.id.root);
        contentHamburger = (View) findViewById(R.id.content_hamburger);

        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setTitle(null);
        }

        View Menu = LayoutInflater.from(this).inflate(R.layout.guillotine, null);
        root.addView(Menu);

        new GuillotineAnimation.GuillotineBuilder(Menu, Menu.findViewById(R.id.guillotine_hamburger), contentHamburger)
                .setStartDelay(250)
                .setActionBarViewForAnimation(toolbar)
                .build();

        tabHost = (MaterialTabHost) Menu.findViewById(R.id.tabHost);
        pager = (ViewPager) Menu.findViewById(R.id.pager);
        pagerAdapter = new ViewpagerAdapter(getSupportFragmentManager());
        pager.setAdapter(pagerAdapter);
        pager.setOffscreenPageLimit(3);
        pager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                tabHost.setSelectedNavigationItem(position);
            }


        });

        for (int i = 0; i < pagerAdapter.getCount(); i++) {
            tabHost.addTab(tabHost.newTab()
                    .setIcon(getIcon(i))
                    .setTabListener(this));
        }

        title = (TextView)Menu.findViewById(R.id.title);
    }

    @Override
    public void onTabSelected(MaterialTab materialTab) {
        pager.setCurrentItem(materialTab.getPosition());
    }

    @Override
    public void onTabReselected(MaterialTab materialTab) {

    }

    @Override
    public void onTabUnselected(MaterialTab materialTab) {

    }



    class ViewpagerAdapter extends FragmentStatePagerAdapter {
        public ViewpagerAdapter(FragmentManager fm) {
            super(fm);
        }



        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return new OnLine_Fragment();
                case 1:
                    return new OffLine_Fragment();
                case 2:
                    return new OffLine_Fragment();

                default:
                    return new OnLine_Fragment();
            }

        }

        @Override
        public int getCount() {
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "tab1";
                case 1:
                    return "tab2";
                case 2:
                    return "tab3";
                default:
                    return null;
            }
        }
    }

    Drawable getIcon(int position) {
        switch (position) {
            case 0:
                return res.getDrawable(R.drawable.ic_online);
            case 1:
                return res.getDrawable(R.drawable.ic_offline);
            case 2:
                return res.getDrawable(R.drawable.ic_settings);

        }
        return null;
    }
}
