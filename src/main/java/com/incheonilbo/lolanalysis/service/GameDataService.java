package com.incheonilbo.lolanalysis.service;

import com.incheonilbo.lolanalysis.dto.ForChemChamp;
import com.incheonilbo.lolanalysis.dto.ForChemChampLessLaneAndChampionId;
import com.incheonilbo.lolanalysis.dto.ForSkillAllCountAndWinCount;
import com.incheonilbo.lolanalysis.dto.ForSkillFormat;
import com.incheonilbo.lolanalysis.repository.query.ChampSkillQueryRepository;
import com.incheonilbo.lolanalysis.repository.query.ChemChampsQueryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class GameDataService {

    private final ChemChampsQueryRepository gameDataQueryRepository;
    private final ChampSkillQueryRepository champSkillQueryRepository;

    final List<String> laneList = Arrays.asList("TOP", "JUNGLE", "MIDDLE", "BOTTOM", "UTILITY");
    final List<String> skillChoiceArray = Arrays.asList("skill5", "skill10", "skill15");

    public Map<String, ForSkillAllCountAndWinCount> findOneAboutSkillByChampId(Integer championIdCond, String laneCond, String skillLengthCond) {

        List<ForSkillFormat> amountOfGameAboutSkillData;
        List<ForSkillFormat> amountOfGameAboutWinSkillData;

        if (Objects.equals(skillLengthCond, skillChoiceArray.get(0))) {
            amountOfGameAboutSkillData = champSkillQueryRepository.getAmountOfGameAboutSkillDataIndex0(championIdCond, laneCond);
            amountOfGameAboutWinSkillData = champSkillQueryRepository.getAmountOfGameAboutWinSkillDataIndex0(championIdCond, laneCond);
        } else if (Objects.equals(skillLengthCond, skillChoiceArray.get(1))) {
            amountOfGameAboutSkillData = champSkillQueryRepository.getAmountOfGameAboutSkillDataIndex1(championIdCond, laneCond);
            amountOfGameAboutWinSkillData = champSkillQueryRepository.getAmountOfGameAboutWinSkillDataIndex1(championIdCond, laneCond);
        } else {
            amountOfGameAboutSkillData = champSkillQueryRepository.getAmountOfGameAboutSkillDataIndex2(championIdCond, laneCond);
            amountOfGameAboutWinSkillData = champSkillQueryRepository.getAmountOfGameAboutWinSkillDataIndex2(championIdCond, laneCond);
        }

        Map<String, ForSkillAllCountAndWinCount> dataListToMap = amountOfGameAboutSkillData.stream().collect(Collectors.toMap(ForSkillFormat::getSkillCombination, data -> new ForSkillAllCountAndWinCount(data.getAmountOfGame(), 0L)));
        for (ForSkillFormat data : amountOfGameAboutWinSkillData) {
            dataListToMap.get(data.getSkillCombination()).setWinAmountOfGame(data.getAmountOfGame());
        }

        return dataListToMap;
    }

    public Map<String, Map<Integer, ForChemChampLessLaneAndChampionId>> findChemChampsByLaneAndChamp(Integer championId, String laneCond, List<Integer> championIdsCond, List<String> lanesCond) {
        List<ForChemChamp> champs = gameDataQueryRepository.getForChemChamps(championId, laneCond, championIdsCond, lanesCond);
        List<ForChemChamp> champsAboutWin = gameDataQueryRepository.getForChemChampsAboutWin(championId, laneCond, championIdsCond, lanesCond);

        Map<String, Map<Integer, ForChemChampLessLaneAndChampionId>> champsUsingMap = chageChampListToMap(champs);

        return setChampVictoryAmountInMap(champsAboutWin, champsUsingMap);
    }

    private Map<String, Map<Integer, ForChemChampLessLaneAndChampionId>> setChampVictoryAmountInMap(List<ForChemChamp> champsAboutWin, Map<String, Map<Integer, ForChemChampLessLaneAndChampionId>> champsUsingMap) {
        for (ForChemChamp champ : champsAboutWin) {
            champsUsingMap.get(champ.getLane()).get(champ.getChampionId()).setAmountOfGameAboutWin(champ.getAmountOfGame());
        }

        return champsUsingMap;
    }

    private Map<String, Map<Integer, ForChemChampLessLaneAndChampionId>> chageChampListToMap(List<ForChemChamp> champs) {

        Map<String, Map<Integer, ForChemChampLessLaneAndChampionId>> map = new HashMap<>();

        for (String s : laneList) {
            map.put(s, new HashMap<>());
        }

        for (ForChemChamp champ : champs) {
            map.get(champ.getLane()).put(champ.getChampionId(), new ForChemChampLessLaneAndChampionId(champ.getAmountOfGame(), 0L));
        }
        return map;
    }

}
