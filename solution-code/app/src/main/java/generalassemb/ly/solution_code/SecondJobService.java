package generalassemb.ly.solution_code;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.util.Log;

import java.util.Random;

/**
 * Created by alanjcaceres on 8/22/16.
 */
public class SecondJobService extends JobService {
    private static final String TAG = "SecondJobService";

    @Override
    public boolean onStartJob(JobParameters jobParameters) {
        Log.d(TAG, "onStartJob: ");
        Random random = new Random();
        DataSingleton.getInstance().updateSecondString(String.valueOf(random.nextInt(100)));
        jobFinished(jobParameters,true);
        return true;
    }

    @Override
    public boolean onStopJob(JobParameters jobParameters) {
        return false;
    }
}
