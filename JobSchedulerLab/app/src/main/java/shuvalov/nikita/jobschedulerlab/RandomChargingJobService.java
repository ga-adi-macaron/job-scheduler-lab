package shuvalov.nikita.jobschedulerlab;

import android.app.job.JobParameters;
import android.app.job.JobService;

/**
 * Created by NikitaShuvalov on 11/29/16.
 */

public class RandomChargingJobService extends JobService {
    @Override
    public boolean onStartJob(JobParameters jobParameters) {
        return false;
    }

    @Override
    public boolean onStopJob(JobParameters jobParameters) {
        return false;
    }
}
