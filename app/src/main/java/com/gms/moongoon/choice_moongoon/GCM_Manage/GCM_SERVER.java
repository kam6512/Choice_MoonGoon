package com.gms.moongoon.choice_moongoon.GCM_Manage;


import android.os.Message;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.google.android.gcm.server.MulticastResult;
import com.google.android.gcm.server.Result;
import com.google.android.gcm.server.Sender;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 2015-08-17.
 */
public class GCM_SERVER {

    private String token = null;
    private String API = null;
    private String arg1 = null;
    private String arg2 = null;
    private String message = null;
    private boolean isQuestion;

    public GCM_SERVER(String token,String message,boolean isQuestion){
        API = "AIzaSyChGfoWsxgpkc74g8Bx0Zd_naAumBAQ5_0";
        this.token = token;
        this.message = message;
        this.isQuestion = isQuestion;
//        token = "e-siVUhNN1E:APA91bFxWcrKPWl40uMZQafcw2uVXSBhscUPPngZTf0GTLkKmrCTW_ca8JjMgacb0L1nAyfeqZrDVYwpJJwpNbOGkMlEGnryHKr72zCwXpIbUFQP6iHjtqeMUCytDULIFd9YOYlLd4LM";
        PostThread postThread = new PostThread();
        postThread.start();
    }

    class PostThread extends Thread{

        @Override
        public void run() {
            try {
                arg1 = message;
                arg1 = URLEncoder.encode(arg1,"UTF-8");
                if (isQuestion){
                    arg2 = "yes";
                }else {
                    arg2 = "no";
                }

            }catch (Exception e){

            }
            Sender sender = new Sender(API);

            com.google.android.gcm.server.Message message = new com.google.android.gcm.server.Message.Builder().addData("msg", arg1).addData("isQuestion",arg2).build();
            List<String> list = new ArrayList<String>();
            list.add(token);

            MulticastResult multicastResult = null;

            try {
                multicastResult = sender.send(message,list,5);
            }catch (Exception e){
                e.printStackTrace();
            }

            if(multicastResult !=null){
                List<Result> resultList = multicastResult.getResults();

                for (Result result : resultList){
                    Log.e("result", "" +  result.getMessageId());

                }
            }

        }
    }
}
