package com.juranoaa.chatting.rest;

import android.app.Activity;
import android.util.Log;
import android.widget.TextView;

import com.juranoaa.chatting.R;

import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.rest.RestService;


/**
 * Created by SungGeun on 2015-08-31.
 */
@EActivity(R.layout.rest)
public class AARestTestActivity extends Activity {

    private static final String LOG_TAG = "AARestTestActivity";

    @ViewById(R.id.message)
    TextView messageTv;

    @RestService
    AARestProtocol aaRestProtocol;

    @Click(R.id.send)
    void getSendBtn(){
        background_send();
    }

    @Background
    void background_send() {
        Message message = aaRestProtocol.echoProtocol(new Message("Tae", "Hello my name is Tae"));
        Log.d(LOG_TAG, "RestProtocol Update Message [" + message.toString() + "]");
        changeTextView(message.toString());
    }

    @UiThread
    void changeTextView(String str){
        messageTv.setText(str);
    }

}
