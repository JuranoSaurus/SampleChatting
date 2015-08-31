package com.juranoaa.chatting.service;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.juranoaa.chatting.common.Constants;

/**
 * Created by slhyv on 8/31/2015.
 * When reboot, start ChatService
 */
public class ChatWaker extends BroadcastReceiver {
    private static final String TAG = ChatWaker.class.getSimpleName();

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i(TAG, "onReceive invoked!");

        if (intent.getAction() != null) {
            if(intent.getAction().equals("android.intent.action.BOOT_COMPLETED")) { //when reboot
                Log.v(TAG, "BOOT_COMPLETED received");

                //start service
                Intent i = new Intent();
                i.setClassName(Constants.Package.PUSH_PACKAGE_NAME, Constants.Package.PUSH_SERVICE_NAME);
                i.setAction(".START");
                ComponentName componentName = context.startService(i);
                Log.v(TAG, "result of startService: "+componentName.toShortString());

            }
        }

    }
}
