package com.targetcoders.sowing.authentication.service;

import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class DefaultDate implements IDate {

    @Override
    public Date instance() {
        return new Date();
    }

    @Override
    public long nowTime() {
        return new Date().getTime();
    }

}
