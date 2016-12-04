package com.jonathanlieblich.myapplication;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.os.AsyncTask;

/**
 * Created by jonlieblich on 11/29/16.
 */

public class SoloJobService extends JobService {
    AsyncTask<Integer, Void, Integer> mTask;
    CountIncreasedListener mListener;

    @Override
    public boolean onStartJob(final JobParameters jobParameters) {
        mListener = ServiceListenerHelper.getInstance().getCount();
        ServiceListenerHelper.getInstance().setCountListener(mListener);

        mTask = new AsyncTask<Integer, Void, Integer>() {
            @Override
            protected Integer doInBackground(Integer... integers) {
                return integers[0]++;
            }

            @Override
            protected void onPostExecute(Integer integer) {
                super.onPostExecute(integer);
                mListener.onCountIncreaseListener(integer);
                jobFinished(jobParameters, true);
            }
        }.execute(jobParameters.getExtras().getInt("count"));

        return true;
    }

    @Override
    public boolean onStopJob(JobParameters jobParameters) {
        if(mTask != null && mTask.getStatus().equals(AsyncTask.Status.RUNNING)) {
            mTask.cancel(true);
        }

        return true;
    }

    public interface CountIncreasedListener {
        void onCountIncreaseListener(int count);
    }
}
