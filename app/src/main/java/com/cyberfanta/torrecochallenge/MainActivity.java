package com.cyberfanta.torrecochallenge;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import static com.cyberfanta.torrecochallenge.ApiController.getInfo_bios;


public class MainActivity extends AppCompatActivity {

    private Thread QueryThread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        QueryThread = new Thread(new readJson());

    }

    public void click_1(View view) {
        if (!QueryThread.isAlive()) {
            QueryThread = new Thread(new readJson());
            QueryThread.start();
        }

    }

    /**
     * Internal class to implement a runnable object to get the JSON answers from torre.co
     */
    private static class readJson implements Runnable {
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
            getInfo_bios();
        }
    }

}