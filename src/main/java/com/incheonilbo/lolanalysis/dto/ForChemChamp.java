package com.incheonilbo.lolanalysis.dto;

import lombok.Data;

@Data
public class ForChemChamp {
    private Integer championId;
    private String lane;
    private Long amountOfGame;
}
