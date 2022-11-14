package com.targetcoders.sowing.seed;

import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class DefaultLocalDate implements ILocalDate {
    @Override
    public LocalDate now() {
        return LocalDate.now();
    }
}
