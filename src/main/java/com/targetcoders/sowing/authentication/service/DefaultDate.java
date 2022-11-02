package com.targetcoders.sowing.authentication.service;

import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class DefaultDate implements IDate {

    private final Date date = new Date();

    @Override
    public Date instance() {
        return date;
    }

    @Override
    public long getTime() {
        return date.getTime();
    }

}
