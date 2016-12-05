package com.justinwells.jobschedulerlab;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.graphics.Color;
import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;
import android.os.AsyncTask;
import android.widget.Toast;

import java.util.Random;

/**
 * Created by justinwells on 11/29/16.
 */

public class ColorService extends JobService {
    private AsyncTask mTask;

    @Override
    public boolean onStartJob(JobParameters jobParameters) {
        mTask = new AsyncTask<Void,Void,ColorWord>(){
            @Override
            protected ColorWord doInBackground(Void... voids) {
                Random r = new Random();
                int rand = (r.nextInt(5));


                switch (rand) {
                    case 0: return new ColorWord(Color.BLUE, "blue");

                    case 1: return new ColorWord(Color.RED, "red");

                    case 2: return new ColorWord(Color.GREEN, "green");

                    case 3: return new ColorWord(Color.YELLOW, "yellow");

                    case 4: return new ColorWord(Color.GRAY, "gray");

                    case 5: return new ColorWord(Color.MAGENTA, "magenta");


                }

                return new ColorWord(Color.BLACK, "adsfasdfas ");
            }

            @Override
            protected void onPostExecute(ColorWord color) {
                super.onPostExecute(color);
                ViewManager manager = ViewManager.getInstance();

                if (manager.getNewColorText() != null) {
                    manager.setOldColor(new ColorWord(manager.getNewColor(), manager.getNewColorText()));
                    manager.setNewColor(color);
                } else {
                    manager.setNewColor(color);
                }

            }
        }.execute();

        MainActivity.updateColorViews();
        return false;
    }

    @Override
    public boolean onStopJob(JobParameters jobParameters) {
        return false;
    }
}
