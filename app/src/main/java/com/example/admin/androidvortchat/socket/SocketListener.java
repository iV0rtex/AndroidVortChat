package com.example.admin.androidvortchat.socket;

import android.util.Log;

import com.example.admin.androidvortchat.Factories.interfaces.ObjectInterface;
import com.example.admin.androidvortchat.socket.observer.ObsInterface;

import java.net.URISyntaxException;
import java.util.ArrayList;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;

public class SocketListener implements ObjectInterface {
    public static final String EVENT_SET_USER = "chat setUser";
    public static final String EVENT_CHECK_USER = "chat checkUser";
    public static final String EVENT_CHAT_MESSAGE = "chat message";
    private Socket socket;
    public ArrayList<ObsInterface> observers;
    public SocketListener(){
        try {
            this.socket = IO.socket("http://192.168.0.101:3000");
        } catch (URISyntaxException e) {
            //TODO: Need to finish App
        }
        this.socket.on(io.socket.client.Socket.EVENT_CONNECT, new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                SocketListener.this.informAll(args);
            }
        }).on(io.socket.client.Socket.EVENT_DISCONNECT, new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                SocketListener.this.informAll(args);
            }
        }).on(SocketListener.EVENT_CHECK_USER, new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                SocketListener.this.informAll(args);
            }//TODO: situation where need to back user to the connect page
        }).on(SocketListener.EVENT_CHAT_MESSAGE, new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                SocketListener.this.informAll(args);
            }
        });
        this.socket.connect();
    }
    @Override
    public void registerObs(ObsInterface obs){
        this.observers = new ArrayList<>();
        this.observers.add(obs);
    }
    @Override
    public void informAll(Object... arg){
        if(this.observers != null && this.observers.size() > 0){
            for(int i=0;i<this.observers.size();i++){
                this.observers.get(i).inform(SocketListener.this,arg);
            }
        }
    }
    public void emit(String event,String text) {
        this.socket.emit(event,text);
    }
}
