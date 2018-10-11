package com.example.admin.androidvortchat;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.renderscript.RenderScript;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.admin.androidvortchat.Factories.HelpFactory;
import com.example.admin.androidvortchat.Factories.interfaces.FactoryInterface;
import com.example.admin.androidvortchat.Factories.interfaces.ObjectInterface;
import com.example.admin.androidvortchat.socket.SocketListener;
import com.example.admin.androidvortchat.socket.observer.ObsInterface;

public class ChatActivity extends AppCompatActivity implements ObsInterface {
    private Handler handler;
    @SuppressLint("HandlerLeak")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FactoryInterface helpFactory = HelpFactory.innit();
        helpFactory.activate(HelpFactory.SOCKET_LISTENER);
        SocketListener socketListener = null;
        try {
            socketListener = (SocketListener) helpFactory.getObj(HelpFactory.SOCKET_LISTENER);
            this.registerOn(socketListener);
            socketListener.emit(SocketListener.EVENT_CHECK_USER,"");
        } catch (Exception e) {
            finish();
        }
        setContentView(R.layout.chat_main);
        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                EditText chatTextArea = findViewById(R.id.editText4);
                String text = (String) msg.obj;
                chatTextArea.setText(chatTextArea.getText()+"\nt"+text);
            }
        };

        final Button sendMessageButton = findViewById(R.id.button2);
        SocketListener finalSocketListener = socketListener;
        sendMessageButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                EditText input = findViewById(R.id.editText);
                String text = input.getText().toString();
                input.setText("");
                finalSocketListener.emit(SocketListener.EVENT_CHAT_MESSAGE,text);
            }
        });

    }
    protected void registerOn(ObjectInterface obj){
        obj.registerObs(ChatActivity.this);
    }

    @Override
    public void inform(ObjectInterface obj,Object args,String event) {
        if(obj instanceof SocketListener){
            if(event.contentEquals(SocketListener.EVENT_CHAT_MESSAGE)){
                new Thread() {
                    @Override
                    public void run() {
                        String text = (String) args;
                        Message msg = new Message();
                        msg.obj = text;
                        handler.sendMessage(msg);
                    }
                }.start();
            }else if(event.contentEquals(SocketListener.EVENT_CHECK_USER)){
                Boolean response = (Boolean) args;
                if(!response){
                    //TODO: close chat and return user to the start page;
                }
            }
        }
    }
}
