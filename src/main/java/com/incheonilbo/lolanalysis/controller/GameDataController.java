package com.incheonilbo.lolanalysis.controller;

import com.incheonilbo.lolanalysis.dto.ForChemChampLessLaneAndChampionId;
import com.incheonilbo.lolanalysis.service.GameDataService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;


@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/gamedata")
public class GameDataController {

    private final GameDataService gameDataService;

    @GetMapping("/champs/{id}")
    public Map<String, Map<Integer, ForChemChampLessLaneAndChampionId>> abcd(@PathVariable(value = "id") Integer championId, @RequestParam("lane") String lane, @RequestParam(value = "champs", required = false, defaultValue = "") List<Integer> champs, @RequestParam(value = "lanes", required = false, defaultValue = "") List<String> lanes) {
        return gameDataService.findChemChampsByLaneAndChamp(championId, lane, champs, lanes);
    }
}
