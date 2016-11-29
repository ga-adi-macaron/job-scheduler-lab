package com.scottlindley.jobschedulerlab;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    public static final int FIRST_JOB_ID = 1;
    public static final int SECOND_JOB_ID = 2;
    public static final int THIRD_JOB_ID = 3;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final ImageView oldColor1Image = (ImageView)findViewById(R.id.old_color1);
        final ImageView oldColor2Image = (ImageView)findViewById(R.id.old_color2);


        BroadcastReceiver mReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                int oldColor1 = -1;
                int oldColor2 = -1;

                String message = intent.getStringExtra("message");
                int color1 = intent.getIntExtra("color1", -1);
                int color2 = intent.getIntExtra("color2", -1);

                String name = intent.getStringExtra("name");
                switch (name) {
                    case "message":
                        ((TextView) findViewById(R.id.message)).setText(message);

                        break;
                    case "color1":
                        if (color1 != -1) {

                            ColorDrawable drawable =
                                    (ColorDrawable) findViewById(R.id.image_middle).getBackground();
                            if(drawable!=null) {
                                oldColor1 = drawable.getColor();
                            }
                            (findViewById(R.id.image_middle))
                                    .setBackgroundColor(color1);
                        }
                        break;
                    case "color2":
                        if (color2 != -1) {
                            ColorDrawable drawable =
                                    (ColorDrawable) findViewById(R.id.image_bottom).getBackground();
                            if(drawable!=null) {
                                oldColor2 = drawable.getColor();
                            }
                            (findViewById(R.id.image_bottom))
                                    .setBackgroundColor(color2);
                        }
                        break;
                }
                if(oldColor1!=-1){
                    oldColor1Image.setBackgroundColor(oldColor1);
                }
                if(oldColor2 !=-1){
                    oldColor2Image.setBackgroundColor(oldColor2);
                }


            }
        };


        LocalBroadcastManager.getInstance(this).registerReceiver(mReceiver,
                new IntentFilter("receive-info"));


        PersistableBundle networkPersistedBundle = new PersistableBundle();
        networkPersistedBundle.putString("network check","Connected to \nthe Interwebs at");


        JobInfo job1 = new JobInfo.Builder(FIRST_JOB_ID,
                new ComponentName(this, MyJobService.class))
                .setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY)
                .setExtras(networkPersistedBundle)
                .build();
        JobInfo job2 = new JobInfo.Builder(SECOND_JOB_ID,
                new ComponentName(this, MyJobService2.class))
                .setRequiresCharging(true)
                .setBackoffCriteria(500, JobInfo.BACKOFF_POLICY_LINEAR)
                .build();
        JobInfo job3 = new JobInfo.Builder(THIRD_JOB_ID,
                new ComponentName(this, MyJobService3.class))
                .setRequiresCharging(true)
                .setBackoffCriteria(500, JobInfo.BACKOFF_POLICY_LINEAR)
                .build();

        JobScheduler jobScheduler = (JobScheduler) getSystemService(JOB_SCHEDULER_SERVICE);
        jobScheduler.schedule(job1);
        jobScheduler.schedule(job2);
        jobScheduler.schedule(job3);

    }
}
