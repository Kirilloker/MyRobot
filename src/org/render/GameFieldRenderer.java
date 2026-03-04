package org.render;

import org.domain.model.CellType;
import org.domain.model.GameField;
import org.domain.model.Position;
import org.domain.model.Robot;

public class GameFieldRenderer {

    private static final String RESET = "\u001B[0m";
    private static final String GREEN = "\u001B[32m";
    private static final String YELLOW = "\u001B[33m";
    private static final String GRAY = "\u001B[90m";

    public static void renderField(GameField field, Robot robot) {
        int height = field.getHeight();
        int width = field.getWidth();

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                CellType entity = field.getCellType(i, j);

                if (robot.getPosition().equals(new Position(i, j))) {
                    entity = CellType.ROBOT;
                }

                System.out.print(getColoredSymbol(entity, robot, field, i, j) + ' ');
            }
            System.out.println();
        }
    }

    public static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    private static String getColoredSymbol(CellType cellType, Robot robot, GameField field, int i, int j) {
        return switch (cellType) {
            case ROBOT -> GREEN + getRobotSymbol(robot) + RESET;
            case WALL -> GRAY + 'H' + RESET;
//            case WALL -> GRAY + getBorderSymbol(field, i, j) + RESET;
            case FINISH -> YELLOW + 'F' + RESET;
            case EMPTY -> " ";
            default -> "?";
        };
    }

    private static char getRobotSymbol(Robot robot) {
        return switch (robot.getDirection()) {
            case NORTH -> '^';
            case WEST -> '<';
            case SOUTH -> 'v';
            case EAST -> '>';
        };
    }

    private static char getBorderSymbol(GameField field, int i, int j) {
        boolean up = isWall(field, i - 1, j);
        boolean down = isWall(field, i + 1, j);
        boolean left = isWall(field, i, j - 1);
        boolean right = isWall(field, i, j + 1);

        int mask = 0;
        if (up) mask |= 1;
        if (down) mask |= 2;
        if (left) mask |= 4;
        if (right) mask |= 8;

        return switch (mask) {
            case 0 -> '\u25A0';
            case 1, 2, 3 -> '\u2502';
            case 4, 8, 12 -> '\u2500';
            case 1 | 8 -> '\u2514';
            case 1 | 4 -> '\u2518';
            case 2 | 8 -> '\u250C';
            case 2 | 4 -> '\u2510';
            case 1 | 2 | 8 -> '\u251C';
            case 1 | 2 | 4 -> '\u2524';
            case 2 | 4 | 8 -> '\u252C';
            case 1 | 4 | 8 -> '\u2534';
            case 1 | 2 | 4 | 8 -> '\u253C';
            default -> '?';
        };

    }

    private static boolean isWall(GameField field, int i, int j) {
        if (i < 0 || j < 0 || i >= field.getHeight() || j >= field.getWidth()) {
            return false;
        }
        return field.getCellType(i, j) == CellType.WALL;
    }

}
