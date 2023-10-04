package com.android.print.demo;

public class MyProperties {
    private static MyProperties mInstance= null;

    public int regularCount = 1;
    public int priorityCount = 1;

    protected MyProperties(){}

    public static synchronized MyProperties getInstance() {
        if(null == mInstance){
            mInstance = new MyProperties();
        }
        return mInstance;
    }
}