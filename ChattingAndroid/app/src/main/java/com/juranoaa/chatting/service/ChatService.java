package com.juranoaa.chatting.service;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.util.Log;

import com.juranoaa.chatting.common.Constants;

/**
 * Created by slhyv on 8/31/2015.
 * The Service Managing All Communications
 */
public class ChatService extends Service {
    private static final String TAG = ChatService.class.getSimpleName();

    private MessageReceiver mReceiver = null;

    @Override
    public void onCreate() {
        Log.i(TAG, "onCreate invoked!!!");

        registerReceiverWithActions();
    }

    private void registerReceiverWithActions() {
        //register receiver with actions
        IntentFilter filter = new IntentFilter();
        filter.addAction(Constants.Action.ACTION_TO_SERVICE_SEND_CHATMSG);

        mReceiver = new MessageReceiver();
        registerReceiver(mReceiver, filter);
        Log.v(TAG, "registerReceiver()");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i(TAG, "\n================PushService startCommand()!================");

        //below is demoscript
        Intent demoIntent = new Intent(Constants.Action.ACTION_TO_CLIENT_SEND_CHATMSG);
        String initMsg = "Hello!";
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

    private class MessageReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.v(TAG, "onReceive invoked!");

            if(intent.getAction().equals(Intent.ACTION_BOOT_COMPLETED) ||
                    intent.getAction().equals(Intent.ACTION_USER_PRESENT))	{
                //wake up process. start service.
                context.startService(new Intent(context, ChatService.class));

            } else if(intent.getAction().equals(Constants.Action.ACTION_TO_SERVICE_SEND_CHATMSG)) {
                Log.v(TAG, "receive ACTION_TO_SERVICE_SEND_CHATMSG");
                String chatMsg = intent.getStringExtra(Constants.Action.ACTION_TO_SERVICE_SEND_CHATMSG);
                Log.d(TAG, "received msg from client: " + chatMsg);

                //msg from client. send msg to server.
                //TODO: DO SERVER REQUEST HERE!

            }
        }
    }
}