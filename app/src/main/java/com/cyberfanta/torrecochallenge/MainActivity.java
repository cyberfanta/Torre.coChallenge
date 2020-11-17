package com.cyberfanta.torrecochallenge;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Message;
import android.os.Handler;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

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
    private static final Handler handler = new Handler() {
        @Override
        public void handleMessage(Message message) {
//            Toast.makeText(getApplicationContext(), R.string.permissionGranted, Toast.LENGTH_LONG).show();

//            switch (message.what) {
//                case CHECK_YOUR_INTERNET: {
//                    if (!abortAllProcess) {
//                        if (!(breedsLoaded && categoriesLoaded && imageLoaded)) {
//                            Toast.makeText(getApplicationContext(), R.string.internet_fail, Toast.LENGTH_SHORT).show();
//                            Toast.makeText(getApplicationContext(), R.string.retryConnection, Toast.LENGTH_SHORT).show();
//
//                            if (!NetworkQueryThread.isAlive()) {
//                                NetworkQueryThread = new Thread(new NetworkQueryThread());
//                                NetworkQueryThread.start();
//                            }
//                        }
//                    }
//
//                    break;
//                }
//                case IMAGE_UPDATE: {
//                    imageView = findViewById(R.id.petImage_iv);
//                    imageView.getLayoutParams().width = ((Bitmap) message.obj).getWidth();
//                    imageView.getLayoutParams().height = ((Bitmap) message.obj).getHeight();
//                    imageView.setImageBitmap((Bitmap) message.obj);
//
//                    if (firstRun) {
//                        imageView.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.image_view_second_style, null));
//                        firstRun = false;
//                    }
//
//                    button = findViewById(R.id.morePets_button);
//                    button.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.button_normal, null));
//                    button.setEnabled(true);
//                    button.setClickable(true);
//
//                    loadingArrow_animatorSet.pause();
//                    imageView = findViewById(R.id.loadingArrow_iv);
//                    imageView.setEnabled(false);
//                    imageView.setVisibility(View.GONE);
//
//                    break;
//                }
//                case IMAGE_UPDATE_FAIL: {
//                    button = findViewById(R.id.morePets_button);
//                    button.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.button_normal, null));
//                    button.setEnabled(true);
//                    button.setClickable(true);
//
//                    break;
//                }
//                case IMAGE_UPDATE_NOT_FOUND: {
//                    button = findViewById(R.id.morePets_button);
//                    button.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.button_normal, null));
//                    button.setEnabled(true);
//                    button.setClickable(true);
//
//                    loadingArrow_animatorSet.pause();
//                    imageView = findViewById(R.id.loadingArrow_iv);
//                    imageView.setEnabled(false);
//                    imageView.setVisibility(View.GONE);
//
//                    Toast.makeText(getApplicationContext(), R.string.petNoFound, Toast.LENGTH_SHORT).show();
//
//                    break;
//                }
//                case BREED_UPDATE: {
//                    Spinner breeds_spinner = findViewById(R.id.breeds_spinner);
//                    String[] breedlist = (String[]) message.obj;
//
//                    ArrayAdapter<String> breeds_spinner_adapter = new ArrayAdapter<>(MainActivity.this, R.layout.spinner_text_style, breedlist);
//                    breeds_spinner.setAdapter(breeds_spinner_adapter);
//                    break;
//                }
//                case CATEGORY_UPDATE: {
//                    Spinner categories_spinner = findViewById(R.id.categories_spinner);
//                    String[] categorylist = (String[]) message.obj;
//
//                    ArrayAdapter<String> breeds_spinner_adapter = new ArrayAdapter<>(MainActivity.this, R.layout.spinner_text_style, categorylist);
//                    categories_spinner.setAdapter(breeds_spinner_adapter);
//                    break;
//                }
//            }
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