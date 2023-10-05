package com.android.print.demo.bean;

import java.util.Date;

public class COUNT {

    private static COUNT instance;

    private int regularCount = 0;
    private int priorityCount = 0;
    private Date runningDate;

    public COUNT(){}

    public void setPriorityCount(int priorityCount) {
        this.priorityCount = priorityCount;
    }

    public void setRegularCount(int regularCount) {
        this.regularCount = regularCount;
    }

    public int getPriorityCount() {
        return priorityCount;
    }

    public int getRegularCount() {
        return regularCount;
    }

    public Date getRunningDate() {
        return runningDate;
    }

    public void setRunningDate(Date runningDate) {
        this.runningDate = runningDate;
    }

    public static synchronized COUNT getInstance() {
        if(instance == null) {
            instance = new COUNT();
        }
        return instance;
    }

}
