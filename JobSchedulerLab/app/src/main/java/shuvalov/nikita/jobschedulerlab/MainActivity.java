package shuvalov.nikita.jobschedulerlab;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.PersistableBundle;
import android.support.annotation.NonNull;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity{
    public static final int PERIODIC_JOB_ID = 47;
    public static final int RANDOM_JOB_ID = 123;
    public static final int OTHER_JOB_ID = 55;
    public static final String CURRENT_COUNT = "count";
    public static final String PREVIOUS_COUNT = "previousCount";
    public static final String BROADCAST_TYPE = "broadcast type";
    public static final String COLOR_VALUE = "The Seeker";
    public static final String LAST_COLOR_VALUE = "El Scorcho";
    public static final String INTENT_FILTER_VALUE = "Who are you?";

    private String mPreviousValue = "";
    private int mPreviousColor=Color.GRAY;
    private int mPreviousTextColor=Color.WHITE;


    TextView mCurrentCountView, mPreviousCountView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        LocalBroadcastManager.getInstance(this).registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                int bType = intent.getIntExtra(BROADCAST_TYPE,0);
                Log.d("ID", "onReceive: "+bType);
                if (bType == PERIODIC_JOB_ID){
                    String current = String.valueOf(intent.getStringExtra(CURRENT_COUNT));
                    mCurrentCountView.setText(current);
                    mPreviousCountView.setText(mPreviousValue);
                    mPreviousValue= current;
                }else if (bType == RANDOM_JOB_ID){
                    int currentColor= intent.getIntExtra(COLOR_VALUE, Color.BLUE);
                    mPreviousCountView.setBackgroundColor(mPreviousColor);
                    mCurrentCountView.setBackgroundColor(currentColor);
                    mPreviousColor = currentColor;
                } else if (bType == OTHER_JOB_ID){
                    int currentColor= intent.getIntExtra(COLOR_VALUE, Color.BLUE);
                    mPreviousCountView.setTextColor(mPreviousTextColor);
                    mCurrentCountView.setTextColor(currentColor);
                    mPreviousTextColor = currentColor;
                }
            }
        }, new IntentFilter(INTENT_FILTER_VALUE));

        mCurrentCountView = (TextView)findViewById(R.id.current_count);
        mPreviousCountView = (TextView)findViewById(R.id.previous_count);

        assignJobs();
    }

    public void assignJobs(){

        PersistableBundle periodicPersistentBundle = new PersistableBundle();
        periodicPersistentBundle.putInt(CURRENT_COUNT, 0);

        JobInfo periodicJobInfo = new JobInfo.Builder(PERIODIC_JOB_ID, new ComponentName(this,PeriodicJobService.class))
                .setPeriodic(5000) //The values... they do nothing! It goes off seemingly whenever it wants.
                .setExtras(periodicPersistentBundle)
                .build();

        JobInfo randomJobInfo = new JobInfo.Builder(RANDOM_JOB_ID, new ComponentName(this,RandomChargingJobService.class))
                .setRequiresCharging(true)
                .setBackoffCriteria(3000,JobInfo.BACKOFF_POLICY_LINEAR)
                .build();

        JobInfo otherJobInfo = new JobInfo.Builder(OTHER_JOB_ID, new ComponentName(this, OtherChargingJobService.class))
                .setRequiresCharging(true)
                .setBackoffCriteria(1000, JobInfo.BACKOFF_POLICY_LINEAR)
                .build();

        JobScheduler jobScheduler = (JobScheduler)getSystemService(JOB_SCHEDULER_SERVICE);
        jobScheduler.schedule(periodicJobInfo);
        jobScheduler.schedule(randomJobInfo);
        jobScheduler.schedule(otherJobInfo);
    }
}
