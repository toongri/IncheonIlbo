package com.incheonilbo.lolanalysis.service;

import com.incheonilbo.lolanalysis.dto.*;
import com.incheonilbo.lolanalysis.repository.query.ChampCounterQueryRepository;
import com.incheonilbo.lolanalysis.repository.query.ChampSkillQueryRepository;
import com.incheonilbo.lolanalysis.repository.query.ChampTableRepository;
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
    private final ChampCounterQueryRepository champCounterQueryRepository;
    private final ChampTableRepository champTableRepository;

    final List<String> laneList = Arrays.asList("TOP", "JUNGLE", "MIDDLE", "BOTTOM", "UTILITY");
    final List<String> skillChoiceArray = Arrays.asList("skill5", "skill10", "skill15");
    final Integer COUNT5_GAME_LIMIT = 100;
    final Integer COUNT5_CHAMP_LIMIT = 5;

    public Map<Integer, ForChampsTableAboutAllAndWinRate> getChampsTable(String laneCond) {
        List<ForChampsTable> champsByLane = champTableRepository.findChampsByLane(laneCond);
        List<ForChampsTable> champsWinByLane = champTableRepository.findChampsWinByLane(laneCond);

        Map<Integer, Long> map = champsByLane.stream().collect(Collectors.toMap(ForChampsTable::getChampionId, ForChampsTable::getAmountOfGame));
        Map<Integer, ForChampsTableAboutAllAndWinRate> result = new HashMap<>();
        for (ForChampsTable champ : champsWinByLane) {
            result.put(champ.getChampionId(), new ForChampsTableAboutAllAndWinRate(map.get(champ.getChampionId()), (double) champ.getAmountOfGame() / map.get(champ.getChampionId())));
        }
        return result;

    }

    public Map<Integer, Double> find5ChampsAboutCountersOfMainChamp(Integer championIdCond, String laneCond) {
        List<ForCounter5Champ> champsAll = champCounterQueryRepository.get5ChampsAboutCountersOfMainChamp(championIdCond, laneCond);
        List<ForCounter5Champ> champsWin = champCounterQueryRepository.get5ChampsAboutWinCountersOfMainChamp(championIdCond, laneCond);

        Map<Integer, Long> map = champsAll.stream().filter(champ -> champ.getAmountOfGame() > COUNT5_GAME_LIMIT).collect(Collectors.toMap(ForCounter5Champ::getChampionId, ForCounter5Champ::getAmountOfGame));
        Map<Integer, Double> rateMap = new HashMap<>();

        for (ForCounter5Champ champ : champsWin) {
            Integer championId = champ.getChampionId();
            if (map.containsKey(championId)) {
                rateMap.put(championId, (double)champ.getAmountOfGame()/map.get(championId));
            }
        }

        List<Map.Entry<Integer, Double>> entries = new ArrayList<>(rateMap.entrySet());
        entries.sort((v1, v2) -> v1.getValue().compareTo(v2.getValue()));
        Map<Integer, Double> result = new HashMap<>();

        for (int i = 0; i < COUNT5_CHAMP_LIMIT; i++) {
            result.put(entries.get(i).getKey(), entries.get(i).getValue());
        }
        return result;
    }

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
