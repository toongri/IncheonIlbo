package com.incheonilbo.lolanalysis.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ForChemChamp {
    private Integer championId;
    private String lane;
    private Long amountOfGame;
}
