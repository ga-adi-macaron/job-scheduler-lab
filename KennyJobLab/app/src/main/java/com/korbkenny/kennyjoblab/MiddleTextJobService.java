package com.korbkenny.kennyjoblab;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.content.LocalBroadcastManager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by KorbBookProReturns on 11/29/16.
 */

public class MiddleTextJobService extends JobService {
    private AsyncTask mTask;
    LocalBroadcastManager bMan;

    @Override
    public boolean onStartJob(final JobParameters jobParameters) {
        bMan = LocalBroadcastManager.getInstance(this);
        mTask = new AsyncTask<Void,Void,Void>(){

            @Override
            protected Void doInBackground(Void... voids) {
                Intent intent = new Intent("middle_text");
                String hello = "HELLO";
                String newString = "";
                List<Character> chars = new ArrayList<Character>();
                for (char c:hello.toCharArray()) {
                    chars.add(c);
                }
                char p = chars.get((int) (Math.random()*5));
                chars.add(p);
                for (char d:chars) {
                    newString+=d;
                }
                intent.putExtra("hi",newString);
                bMan.sendBroadcast(intent);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                jobFinished(jobParameters,false);
            }
        }.execute();

        return false;
    }

    @Override
    public boolean onStopJob(JobParameters jobParameters) {
        return false;
    }
}
