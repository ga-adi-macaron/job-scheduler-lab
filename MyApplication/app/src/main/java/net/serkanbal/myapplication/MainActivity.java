package net.serkanbal.myapplication;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    TextView mJob1Before, mJob1After, mJob2Before, mJob2After, mJob3Before, mJob3After;
    BroadcastReceiver mReceiver, mReceiver2, mReceiver3;
    Integer mFirstNumber = 0, mSecondNumber = 0;
    String mFirstDate = "", mSecondDate = "", mFirstToken = "", mSecondToken = "";
    public static final int FIRST_JOB_ID = 1;
    public static final int SECOND_JOB_ID = 2;
    public static final int THIRD_JOB_ID = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mJob1Before = (TextView) findViewById(R.id.job1_before);
        mJob1After = (TextView) findViewById(R.id.job1_after);
        mJob2Before = (TextView) findViewById(R.id.job2_before);
        mJob2After = (TextView) findViewById(R.id.job2_after);
        mJob3Before = (TextView) findViewById(R.id.job3_before);
        mJob3After = (TextView) findViewById(R.id.job3_after);

        mReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                Integer value = intent.getIntExtra(JobServiceRandomNumber.MESSAGE1, -1);
                Log.d("SERKANO", "onReceive: " + value);
                if (mFirstNumber  == 0 && mSecondNumber == 0) {
                    mJob1Before.setText("Waiting...");
                    mSecondNumber = value;
                    mJob1After.setText(mSecondNumber.toString());
                } else {
                    mFirstNumber = mSecondNumber;
                    mJob1Before.setText(mFirstNumber.toString());
                    mSecondNumber = value;
                    mJob1After.setText(mSecondNumber.toString());
                }

            }
        };

        mReceiver2 = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String date = intent.getStringExtra(JobServiceDateTime.MESSAGE2);

                if (mFirstDate.equals("") && mSecondDate.equals("")) {
                    mJob2Before.setText("Waiting...");
                    mSecondDate = date;
                    mJob2After.setText(mSecondDate);
                } else {
                    mFirstDate = mSecondDate;
                    mJob2Before.setText(mFirstDate);
                    mSecondDate = date;
                    mJob2After.setText(mSecondDate);
                }

            }
        };

        mReceiver3 = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String token = intent.getStringExtra(JobServiceRandomToken.MESSAGE3);

                if (mFirstToken.equals("") && mSecondToken.equals("")) {
                    mJob3Before.setText("Waiting...");
                    mSecondToken = token;
                    mJob3After.setText(mSecondToken);
                } else {
                    mFirstToken = mSecondToken;
                    mJob3Before.setText(mFirstToken);
                    mSecondToken = token;
                    mJob3After.setText(mSecondToken);
                }
            }
        };

        JobInfo periodicJobInfo = new JobInfo.Builder(FIRST_JOB_ID,
                new ComponentName(this, JobServiceRandomNumber.class))
                .setPeriodic(5000)
                .build();

        JobInfo periodicJobInfo2 = new JobInfo.Builder(SECOND_JOB_ID,
                new ComponentName(this, JobServiceDateTime.class))
                .setPeriodic(3000)
                .build();

        JobInfo conditionJobInfo = new JobInfo.Builder(THIRD_JOB_ID,
                new ComponentName(this, JobServiceRandomToken.class))
                .setRequiresCharging(true)
                .setBackoffCriteria(1000, JobInfo.BACKOFF_POLICY_LINEAR)
                .build();

        JobScheduler jobScheduler = (JobScheduler) getSystemService(JOB_SCHEDULER_SERVICE);
        jobScheduler.schedule(periodicJobInfo);
        jobScheduler.schedule(periodicJobInfo2);
        jobScheduler.schedule(conditionJobInfo);


    }

    @Override
    protected void onStart() {
        super.onStart();
        LocalBroadcastManager.getInstance(this).registerReceiver((mReceiver),
                new IntentFilter(JobServiceRandomNumber.RESULT1)
        );
        LocalBroadcastManager.getInstance(this).registerReceiver((mReceiver2),
                new IntentFilter(JobServiceDateTime.RESULT2)
        );
        LocalBroadcastManager.getInstance(this).registerReceiver((mReceiver3),
                new IntentFilter(JobServiceRandomToken.RESULT3)
        );
    }

    @Override
    protected void onStop() {
        super.onStop();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mReceiver);
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mReceiver2);
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mReceiver3);
        super.onStop();
    }
}
