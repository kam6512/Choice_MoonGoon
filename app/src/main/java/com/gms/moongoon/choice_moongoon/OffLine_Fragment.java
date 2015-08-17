package com.gms.moongoon.choice_moongoon;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Debug;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

/**
 * Created by user on 2015-08-08.
 */
public class OffLine_Fragment extends Fragment {
    int position = 0;
    ImageView backGround_offline, fish_offline;
    AnimationDrawable backGround_offline_frameAnimationDrawable, fish_offline_frameAnimationDrawable;
    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.offline, container, false);

        backGround_offline = (ImageView) view.findViewById(R.id.backGround_offline);
        backGround_offline.setBackgroundResource(R.drawable.main_online);
        fish_offline = (ImageView) view.findViewById(R.id.fish_offline);
        fish_offline.setBackgroundResource(R.drawable.fish_anim);

        Log.e("getNativeHeapSize", "" + Debug.getNativeHeapSize());
        Log.e("getNativeHeapFreeSize", "" + Debug.getNativeHeapFreeSize());
        Log.e("getNativeHeapAllocatedSize", "" + Debug.getNativeHeapAllocatedSize());

//        backGround_offline_frameAnimationDrawable  = (AnimationDrawable)backGround_offline.getBackground();
//        backGround_offline_frameAnimationDrawable.start();
        fish_offline_frameAnimationDrawable =(AnimationDrawable)fish_offline.getBackground();
        fish_offline_frameAnimationDrawable.start();

        view.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        return view;
    }




}
