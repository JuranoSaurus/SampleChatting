package com.juranoaa.chatting.ui;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.juranoaa.chatting.R;

import com.juranoaa.chatting.common.Constants;
import com.juranoaa.chatting.service.ChatService;

/**
 * Created by slhyvaa on 2015-08-31.
 */
public class MainChatActivity extends Activity {

    private static final String TAG = MainChatActivity.class.getSimpleName();

    private MessageReceiver mReceiver = null;

    private TextView mTvChatLog;
    private EditText etChatMsgToSend;
    private Button btnSendChatMsg;

    private String myChatPrefix;
    private String serverChatPrefix;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setChatPrefix();
        initUiComponents();
        startChatService();
        registerReceiverWithActions();
    }

    private void setChatPrefix() {
        myChatPrefix = getResources().getString(R.string.sender_me) + ": ";
        serverChatPrefix = getResources().getString(R.string.sender_server) + ": ";
    }

    private void initUiComponents() {
        mTvChatLog = (TextView) findViewById(R.id.main_chat_log);

        etChatMsgToSend = (EditText) findViewById(R.id.main_et_chatmsg_to_send);

        btnSendChatMsg = (Button) findViewById(R.id.main_btn_send_chatmsg);
        btnSendChatMsg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
        });
    }

    private void startChatService() {
        //start service
        Intent intent = new Intent(this, ChatService.class);
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

        mReceiver = new MessageReceiver();
        registerReceiver(mReceiver, filter);
        Log.v(TAG, "registerReceiver()");
    }

    private void addMsgToChatLog(String chatMsg, boolean senderIsMe) {
        if(senderIsMe) {
            mTvChatLog.append("\n" + myChatPrefix + chatMsg);
        } else {
            mTvChatLog.append("\n" + serverChatPrefix + chatMsg);
        }
    }

    private class MessageReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.v(TAG, "onReceive invoked!");

            if (intent.getAction().equals(Constants.Action.ACTION_TO_CLIENT_SEND_CHATMSG)) {
                Log.v(TAG, "ACTION_TO_CLIENT_SEND_CHATMSG received");
                //received msg from service

                String chatMsg = intent.getStringExtra(Constants.Action.ACTION_TO_CLIENT_SEND_CHATMSG);

                addMsgToChatLog(chatMsg, false);

            }
        }
    }
}
