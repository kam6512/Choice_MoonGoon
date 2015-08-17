package com.gms.moongoon.choice_moongoon;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;


import com.gms.moongoon.choice_moongoon.GCM_Manage.GCM_SERVER;
import com.gms.moongoon.choice_moongoon.GCM_Manage.GcmQuickStartPreference;
import com.gms.moongoon.choice_moongoon.GCM_Manage.GcmRegisterIntentService;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;

import it.neokree.materialtabs.MaterialTab;
import it.neokree.materialtabs.MaterialTabHost;
import it.neokree.materialtabs.MaterialTabListener;

public class MainActivity extends AppCompatActivity implements MaterialTabListener {

    Toolbar toolbar;

    ViewPager pager;
    ViewpagerAdapter pagerAdapter;
    MaterialTabHost tabHost;
    Resources res;

    private BroadcastReceiver mRegistrationBroadcastReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(android.R.style.Theme_Holo_Light_NoActionBar_TranslucentDecor);
        setContentView(R.layout.activity_main);

        res = getResources();

        toolbar = (Toolbar) findViewById(R.id.toolbar);

        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setTitle(null);
        }


        tabHost = (MaterialTabHost) findViewById(R.id.tabHost);
        pager = (ViewPager) findViewById(R.id.pager);
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

        getFirstExe();
        registBroadcastReceiver();
        getInstanceIDToken();

        new GCM_SERVER();
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

    @Override
    protected void onPause() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mRegistrationBroadcastReceiver);
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();

        LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver, new IntentFilter(GcmQuickStartPreference.REGISTRATION_READY));
        LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver, new IntentFilter(GcmQuickStartPreference.REGISTRATION_GENERATING));
        LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver, new IntentFilter(GcmQuickStartPreference.REGISTRATION_COMPLETE));
    }

    public void getInstanceIDToken() {
        if (checkPlayService()) {
            startService(new Intent(this, GcmRegisterIntentService.class));
        }
    }

    public void registBroadcastReceiver() {
        mRegistrationBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

                String action = intent.getAction();

                if (action.equals(GcmQuickStartPreference.REGISTRATION_READY)) {
                    // 액션이 READY일 경우

                } else if (action.equals(GcmQuickStartPreference.REGISTRATION_GENERATING)) {
                    // 액션이 GENERATING일 경우

                } else if (action.equals(GcmQuickStartPreference.REGISTRATION_COMPLETE)) {
                    // 액션이 COMPLETE일 경우
                    String token = intent.getStringExtra("token");
                    Snackbar.make(getWindow().getDecorView(), token, Snackbar.LENGTH_SHORT).show();
                }
            }
        };
    }

    public void getFirstExe() {
        SharedPreferences pref = getSharedPreferences("VER", 0);

        try {
            PackageManager pm = this.getPackageManager();
            PackageInfo packageInfo = pm.getPackageInfo(getPackageName(), 0);
            int VERSION = packageInfo.versionCode;
            int old_Ver = pref.getInt("version", 0);

            if (old_Ver < VERSION) {
                TextView msg = new TextView(this);
                msg.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
                msg.setTextColor(0xffffffff);
                msg.setText(R.string.update);

                new AlertDialog.Builder(this)
                        .setTitle("업데이트 내역")
                        .setView(msg)
                        .setPositiveButton("확인", null)
                        .show();

                SharedPreferences.Editor edit = pref.edit();
                edit.putInt("version", VERSION);
                edit.commit();
            }

        } catch (Exception e) {
        }
    }

    private boolean checkPlayService() {
        int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);

        if (resultCode != ConnectionResult.SUCCESS) {

            if (GooglePlayServicesUtil.isUserRecoverableError(resultCode)) {
                GooglePlayServicesUtil.getErrorDialog(resultCode, this, 90000).show();

            } else {
                Log.i("MainActivity", "This device is not supported.");
                //finish();
            }
            return false;
        }
        return true;
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
                    return new OnLine_Fragment();

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
