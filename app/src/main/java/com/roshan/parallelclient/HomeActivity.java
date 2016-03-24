package com.roshan.parallelclient;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;


public class HomeActivity extends Activity {

    static TextView promptTxt;
    static ScrollView promptScroll;
    EditText ipAddr, portAddr;
    Button btnConnect;
    RosChatClient chatClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        promptTxt = (TextView) findViewById(R.id.txtPrompt);
        promptScroll = (ScrollView) findViewById(R.id.scrollPrompt);
        ipAddr = (EditText) findViewById(R.id.ipAddr);
        portAddr = (EditText) findViewById(R.id.portAddr);
        btnConnect = (Button) findViewById(R.id.btnConnect);

        btnConnect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chatClient = new RosChatClient(ipAddr.getText().toString(), Integer.parseInt(portAddr.getText().toString()), getBaseContext());
                ipAddr.setFocusable(false);
                portAddr.setFocusable(false);
                btnConnect.setEnabled(false);
                btnConnect.setVisibility(View.GONE);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        chatClient.runCommunicator();
                    }
                }).start();
            }
        });
    }

    public static void setPromptText(final String text) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String temp = promptTxt.getText().toString();
                temp += "\n\n" + text;
                promptTxt.setText(temp);
                promptScroll.post(new Runnable() {
                    @Override
                    public void run() {
                        promptScroll.fullScroll(View.FOCUS_DOWN);
                    }
                });
            }
        }).run();
    }
}
