package com.example.admin.androidvortchat.Factories;

import android.util.Log;

import com.example.admin.androidvortchat.Factories.interfaces.FactoryInterface;
import com.example.admin.androidvortchat.Factories.interfaces.ObjectInterface;
import com.example.admin.androidvortchat.socket.SocketListener;

import java.util.ArrayList;

public class HelpFactory implements FactoryInterface {
    public static final String SOCKET_LISTENER = "SocketListener";

    private static FactoryInterface helpFactory = null;

    private ArrayList<ObjectInterface> objectList;
    void HelpFactory(){ }
    @Override
    public void activate(String objName) {
        if(this.objectList == null){
            this.objectList = new ArrayList<>();
        }
        try{
            this.getObj(objName);
        }catch (Exception e){
            if(objName == "SocketListener"){
                this.objectList.add(new SocketListener());
            }
        }
    }

    @Override
    public ObjectInterface getObj(String objName) throws Exception {
        for(int i = 0; i < this.objectList.size();i++){
            ObjectInterface obj = this.objectList.get(i);
            if(obj.getClass().getSimpleName().equals(objName)){
                return obj;
            }
        }
        throw new Exception("Factory does not have obj called "+objName);
    }
    public static FactoryInterface innit(){
        if(HelpFactory.helpFactory == null){
            HelpFactory.helpFactory = new HelpFactory();
        }
        return HelpFactory.helpFactory;
    }
}
