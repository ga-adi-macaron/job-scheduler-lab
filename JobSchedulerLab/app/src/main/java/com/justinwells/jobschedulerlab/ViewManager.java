package com.justinwells.jobschedulerlab;

/**
 * Created by justinwells on 11/29/16.
 */
public class ViewManager {
    private static ViewManager sInstance;
    private String mOldTime, mNewTime, mOldText, mNewText;
    private int mOldColor, mNewColor;

    public static ViewManager getInstance() {
        if (sInstance == null) {
            sInstance = new ViewManager();
        }
        return sInstance;
    }

    public String getNewTime() {
        return mNewTime;
    }

    public void setNewTime(String mNewTime) {
        this.mNewTime = mNewTime;
    }

    public String getOldTime() {
        return mOldTime;
    }

    public void setOldTime(String mOldTime) {
        this.mOldTime = mOldTime;
    }

    private ViewManager() {
        mOldTime = " ";
        mNewTime = " ";

    }

    public void setNewColor(ColorWord color) {
        mNewColor = color.getmColor();
        mNewText = color.getmText();
    }

    public void setOldColor(ColorWord color) {
        mOldColor = color.getmColor();
        mOldText = color.getmText();
    }

    public String getNewColorText() {
        return mNewText;
    }

    public String getOldColorText() {
        return mOldText;
    }

    public int getOldColor () {
        return mOldColor;
    }

    public int getNewColor () {
        return mNewColor;
    }


}
