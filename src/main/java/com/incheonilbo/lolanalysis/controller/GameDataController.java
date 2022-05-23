package com.incheonilbo.lolanalysis.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping(value = "/gamedata")
public class GameDataController {

    @GetMapping("")
    public String getMainPage(Model model) {
        return "main";
    }

    @GetMapping("/{id}")
    public String getChampInfoPage(Model model, @PathVariable(value = "id") Integer championId,
                                   @RequestParam(value = "lane") String lane) {
        model.addAttribute("championId", championId);
        model.addAttribute("lane", lane);
        return "championInfo";
    }

    @GetMapping("/{id}/chem")
    public String getChemiPage(
            Model model,
            @PathVariable(value = "id") Integer championId, @RequestParam("lane") String lane,
            @RequestParam(value = "champs", required = false, defaultValue = "") List<Integer> champs,
            @RequestParam(value = "lanes", required = false, defaultValue = "") List<String> lanes) {
        System.out.println("chemiPage");
        model.addAttribute("championId", championId);
        model.addAttribute("lane", lane);
        model.addAttribute("champs", champs);
        model.addAttribute("lanes", lanes);
        return "championchemi";
    }

    @GetMapping("/{id}/counter")
    public String getCounterPage(
            Model model,
            @PathVariable(value = "id") Integer championId, @RequestParam("lane") String lane,
            @RequestParam(value = "champs", required = false, defaultValue = "") List<Integer> champs,
            @RequestParam(value = "lanes", required = false, defaultValue = "") List<String> lanes) {
        model.addAttribute("championId", championId);
        model.addAttribute("lane", lane);
        model.addAttribute("champs", champs);
        model.addAttribute("lanes", lanes);
        return "championcounter";
    }

}
