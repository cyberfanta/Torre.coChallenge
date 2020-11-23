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
import static com.cyberfanta.torrecochallenge.SharedConstants.*;

public class MainActivity extends AppCompatActivity {

    /**
     * User data
     */
    private static String userName;
    private Persons persons;

    /**
     * Link with controller class
     */
    private ApiController apiController;

    /**
     * Thread to make the asyncronize data reading
     */
    private Thread QueryThread;
    private Thread QueryThreadImages;

    /**
     * URL links
     */
    private String linkinURL = "";
    private String githubURL = "";
    private String gitlabURL = "";
    private String mediumURL = "";

    private String twitterURL = "";
    private String facebookURL = "";
    private String instagramURL = "";

    private String webpageURL1 = "";
    private String webpageURL2 = "";
    private String webpageURL3 = "";
    private String webpageURL4 = "";
    private String webpageURL5 = "";

    /**
     * Animator objects
     */
    AnimatorSet loadingArrow_animatorSet_3;

    /**
     * perform the action in `handleMessage` when the thread calls
     * `Handler.sendMessage(msg)`
     */
    @SuppressLint("HandlerLeak")
    private final Handler handler = new Handler() {
        @Override
        public void handleMessage(Message message) {
            if (NAME_EMPTY.equals(message.obj)) {
                Toast.makeText(getApplicationContext(), R.string.NAME_EMPTY, Toast.LENGTH_SHORT).show();

                LinearLayout linearLayout = findViewById(R.id.loading);
                linearLayout.setVisibility(View.GONE);
            }
            else if (PERSON_NOT_FOUND.equals(message.obj)) {
                Toast.makeText(getApplicationContext(), R.string.PERSON_NOT_FOUND, Toast.LENGTH_SHORT).show();

                LinearLayout linearLayout = findViewById(R.id.loading);
                linearLayout.setVisibility(View.GONE);
            }
            else if (PERSON_LOAD_OK.equals(message.obj)) {
                Toast.makeText(getApplicationContext(), R.string.PERSON_LOAD_OK, Toast.LENGTH_SHORT).show();
                persons = apiController.getPersons();

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

                textView = findViewById(R.id.country);
                textView.setText(persons.getLocations().getCountry());

                textView = findViewById(R.id.latitude);
                textView.setText(persons.getLocations().getLatitude());

                textView = findViewById(R.id.longitude);
                textView.setText(persons.getLocations().getLongitude());

                textView = findViewById(R.id.timezone);
                textView.setText(persons.getLocations().getTimezone());

                for (int i = 0; i < persons.getLinkss().size(); i++) {
                    String name = persons.getLinkss().elementAt(i).getName();
                    ImageView imageView;

                    switch (name) {
                        case "linkedin":
                            linkinURL = persons.getLinkss().elementAt(i).getAddress();
                            imageView = findViewById(R.id.linkin);
                            imageView.setImageDrawable(ResourcesCompat.getDrawable(getResources(), R.drawable.ic_linkin_edited, null));
                            break;
                        case "github":
                            githubURL = persons.getLinkss().elementAt(i).getAddress();
                            imageView = findViewById(R.id.github);
                            imageView.setImageDrawable(ResourcesCompat.getDrawable(getResources(), R.drawable.ic_github, null));
                            break;
                        case "gitlab":
                            gitlabURL = persons.getLinkss().elementAt(i).getAddress();
                            imageView = findViewById(R.id.gitlab);
                            imageView.setImageDrawable(ResourcesCompat.getDrawable(getResources(), R.drawable.ic_gitlab3, null));
                            break;
                        case "medium":
                            mediumURL = persons.getLinkss().elementAt(i).getAddress();
                            imageView = findViewById(R.id.medium);
                            imageView.setImageDrawable(ResourcesCompat.getDrawable(getResources(), R.drawable.ic_medium, null));
                            break;
                        case "twitter":
                            twitterURL = persons.getLinkss().elementAt(i).getAddress();
                            imageView = findViewById(R.id.twitter);
                            imageView.setImageDrawable(ResourcesCompat.getDrawable(getResources(), R.drawable.ic_twitter, null));
                            break;
                        case "facebook":
                            facebookURL = persons.getLinkss().elementAt(i).getAddress();
                            imageView = findViewById(R.id.facebook);
                            imageView.setImageDrawable(ResourcesCompat.getDrawable(getResources(), R.drawable.ic_facebook, null));
                            break;
                        case "instagram":
                            instagramURL = persons.getLinkss().elementAt(i).getAddress();
                            imageView = findViewById(R.id.instagram);
                            imageView.setImageDrawable(ResourcesCompat.getDrawable(getResources(), R.drawable.ic_instagram, null));
                            break;
                        default:
                            if (webpageURL1.equals("")) {
                                webpageURL1 = persons.getLinkss().elementAt(i).getAddress();
                                imageView = findViewById(R.id.www1);
                                imageView.setImageDrawable(ResourcesCompat.getDrawable(getResources(), R.drawable.ic_www7, null));
                            } else if (webpageURL2.equals("")) {
                                webpageURL2 = persons.getLinkss().elementAt(i).getAddress();
                                imageView = findViewById(R.id.www2);
                                imageView.setImageDrawable(ResourcesCompat.getDrawable(getResources(), R.drawable.ic_www7, null));
                            } else if (webpageURL3.equals("")) {
                                webpageURL3 = persons.getLinkss().elementAt(i).getAddress();
                                imageView = findViewById(R.id.www3);
                                imageView.setImageDrawable(ResourcesCompat.getDrawable(getResources(), R.drawable.ic_www7, null));
                            } else if (webpageURL4.equals("")) {
                                webpageURL4 = persons.getLinkss().elementAt(i).getAddress();
                                imageView = findViewById(R.id.www4);
                                imageView.setImageDrawable(ResourcesCompat.getDrawable(getResources(), R.drawable.ic_www7, null));
                            } else {
                                webpageURL5 = persons.getLinkss().elementAt(i).getAddress();
                                imageView = findViewById(R.id.www5);
                                imageView.setImageDrawable(ResourcesCompat.getDrawable(getResources(), R.drawable.ic_www7, null));
                            }

                            break;
                    }
                    imageView.setVisibility(View.VISIBLE);
                }

                ImageView imageView = findViewById(R.id.verified);
                if (persons.getVerified().equals("true"))
                    imageView.setVisibility(View.VISIBLE);
                else
                    imageView.setVisibility(View.GONE);

                textView = findViewById(R.id.usernamePhone);
                if (persons.getShowPhone().equals("true")) {
                    textView.setText(persons.getPhone());
                    textView.setVisibility(View.VISIBLE);
                } else
                    textView.setVisibility(View.GONE);

                if (!QueryThreadImages.isAlive()) {
                    QueryThreadImages = new Thread(new readJsonImages());
                    QueryThreadImages.start();
                }
            }
            else if (PERSON_LOAD_FAIL.equals(message.obj)) {
                Toast.makeText(getApplicationContext(), R.string.PERSON_LOAD_FAIL, Toast.LENGTH_SHORT).show();
            }
            else if (FUNCTION_NOT_IMPLEMENTED.equals(message.obj)) {
                Toast.makeText(getApplicationContext(), R.string.FUNCTION_NOT_IMPLEMENTED, Toast.LENGTH_SHORT).show();
                LinearLayout linearLayout = findViewById(R.id.loading);
                linearLayout.setVisibility(View.GONE);
            }
            else if (IMAGES_OK.equals(message.obj)) {
                Toast.makeText(getApplicationContext(), R.string.IMAGES_OK, Toast.LENGTH_SHORT).show();

                loadingArrow_animatorSet_3.removeAllListeners();
                loadingArrow_animatorSet_3.end();

                ImageView imageView;

                imageView = findViewById(R.id.usernamePhoto);
                imageView.setImageBitmap(persons.getPictureThumbnailPhoto());
            }
            else if (IMAGES_FAILED.equals(message.obj)) {
                Toast.makeText(getApplicationContext(), R.string.IMAGES_FAILED, Toast.LENGTH_SHORT).show();
            }
        }
    };

//    ---

