package com.juranoaa.chatting.ui;

import android.app.Activity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.juranoaa.chatting.R;

/**
 * Created by slhyvaa on 2015-08-31.
 */
public class MainChatActivity extends Activity {

    private static final String TAG = MainChatActivity.class.getSimpleName();

    private TextView mTvChatLog;
    private EditText etChatMsgToSend;
    private Button btnSendChatMsg;

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
                    return;
                }

                //log chatmsg to chatlog
                addMsgToChatLog(chatMsg);

                //clear edittext
                etChatMsgToSend.setText("");

                //send broadcast to service


            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initUiComponents();


    }



    private void addMsgToChatLog(String chatMsg) {
        mTvChatLog.append("\n" + chatMsg);
    }
}
