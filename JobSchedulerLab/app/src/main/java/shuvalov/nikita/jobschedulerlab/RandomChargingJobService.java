package shuvalov.nikita.jobschedulerlab;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v4.content.LocalBroadcastManager;

import java.util.Random;

/**
 * Created by NikitaShuvalov on 11/29/16.
 */

public class RandomChargingJobService extends JobService {
    private AsyncTask mTask;


    @Override
    public boolean onStartJob(final JobParameters jobParameters) {
        /**
         * This changes the color value of the background of the textView.
         */
        final LocalBroadcastManager localBroadcastManager = LocalBroadcastManager.getInstance(this);
        mTask = new AsyncTask<Void, Void, Integer>(){
            @Override
            protected Integer doInBackground(Void... voids) {
                Random rng = new Random();
                int colorInt = Color.argb(225,
                        rng.nextInt(255),
                        rng.nextInt(255),
                        rng.nextInt(255));
                return colorInt;
            }

            @Override
            protected void onPostExecute(Integer integer) {
                super.onPostExecute(integer);
                Intent intent = new Intent(MainActivity.INTENT_FILTER_VALUE);
                intent.putExtra(MainActivity.COLOR_VALUE,integer);
                localBroadcastManager.sendBroadcast(intent);
                intent.putExtra(MainActivity.BROADCAST_TYPE, MainActivity.RANDOM_JOB_ID);

                //update background color of a view
                jobFinished(jobParameters, true);
            }
        }.execute();
        return true;
    }

    @Override
    public boolean onStopJob(JobParameters jobParameters) {
        if (mTask!= null && mTask.getStatus().equals(AsyncTask.Status.RUNNING)){
            mTask.cancel(true);
        }
        return false;
    }
}
