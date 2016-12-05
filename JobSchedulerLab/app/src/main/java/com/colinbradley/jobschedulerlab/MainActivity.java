package com.colinbradley.jobschedulerlab;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.PersistableBundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    public static final int JOB_A = 0;
    public static final int JOB_B = 1;
    public static final int JOB_C = 2;

    TextView mNewNum, mNewWord, mOldNum, mOldWord;
    ImageView mNewColor, mOldColor;

    BroadcastReceiver mNumReceiver, mWordReceiver, mColorReceiver;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mNewNum = (TextView)findViewById(R.id.newNumber);
        mNewWord = (TextView)findViewById(R.id.newWord);
        mNewColor = (ImageView) findViewById(R.id.newColor);
        mOldNum = (TextView)findViewById(R.id.oldnumber);
        mOldWord = (TextView)findViewById(R.id.oldWord);
        mOldColor = (ImageView) findViewById(R.id.oldColor);

        JobScheduler scheduler = (JobScheduler)getSystemService(JOB_SCHEDULER_SERVICE);

        JobInfo numJobInfo = new JobInfo.Builder(JOB_A,
                new ComponentName(this, NumJobService.class))
                .setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY)
                .build();

        JobInfo colorJobInfo = new JobInfo.Builder(JOB_B,
                new ComponentName(this, ColorJobService.class))
                .setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY)
                .build();

        JobInfo wordJobInfo = new JobInfo.Builder(JOB_C,
                new ComponentName(this, WordJobService.class))
                .setPeriodic(3500)
                .build();

        scheduler.schedule(numJobInfo);
        scheduler.schedule(colorJobInfo);
        scheduler.schedule(wordJobInfo);


        mNumReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String newNum = intent.getStringExtra(getString(R.string.NumMessage));
                mOldNum.setText(mNewNum.getText());
                mNewNum.setText(newNum);
            }
        };

        mColorReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                int red = intent.getIntExtra(getString(R.string.RedMessage),0);
                int green = intent.getIntExtra(getString(R.string.GreenMessage),0);
                int blue = intent.getIntExtra(getString(R.string.BlueMessage),0);

                ColorDrawable color = (ColorDrawable)mNewColor.getBackground();
                if (color != null){
                    mOldColor.setBackgroundColor(color.getColor());
                    mNewColor.setBackgroundColor(Color.rgb(red,green,blue));
                }
            }
        };

        mWordReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String newWord = intent.getStringExtra(getString(R.string.WORD));
                mOldWord.setText(mNewWord.getText());
                mNewWord.setText(newWord);
            }
        };

    }

    @Override
    protected void onStart() {
        super.onStart();
        LocalBroadcastManager.getInstance(this)
                .registerReceiver(mNumReceiver,
                        new IntentFilter(getString(R.string.NumBroadcast)));

        LocalBroadcastManager.getInstance(this)
                .registerReceiver(mColorReceiver,
                        new IntentFilter(getString(R.string.ColorBroadcast)));

        LocalBroadcastManager.getInstance(this)
                .registerReceiver(mWordReceiver,
                        new IntentFilter(getString(R.string.WordBroadcast)));
    }

}
