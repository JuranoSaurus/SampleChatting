package com.juranoaa.chatting.ui;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;

import com.juranoaa.chatting.R;
import com.juranoaa.chatting.common.Constants;
import com.juranoaa.chatting.receiver.MainChatActivityMessageReceiver;
import com.juranoaa.chatting.receiver.MainChatActivityMessageReceiver_;
import com.juranoaa.chatting.service.ChatService_;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.res.StringRes;

/**
 * Created by slhyvaa on 2015-08-31.
 */
@EActivity(R.layout.activity_main)
public class MainChatActivity extends Activity {

    private static final String TAG = MainChatActivity.class.getSimpleName();

    private MainChatActivityMessageReceiver mReceiver = null;

    @ViewById(R.id.main_tv_chat_log)
    TextView tvChatLog; //cannot be private
    @ViewById(R.id.main_et_chat_msg_to_send)
    EditText etChatMsgToSend;

    @StringRes(R.string.sender_me)
    String myChatPrefix;
    @StringRes(R.string.sender_server)
    String serverChatPrefix;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        startChatService();
        registerReceiverWithActions();
    }

    @Click(R.id.main_btn_send_chat_msg)
    void onClickBtnSendChatMsg() {
        Log.v(TAG, "btnSendChatMsg clicked");

        String chatMsg = etChatMsgToSend.getText().toString();

        //not send empty msg
        if(chatMsg.equals("")) {
            Log.i(TAG, "empty msg. cancel request.");
            return;
        }

        //log chatmsg to chatlog
        addMsgToChatLog(chatMsg, true);

        //clear edittext
        etChatMsgToSend.setText("");

        //send broadcast with msg to service
        Intent sendMsgIntent = new Intent(Constants.Action.ACTION_TO_SERVICE_SEND_CHATMSG);
        sendMsgIntent.putExtra(
                Constants.Action.ACTION_TO_SERVICE_SEND_CHATMSG,
                chatMsg
        );
        sendBroadcast(sendMsgIntent);
    }

    private void startChatService() {
        //start service
        Intent intent = new Intent(this, ChatService_.class);
        ComponentName componentName = startService(intent);
        if(componentName != null) {
            Log.v(TAG, "result of startService: " + componentName.toShortString());
        } else {
            Log.e(TAG, "FATAL ERROR TO START PUSHSERVICE! EXIT APPLICATION");
            //TODO: exit app
        }
    }
    
    private void registerReceiverWithActions() {
        //register receiver with actions
        IntentFilter filter = new IntentFilter();
        filter.addAction(Constants.Action.ACTION_TO_CLIENT_SEND_CHATMSG);

        mReceiver = new MainChatActivityMessageReceiver_();
        mReceiver.setParentActivity(this);
        registerReceiver(mReceiver, filter);
        Log.v(TAG, "registerReceiver()");
    }

    public void addMsgToChatLog(String chatMsg, boolean senderIsMe) {
        if(senderIsMe) {
            tvChatLog.append("\n" + myChatPrefix + ": " + chatMsg);
        } else {
            tvChatLog.append("\n" + serverChatPrefix + ": " + chatMsg);
        }
    }


}
