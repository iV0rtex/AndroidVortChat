package com.example.admin.androidvortchat;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
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

import org.json.JSONObject;

import java.util.List;
import java.util.Map;

public class ChatActivity extends AppCompatActivity implements ObsInterface {
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
    public void inform(ObjectInterface obj,Object... args) {
        if(obj instanceof SocketListener){
            /*Map<String, List<String>> headers = (Map<String, List<String>>)args[0];
            for(Map.Entry<String, List<String>> pair : headers.entrySet())
            {
                List val = pair.getValue();
                for(int i = 0; i<pair.getValue().size();i++){
                    String t = val.get(i).toString();
                    Log.d("SocketListener",t);
                }
            }*/
            Log.d("SocketListener","informed");//TODO: Need to react on all new events (chang text in the chat or close chat)
        }
    }
}
