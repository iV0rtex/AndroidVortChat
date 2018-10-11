package com.example.admin.androidvortchat.Factories.interfaces;

import com.example.admin.androidvortchat.socket.observer.ObsInterface;

public interface ObjectInterface {
    void registerObs(ObsInterface obs);
    void informAll(Object arg,String event);
}
