package com.jonathanlieblich.myapplication;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.os.AsyncTask;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

/**
 * Created by jonlieblich on 11/29/16.
 */

public class DualJobService extends JobService {
    AsyncTask<Void, Void, String> mTask;

    @Override
    public boolean onStartJob(final JobParameters jobParameters) {
        mTask = new AsyncTask<Void, Void, String>() {
            @Override
            protected String doInBackground(Void... voids) {
                Calendar cal = Calendar.getInstance();
                SimpleDateFormat fmt = new SimpleDateFormat("hh:mm:ss", Locale.US);
                return fmt.format(cal.getTime());
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);

                jobFinished(jobParameters, false);
            }
        }.execute();

        return false;
    }

    @Override
    public boolean onStopJob(JobParameters jobParameters) {
        if(mTask != null && mTask.getStatus().equals(AsyncTask.Status.RUNNING)); {
            mTask.cancel(false);
        }

        return false;
    }
    public interface TimeChangeListener {
        void onChangeListener(String time);
    }
}
