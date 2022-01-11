package com.incheonilbo.lolanalysis.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ForSkillAllCountAndWinCount {
    private Long amountOfGame;
    private Long winAmountOfGame;
}
