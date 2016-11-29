package shuvalov.nikita.jobschedulerlab;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {
    public static final int PERIODIC_JOB_ID = 47;
    public static final int RANDOM_JOB_ID = 123;
    public static final int OTHER_JOB_ID = 55;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        assignJobs();
    }

    public void assignJobs(){

        PersistableBundle periodicPersistentBundle = new PersistableBundle();
        periodicPersistentBundle.putInt("count", 0);

        JobInfo periodicJobInfo = new JobInfo.Builder(PERIODIC_JOB_ID, new ComponentName(this,PeriodicJobService.class))
                .setPeriodic(10000)
                .setExtras(periodicPersistentBundle)
                .build();

        JobInfo randomJobInfo = new JobInfo.Builder(RANDOM_JOB_ID, new ComponentName(this,RandomChargingJobService.class))
                .setRequiresCharging(true)
                .setBackoffCriteria(4000,JobInfo.BACKOFF_POLICY_LINEAR)
                .build();

        JobInfo otherJobInfo = new JobInfo.Builder(OTHER_JOB_ID, new ComponentName(this, OtherChargingJobService.class))
                .setRequiresCharging(true)
                .setBackoffCriteria(4000, JobInfo.BACKOFF_POLICY_LINEAR)
                .build();

        JobScheduler jobScheduler = (JobScheduler)getSystemService(JOB_SCHEDULER_SERVICE);
        jobScheduler.schedule(periodicJobInfo);
        jobScheduler.schedule(randomJobInfo);
        jobScheduler.schedule(otherJobInfo);
    }
}
