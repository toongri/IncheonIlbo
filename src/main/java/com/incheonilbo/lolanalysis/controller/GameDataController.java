package com.incheonilbo.lolanalysis.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping(value = "/gamedata")
public class GameDataController {

    @GetMapping("/")
    public String dfdfd(Model model) {
        return "index";
    }
}
