package com.android.print.demo.bean;

public class CONSTANTS {

    private static CONSTANTS instance;

    private String SERVERADDR = "192.168.254.60";

    public CONSTANTS(){}

    public String getSERVERADDR() {
        return SERVERADDR;
    }

    public static synchronized CONSTANTS getInstance() {
        if(instance == null) {
            instance = new CONSTANTS();
        }
        return instance;
    }

}
