package com.korbkenny.kennyjoblab;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.content.LocalBroadcastManager;
import android.view.View;


/**
 * Created by KorbBookProReturns on 11/29/16.
 */

public class KennysGoodOldJobService extends JobService {
    private static final String TAG = "FromDaJobService";
    private AsyncTask mTask;
    LocalBroadcastManager bMan;


    @Override
    public boolean onStartJob(final JobParameters jobParameters) {

        bMan = LocalBroadcastManager.getInstance(this);

        mTask = new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                Intent intent = new Intent("top_red");
                int red = (int)(Math.random()*255);
                int green = (int)(Math.random()*255);
                int blue = (int)(Math.random()*255);
                intent.putExtra("red",red).putExtra("green",green).putExtra("blue",blue);
                bMan.sendBroadcast(intent);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                jobFinished(jobParameters,false);
            }
        }.execute();
        return false;
    }

    @Override
    public boolean onStopJob(JobParameters jobParameters) {
        return false;
    }

}