package com.targetcoders.sowing.seedtype.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class SeedTypeRenameDTO {
    private Long seedTypeId;
    private String newSeedTypeName;
}
