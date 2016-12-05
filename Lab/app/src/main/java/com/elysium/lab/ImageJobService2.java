package com.elysium.lab;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.os.AsyncTask;
import android.os.PersistableBundle;
import android.util.Log;

/**
 * Created by jay on 11/30/16.
 */

public class ImageJobService2 extends JobService {

    private AsyncTask<String, Void, String> task;
    private static int count;


    @Override
    public boolean onStartJob(final JobParameters jobParameters) {

        PersistableBundle bundle = jobParameters.getExtras();
        final String raceType = bundle.getString("type");
        Log.i("TESTING", "onStartJob: " + raceType + count);


        task = new AsyncTask<String, Void, String>() {

            @Override
            protected String doInBackground(String... string) {

                return string[0];
            }

            @Override
            protected void onPostExecute(String string) {
                super.onPostExecute(string);

                ImageRacer.getInstance().updateMainThread(string);
                Log.i("TESTING", "onPostExecute: " + string + count);
                count++;
                jobFinished(jobParameters, false);

            }
        };

        task.execute(raceType);

        return true;
    }

    @Override
    public boolean onStopJob(JobParameters jobParameters) {

        if (task.getStatus().equals(AsyncTask.Status.RUNNING)) {

            task.cancel(false);
        }

        return false;
    }
}
