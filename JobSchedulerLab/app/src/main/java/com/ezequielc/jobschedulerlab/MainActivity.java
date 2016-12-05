package com.ezequielc.jobschedulerlab;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    public static final int PERIODIC_ONE_JOB_ID = 1; // JobServiceOne
    public static final int NETWORK_JOB_ID = 2; // JobServiceTwo
    public static final int PERIODIC_TWO_JOB_ID = 3; // JobServiceThree

    JobScheduler mJobScheduler;
    TextView mPeriodicOneOld, mNetworkOld, mPeriodicTwoOld;
    TextView mPeriodicOneNew, mNetworkNew, mPeriodicTwoNew;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mJobScheduler = (JobScheduler) getSystemService(JOB_SCHEDULER_SERVICE);

        mPeriodicOneOld = (TextView) findViewById(R.id.periodic_one_old);
        mNetworkOld = (TextView) findViewById(R.id.network_old);
        mPeriodicTwoOld = (TextView) findViewById(R.id.periodic_two_old);
        mPeriodicOneNew = (TextView) findViewById(R.id.periodic_one_new);
        mNetworkNew = (TextView) findViewById(R.id.network_new);
        mPeriodicTwoNew = (TextView) findViewById(R.id.periodic_two_new);

        // Periodic One Job (5 seconds)
        PersistableBundle periodicOneJobPersistableBundle = new PersistableBundle();
        periodicOneJobPersistableBundle.putString("type", "periodicOne");

        JobInfo periodicOneJobInfo = new JobInfo.Builder(PERIODIC_ONE_JOB_ID,
                new ComponentName(this, JobServiceOne.class)) // JobServiceOne
                .setExtras(periodicOneJobPersistableBundle)
                .setPeriodic(5000)
                .build();

        mJobScheduler.schedule(periodicOneJobInfo);

        // Network Job
        PersistableBundle networkJobPersistableBundle = new PersistableBundle();
        networkJobPersistableBundle.putString("type", "network");

        JobInfo networkJobInfo = new JobInfo.Builder(PERIODIC_TWO_JOB_ID,
                new ComponentName(this, JobServiceThree.class)) // JobServiceThree
                .setExtras(networkJobPersistableBundle)
                .setPeriodic(5000) // Do I need this?
                .setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY)
                .build();

        mJobScheduler.schedule(networkJobInfo);

        // Periodic Two Job (15 seconds)
        PersistableBundle periodicTwoJobPersistableBundle = new PersistableBundle();
        periodicTwoJobPersistableBundle.putString("type", "periodicTwo");

        JobInfo periodicTwoJobInfo = new JobInfo.Builder(NETWORK_JOB_ID,
                new ComponentName(this, JobServiceTwo.class)) // JobServiceTwo
                .setExtras(periodicTwoJobPersistableBundle)
                .setPeriodic(15000)
                .build();

        mJobScheduler.schedule(periodicTwoJobInfo);
    }

    public interface JobServiceCommunicationListener {
        void communicationMethod();
    }
}
