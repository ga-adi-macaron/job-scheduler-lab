package com.korbkenny.kennyjoblab;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.PersistableBundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    public static final int PERIODIC_JOB_ID = 1;
    public static final int EXPONENTIAL_JOB_ID = 2;
    public static final int CONDITIONAL_JOB_ID = 3;

    TextView mTopLeft,mTopRight,mMiddleLeft,mMiddleRight,mBottomLeft,mBottomRight;

    BroadcastReceiver mTopReceiver, mMiddleReceiver, mBottomReceiver;

    @Override
    protected void onStart() {
        super.onStart();
        LocalBroadcastManager.getInstance(this)
                .registerReceiver(mTopReceiver,
                        new IntentFilter("top_red"));
        LocalBroadcastManager.getInstance(this)
                .registerReceiver(mMiddleReceiver,
                        new IntentFilter("middle_text"));
        LocalBroadcastManager.getInstance(this)
                .registerReceiver(mBottomReceiver,
                        new IntentFilter("bottom_green"));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTopLeft = (TextView) findViewById(R.id.TopLeft);
        mTopRight = (TextView) findViewById(R.id.TopRight);
        mMiddleLeft = (TextView) findViewById(R.id.MiddleLeft);
        mMiddleRight = (TextView) findViewById(R.id.MiddleRight);
        mBottomLeft = (TextView) findViewById(R.id.BottomLeft);
        mBottomRight = (TextView) findViewById(R.id.BottomRight);

        JobScheduler scheduler = (JobScheduler) getSystemService(JOB_SCHEDULER_SERVICE);


        //~~~~~~~~~~~~~~~~~~~~~~~~
        //
        //TOP STUFF
        PersistableBundle periodicBundle = new PersistableBundle();
        periodicBundle.putString("type","periodic");

        JobInfo periodicJob = new JobInfo.Builder(PERIODIC_JOB_ID,
                new ComponentName(this,KennysGoodOldJobService.class))
                .setExtras(periodicBundle)
                .setPeriodic(500)
                .build();

        scheduler.schedule(periodicJob);

        mTopReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                int red = intent.getIntExtra("red",0);
                int green = intent.getIntExtra("green",0);
                int blue = intent.getIntExtra("blue",0);
                ColorDrawable cd = (ColorDrawable)mTopLeft.getBackground();
                int colorCode = cd.getColor();
                mTopRight.setBackgroundColor(colorCode);
                mTopLeft.setBackgroundColor(Color.rgb(red,green,blue));
            }
        };

        //~~~~~~~~~~~~~~~~~~~~~~~~
        //
        //MIDDLE STUFF

        PersistableBundle exponentialBundle = new PersistableBundle();
        periodicBundle.putString("type","exponential");

        JobInfo exponentialJob = new JobInfo.Builder(EXPONENTIAL_JOB_ID,
                new ComponentName(this,KennysGoodOldJobService.class))
                .setExtras(exponentialBundle)
                .setRequiredNetworkType(JobInfo.NETWORK_TYPE_UNMETERED)
                .setRequiresCharging(true)
//                .setRequiredNetworkType(JobInfo.NETWORK_TYPE_NONE)
                .setBackoffCriteria(50,JobInfo.BACKOFF_POLICY_EXPONENTIAL)
                .build();

        scheduler.schedule(exponentialJob);

        mMiddleReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String newThing = intent.getStringExtra("hi");
                String oldThing = mMiddleLeft.getText().toString();
                mMiddleRight.setText(oldThing);
                mMiddleLeft.setText(newThing);
            }
        };


        //////////////////////
        //  Conditional No Network Job

        PersistableBundle conditionalBundle = new PersistableBundle();
        periodicBundle.putString("type","conditional");

        JobInfo conditionalJob = new JobInfo.Builder(CONDITIONAL_JOB_ID,
                new ComponentName(this,KennysGoodOldJobService.class))
                .setExtras(conditionalBundle)
                .setPeriodic(500)
                .build();

        scheduler.schedule(conditionalJob);

        mBottomReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                int green = intent.getIntExtra("green",0);
                ColorDrawable cd = (ColorDrawable)mTopLeft.getBackground();
                int colorCode = cd.getColor();
                mBottomRight.setBackgroundColor(colorCode);
                mBottomLeft.setBackgroundColor(Color.rgb(255,green,255));
            }
        };
    }
}
