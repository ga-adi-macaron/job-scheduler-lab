package com.ezequielc.jobschedulerlab;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.os.AsyncTask;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by student on 11/29/16.
 */

public class JobServiceOne extends JobService
        implements MainActivity.JobServiceCommunicationListener {
    private static final String TAG = "JobServiceOne";
    private AsyncTask mTask;
    private MainActivity.JobServiceCommunicationListener mListener;

    @Override
    public boolean onStartJob(final JobParameters jobParameters) {
        Log.d(TAG, "onStartJob: " + jobParameters.getExtras().getString("type"));

        mTask = new AsyncTask<Void, Void, String>(){
            @Override
            protected String doInBackground(Void... voids) {
                Calendar cal = Calendar.getInstance();
                cal.setTimeInMillis(System.currentTimeMillis());

                SimpleDateFormat format = new SimpleDateFormat("hh:mm:ss");
                return "Current time: " + format.format(cal.getTime());
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                Log.d(TAG, "onPostExecute: " + s);
                jobFinished(jobParameters, false);
            }
        }.execute();
        return false;
    }

    @Override
    public boolean onStopJob(JobParameters jobParameters) {
        Log.d(TAG, "onStopJob: ");
        if (mTask != null && mTask.getStatus().equals(AsyncTask.Status.RUNNING)) {
            mTask.cancel(false);
        }
        return false;
    }

    @Override
    public void communicationMethod() {

    }
}
