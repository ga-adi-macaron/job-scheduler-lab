package com.joelimyx.myapplication;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.content.LocalBroadcastManager;

/**
 * Created by Joe on 11/29/16.
 */

public class ColorJobService extends JobService {
    private AsyncTask mTask;
    LocalBroadcastManager broadcastManager;


    @Override
    public boolean onStartJob(final JobParameters jobParameters) {
        broadcastManager = LocalBroadcastManager.getInstance(this);
        mTask = new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                Intent intent = new Intent(getString(R.string.COLOR_BROADCAST));

                int red = (int)(Math.random()*255);
                intent.putExtra(getString(R.string.red_color_message),red);
                int green = (int)(Math.random()*255);
                intent.putExtra(getString(R.string.green_color_message),green);
                int blue = (int)(Math.random()*255);
                intent.putExtra(getString(R.string.blue_color_message),blue);

                broadcastManager.sendBroadcast(intent);
                return null;
            }

            @Override
            protected void onPostExecute(Void voids) {
                super.onPostExecute(voids);
                jobFinished(jobParameters,true);
            }
        }.execute();
        return true;
    }

    @Override
    public boolean onStopJob(JobParameters jobParameters) {
        mTask.cancel(true);
        return false;
    }
}
