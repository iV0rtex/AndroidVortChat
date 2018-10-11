package com.example.admin.androidvortchat.socket.observer;

import com.example.admin.androidvortchat.Factories.interfaces.ObjectInterface;

public interface ObsInterface {
    void inform(ObjectInterface obj,Object args,String event);
}
