package com.junohkim.DdongGame.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/game")
public class GameController {

    private int score = 0;

    @GetMapping
    public String showGame(Model model) {
        model.addAttribute("score", score);
        return "game"; // Thymeleaf 템플릿 이름
    }

    @PostMapping("/move")
    public String movePlayer(/* Add necessary parameters */) {
        // Handle player movement logic here
        // Update player's position or check for collisions

        return "redirect:/game"; // Redirect back to the game page
    }

    // Additional methods for handling other game actions (e.g., restarting the game)
}
