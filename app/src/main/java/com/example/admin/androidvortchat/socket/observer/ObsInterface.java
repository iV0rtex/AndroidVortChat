package com.example.admin.androidvortchat.socket.observer;

import com.example.admin.androidvortchat.Factories.interfaces.ObjectInterface;
//TODO: need to rewrite it to the abstract class and add method 'register' which tack ObjectInterface and realize register to it
public interface ObsInterface {
    void inform(ObjectInterface obj,Object args,String event);
}
