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
    final Integer COUNT5_GAME_LIMIT = 20;
    final Integer COUNT5_CHAMP_LIMIT = 5;

    public List<ForChampsTableAboutChampAllAndWinRate> getChampsTable(String laneCond) {

        List<ForChampsTable> champsByLane = champTableRepository.findChampsByLane(laneCond);
        List<ForChampsTable> champsWinByLane = champTableRepository.findChampsWinByLane(laneCond);

        Map<Integer, Long> map = champsByLane.stream().collect(Collectors.toMap(ForChampsTable::getChampionId, ForChampsTable::getAmountOfGame));
        List<ForChampsTableAboutChampAllAndWinRate> result = new ArrayList<>();

        for (ForChampsTable champ : champsWinByLane) {
            result.add(new ForChampsTableAboutChampAllAndWinRate(champ.getChampionId(), map.get(champ.getChampionId()), (double) champ.getAmountOfGame() / map.get(champ.getChampionId())));
        }

        return result;
    }

    public List<ForChampIdAndRate> find5ChampsAboutCountersOfMainChamp(Integer championIdCond, String laneCond) {

        List<ForCounter5Champ> champsAll = champCounterQueryRepository.get5ChampsAboutCountersOfMainChamp(championIdCond, laneCond);
        List<ForCounter5Champ> champsWin = champCounterQueryRepository.get5ChampsAboutWinCountersOfMainChamp(championIdCond, laneCond);

        Map<Integer, Long> map = champsAll.stream().filter(champ -> champ.getAmountOfGame() > COUNT5_GAME_LIMIT).collect(Collectors.toMap(ForCounter5Champ::getChampionId, ForCounter5Champ::getAmountOfGame));
        List<ForChampIdAndRate> champIdAndRates = new ArrayList<>();

        for (ForCounter5Champ champ : champsWin) {
            Integer championId = champ.getChampionId();
            if (map.containsKey(championId)) {
                champIdAndRates.add(new ForChampIdAndRate(championId, (double)champ.getAmountOfGame()/map.get(championId)));
            }
        }

        champIdAndRates.sort(new ChampRateComparator());

        List<ForChampIdAndRate> result = champIdAndRates.stream().limit(5).collect(Collectors.toList());

        return result;
    }

    public static class ChampRateComparator implements Comparator<ForChampIdAndRate> {

        @Override
        public int compare(ForChampIdAndRate o1, ForChampIdAndRate o2) {
            if (o1.getRate() > o2.getRate()) {
                return 1;
            } else if (o1.getRate() < o2.getRate()) {
                return -1;
            }
            return 0;
        }
    }

    public List<ForSkillAllCountAndWinCount> findOneAboutSkillByChampId(Integer championIdCond, String laneCond, String skillLengthCond) {

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

        Map<String, Long> dataListToMap = amountOfGameAboutSkillData.stream().collect(Collectors.toMap(ForSkillFormat::getSkillCombination, ForSkillFormat::getAmountOfGame));
        List<ForSkillAllCountAndWinCount> result = new ArrayList<>();


        for (ForSkillFormat data : amountOfGameAboutWinSkillData) {
            String skillCombination = data.getSkillCombination();
            if (dataListToMap.containsKey(skillCombination)) {
                result.add(new ForSkillAllCountAndWinCount(data.getSkillCombination(), dataListToMap.get(data.getSkillCombination()), data.getAmountOfGame()));
            }
        }
        return result;
    }

    public Long findCountAboutSkillByChampId(Integer championIdCond, String laneCond, String skillLengthCond) {

        if (Objects.equals(skillLengthCond, skillChoiceArray.get(0))) {
            return champSkillQueryRepository.getCountAmountOfGameAboutSkillDataIndex0(championIdCond, laneCond);
        } else if (Objects.equals(skillLengthCond, skillChoiceArray.get(1))) {
            return champSkillQueryRepository.getCountAmountOfGameAboutSkillDataIndex1(championIdCond, laneCond);
        } else {
            return champSkillQueryRepository.getCountAmountOfGameAboutSkillDataIndex2(championIdCond, laneCond);
        }
    }

    public Map<String, Long> findChemCountChampsByLaneAndChamp(Map<String, List<ForChemChampLessLaneAndChampionId>> map) {

        Map<String, Long> result = new HashMap<>();
        for (String s : map.keySet()) {
            List<ForChemChampLessLaneAndChampionId> forChemChampLessLaneAndChampionIds = map.get(s);
            result.put(s, 0L);
            for (ForChemChampLessLaneAndChampionId champ : forChemChampLessLaneAndChampionIds) {
                result.put(s,result.get(s) + champ.getAmountOfGame());
            }

        }
        return result;
    }

    public Map<String, List<ForChemChampLessLaneAndChampionId>> findChemChampsByLaneAndChamp(Integer championId, String laneCond, List<Integer> championIdsCond, List<String> lanesCond) {
        List<ForChemChamp> champs = gameDataQueryRepository.getForChemChamps(championId, laneCond, championIdsCond, lanesCond);
        List<ForChemChamp> champsAboutWin = gameDataQueryRepository.getForChemChampsAboutWin(championId, laneCond, championIdsCond, lanesCond);

        Map<String, Map<Integer, Long>> champsUsingMap = changeChampListToMap(champs);
        return setChampVictoryAmountInMap(champsAboutWin, champsUsingMap);
    }

    private Map<String, List<ForChemChampLessLaneAndChampionId>> setChampVictoryAmountInMap(List<ForChemChamp> champsAboutWin, Map<String, Map<Integer, Long>> champsUsingMap) {

        Map<String, List<ForChemChampLessLaneAndChampionId>> result = new HashMap<>();
        for (String s : laneList) {
            result.put(s, new ArrayList<>());
        }

        for (ForChemChamp champ : champsAboutWin) {
            Integer championId = champ.getChampionId();
            if (champsUsingMap.get(champ.getLane()).containsKey(championId)) {
                result.get(champ.getLane()).add(new ForChemChampLessLaneAndChampionId(champ.getChampionId(), champsUsingMap.get(champ.getLane()).get(championId), champ.getAmountOfGame()));
            }
        }
        for (String s : laneList) {
            result.get(s).sort(new ChemPickRateComparator());
        }

        return result;
    }

    public static class ChemPickRateComparator implements Comparator<ForChemChampLessLaneAndChampionId> {

        @Override
        public int compare(ForChemChampLessLaneAndChampionId o1, ForChemChampLessLaneAndChampionId o2) {
            if (o1.getAmountOfGame() < o2.getAmountOfGame()) {
                return 1;
            } else if (o1.getAmountOfGame() > o2.getAmountOfGame()) {
                return -1;
            }
            return 0;
        }
    }

    private Map<String, Map<Integer, Long>> changeChampListToMap(List<ForChemChamp> champs) {

        Map<String, Map<Integer, Long>> map = new HashMap<>();

        for (String s : laneList) {
            map.put(s, new HashMap<>());
        }

        for (ForChemChamp champ : champs) {
            map.get(champ.getLane()).put(champ.getChampionId(), champ.getAmountOfGame());
        }

        return map;
    }

}
