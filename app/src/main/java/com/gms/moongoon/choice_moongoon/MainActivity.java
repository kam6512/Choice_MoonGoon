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
import android.os.Handler;
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
import com.gms.moongoon.choice_moongoon.GET_POST.PostServer;
import com.gms.moongoon.choice_moongoon.tools.DecodeJson;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;

import org.json.JSONArray;
import org.json.JSONObject;

import it.neokree.materialtabs.MaterialTab;
import it.neokree.materialtabs.MaterialTabHost;
import it.neokree.materialtabs.MaterialTabListener;

public class MainActivity extends AppCompatActivity implements MaterialTabListener {

    Toolbar toolbar;
    View view;

    ViewPager pager;
    ViewpagerAdapter pagerAdapter;
    MaterialTabHost tabHost;
    Resources res;

    private BroadcastReceiver mRegistrationBroadcastReceiver;

    SharedPreferences pref;
    SharedPreferences.Editor edit;
    String sex = null;
    String age = null;
    String token = null;

    public static String userRes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(android.R.style.Theme_Holo_Light_NoActionBar_TranslucentDecor);
        setContentView(R.layout.activity_main);
        view = getWindow().getDecorView();

        pref = getSharedPreferences("VER", MODE_PRIVATE);
        edit = pref.edit();

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


//        new GCM_SERVER();
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
                    token = intent.getStringExtra("token");
                    String[] params = new String[3];
                    params[0] = token;
                    params[1] = sex;
                    params[2] = age;


                    Log.e("params", params[0] + "/" + params[1] + "/" + params[2]);


                    if (token != null) {
                        edit.putBoolean("Enable", true);
                        edit.putString("GCM", token);
                        edit.putString("sex", sex);
                        edit.putString("age", age);

                        edit.commit();

                        new PostServer().execute(params);
                    } else {
                        Snackbar.make(getWindow().getDecorView(), "GCM코드를 받아오지 못했습니다. (2초 뒤 종료)", Snackbar.LENGTH_SHORT).show();
                        edit.putBoolean("Enable", false);
                        edit.commit();

                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                finish();
                            }
                        }, 2000);
                    }


                }
            }
        };
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        System.gc();
    }

    public void getFirstExe() {


        try {
            PackageManager pm = this.getPackageManager();
            PackageInfo packageInfo = pm.getPackageInfo(getPackageName(), 0);
            int VERSION = packageInfo.versionCode;
            int old_Ver = pref.getInt("version", 0);


            if (old_Ver < VERSION) {
                TextView msg = new TextView(this);
                msg.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
                msg.setTextColor(0x00000000);
                msg.setText(R.string.update);

                new AlertDialog.Builder(this)
                        .setTitle("업데이트 내역")
                        .setView(msg)
                        .setPositiveButton("확인", null)
                        .show();

                edit.putInt("version", VERSION);
                edit.commit();

            }

            Boolean enable = pref.getBoolean("Enable", false);
            if (enable) {
                Snackbar.make(getWindow().getDecorView(), "인증된 사용자 입니다", Snackbar.LENGTH_SHORT).show();
            } else {
                startActivityForResult(new Intent(MainActivity.this, FirstUserInfo.class), 1);
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


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
        Snackbar.make(view, "onActivityResult", Snackbar.LENGTH_SHORT).show();
        switch (resultCode) {
            case 0:

                data.getExtras().getString("res");
                new DecodeJson().decodeJson(MainActivity.userRes, view);
                break;
        }

        if (resultCode == RESULT_OK) {
            sex = data.getStringExtra("sex");
            age = data.getStringExtra("age");

            Log.e("params", sex + "/" + age);

            registBroadcastReceiver();
            getInstanceIDToken();
        }
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
                    return new OffLine_Fragment();
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
