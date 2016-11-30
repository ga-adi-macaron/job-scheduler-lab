package com.colinbradley.jobschedulerlab;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.content.LocalBroadcastManager;

import java.util.Random;

/**
 * Created by colinbradley on 11/29/16.
 */

public class ColorJobService extends JobService {

    private AsyncTask mTask;

    LocalBroadcastManager broadcastManager;


    @Override
    public boolean onStartJob(final JobParameters jobParameters) {
        broadcastManager = LocalBroadcastManager.getInstance(this);

        mTask = new AsyncTask<Void,Void,Void>(){
            @Override
            protected Void doInBackground(Void... voids) {
                Random random = new Random();
                Intent intent = new Intent(getString(R.string.ColorBroadcast));

                int red = random.nextInt(255);
                intent.putExtra(getString(R.string.RedMessage),red);

                int green = random.nextInt(255);
                intent.putExtra(getString(R.string.GreenMessage),green);

                int blue = random.nextInt(255);
                intent.putExtra(getString(R.string.BlueMessage),blue);

                broadcastManager.sendBroadcast(intent);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
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
