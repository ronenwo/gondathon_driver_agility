package com.gondathon.driveragality;


import java.io.InputStream;
import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.ViewSwitcher.ViewFactory;


public class DriverStateActivity extends Activity {


    private Integer images [] = {R.drawable.smiley_cool, R.drawable.yawningsmileyface, R.drawable.sleepingface};
    private int currImage = 0;

    private Timer t;

    ImageSwitcher imageSwitcher;
    Button startbutton, stopButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_state);

        startbutton = (Button) findViewById(R.id.btnStart);
        stopButton = (Button) findViewById(R.id.btnStop);

        initializeImageSwitcher();
        setInitialImage();
        setStartScriptListener();
        setStopScriptListener();

    }

    private void initializeImageSwitcher() {
        imageSwitcher = (ImageSwitcher) findViewById(R.id.imageSwitcher);
        imageSwitcher.setFactory(new ViewFactory() {
            @Override
            public View makeView() {
                ImageView imageView = new ImageView(DriverStateActivity.this);
                return imageView;
            }
        });

        imageSwitcher.setInAnimation(AnimationUtils.loadAnimation(this, android.R.anim.fade_in));
        imageSwitcher.setOutAnimation(AnimationUtils.loadAnimation(this, android.R.anim.fade_out));

    }

    private void setStartScriptListener() {
//        Button startbutton = (Button) findViewById(R.id.btnStart);
        startbutton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
//                setCurrentImage();
                //Set the schedule function and rate
                startbutton.setEnabled(false);
                stopButton.setEnabled(true);
                t = new Timer();
                t.schedule(new TimerTask() {

                    public void run() {
                        //Called each time when 1000 milliseconds (1 second) (the period parameter)
                        currImage = 0;
                        runOnUiThread(new Runnable() {

                            public void run() {
                                setCurrentImage();
                            }
                        });
                    }

                }, 0);
                t.schedule(new TimerTask() {

                    public void run() {
                        //Called each time when 1000 milliseconds (1 second) (the period parameter)
                        currImage = 1;
                        runOnUiThread(new Runnable() {

                            public void run() {
                                setCurrentImage();
                                Animation animation1 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.blink);
                                imageSwitcher.startAnimation(animation1);
                            }
                        });
                    }

                }, 10000);
                t.schedule(new TimerTask() {

                    public void run() {
                        //Called each time when 1000 milliseconds (1 second) (the period parameter)
                        currImage = 2;
                        runOnUiThread(new Runnable() {

                            public void run() {
                                setCurrentImage();
                                Animation animation1 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.myanimation);
                                imageSwitcher.startAnimation(animation1);
                            }
                        });
                    }

                }, 20000);
                t.schedule(new TimerTask() {

                    public void run() {
                        //Called each time when 1000 milliseconds (1 second) (the period parameter)
                        currImage = 0;
                        runOnUiThread(new Runnable() {

                            public void run() {
                                setCurrentImage();
                            }
                        });
                    }

                }, 30000);
            }
        });
    }

    private void setStopScriptListener() {
//        final Button stopbutton = (Button) findViewById(R.id.btnStop);
        stopButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                startbutton.setEnabled(true);
                stopButton.setEnabled(false);
                t.cancel();
                currImage=0;
                setCurrentImage();
                startbutton.setEnabled(true);
            }
        });
    }



    private void setInitialImage() {
        setCurrentImage();
    }

    private void setCurrentImage() {
//        final ImageSwitcher imageSwitcher = (ImageSwitcher) findViewById(R.id.imageSwitcher);
        imageSwitcher.setImageResource(images[currImage]);
    }

    private Integer getCurrImage() {
//        final ImageSwitcher imageSwitcher = (ImageSwitcher) findViewById(R.id.imageSwitcher);
        return images[currImage];
    }





}