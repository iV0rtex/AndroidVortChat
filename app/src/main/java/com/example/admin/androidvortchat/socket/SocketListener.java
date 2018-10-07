package com.example.admin.androidvortchat.socket;

import com.example.admin.androidvortchat.Factories.interfaces.ObjectInterface;

import java.net.URISyntaxException;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;
import android.util.Log;

public class SocketListener implements ObjectInterface {
    public SocketListener(){
        Socket socket = null;
        try {
            socket = IO.socket("http://192.168.0.101:3000");
        } catch (URISyntaxException e) {
            Log.d("URISyntaxException",e.getMessage());
        }
        socket.on(io.socket.client.Socket.EVENT_CONNECT, new Emitter.Listener() {
            @Override
            public void call(Object... args) {}
        }).on("event", new Emitter.Listener() {
            @Override
            public void call(Object... args) {}
        }).on(io.socket.client.Socket.EVENT_DISCONNECT, new Emitter.Listener() {
            @Override
            public void call(Object... args) {}
        });
        socket.connect();
    }
}
