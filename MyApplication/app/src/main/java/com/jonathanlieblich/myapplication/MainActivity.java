package com.jonathanlieblich.myapplication;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements SoloJobService.CountIncreasedListener,
        DualJobService.TimeChangeListener, SecondDualJobService.PlugChangeListener {
    public static final int DUAL_JOB_ID = 1;
    public static final int SOLO_JOB_ID = 2;
    public static final int SECOND_DUAL_JOB = 3;

    private TextView mSoloText, mFirstDual, mSecondDual;
    private TextView mSoloOld, mFirstOld, mSecondOld;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mSoloText = (TextView)findViewById(R.id.solo_counter);
        mFirstDual = (TextView)findViewById(R.id.first_dual);
        mSecondDual = (TextView)findViewById(R.id.second_dual);

        mSoloOld = (TextView)findViewById(R.id.solo_old);
        mFirstOld = (TextView)findViewById(R.id.first_old);
        mSecondOld = (TextView)findViewById(R.id.second_old);

        JobInfo firstDualJob = new JobInfo.Builder(DUAL_JOB_ID,
                new ComponentName(this, DualJobService.class))
                .setRequiresCharging(true)
                .setBackoffCriteria(500, JobInfo.BACKOFF_POLICY_LINEAR)
                .build();

        JobInfo secondDualJob = new JobInfo.Builder(SECOND_DUAL_JOB,
                new ComponentName(this, SecondDualJobService.class))
                .setRequiresCharging(true)
                .setBackoffCriteria(500, JobInfo.BACKOFF_POLICY_LINEAR)
                .build();

        int count = 1;

        PersistableBundle soloJobBundle = new PersistableBundle();
        soloJobBundle.putInt("count", count);

        JobInfo soloJob = new JobInfo.Builder(SOLO_JOB_ID,
                new ComponentName(this, SoloJobService.class))
                .setPeriodic(5000)
                .setExtras(soloJobBundle)
                .build();

        JobScheduler scheduler = (JobScheduler)getSystemService(JOB_SCHEDULER_SERVICE);
        scheduler.schedule(firstDualJob);
        scheduler.schedule(secondDualJob);
        scheduler.schedule(soloJob);

        if(mFirstDual.getText() != null) {
            mFirstOld.setText(mFirstDual.getText());
        }

        if(mSecondDual.getText() != null) {
            mSecondOld.setText(mSecondDual.getText());
        }

        if(mSoloText.getText() != null) {
            mSoloOld.setText(mSoloText.getText());
        }

        ServiceListenerHelper.getInstance().setCountListener(this);
        ServiceListenerHelper.getInstance().setPlugListener(this);
        ServiceListenerHelper.getInstance().setTimeListener(this);
    }

    @Override
    public void onChangeListener(String time) {
        mFirstDual.setText(time);
    }

    @Override
    public String updateStatusListener(String status) {
        if(status.equals("Unplugged")) {
            return "Plugged in";
        }
        return "Unplugged";
    }

    @Override
    public void onCountIncreaseListener(int count) {
        mSoloText.setText(count+++"");
    }
}
