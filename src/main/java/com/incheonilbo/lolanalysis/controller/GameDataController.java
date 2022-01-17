package com.incheonilbo.lolanalysis.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
@RequestMapping(value = "/gamedata")
public class GameDataController {

    @GetMapping("")
    public String dfdfd(Model model) {
        return "index";
    }

    @GetMapping("/{id}")
    public String xcvzxv(Model model, @PathVariable(value = "id") Integer championId,
                         @RequestParam(value = "lane") String lane) {
        model.addAttribute("championId", championId);
        model.addAttribute("lane", lane);
        return "skill";
    }
}
