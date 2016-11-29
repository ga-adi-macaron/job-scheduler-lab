package com.scottlindley.jobschedulerlab;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.content.LocalBroadcastManager;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by Scott Lindley on 11/29/2016.
 */

public class MyJobService extends JobService{
    private AsyncTask mTask;
    private static String mMessage = "";

    @Override
    public boolean onStartJob(final JobParameters jobParameters){
        final String text = jobParameters.getExtras().getString("network check");

        mTask = new AsyncTask<Void, Void, String>() {
            @Override
            protected String doInBackground(Void... voids) {
                Calendar cal = Calendar.getInstance();
                cal.setTimeInMillis(System.currentTimeMillis());

                SimpleDateFormat format = new SimpleDateFormat("hh:mm:ss");
                return format.format(cal.getTime());
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                mMessage = text+ "\n" + s;
                Intent intent = new Intent("receive-info");
                intent.putExtra("name", "message");
                intent.putExtra("message", mMessage);
                LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(intent);
                jobFinished(jobParameters, false);
            }
        }.execute();
        return false;
    }

    @Override
    public boolean onStopJob(JobParameters jobParameters) {
        jobFinished(jobParameters, true);
        return true;
    }

}
