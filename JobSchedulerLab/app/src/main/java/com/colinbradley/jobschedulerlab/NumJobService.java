package com.colinbradley.jobschedulerlab;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.content.LocalBroadcastManager;
import android.widget.TextView;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/**
 * Created by colinbradley on 11/29/16.
 */

public class NumJobService extends JobService {
    private AsyncTask mTask;
    LocalBroadcastManager broadcastManager;

    @Override
    public boolean onStartJob(final JobParameters jobParameters) {

        broadcastManager = LocalBroadcastManager.getInstance(this);
        mTask = new AsyncTask<Void,Void,String>(){

            @Override
            protected String doInBackground(Void... voids) {
                Random random = new Random();
                int rngNum = random.nextInt(500);
                return String.valueOf(rngNum);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                Intent intent = new Intent(getString(R.string.NumBroadcast));

                intent.putExtra(getString(R.string.NumMessage),s);

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
