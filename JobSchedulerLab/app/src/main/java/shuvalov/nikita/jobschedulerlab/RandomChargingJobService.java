package shuvalov.nikita.jobschedulerlab;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.graphics.Color;
import android.os.AsyncTask;

import java.util.Random;

/**
 * Created by NikitaShuvalov on 11/29/16.
 */

public class RandomChargingJobService extends JobService {
    private AsyncTask mTask;
    //ToDo:Keep track of previous value as well.

    @Override
    public boolean onStartJob(final JobParameters jobParameters) {
        mTask = new AsyncTask<Void, Void, Integer>(){

            @Override
            protected Integer doInBackground(Void... voids) {
                Random rng = new Random();
                int colorInt = Color.argb(100,
                        rng.nextInt(255),
                        rng.nextInt(255),
                        rng.nextInt(255));
                return colorInt;
            }

            @Override
            protected void onPostExecute(Integer integer) {
                super.onPostExecute(integer);
                //ToDo: update background color of a view
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
