package com.joelimyx.myapplication;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.content.LocalBroadcastManager;

/**
 * Created by Joe on 11/29/16.
 */

public class WordJobService extends JobService {
    private AsyncTask mTask;
    LocalBroadcastManager broadcastManager;

    @Override
    public boolean onStartJob(final JobParameters jobParameters) {
        broadcastManager = LocalBroadcastManager.getInstance(this);
        mTask = new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                Intent intent = new Intent(getString(R.string.WORD_BROADCAST));
                String [] temps = getResources().getStringArray(R.array.random_list);
                int rand = (int)(Math.random()*temps.length);
                intent.putExtra(getString(R.string.random_word),temps[rand]);
                broadcastManager.sendBroadcast(intent);

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
        mTask.cancel(true);
        return false;
    }
}
