package com.incheonilbo.lolanalysis.service;

import com.incheonilbo.lolanalysis.dto.ForChemChamp;
import com.incheonilbo.lolanalysis.dto.ForChemChampLessLane;
import com.incheonilbo.lolanalysis.dto.ForChemChampLessLaneAndChampionId;
import com.incheonilbo.lolanalysis.repository.query.GameDataQueryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GameDataService {

    private final GameDataQueryRepository gameDataQueryRepository;
    List<String> laneList = Arrays.asList("Top", "Jungle", "Middle", "Bot", "Supporter");

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
