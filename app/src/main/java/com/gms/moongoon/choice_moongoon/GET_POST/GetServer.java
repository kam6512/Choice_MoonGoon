package com.gms.moongoon.choice_moongoon.GET_POST;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;

import com.gms.moongoon.choice_moongoon.OnLine_Fragment;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

/**
 * Created by user on 2015-08-18.
 */
public class GetServer extends AsyncTask<Void, Void, String> {
    @Override
    protected String doInBackground(Void... voids) {

        HttpClient httpClient = new DefaultHttpClient();
        HttpGet httpGet = new HttpGet(SERVER_SECURITY.getUrl);

        try {
            HttpResponse httpResponse = httpClient.execute(httpGet);
            HttpEntity httpEntity = httpResponse.getEntity();

            if (httpEntity != null) {
                //connect
            }else{
                //connect but no Data
            }
            String res = EntityUtils.toString(httpEntity);
            Log.e("GetServer", res);
            return res;
        } catch (Exception e) {
            Log.e("GetServer", "is null");
        } finally {
            httpClient = null;
            httpGet.abort();
        }

        return null;
    }

    @Override
    protected void onPostExecute(String res) {
        super.onPostExecute(res);
        Bundle bundle = new Bundle();
        Message message;
        if (res != null){

            bundle.putString("res", res);
            message = Message.obtain(OnLine_Fragment.handler, 0);
            message.setData(bundle);
        }else{
            message = Message.obtain(OnLine_Fragment.handler, 1);
            bundle.putString("res", null);
            message.setData(bundle);
        }
        OnLine_Fragment.handler.sendMessage(message);
    }
}