    /**
     * Task made before app start
     * @param savedInstanceState Needed to start the app
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        apiController = new ApiController();

        QueryThread = new Thread(new readJson());
        QueryThreadImages = new Thread(new readJsonImages());

        ImageView imageView = findViewById(R.id.loadingImage);

        ObjectAnimator loadingArrow_animator = ObjectAnimator.ofFloat(imageView, "rotation", 0, -360);
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

        ImageView imageView3 = findViewById(R.id.usernamePhoto);

        ObjectAnimator loadingArrow_animator_3 = ObjectAnimator.ofFloat(imageView3, "rotation", 0, -360);
        loadingArrow_animatorSet_3 = new AnimatorSet();
        loadingArrow_animatorSet_3.play(loadingArrow_animator_3);
        loadingArrow_animatorSet_3.setDuration((int) ResourcesCompat.getFloat(getResources(), R.dimen.loading_animation));
        loadingArrow_animatorSet_3.addListener(
                new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                        loadingArrow_animatorSet_3.start();
                    }
                }
        );
        loadingArrow_animatorSet_3.start();
    }

//    ---

    /**
     * Start the data reading from server
     * @param view Necessary to attach it to a button view
     */
    public void click_search(View view) {
        LinearLayout linearLayout = findViewById(R.id.loading);
        linearLayout.setVisibility(View.VISIBLE);

        linearLayout = findViewById(R.id.help);
        linearLayout.setVisibility(View.GONE);

        {
            ImageView imageView = findViewById(R.id.linkin);
            imageView.setImageDrawable(ResourcesCompat.getDrawable(getResources(), R.drawable.ic_linkin_edited2, null));
            imageView.setVisibility(View.GONE);

            imageView = findViewById(R.id.github);
            imageView.setImageDrawable(ResourcesCompat.getDrawable(getResources(), R.drawable.ic_github2, null));
            imageView.setVisibility(View.GONE);

            imageView = findViewById(R.id.gitlab);
            imageView.setImageDrawable(ResourcesCompat.getDrawable(getResources(), R.drawable.ic_gitlab4, null));
            imageView.setVisibility(View.GONE);

            imageView = findViewById(R.id.medium);
            imageView.setImageDrawable(ResourcesCompat.getDrawable(getResources(), R.drawable.ic_medium2, null));
            imageView.setVisibility(View.GONE);

            imageView = findViewById(R.id.twitter);
            imageView.setImageDrawable(ResourcesCompat.getDrawable(getResources(), R.drawable.ic_twitter2, null));
            imageView.setVisibility(View.GONE);

            imageView = findViewById(R.id.facebook);
            imageView.setImageDrawable(ResourcesCompat.getDrawable(getResources(), R.drawable.ic_facebook2, null));
            imageView.setVisibility(View.GONE);

            imageView = findViewById(R.id.instagram);
            imageView.setImageDrawable(ResourcesCompat.getDrawable(getResources(), R.drawable.ic_instagram2, null));
            imageView.setVisibility(View.GONE);

            imageView = findViewById(R.id.www1);
            imageView.setImageDrawable(ResourcesCompat.getDrawable(getResources(), R.drawable.ic_www8, null));
            imageView.setVisibility(View.GONE);

            imageView = findViewById(R.id.verified);
            imageView.setVisibility(View.GONE);

            webpageURL1="";
            webpageURL2="";
            webpageURL3="";
            webpageURL4="";
            webpageURL5="";
        }

        TextView textView = findViewById(R.id.username);
        userName = textView.getText().toString();
        if (!QueryThread.isAlive()) {
            QueryThread = new Thread(new readJson());
            QueryThread.start();
        }
    }

//    ---

