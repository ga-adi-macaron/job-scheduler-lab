package com.jonathanlieblich.myapplication;

/**
 * Created by jonlieblich on 11/29/16.
 */
public class SelectionHelper {
    private static SelectionHelper ourInstance = new SelectionHelper();

    public static SelectionHelper getInstance() {
        return ourInstance;
    }

    private SelectionHelper() {
    }
}
