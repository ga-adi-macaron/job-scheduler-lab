package com.justinwells.jobschedulerlab;

import android.app.Service;
import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.Intent;

/**
 * Created by justinwells on 11/29/16.
 */

public class ConnectedService extends JobService {

    @Override
    public boolean onStartJob(JobParameters jobParameters) {
        MainActivity.setChargingIcon(true);
        return false;
    }

    @Override
    public boolean onStopJob(JobParameters jobParameters) {
        MainActivity.setChargingIcon(false);
        return false;
    }


}