    /**
     * Open intent with link for user linkedin
     * @param view Necessary to attach it to a button view
     */
    public void click_linkinURL(View view) {
        if (!linkinURL.isEmpty()) {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(linkinURL));
            startActivity(intent);
        }
    }

    /**
     * Open intent with link for user github
     * @param view Necessary to attach it to a button view
     */
    public void click_githubURL(View view) {
        if (!githubURL.isEmpty()) {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(githubURL));
            startActivity(intent);
        }
    }

    /**
     * Open intent with link for user gitlab
     * @param view Necessary to attach it to a button view
     */
    public void click_gitlabURL(View view) {
        if (!gitlabURL.isEmpty()) {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(gitlabURL));
            startActivity(intent);
        }
    }

    /**
     * Open intent with link for user medium
     * @param view Necessary to attach it to a button view
     */
    public void click_mediumURL(View view) {
        if (!mediumURL.isEmpty()) {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(mediumURL));
            startActivity(intent);
        }
    }

//    ---

    /**
     * Open intent with link for user twitter
     * @param view Necessary to attach it to a button view
     */
    public void click_twitterURL(View view) {
        if (!twitterURL.isEmpty()) {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(twitterURL));
            startActivity(intent);
        }
    }

    /**
     * Open intent with link for user facebook
     * @param view Necessary to attach it to a button view
     */
    public void click_facebookURL(View view) {
        if (!facebookURL.isEmpty()) {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(facebookURL));
            startActivity(intent);
        }
    }

    /**
     * Open intent with link for user instagram
     * @param view Necessary to attach it to a button view
     */
    public void click_instagramURL(View view) {
        if (!instagramURL.isEmpty()) {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(instagramURL));
            startActivity(intent);
        }
    }

    /**
     * Open intent with link for user webpage 1
     * @param view Necessary to attach it to a button view
     */
    public void click_webpageURL_1(View view) {
        if (!webpageURL1.isEmpty()) {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(webpageURL1));
            startActivity(intent);
        }
    }

    /**
     * Open intent with link for user webpage 2
     * @param view Necessary to attach it to a button view
     */
    public void click_webpageURL_2(View view) {
        if (!webpageURL2.isEmpty()) {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(webpageURL2));
            startActivity(intent);
        }
    }

    /**
     * Open intent with link for user webpage 3
     * @param view Necessary to attach it to a button view
     */
    public void click_webpageURL_3(View view) {
        if (!webpageURL3.isEmpty()) {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(webpageURL3));
            startActivity(intent);
        }
    }

    /**
     * Open intent with link for user webpage 4
     * @param view Necessary to attach it to a button view
     */
    public void click_webpageURL_4(View view) {
        if (!webpageURL4.isEmpty()) {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(webpageURL4));
            startActivity(intent);
        }
    }

    /**
     * Open intent with link for user webpage 5
     * @param view Necessary to attach it to a button view
     */
    public void click_webpageURL_5(View view) {
        if (!webpageURL5.isEmpty()) {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(webpageURL5));
            startActivity(intent);
        }
    }

