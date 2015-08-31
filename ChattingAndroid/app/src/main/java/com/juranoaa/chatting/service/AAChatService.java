package com.juranoaa.chatting.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import com.juranoaa.chatting.common.Constants;
import com.juranoaa.chatting.rest.AARestProtocol;
import com.juranoaa.chatting.rest.Message;

import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.EService;
import org.androidannotations.annotations.Receiver;
import org.androidannotations.annotations.rest.RestService;

/**
 * Created by slhyv on 8/31/2015.
 * The Service Managing All Communications
 */
@EService
public class AAChatService extends Service {
    private static final String TAG = AAChatService.class.getSimpleName();

    private Context mContext;

    @RestService
    AARestProtocol aaRestProtocol;

    @Override
    public void onCreate() {
        Log.i(TAG, "onCreate invoked!!!");
        mContext = this;
    }

    @Receiver(actions = {Intent.ACTION_BOOT_COMPLETED, Intent.ACTION_USER_PRESENT })
    void restartService(Intent intent) {
        Log.v(TAG, "restartService() invoked!");
        //wake up process. start service.
        mContext.startService(new Intent(mContext, AAChatService_.class));
    }

    @Receiver(actions = Constants.Action.ACTION_TO_SERVICE_SEND_CHATMSG)
    void requestToRestServerWithChatMsg(
            @Receiver.Extra(Constants.Action.ACTION_TO_SERVICE_SEND_CHATMSG) String chatMsg) {
        Log.v(TAG, "receive ACTION_TO_SERVICE_SEND_CHATMSG");
        Log.d(TAG, "received msg from client: " + chatMsg);

        //msg from client. send msg to server.
        background_send(chatMsg);
    }

    @Background
    void background_send(String chatMsg) {
        Message message = aaRestProtocol.echoProtocol(new Message("Tae", chatMsg));
        Intent intent = new Intent(Constants.Action.ACTION_TO_CLIENT_SEND_CHATMSG);
        intent.putExtra(Constants.Action.ACTION_TO_CLIENT_SEND_CHATMSG, message.getData());
        sendBroadcast(intent);
        Log.d(TAG, "msg send: " + message.getData());
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

}