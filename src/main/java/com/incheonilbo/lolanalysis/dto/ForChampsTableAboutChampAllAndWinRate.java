package com.incheonilbo.lolanalysis.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ForChampsTableAboutChampAllAndWinRate {
    private Integer championId;
    private Long amountOfGame;
    private Double winRate;
}
