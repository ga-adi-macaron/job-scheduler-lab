package com.joelimyx.myapplication;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity{
    public static final int FIRST_JOB_ID = 1;
    public static final int SECOND_JOB_ID = 2;
    public static final int THIRD_JOB_ID = 3;
    private TextView mOldNumberText, mOldWordText, mUpdatedNumberText, mUpdateWordText;
    private ImageView mOldColor, mUpdateColor;
    BroadcastReceiver mNumberReceiver, mColorReceiver,mWordReceiver;

    @Override
    protected void onStart() {
        super.onStart();
        LocalBroadcastManager.getInstance(this).registerReceiver(mNumberReceiver,new IntentFilter(getString(R.string.NUMBER_BROADCAST)));
        LocalBroadcastManager.getInstance(this).registerReceiver(mColorReceiver,new IntentFilter(getString(R.string.COLOR_BROADCAST)));
        LocalBroadcastManager.getInstance(this).registerReceiver(mWordReceiver,new IntentFilter(getString(R.string.WORD_BROADCAST)));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mOldNumberText = (TextView) findViewById(R.id.old_number_text);
        mOldColor = (ImageView) findViewById(R.id.old_color);
        mOldWordText = (TextView) findViewById(R.id.old_word_text);
        mUpdatedNumberText = (TextView) findViewById(R.id.updated_number_text);
        mUpdateColor = (ImageView) findViewById(R.id.updated_color);
        mUpdateWordText = (TextView) findViewById(R.id.updated_word_text);

        JobScheduler scheduler = (JobScheduler) getSystemService(JOB_SCHEDULER_SERVICE);

        JobInfo numberJobInfo = new JobInfo.Builder(FIRST_JOB_ID,
                new ComponentName(this,NumberJobService.class))
                .setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY)
                .build();

        JobInfo colorJobInfo = new JobInfo.Builder(SECOND_JOB_ID,
                new ComponentName(this,ColorJobService.class))
                .setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY)
                .build();

        JobInfo wordJobInfo = new JobInfo.Builder(THIRD_JOB_ID,
                new ComponentName(this,WordJobService.class))
                .setPeriodic(4000)
                .build();

        scheduler.schedule(numberJobInfo);
        scheduler.schedule(colorJobInfo);
        scheduler.schedule(wordJobInfo);

        //Random Number
        mNumberReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String newNumber = intent.getStringExtra(getString(R.string.number_message));
                mOldNumberText.setText(mUpdatedNumberText.getText());
                mUpdatedNumberText.setText(newNumber);
            }
        };

        //Random Color
        mColorReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                int redColor = intent.getIntExtra(getString(R.string.red_color_message),0);
                int greenColor = intent.getIntExtra(getString(R.string.green_color_message),0);
                int blueColor= intent.getIntExtra(getString(R.string.blue_color_message),0);
                ColorDrawable color = (ColorDrawable) mUpdateColor.getBackground();
                if (color!=null)
                    mOldColor.setBackgroundColor(color.getColor());
                mUpdateColor.setBackgroundColor(Color.rgb(redColor,greenColor,blueColor));
            }
        };

        //Random Word
        mWordReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String newWord = intent.getStringExtra(getString(R.string.random_word));
                mOldWordText.setText(mUpdateWordText.getText());
                mUpdateWordText.setText(newWord);
            }
        };
    }

    @Override
    protected void onStop() {
        super.onStop();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mNumberReceiver);
    }
}
