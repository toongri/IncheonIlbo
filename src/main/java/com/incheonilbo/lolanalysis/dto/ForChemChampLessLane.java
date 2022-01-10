package com.incheonilbo.lolanalysis.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ForChemChampLessLane {
    private Integer championId;
    private Long amountOfGame;
    private Long amountOfGameAboutWin;
}
