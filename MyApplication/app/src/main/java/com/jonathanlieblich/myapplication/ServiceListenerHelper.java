package com.jonathanlieblich.myapplication;

/**
 * Created by jonlieblich on 11/29/16.
 */

public class ServiceListenerHelper {
    private DualJobService.TimeChangeListener mTimeListener;
    private SecondDualJobService.PlugChangeListener mPlugListener;
    private SoloJobService.CountIncreasedListener mCountListener;

    private static ServiceListenerHelper sInstance = new ServiceListenerHelper();

    public static ServiceListenerHelper getInstance() {
        if(sInstance == null) {
            sInstance = new ServiceListenerHelper();
        }
        return sInstance;
    }

    private ServiceListenerHelper() {}

    public void setTimeListener(DualJobService.TimeChangeListener listener) {
        mTimeListener = listener;
    }

    public void setPlugListener(SecondDualJobService.PlugChangeListener listener) {
        mPlugListener = listener;
    }

    public void setCountListener(SoloJobService.CountIncreasedListener listener) {
        mCountListener = listener;
    }

    public DualJobService.TimeChangeListener getTime() {
        return mTimeListener;
    }

    public SecondDualJobService.PlugChangeListener getPlug() {
        return mPlugListener;
    }

    public SoloJobService.CountIncreasedListener getCount() {
        return mCountListener;
    }
}
