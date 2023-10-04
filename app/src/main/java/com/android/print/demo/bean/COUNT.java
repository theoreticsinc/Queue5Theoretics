package com.android.print.demo.bean;

public class COUNT {

    private static COUNT instance;

    private int regularCount = 0;
    private int priorityCount = 0;

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

    public static synchronized COUNT getInstance() {
        if(instance == null) {
            instance = new COUNT();
        }
        return instance;
    }

}
