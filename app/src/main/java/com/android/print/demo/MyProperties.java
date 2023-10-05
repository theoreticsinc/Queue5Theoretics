package com.android.print.demo;

import java.util.Date;

public class MyProperties {
    private static MyProperties mInstance= null;

    public int regularCount = 0;
    public int priorityCount = 0;
    private Date runningDate;

    protected MyProperties(){}

    public void setRunningDate(Date runningDate) {
        this.runningDate = runningDate;
    }

    public Date getRunningDate() {
        return runningDate;
    }

    public static synchronized MyProperties getInstance() {
        if(null == mInstance){
            mInstance = new MyProperties();
        }
        return mInstance;
    }
}