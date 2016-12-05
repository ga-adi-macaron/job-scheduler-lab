package com.scottlindley.jobschedulerlab;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v4.content.LocalBroadcastManager;

import java.util.Random;

/**
 * Created by Scott Lindley on 11/29/2016.
 */

public class MyJobService2 extends JobService{
    private AsyncTask mTask;

    @Override
    public boolean onStartJob(final JobParameters jobParameters) {
        mTask = new AsyncTask<Void, Void, Integer>() {
            @Override
            protected Integer doInBackground(Void... voids) {
                Random r = new Random();
                return Color.rgb(r.nextInt(256),r.nextInt(256),r.nextInt(256));
            }

            @Override
            protected void onPostExecute(Integer i) {
                super.onPostExecute(i);
                Intent intent = new Intent("receive-info");
                intent.putExtra("name", "color1");
                intent.putExtra("color1", i);
                LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(intent);
                jobFinished(jobParameters, true);
            }
        }.execute();
        return true;
    }

    @Override
    public boolean onStopJob(JobParameters jobParameters) {
        jobFinished(jobParameters, false);
        return false;
    }

}
