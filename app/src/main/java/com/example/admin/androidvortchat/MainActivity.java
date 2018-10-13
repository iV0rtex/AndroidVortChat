package com.example.admin.androidvortchat;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;

import com.example.admin.androidvortchat.Factories.HelpFactory;
import com.example.admin.androidvortchat.Factories.interfaces.FactoryInterface;
import com.example.admin.androidvortchat.Factories.interfaces.ObjectInterface;
import com.example.admin.androidvortchat.socket.SocketListener;
import com.example.admin.androidvortchat.socket.observer.ObsInterface;

public class MainActivity extends AppCompatActivity implements ObsInterface {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FactoryInterface helpFactory = HelpFactory.innit();
        helpFactory.activate(HelpFactory.SOCKET_LISTENER);

        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final Button sendButton = findViewById(R.id.button);
        sendButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                EditText input = findViewById(R.id.editText);
                String text = input.getText().toString();
                try {
                    SocketListener socketListener = (SocketListener) helpFactory.getObj(HelpFactory.SOCKET_LISTENER);
                    socketListener.registerObs(MainActivity.this);
                    socketListener.emit(SocketListener.EVENT_SET_USER,text);
                } catch (Exception e) {

                }
            }
        });
        if(!this.checkConnection()){
            Log.d("InternetStatus","does not connect");//TODO: does not connect to the internet. Inform user.
        }
    }
    @Override
    public void inform(ObjectInterface obj,Object args,String event){
        if(obj instanceof SocketListener){
            if(event == SocketListener.EVENT_CHECK_USER){
                Boolean response = (Boolean) args;
                if(response){
                    final Button sendButton = findViewById(R.id.button);
                    Context context = sendButton.getContext();
                    Intent intent = new Intent(context, ChatActivity.class);
                    context.startActivity(intent);
                    finish();
                }else{
                    //TODO: show up an issue for user. MB it issue of user name.
                }
            }
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
    public boolean checkConnection() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }
}
