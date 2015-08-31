package com.juranoaa.chatting.rest;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.juranoaa.chatting.R;

import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

/**
 * Created by SungGeun on 2015-08-31.
 */
public class RestTestActivity extends Activity{

    private static final String LOG_TAG = "RestTestActivity";

    private TextView messageTv;
    private Button sendBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rest);

        messageTv = (TextView) findViewById(R.id.message);

        sendBtn = (Button) findViewById(R.id.send);
        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new RestProtocol().execute(new Message("Tae", "Hello my name is Tae"));
            }
        });
    }

    public class RestProtocol extends AsyncTask<Message, Void, Message>{

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
                String url = getString(R.string.host)+"/send";

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
                messageTv.setText(message.toString());
            }
        }
    }


}
