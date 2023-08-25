package com.junohkim.DdongGame.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

@Controller
@RequestMapping("/game")
public class GameController {

    private int score = 0;
    private boolean isGameOver = false;
    private int playerX = 0;
    private int playerY = 0;
    private int playerWidth = 50;
    private int playerHeight = 50;
    private List<Obstacle> obstacles = new ArrayList<>();

    @GetMapping
    public String showGame(Model model) {
        if (isGameOver) {
            model.addAttribute("score", score);
            score = 0;
            isGameOver = false;
        } else {
            model.addAttribute("score", score);
        }
        model.addAttribute("playerX", playerX);
        model.addAttribute("playerY", playerY);
        model.addAttribute("playerWidth", playerWidth);
        model.addAttribute("playerHeight", playerHeight);
        model.addAttribute("obstacles", obstacles);
        return "game";
    }

    @PostMapping("/move")
    public String movePlayer(@RequestParam("direction") String direction) {
        final int MOVEMENT_SPEED = 5;

        if ("left".equals(direction) && playerX > 0) {
            playerX -= MOVEMENT_SPEED;
        } else if ("right".equals(direction) && playerX + playerWidth < 750) {
            playerX += MOVEMENT_SPEED;
        }


        updateObstacles();

        if (isCollisionOccurred()) {
            isGameOver = true;
        }

        return "redirect:/game";
    }


    private void updateObstacles() {
        Iterator<Obstacle> iterator = obstacles.iterator();
        while (iterator.hasNext()) {
            Obstacle obstacle = iterator.next();
            obstacle.setY(obstacle.getY() + 5); // 장애물을 아래로 이동시킴
            if (obstacle.getY() > 300) { // 화면 아래로 벗어난 장애물은 삭제
                iterator.remove();
            }
        }
    }


    private void generateObstacle() {
        Obstacle obstacle = new Obstacle();
        Random random = new Random();
        obstacle.setX(random.nextInt(/* Max X position */));
        obstacle.setY(0);
        obstacle.setWidth(50);
        obstacle.setHeight(50);

        obstacles.add(obstacle);
    }

    private boolean isCollisionOccurred() {
        Iterator<Obstacle> iterator = obstacles.iterator();
        while (iterator.hasNext()) {
            Obstacle obstacle = iterator.next();
            int obstacleX = obstacle.getX();
            int obstacleY = obstacle.getY();
            int obstacleWidth = obstacle.getWidth();
            int obstacleHeight = obstacle.getHeight();

            if (playerX < obstacleX + obstacleWidth &&
                    playerX + playerWidth > obstacleX &&
                    playerY < obstacleY + obstacleHeight &&
                    playerY + playerHeight > obstacleY) {
                iterator.remove();
                return true;
            }
        }

        return false;
    }

    private static class Obstacle {
        private int x;
        private int y;
        private int width;
        private int height;

        // Getters and setters

        public int getX() {
            return x;
        }

        public void setX(int x) {
            this.x = x;
        }

        public int getY() {
            return y;
        }

        public void setY(int y) {
            this.y = y;
        }

        public int getWidth() {
            return width;
        }

        public void setWidth(int width) {
            this.width = width;
        }

        public int getHeight() {
            return height;
        }

        public void setHeight(int height) {
            this.height = height;
        }
    }
}
