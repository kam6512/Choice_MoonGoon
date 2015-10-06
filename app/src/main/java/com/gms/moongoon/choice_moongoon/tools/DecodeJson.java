package com.gms.moongoon.choice_moongoon.tools;

import android.support.design.widget.Snackbar;
import android.view.View;

import com.gms.moongoon.choice_moongoon.GCM_Manage.GCM_SERVER;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by user on 2015-09-09.
 */
public class DecodeJson {
    public void decodeJson(String userRes,View view,String message,Boolean isQuestion) {
        try {
            JSONObject root = new JSONObject(userRes);
            JSONArray rootArray = root.getJSONArray("results");
            String rootArrayNum = root.getString("num_results");

            if (Integer.parseInt(rootArrayNum) == 0) {
                Snackbar.make(view, "DB서버에 유저목록이 없습니다", Snackbar.LENGTH_SHORT).show();
            } else {
                Snackbar.make(view, rootArrayNum + " 개의 유저에게 송신합니다.", Snackbar.LENGTH_SHORT).show();
            }

            for (int i = 0; i < rootArray.length(); i++) {
                JSONObject jsonObject = rootArray.getJSONObject(i);
                String gcm = jsonObject.getString("gcm");
                String age = jsonObject.getString("age");
                String sex = jsonObject.getString("sex");
                synchronized (gcm) {
                    new GCM_SERVER(gcm,message,isQuestion);
                }
            }

        } catch (Exception e) {
            Snackbar.make(view, "DB서버에 유저목록이 없습니다", Snackbar.LENGTH_SHORT).show();

        }

    }
}
