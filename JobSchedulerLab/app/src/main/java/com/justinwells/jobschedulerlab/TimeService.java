package com.justinwells.jobschedulerlab;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;
import android.os.AsyncTask;
import android.util.Log;

import static android.content.ContentValues.TAG;

/**
 * Created by justinwells on 11/29/16.
 *
 */

public class TimeService extends JobService{
    private AsyncTask mTask;

    @Override
    public boolean onStartJob(JobParameters jobParameters) {
        mTask = new AsyncTask<Void,Void,String>(){
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
                ViewManager manager = ViewManager.getInstance();

                if (manager.getNewTime() != " ") {
                    manager.setOldTime(manager.getNewTime());
                    manager.setNewTime(s);
                } else {
                    manager.setNewTime(s);
                }

            }
        }.execute();

        MainActivity.updateTimeViews();
        return false;
    }

    @Override
    public boolean onStopJob(JobParameters jobParameters) {
        return false;
    }
}
