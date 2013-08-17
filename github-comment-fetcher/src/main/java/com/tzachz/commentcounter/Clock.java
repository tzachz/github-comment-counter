package com.tzachz.commentcounter;

import org.joda.time.LocalDate;

public class Clock {
    public Clock() {
    }

    LocalDate getLocalDateNow() {
        return LocalDate.now();
    }
}