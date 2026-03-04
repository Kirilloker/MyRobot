package org.io;

import org.domain.model.RobotCommand;

import java.util.Scanner;

public class ConsoleCommandReader {

    public static RobotCommand readCommand() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Input symbol:");
            System.out.println("Q - turn left");
            System.out.println("W - move forward");
            System.out.println("E - turn right");
            System.out.println("K - killself");

            int number;
//            scanner.nextLine();

            String input = scanner.nextLine().trim().toUpperCase();

            if (input.length() != 1) {
                continue;
            }

            char command = input.charAt(0);

            switch (command) {
                case 'Q':
                    return RobotCommand.TURN_LEFT;
                case 'W':
                    return RobotCommand.MOVE_FORWARD;
                case 'E':
                    return RobotCommand.TURN_RIGHT;
                case 'K':
                    return RobotCommand.EXIT;
            }

            System.out.println("Input not correct");
        }
    }
}