//    ---

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

//    ---

    /**
     * Show the developer info
     */
    public void author_selected (View view) {
        ConstraintLayout constraintLayout = findViewById(R.id.author);
        constraintLayout.setVisibility(View.GONE);
    }

//    ---

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
            SharedConstants status = apiController.getInfo_bios(userName);

            Message message = handler.obtainMessage();

            if (status == NAME_EMPTY) {
                message.obj = NAME_EMPTY;
            } else if (status == PERSON_NOT_FOUND) {
                message.obj = PERSON_NOT_FOUND;
            } else if (status == PERSON_LOAD_OK) {
                message.obj = PERSON_LOAD_OK;
            } else if (status == PERSON_LOAD_FAIL) {
                message.obj = PERSON_LOAD_FAIL;
            } else
                message.obj = FUNCTION_NOT_IMPLEMENTED;

            handler.sendMessageAtFrontOfQueue(message);
        }
    }

    /**
     * Internal class to implement a runnable object to get the JSON images from torre.co
     */
    private class readJsonImages implements Runnable {
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
            SharedConstants status = apiController.readImages();

            Message message = handler.obtainMessage();

            if (status == IMAGES_OK) {
                message.obj = IMAGES_OK;
            } else if (status == IMAGES_FAILED) {
                message.obj = IMAGES_FAILED;
            } else
                message.obj = FUNCTION_NOT_IMPLEMENTED;

            handler.sendMessageAtFrontOfQueue(message);
        }
    }

}