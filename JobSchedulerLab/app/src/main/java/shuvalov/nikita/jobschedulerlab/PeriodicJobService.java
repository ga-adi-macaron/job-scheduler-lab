package shuvalov.nikita.jobschedulerlab;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.PersistableBundle;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import java.util.Random;

/**
 * Created by NikitaShuvalov on 11/29/16.
 */

public class PeriodicJobService extends JobService{
    /**
     * This changes the number value being shown in the textViews.
     */

    private LocalBroadcastManager mLocalBroadcastManager;
    private AsyncTask mASyncTask;

    @Override
    public boolean onStartJob(final JobParameters jobParameters) {
        mLocalBroadcastManager = LocalBroadcastManager.getInstance(this);
        final int i = jobParameters.getExtras().getInt(MainActivity.PREVIOUS_COUNT);
        mASyncTask = new AsyncTask<Void, Void, String>() {
            @Override
            protected String doInBackground(Void... voids) {
                Random rng = new Random();

                return String.valueOf(rng.nextInt(100000));
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                Log.d("Periodic", "onPostExecute: "+i);
                Intent intent = new Intent(MainActivity.INTENT_FILTER_VALUE);
                intent.putExtra(MainActivity.CURRENT_COUNT,s);

                intent.putExtra(MainActivity.PREVIOUS_COUNT,String.valueOf(i));
                intent.putExtra(MainActivity.BROADCAST_TYPE, MainActivity.PERIODIC_JOB_ID);
                mLocalBroadcastManager.sendBroadcast(intent);
                jobFinished(jobParameters,true);
            }
        }.execute();

        return true;
    }

    @Override
    public boolean onStopJob(JobParameters jobParameters) {
        if (mASyncTask!= null && mASyncTask.getStatus().equals(AsyncTask.Status.RUNNING)){
            mASyncTask.cancel(true);
        }
        return false;
    }
}
