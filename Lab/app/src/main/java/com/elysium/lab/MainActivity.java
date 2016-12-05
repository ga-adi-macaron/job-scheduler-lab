package com.elysium.lab;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity implements ImageRacer.ImageRacerListener {

    private ImageView top1, top2, top3, top4, top5, mid1, mid2, mid3, mid4, mid5, bot1, bot2, bot3, bot4, bot5;
    private int currentTop, currentMid, currentBot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        top1 = (ImageView) findViewById(R.id.topImage1);
        top2 = (ImageView) findViewById(R.id.topImage2);
        top3 = (ImageView) findViewById(R.id.topImage3);
        top4 = (ImageView) findViewById(R.id.topImage4);
        top5 = (ImageView) findViewById(R.id.topImage5);
        mid1 = (ImageView) findViewById(R.id.midImage1);
        mid2 = (ImageView) findViewById(R.id.midImage2);
        mid3 = (ImageView) findViewById(R.id.midImage3);
        mid4 = (ImageView) findViewById(R.id.midImage4);
        mid5 = (ImageView) findViewById(R.id.midImage5);
        bot1 = (ImageView) findViewById(R.id.botImage1);
        bot2 = (ImageView) findViewById(R.id.botImage2);
        bot3 = (ImageView) findViewById(R.id.botImage3);
        bot4 = (ImageView) findViewById(R.id.botImage4);
        bot5 = (ImageView) findViewById(R.id.botImage5);

        currentTop = 1;
        currentMid = 1;
        currentBot = 1;

        ImageRacer.getInstance().setImageRacerListener(this);

        PersistableBundle topBundle = new PersistableBundle();

        topBundle.putString("type", "top");

        JobInfo topInfo = new JobInfo.Builder

                (1, new ComponentName(getPackageName(), ImageJobService.class.getName()))
                .setExtras(topBundle)
                .setRequiresCharging(false)
                .setPeriodic(1000)
                .build();

        PersistableBundle midBundle = new PersistableBundle();

        midBundle.putString("type", "mid");

        JobInfo midInfo = new JobInfo.Builder

                (2, new ComponentName(getPackageName(), ImageJobService.class.getName()))
                .setExtras(midBundle)
                .setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY)
                .setPeriodic(2000)
                .build();

        PersistableBundle botBundle = new PersistableBundle();

        botBundle.putString("type", "bot");

        JobInfo botInfo = new JobInfo.Builder

                (3, new ComponentName(getPackageName(), ImageJobService.class.getName()))
                .setExtras(botBundle)
                .setRequiresDeviceIdle(false)
                .setPeriodic(3000)
                .build();

        JobScheduler scheduler = (JobScheduler) getSystemService(Context.JOB_SCHEDULER_SERVICE);
        scheduler.schedule(topInfo);
        scheduler.schedule(midInfo);
        scheduler.schedule(botInfo);
    }

    @Override
    public void updateImages(String type) {

        switch(type) {

            case "top":

                switch(currentTop) {

                    case 1: top1.setVisibility(View.INVISIBLE); top2.setVisibility(View.VISIBLE); currentTop = 2; break;
                    case 2: top2.setVisibility(View.INVISIBLE); top3.setVisibility(View.VISIBLE); currentTop = 3; break;
                    case 3: top3.setVisibility(View.INVISIBLE); top4.setVisibility(View.VISIBLE); currentTop = 4; break;
                    case 4: top4.setVisibility(View.INVISIBLE); top5.setVisibility(View.VISIBLE); currentTop = 5; break;
                    case 5: top5.setVisibility(View.INVISIBLE); top1.setVisibility(View.VISIBLE); currentTop = 1; break;

                    default: break;
                }

                break;

            case "mid":

                switch(currentMid) {

                    case 1: mid1.setVisibility(View.INVISIBLE); mid2.setVisibility(View.VISIBLE); currentMid = 2; break;
                    case 2: mid2.setVisibility(View.INVISIBLE); mid3.setVisibility(View.VISIBLE); currentMid = 3; break;
                    case 3: mid3.setVisibility(View.INVISIBLE); mid4.setVisibility(View.VISIBLE); currentMid = 4; break;
                    case 4: mid4.setVisibility(View.INVISIBLE); mid5.setVisibility(View.VISIBLE); currentMid = 5; break;
                    case 5: mid5.setVisibility(View.INVISIBLE); mid1.setVisibility(View.VISIBLE); currentMid = 1; break;
                    default: break;
                }
                break;

            case "bot":

                switch(currentBot) {

                    case 1: bot1.setVisibility(View.INVISIBLE); bot2.setVisibility(View.VISIBLE); currentBot = 2; break;
                    case 2: bot2.setVisibility(View.INVISIBLE); bot3.setVisibility(View.VISIBLE); currentBot = 3; break;
                    case 3: bot3.setVisibility(View.INVISIBLE); bot4.setVisibility(View.VISIBLE); currentBot = 4; break;
                    case 4: bot4.setVisibility(View.INVISIBLE); bot5.setVisibility(View.VISIBLE); currentBot = 5; break;
                    case 5: bot5.setVisibility(View.INVISIBLE); bot1.setVisibility(View.VISIBLE); currentBot = 1; break;

                    default: break;
                }

                break;

            default: break;
        }
    }
}
