package com.gms.moongoon.choice_moongoon;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

/**
 * Created by user on 2015-08-08.
 */
public class OffLine_Fragment extends Fragment {
    int position = 0;
    ImageView imageView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.offline, container, false);

//        position = getArguments().getInt("position");

        imageView = (ImageView) view.findViewById(R.id.backGround_offline);

        view.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        return view;
    }
}
