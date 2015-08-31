package com.juranoaa.chatting.rest;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;

import com.juranoaa.chatting.R;
import com.juranoaa.chatting.common.Constants;

import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

/**
 * Created by SungGeun on 2015-08-31.
 */
public class RestProtocol extends AsyncTask<Message, Void, Message> {

    private static final String LOG_TAG = "RestProtocol";
    private Context context;
    private String toStringMessage;

    public RestProtocol(Context context) {
        this.context = context;
    }

    public String getToStringMessage() {
        return toStringMessage;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected Message doInBackground(Message... params) {
        if(params.length < 1){
            return new Message("Robot", "Parameter length is wrong");
        }

        Message message = null;

        try{
            String url = context.getString(R.string.host)+"/send";

            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<Message> responseEntity = restTemplate.postForEntity(url, params[0], Message.class);
            message = responseEntity.getBody();

        }catch (Exception e){
            Log.e(LOG_TAG, "RestProtocol exception error", e);
            return new Message("Robot", "Rest protocol connect error");
        }

        return message;
    }

    @Override
    protected void onPostExecute(Message message) {
        super.onPostExecute(message);
        if(message != null){
            Log.d(LOG_TAG, "RestProtocol Update Message [" + message.toString()+"]");
            toStringMessage = message.getData();

            Intent intentToClient = new Intent(Constants.Action.ACTION_TO_CLIENT_SEND_CHATMSG);
            intentToClient.putExtra(Constants.Action.ACTION_TO_CLIENT_SEND_CHATMSG, toStringMessage);

            context.sendBroadcast(intentToClient);
        }
    }
}
