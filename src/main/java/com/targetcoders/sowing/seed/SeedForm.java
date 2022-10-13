package com.targetcoders.sowing.seed;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class SeedForm {

    private String username;
    private String title;
    private SeedType selectType;
    private String content;

}
