package com.incheonilbo.lolanalysis.controller.api;

import com.incheonilbo.lolanalysis.dto.ForChemChampLessLaneAndChampionId;
import com.incheonilbo.lolanalysis.entity.ApiResult;
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

    @GetMapping("/main")
    public ApiResult dfdfff(@RequestParam("lane") String lane) {

        return new ApiResult(gameDataService.getChampsTable(lane));
    }

    @GetMapping("/champs/{id}/chem")
    public ApiResult abcd(
            @PathVariable(value = "id") Integer championId, @RequestParam("lane") String lane,
            @RequestParam(value = "champs", required = false, defaultValue = "") List<Integer> champs,
            @RequestParam(value = "lanes", required = false, defaultValue = "") List<String> lanes) {

        return new ApiResult(gameDataService.findChemChampsByLaneAndChamp(championId, lane, champs, lanes));
    }

    @GetMapping("/champs/{id}/skills")
    public ApiResult defg(
            @PathVariable(value = "id") Integer championId, @RequestParam(value = "lane") String lane,
            @RequestParam(value = "skill") String skillLength) {
        return new ApiResult(gameDataService.findOneAboutSkillByChampId(championId, lane, skillLength));
    }

    @GetMapping("/champs/{id}/counter5")
    public ApiResult dgsdf( @PathVariable(value = "id") Integer championId, @RequestParam(value = "lane") String lane) {
        return new ApiResult(gameDataService.find5ChampsAboutCountersOfMainChamp(championId, lane));
    }
}
