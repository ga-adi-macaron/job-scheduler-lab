package net.serkanbal.myapplication;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.content.LocalBroadcastManager;

import java.util.Random;

/**
 * Created by Serkan on 29/11/16.
 */

public class JobServiceRandomToken extends JobService {
    public static final String TAG = "Serkan";
    static final public String RESULT3 = "com.controlj.copame.backend.COPAService.REQUEST_PROCESSE3";
    static final public String MESSAGE3 = "com.controlj.copame.backend.COPAService.COPA_MSG3";
    private static final Random random = new Random();
    private static final String CHARS = "abcdefghijkmnopqrstuvwxyzABCDEFGHJKLMNOPQRSTUVWXYZ234567890!@#$";
    private AsyncTask mTask;

    @Override
    public boolean onStartJob(final JobParameters jobParameters) {

        mTask = new AsyncTask<Void, Void, String>(){
            @Override
            protected String doInBackground(Void... voids) {
                String token = getToken(5);
                return token;
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);

                LocalBroadcastManager broadcaster = LocalBroadcastManager.getInstance(getApplicationContext());
                Intent intent = new Intent(RESULT3);
                intent.putExtra(MESSAGE3, s);
                broadcaster.sendBroadcast(intent);
                jobFinished(jobParameters, true);
            }

        }.execute();

        return true;
    }

    @Override
    public boolean onStopJob(JobParameters jobParameters) {
        return false;
    }

    public static String getToken(int length) {
        StringBuilder token = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            token.append(CHARS.charAt(random.nextInt(CHARS.length())));
        }
        return token.toString();
    }
}
