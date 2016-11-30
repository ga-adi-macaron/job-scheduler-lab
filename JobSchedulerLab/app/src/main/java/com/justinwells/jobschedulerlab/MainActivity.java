package com.justinwells.jobschedulerlab;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    public static final int JOB_ONE_ID = 1;
    public static final int JOB_TWO_ID = 2;
    public static final int JOB_THREE_ID = 3;


    private static TextView oldTime, newTime, oldColor, newColor;
    private static ImageView battery;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        oldTime = (TextView) findViewById(R.id.old_number);
        newTime = (TextView) findViewById(R.id.new_number);

        oldColor = (TextView) findViewById(R.id.old_color);
        newColor = (TextView) findViewById(R.id.new_color);

        battery = (ImageView) findViewById(R.id.battery);
        battery.setVisibility(View.GONE);

        JobScheduler jobScheduler = (JobScheduler) getSystemService(JOB_SCHEDULER_SERVICE);

        JobInfo timeJobInfo = new JobInfo.Builder(JOB_ONE_ID,
                new ComponentName(this, TimeService.class))
                .setPeriodic(5000)
                .build();

        JobInfo colorJobInfo = new JobInfo.Builder(JOB_TWO_ID,
                new ComponentName(this, ColorService.class))
                .setPeriodic(5000)
                .build();

        JobInfo connectedJobInfo = new JobInfo.Builder(JOB_THREE_ID,
                new ComponentName(this, ConnectedService.class))
                .setRequiresCharging(true)
                .build();



        jobScheduler.schedule(timeJobInfo);
        jobScheduler.schedule(colorJobInfo);
        jobScheduler.schedule(connectedJobInfo);
    }

    public static void updateTimeViews () {
        ViewManager manager = ViewManager.getInstance();
        oldTime.setText(manager.getOldTime());
        newTime.setText(manager.getNewTime());



    }

    public static void updateColorViews () {
        ViewManager manager = ViewManager.getInstance();


        if (manager.getOldColorText() != null) {
            oldColor.setText(manager.getOldColorText());
            oldColor.setTextColor(manager.getOldColor());
        }

        newColor.setText(manager.getNewColorText());
        newColor.setTextColor(manager.getNewColor());

    }

    public static void setChargingIcon (boolean charging) {
        if (charging) {
            battery.setVisibility(View.VISIBLE);
        } else {
            battery.setVisibility(View.GONE);
        }
    }
}
