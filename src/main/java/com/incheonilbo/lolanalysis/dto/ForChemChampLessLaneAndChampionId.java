package com.incheonilbo.lolanalysis.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ForChemChampLessLaneAndChampionId {
    private Integer championId;
    private Long amountOfGame;
    private Long amountOfGameAboutWin;
}
