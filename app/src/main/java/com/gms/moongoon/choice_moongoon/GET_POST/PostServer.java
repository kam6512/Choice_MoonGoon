package com.gms.moongoon.choice_moongoon.GET_POST;

import android.os.AsyncTask;
import android.util.Log;

import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

import java.net.URI;
import java.net.URLEncoder;
import java.util.ArrayList;

/**
 * Created by user on 2015-08-18.
 */
public class PostServer extends AsyncTask<String, Void, Void> {

    String gcmID;
    String age;
    String sex;

    @Override
    protected Void doInBackground(String... params) {
        Log.e("doInBackground", params[0] + "/" + params[1] + "/" + params[2]);
        gcmID = params[0];
        sex = params[1];
        age = params[2];

        ArrayList<NameValuePair> postUserInfoVales = new ArrayList<NameValuePair>();
        postUserInfoVales.add(new BasicNameValuePair("age", age));
        postUserInfoVales.add(new BasicNameValuePair("sex", sex));
        postUserInfoVales.add(new BasicNameValuePair("gcm", gcmID));

        HttpClient httpClient = new DefaultHttpClient();
        HttpPost httpPost = new HttpPost(SERVER_SECURITY.postUrl);

        HttpParams httpParams = httpClient.getParams();
        httpPost.setParams(httpParams);

        HttpConnectionParams.setConnectionTimeout(httpParams, 5000);
        HttpConnectionParams.setSoTimeout(httpParams, 5000);

        try {
            UrlEncodedFormEntity urlEncodedFormEntity = new UrlEncodedFormEntity(postUserInfoVales, "UTF-8");
            httpPost.setEntity(urlEncodedFormEntity);
            httpClient.execute(httpPost);
        } catch (Exception e) {

        } finally {
            httpPost.abort();
        }

//        HttpParams httpParams

        return null;
    }
}
