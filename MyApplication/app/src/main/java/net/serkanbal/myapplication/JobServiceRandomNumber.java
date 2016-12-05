package net.serkanbal.myapplication;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

/**
 * Created by Serkan on 29/11/16.
 */

public class JobServiceRandomNumber extends JobService {
    public static final String TAG = "Serkan";
    static final public String RESULT1 = "com.controlj.copame.backend.COPAService.REQUEST_PROCESSED";
    static final public String MESSAGE1 = "com.controlj.copame.backend.COPAService.COPA_MSG";
    private AsyncTask mTask;
    @Override
    public boolean onStartJob(final JobParameters jobParameters) {

        mTask = new AsyncTask<Void, Void, Integer>(){
            @Override
            protected Integer doInBackground(Void... voids) {
                Integer random = (int)(Math.random()*100);
                return random;
            }

            @Override
            protected void onPostExecute(Integer integer) {
                super.onPostExecute(integer);

                Log.d(TAG, "onPostExecute: " + integer);

                LocalBroadcastManager broadcaster = LocalBroadcastManager.getInstance(getApplicationContext());
                Intent intent = new Intent(RESULT1);
                intent.putExtra(MESSAGE1, integer);
                broadcaster.sendBroadcast(intent);
                jobFinished(jobParameters, false);

            }
        }.execute();

        return false;
    }

    @Override
    public boolean onStopJob(JobParameters jobParameters) {
        return false;
    }
}
