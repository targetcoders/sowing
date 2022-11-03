package com.targetcoders.sowing.seed.domain;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
public class SeedForm {

    private Long id;
    private String username;
    private String title;
    private List<SeedType> typeList;
    private SeedType selectType;
    private String content;
    private String sowingDate;

}
