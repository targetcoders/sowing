package com.targetcoders.sowing.seed;

import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class DefaultLocalDateTime implements ILocalDateTime {
    @Override
    public LocalDateTime now() {
        return LocalDateTime.now();
    }
}
