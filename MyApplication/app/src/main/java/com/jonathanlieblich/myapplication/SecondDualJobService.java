package com.jonathanlieblich.myapplication;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.os.AsyncTask;

/**
 * Created by jonlieblich on 11/29/16.
 */

public class SecondDualJobService extends JobService {
    AsyncTask<String, Void, String> mTask;
    PlugChangeListener mListener;

    @Override
    public boolean onStartJob(final JobParameters jobParameters) {
        mListener = ServiceListenerHelper.getInstance().getPlug();

        mTask = new AsyncTask<String, Void, String>() {
            @Override
            protected String doInBackground(String... strings) {
                if(strings[0].equals("Unplugged")) {
                    return "Plugged in";
                } else {
                    return "Unplugged";
                }
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                mListener.updateStatusListener(s);
                jobFinished(jobParameters, false);
            }
        }.execute(jobParameters.getExtras().getString("power"));

        return false;
    }

    @Override
    public boolean onStopJob(JobParameters jobParameters) {
        if(mTask != null && mTask.getStatus().equals(AsyncTask.Status.RUNNING)) {
            mTask.cancel(false);
        }

        return false;
    }

    public interface PlugChangeListener {
        String updateStatusListener(String status);
    }
}
