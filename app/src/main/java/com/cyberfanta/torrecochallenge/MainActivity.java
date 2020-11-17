package com.cyberfanta.torrecochallenge;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import static com.cyberfanta.torrecochallenge.ApiController.getInfo_bios;


public class MainActivity extends AppCompatActivity {

    public static final int NAME_EMPTY = 0;
    public static final int PERSON_NOT_FOUND = 1;
    public static final int PERSON_OK = 2;
    public static final int FUNCTION_NOT_IMPLEMENTED = 3;

    private Thread QueryThread;
    private static String userName;

    /**
     * perform the action in `handleMessage` when the thread calls
     * `Handler.sendMessage(msg)`
     */
    @SuppressLint("HandlerLeak")
    private final Handler handler = new Handler() {
        @Override
        public void handleMessage(Message message) {

            switch (message.what) {
                case NAME_EMPTY: {
                    Toast.makeText(getApplicationContext(), R.string.NAME_EMPTY, Toast.LENGTH_SHORT).show();
                    break;
                }
                case PERSON_NOT_FOUND: {
                    Toast.makeText(getApplicationContext(), R.string.PERSON_NOT_FOUND, Toast.LENGTH_SHORT).show();
                    break;
                }
                case PERSON_OK: {
                    Toast.makeText(getApplicationContext(), R.string.PERSON_OK, Toast.LENGTH_SHORT).show();
                    break;
                }
                case FUNCTION_NOT_IMPLEMENTED: {
                    Toast.makeText(getApplicationContext(), R.string.FUNCTION_NOT_IMPLEMENTED, Toast.LENGTH_SHORT).show();
                    break;
                }
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        QueryThread = new Thread(new readJson());

    }

    public void click_1(View view) {
        TextView textView = findViewById(R.id.username);
        userName = textView.getText().toString();
        if (!QueryThread.isAlive()) {
            QueryThread = new Thread(new readJson());
            QueryThread.start();
        }

    }

    /**
     * Internal class to implement a runnable object to get the JSON answers from torre.co
     */
    private class readJson implements Runnable {
        /**
         * When an object implementing interface <code>Runnable</code> is used
         * to create a thread, starting the thread causes the object's
         * <code>run</code> method to be called in that separately executing
         * thread.
         * <p>
         * The general contract of the method <code>run</code> is that it may
         * take any action whatsoever.
         *
         * @see Thread#run()
         */
        @Override
        public void run() {
            int status = getInfo_bios(userName);

            Message message = handler.obtainMessage();

            if (status == NAME_EMPTY) {
                message.what = NAME_EMPTY;
//                message.obj = bitmap;
//                imageLoaded = true;
            } else if (status == PERSON_NOT_FOUND) {
                message.what = PERSON_NOT_FOUND;
            } else if (status == PERSON_OK) {
                message.what = PERSON_OK;
            } else
                message.what = FUNCTION_NOT_IMPLEMENTED;

            handler.sendMessageAtFrontOfQueue(message);
        }
    }


}