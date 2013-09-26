package com.tzachz.commentcounter;

import org.joda.time.LocalDate;

public class Clock {
    public Clock() {
    }

    public LocalDate getLocalDateNow() {
        return LocalDate.now();
    }
}