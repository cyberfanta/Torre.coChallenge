package com.cyberfanta.torrecochallenge;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.res.ResourcesCompat;

import com.cyberfanta.torrecochallenge.models.Persons;

public class MainActivity extends AppCompatActivity {

    public static final int NAME_EMPTY = 0;
    public static final int PERSON_NOT_FOUND = 1;
    public static final int PERSON_OK = 2;
    public static final int FUNCTION_NOT_IMPLEMENTED = 3;

    private Thread QueryThread;
    private static String userName;

    private ApiController apiController;

    private String linkinURL = "";
    private String githubURL = "";
    private String twitterURL = "";

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
                    Persons persons = apiController.getPersons();

                    LinearLayout linearLayout = findViewById(R.id.loading);
                    linearLayout.setVisibility(View.GONE);

                    TextView textView;

                    textView = findViewById(R.id.usernameName);
                    textView.setText(persons.getName());

                    textView = findViewById(R.id.usernameCarrer);
                    textView.setText(persons.getProfessionalHeadline());

                    textView = findViewById(R.id.objective);
                    textView.setText(persons.getSummaryOfBio());

                    textView = findViewById(R.id.city);
                    textView.setText(persons.getLocations().getName());

                    textView = findViewById(R.id.timezone);
                    textView.setText(persons.getLocations().getTimezone());

                    textView = findViewById(R.id.latitude);
                    textView.setText(persons.getLocations().getLatitude());

                    textView = findViewById(R.id.longitude);
                    textView.setText(persons.getLocations().getLongitude());

                    githubURL = persons.getLinkss().elementAt(0).getAddress();
                    twitterURL = persons.getLinkss().elementAt(1).getAddress();
                    linkinURL = persons.getLinkss().elementAt(2).getAddress();

                    ImageView imageView;

                    imageView = findViewById(R.id.usernamePhoto);
                    imageView.setImageBitmap(persons.getPictureThumbnailPhoto());

                    imageView = findViewById(R.id.verified);
                    if (persons.getVerified().equals("true"))
                        imageView.setVisibility(View.VISIBLE);
                    else
                        imageView.setVisibility(View.GONE);


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

        apiController = new ApiController();

        QueryThread = new Thread(new readJson());

        ImageView imageView = findViewById(R.id.loadingImage);

        ObjectAnimator loadingArrow_animator = ObjectAnimator.ofFloat(imageView, "rotation", 0, 360);
        AnimatorSet loadingArrow_animatorSet = new AnimatorSet();
        loadingArrow_animatorSet.play(loadingArrow_animator);
        loadingArrow_animatorSet.setDuration((int) ResourcesCompat.getFloat(getResources(), R.dimen.loading_animation));
        loadingArrow_animatorSet.addListener(
                new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                        loadingArrow_animatorSet.start();
                    }
                }
        );
        loadingArrow_animatorSet.start();

        ImageView imageView2 = findViewById(R.id.helpImage);

        ObjectAnimator loadingArrow_animator2_a = ObjectAnimator.ofFloat(imageView2, "translationY", 0, 50);
        ObjectAnimator loadingArrow_animator2_b = ObjectAnimator.ofFloat(imageView2, "translationY", 50, 0);
        AnimatorSet loadingArrow_animatorSet2 = new AnimatorSet();
        loadingArrow_animatorSet2.playSequentially(loadingArrow_animator2_a, loadingArrow_animator2_b);
        loadingArrow_animatorSet2.setDuration((int) ResourcesCompat.getFloat(getResources(), R.dimen.help_animation));
        loadingArrow_animatorSet2.addListener(
                new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                        loadingArrow_animatorSet2.start();
                    }
                }
        );
        loadingArrow_animatorSet2.start();
    }

    public void click_1(View view) {
        LinearLayout linearLayout = findViewById(R.id.loading);
        linearLayout.setVisibility(View.VISIBLE);

        linearLayout = findViewById(R.id.help);
        linearLayout.setVisibility(View.GONE);

        TextView textView = findViewById(R.id.username);
        userName = textView.getText().toString();
        if (!QueryThread.isAlive()) {
            QueryThread = new Thread(new readJson());
            QueryThread.start();
        }

    }

    public void click_linkinURL(View view) {
        if (!linkinURL.isEmpty()) {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(linkinURL));
            startActivity(intent);
        }
    }

    public void click_githubURL(View view) {
        if (!githubURL.isEmpty()) {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(githubURL));
            startActivity(intent);
        }
    }

    public void click_twitterURL(View view) {
        if (!twitterURL.isEmpty()) {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(twitterURL));
            startActivity(intent);
        }
    }

    /**
     *  Create the setting menu of the application
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    /**
     *  Handle the setting menu of the application
     */
    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //            case R.id.item_policy:
        ////                Uri uri = Uri.parse("https://docs.google.com/document/d/1jO9nnmsjG2ZO0Si1rEBTAP2kmTin5h-qIb4nhuAN5H0/edit?usp=sharing");
        //                Uri uri = Uri.parse(getResources().getString(R.string.item_policy_url));
        //                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        //                startActivity(intent);
        //                return true;
        //            case R.id.item_rate:
        //                Task<Void> flow = reviewManager.launchReviewFlow(MainActivity.this, reviewInfo);
        //                flow.addOnCompleteListener(
        //                        new OnCompleteListener<Void>() {
        //                            @Override
        //                            public void onComplete(Task<Void> task) {
        //                            }
        //                        }
        //                );
        //                return true;
        if (item.getItemId() == R.id.item_about) {
            ConstraintLayout constraintLayout = findViewById(R.id.author);
            constraintLayout.setVisibility(View.VISIBLE);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    /**
     * Show the developer info
     */
    public void author_selected (View view) {
        ConstraintLayout constraintLayout = findViewById(R.id.author);
        constraintLayout.setVisibility(View.GONE);
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
            int status = apiController.getInfo_bios(userName);

            Message message = handler.obtainMessage();

            if (status == NAME_EMPTY) {
                message.what = NAME_EMPTY;
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