package generalassemb.ly.solution_code;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by alanjcaceres on 8/22/16.
 */
public class FirstJobService extends JobService {
    private static final String TAG = "FirstJobService";
    @Override
    public boolean onStartJob(JobParameters jobParameters) {
        Log.d(TAG, "onStartJob: ");
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(System.currentTimeMillis());

        SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm:ss");
        DataSingleton.getInstance().updateFirstString(timeFormat.format(cal.getTime()));
        jobFinished(jobParameters,true);
        return true;
    }

    @Override
    public boolean onStopJob(JobParameters jobParameters) {
        return false;
    }
}
