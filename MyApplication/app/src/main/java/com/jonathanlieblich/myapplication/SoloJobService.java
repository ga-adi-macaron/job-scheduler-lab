package com.jonathanlieblich.myapplication;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.os.AsyncTask;

/**
 * Created by jonlieblich on 11/29/16.
 */

public class SoloJobService extends JobService {
    AsyncTask<Integer, Void, Integer> mTask;

    @Override
    public boolean onStartJob(final JobParameters jobParameters) {

        mTask = new AsyncTask<Integer, Void, Integer>() {
            @Override
            protected Integer doInBackground(Integer... integers) {
                return integers[0]++;
            }

            @Override
            protected void onPostExecute(Integer integer) {
                super.onPostExecute(integer);

                jobFinished(jobParameters, false);
            }
        }.execute(jobParameters.getExtras().getInt("count"));

        return false;
    }

    @Override
    public boolean onStopJob(JobParameters jobParameters) {
        if(mTask != null && mTask.getStatus().equals(AsyncTask.Status.RUNNING)) {
            mTask.cancel(false);
        }

        return false;
    }

    public interface CountIncreasedListener {
        void onCountIncreaseListener(int count);
    }
}
