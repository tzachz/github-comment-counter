package com.tzachz.commentcounter.server;

/**
 * Created with IntelliJ IDEA.
 * User: tzachz
 * Date: 26/09/13
 * Time: 22:03
 */
public enum Period {

    today(0), week(6), month(30);

    private final int daysBack;

    Period(int daysBack) {
        this.daysBack = daysBack;
    }

    public int getDaysBack() {
        return daysBack;
    }

    public static Period getLongest() {
        return month;
    }
}
