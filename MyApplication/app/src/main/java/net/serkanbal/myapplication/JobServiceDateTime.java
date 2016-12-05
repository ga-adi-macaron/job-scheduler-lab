package net.serkanbal.myapplication;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.content.LocalBroadcastManager;

/**
 * Created by Serkan on 29/11/16.
 */

public class JobServiceDateTime extends JobService{
    public static final String TAG = "Serkan";
    static final public String RESULT2 = "com.controlj.copame.backend.COPAService.REQUEST_PROCESSED2";
    static final public String MESSAGE2 = "com.controlj.copame.backend.COPAService.COPA_MSG2";
    private AsyncTask mTask;

    @Override
    public boolean onStartJob(final JobParameters jobParameters) {
        mTask = new AsyncTask<Void, Void, String>(){
            @Override
            protected String doInBackground(Void... voids) {
                java.util.Calendar cal = java.util.Calendar.getInstance();
                cal.setTimeInMillis(System.currentTimeMillis());

                java.text.SimpleDateFormat format = new java.text.SimpleDateFormat("hh:mm:ss");
                return format.format(cal.getTime());
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);

                LocalBroadcastManager broadcaster = LocalBroadcastManager.getInstance(getApplicationContext());
                Intent intent = new Intent(RESULT2);
                intent.putExtra(MESSAGE2, s);
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
