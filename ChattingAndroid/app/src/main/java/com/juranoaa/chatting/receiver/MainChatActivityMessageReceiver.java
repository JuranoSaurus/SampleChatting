package com.juranoaa.chatting.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.juranoaa.chatting.common.Constants;
import com.juranoaa.chatting.ui.MainChatActivity;

import org.androidannotations.annotations.EReceiver;
import org.androidannotations.annotations.ReceiverAction;

/**
 * Created by slhyv on 8/31/2015.
 */
@EReceiver
public class MainChatActivityMessageReceiver extends BroadcastReceiver {
    private static final String TAG = MainChatActivityMessageReceiver.class.getSimpleName();

    private MainChatActivity mParentActivity;

    public void setParentActivity(MainChatActivity activity) {
        mParentActivity = activity;
    }

    @ReceiverAction(Constants.Action.ACTION_TO_CLIENT_SEND_CHATMSG)
    void receiveChatMsgFromService(
            @ReceiverAction.Extra(Constants.Action.ACTION_TO_CLIENT_SEND_CHATMSG) String chatMsg,
            Context context) {
        Log.v(TAG, "ACTION_TO_CLIENT_SEND_CHATMSG received");
        Log.v(TAG, "received msg: " + chatMsg);

        //received msg from service
        mParentActivity.addMsgToChatLog(chatMsg, false);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        //empty
    }
}