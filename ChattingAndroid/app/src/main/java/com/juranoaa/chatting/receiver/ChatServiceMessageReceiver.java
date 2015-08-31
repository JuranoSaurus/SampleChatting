package com.juranoaa.chatting.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.juranoaa.chatting.common.Constants;
import com.juranoaa.chatting.rest.Message;
import com.juranoaa.chatting.rest.RestProtocol;
import com.juranoaa.chatting.service.ChatService_;

import org.androidannotations.annotations.EReceiver;
import org.androidannotations.annotations.ReceiverAction;

/**
 * Created by slhyv on 8/31/2015.
 */
@EReceiver
public class ChatServiceMessageReceiver extends BroadcastReceiver {
    private static final String TAG = ChatServiceMessageReceiver.class.getSimpleName();

    private Context mContext;

    @ReceiverAction({Intent.ACTION_BOOT_COMPLETED, Intent.ACTION_USER_PRESENT})
    void restartService(Intent intent) {
        Log.v(TAG, "restartService() invoked!");
        //wake up process. start service.
        mContext.startService(new Intent(mContext, ChatService_.class));
    }

    @ReceiverAction(Constants.Action.ACTION_TO_SERVICE_SEND_CHATMSG)
    void requestToRestServerWithChatMsg(
            @ReceiverAction.Extra(Constants.Action.ACTION_TO_SERVICE_SEND_CHATMSG) String chatMsg,
            Context context) {
        Log.v(TAG, "receive ACTION_TO_SERVICE_SEND_CHATMSG");
        Log.d(TAG, "received msg from client: " + chatMsg);
        //msg from client. send msg to server.
        new RestProtocol(mContext).execute(new Message("Tae", chatMsg));
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        mContext = context;
    }
}