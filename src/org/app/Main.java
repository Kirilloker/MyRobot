package org.app;

import org.domain.controller.GameController;
import org.domain.model.GameField;
import org.domain.model.Position;
import org.domain.model.Robot;
import org.levels.GenerateGameField;

import java.util.Random;

public class Main {
    public static void main(String[] args) {
        var height = 10;
        var width = 10;
        var start = new Position(1, 1);
        var finish = getRandomFisinhPosition(height, width);
        var barrierPercent = 40;

        var levelMap = GenerateGameField.CreateRandomMap(start, finish, height, width, barrierPercent);

        var gameField = new GameField(levelMap, barrierPercent);
        gameField.setFinishPosition(finish);

        var robot = new Robot(start);

        var controller = new GameController(robot, gameField);

        controller.runGameLoop();
    }

    public static Position getRandomFisinhPosition(int height, int width)
    {
        Random random = new Random();

        int x = 2 + random.nextInt(height - 3);
        int y = 2 + random.nextInt(width - 3);

        return new Position(x, y);
    }
}