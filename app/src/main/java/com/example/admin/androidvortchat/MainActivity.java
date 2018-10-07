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

public class MainActivity extends AppCompatActivity {

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
                    socketListener.emit(SocketListener.EVENT_SET_USER,text);
                    Context context = v.getContext();
                    Intent intent = new Intent(context, ChatActivity.class);
                    context.startActivity(intent);
                    finish();
                } catch (Exception e) {

                }
            }
        });

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
