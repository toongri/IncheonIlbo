package com.incheonilbo.lolanalysis.controller.api;

import com.incheonilbo.lolanalysis.dto.ForChemChampLessLaneAndChampionId;
import com.incheonilbo.lolanalysis.entity.ApiResult;
import com.incheonilbo.lolanalysis.entity.ApiTotalResult;
import com.incheonilbo.lolanalysis.repository.query.ChampTableRepository;
import com.incheonilbo.lolanalysis.service.GameDataService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;


@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/gamedata/api")
public class GameDataApiController {

    private final GameDataService gameDataService;
    private final ChampTableRepository champTableRepository;

    @GetMapping("/main")
    public ApiTotalResult getMainApi(@RequestParam("lane") String lane) {

        return new ApiTotalResult(gameDataService.getChampsTable(lane), champTableRepository.findTotalCountByLane(lane));
    }

    @GetMapping("/champs/{id}/chem")
    public ApiTotalResult getChemiApi(
            @PathVariable(value = "id") Integer championId, @RequestParam("lane") String lane,
            @RequestParam(value = "champs[]", required = false, defaultValue = "") List<Integer> champs,
            @RequestParam(value = "lanes[]", required = false, defaultValue = "") List<String> lanes) {
        Map<String, List<ForChemChampLessLaneAndChampionId>> dd = gameDataService.findChemChampsByLaneAndChamp(championId, lane, champs, lanes);

        return new ApiTotalResult(dd, gameDataService.findChemCountChampsByLaneAndChamp(dd));
    }

    @GetMapping("/champs/{id}/counter")
    public ApiTotalResult getCounterApi(
            @PathVariable(value = "id") Integer championId, @RequestParam("lane") String lane,
            @RequestParam(value = "champs[]", required = false, defaultValue = "") List<Integer> champs,
            @RequestParam(value = "lanes[]", required = false, defaultValue = "") List<String> lanes) {
        Map<String, List<ForChemChampLessLaneAndChampionId>> dd = gameDataService.findCounterChampsByLaneAndChamp(championId, lane, champs, lanes);

        return new ApiTotalResult(dd, gameDataService.findChemCountChampsByLaneAndChamp(dd));
    }

    @GetMapping("/champs/{id}/skills")
    public ApiTotalResult getSkillApi(
            @PathVariable(value = "id") Integer championId, @RequestParam(value = "lane") String lane,
            @RequestParam(value = "skill") String skillLength) {
        return new ApiTotalResult(gameDataService.findOneAboutSkillByChampId(championId, lane, skillLength), gameDataService.findCountAboutSkillByChampId(championId, lane, skillLength));
    }

    @GetMapping("/champs/{id}/counter5")
    public ApiResult getCounter5Api(@PathVariable(value = "id") Integer championId, @RequestParam(value = "lane") String lane) {
        return new ApiResult(gameDataService.find5ChampsAboutCountersOfMainChamp(championId, lane));
    }
}
