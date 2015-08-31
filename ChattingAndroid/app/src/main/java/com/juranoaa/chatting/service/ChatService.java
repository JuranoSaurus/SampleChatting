package com.juranoaa.chatting.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.util.Log;

import com.juranoaa.chatting.common.Constants;
import com.juranoaa.chatting.receiver.ChatServiceMessageReceiver_;

import org.androidannotations.annotations.EService;

/**
 * Created by slhyv on 8/31/2015.
 * The Service Managing All Communications
 */
@EService
public class ChatService extends Service {
    private static final String TAG = ChatService.class.getSimpleName();

    private ChatServiceMessageReceiver_ mReceiver = null;
    private Context mContext;

    @Override
    public void onCreate() {
        Log.i(TAG, "onCreate invoked!!!");
        mContext = this;
        registerReceiverWithActions();
    }

    private void registerReceiverWithActions() {
        //register receiver with actions
        IntentFilter filter = new IntentFilter();
        filter.addAction(Constants.Action.ACTION_TO_SERVICE_SEND_CHATMSG);

        mReceiver = new ChatServiceMessageReceiver_();
        registerReceiver(mReceiver, filter);
        Log.v(TAG, "registerReceiver()");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i(TAG, "\n================PushService startCommand()!================");

        //below is demoscript
        Intent demoIntent = new Intent(Constants.Action.ACTION_TO_CLIENT_SEND_CHATMSG);
        String initMsg = "Hello, I'm Jurano!";
        demoIntent.putExtra(Constants.Action.ACTION_TO_CLIENT_SEND_CHATMSG, initMsg);
        sendBroadcast(demoIntent);
        Log.d(TAG, "demo msg sent: " + initMsg);

        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        Log.i(TAG, "onDestroy invoked");

        if (mReceiver != null) {
            unregisterReceiver(mReceiver);
            Log.i(TAG, "unregisterReceiver() called");
            mReceiver = null; //explicit null
        }
        super.onDestroy();
    }
}