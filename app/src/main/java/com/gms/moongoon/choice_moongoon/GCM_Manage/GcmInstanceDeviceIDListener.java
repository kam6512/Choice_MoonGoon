package com.gms.moongoon.choice_moongoon.GCM_Manage;

import android.content.Intent;

import com.google.android.gms.iid.InstanceIDListenerService;

//최근 GCM 서비스에서는 Instance ID를 사용한다. 이것은 Android, iOS의 고유한 ID로 GCM에서 디바이스를 구분하기 위한 것이다.
//이 파일은 Instance ID를 획득하기 위한 리스너를 상속받아서 토큰을 갱신하는 코드를 추가한다.

public class GcmInstanceDeviceIDListener extends InstanceIDListenerService {
    private static final String TAG ="MyInstanceIDListener" ;

    @Override
    public void onTokenRefresh() {
        startService(new Intent(this, GcmRegisterIntentService.class));
    }
}