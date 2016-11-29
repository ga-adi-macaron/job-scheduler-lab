package shuvalov.nikita.jobschedulerlab;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.os.AsyncTask;

/**
 * Created by NikitaShuvalov on 11/29/16.
 */

public class PeriodicJobService extends JobService {
    private AsyncTask mTask;


    @Override
    public boolean onStartJob(final JobParameters jobParameters) {
        mTask = new AsyncTask<Void, Void,Integer>(){
            @Override
            protected Integer doInBackground(Void... voids) {
                int i = jobParameters.getExtras().getInt("count");
                jobParameters.getExtras().putInt("count", i+1);
                //ToDo:Update views to show oldCount and newCount
                return i;
            }

            @Override
            protected void onPostExecute(Integer integer) {
                super.onPostExecute(integer);

                jobFinished(jobParameters,true);
            }
        }.execute();


        return true;
    }

    @Override
    public boolean onStopJob(JobParameters jobParameters) {
        if (mTask!=null && mTask.getStatus().equals(AsyncTask.Status.RUNNING)){
            mTask.cancel(true);
        }
        return false;
    }
}
