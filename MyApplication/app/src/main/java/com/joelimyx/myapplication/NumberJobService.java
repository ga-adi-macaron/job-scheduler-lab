package com.joelimyx.myapplication;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.content.LocalBroadcastManager;

/**
 * Created by Joe on 11/29/16.
 * Random Number from 0-100
 */

public class NumberJobService extends JobService {
    private AsyncTask mTask;
    LocalBroadcastManager broadcastManager;

    @Override
    public boolean onStartJob(final JobParameters jobParameters) {
        broadcastManager = LocalBroadcastManager.getInstance(this);
        mTask = new AsyncTask<Void,Void,String>(){
            @Override
            protected String doInBackground(Void... voids) {
                return String.valueOf((int)(Math.random()*100));
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                Intent intent = new Intent(getString(R.string.NUMBER_BROADCAST));
                intent.putExtra(getString(R.string.number_message),s);
                broadcastManager.sendBroadcast(intent);
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
