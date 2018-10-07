package com.example.admin.androidvortchat.Factories.interfaces;

public interface FactoryInterface {
    void activate(String objName);
    ObjectInterface getObj(String objName) throws Exception;

}
