package org.domain.controller;

import org.domain.model.GameField;
import org.domain.model.Robot;
import org.domain.model.RobotCommand;
import org.io.ConsoleCommandReader;
import org.levels.GenerateGameField;
import org.render.GameFieldRenderer;

import javax.swing.*;

public class GameController {
    private Robot robot;
    private GameField field;

    public GameController(Robot robot, GameField field) {
        this.robot = robot;
        this.field = field;
    }

    public void runGameLoop()
    {
        while (true) {
            GameFieldRenderer.clearScreen();
            GameFieldRenderer.renderField(field, robot);

            var command = ConsoleCommandReader.readCommand();

            if (command == RobotCommand.EXIT) {
                System.out.println("You're just... ahhh!");
                break;
            } else if (command == RobotCommand.TURN_LEFT) {
                robot.turnLeft();
            } else if (command == RobotCommand.TURN_RIGHT) {
                robot.turnRight();
            } else if (command == RobotCommand.MOVE_FORWARD) {
                var newPosition = robot.getNextPosition();

                if (field.canMoveTo(newPosition)) {
                    robot.moveTo(newPosition);

                    if (isGameFinished()) {
                        System.out.println("Yeee End game");
                        break;
                    }

//                    generatedNewMap();
                } else {
                    System.out.println("Not!!!!");
                }
            }

        }
    }

    private void generatedNewMap(){
        var levelMap = GenerateGameField.CreateRandomMap(robot.getPosition(),
                field.getFinishPosition(), field.getHeight(),
                field.getWidth(), field.getBarrierPercent());

        var gameField = new GameField(levelMap, field.getBarrierPercent());
        gameField.setFinishPosition(field.getFinishPosition());

        field = gameField;
    }

    private boolean isGameFinished()
    {
        return robot.getPosition().equals(field.getFinishPosition());
    }
}