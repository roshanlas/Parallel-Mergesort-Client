package com.roshan.parallelclient;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;


public class HomeActivity extends ActionBarActivity {

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
