package org.levels;

import org.domain.model.Position;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GenerateGameField {
    private static final int UNKNOWN = 0;
    private static final int WALL = 1;
    private static final int EMPTY = 2;
    private static final int FINISH = 3;
    private static final int START = 4;

    private static final int MAX_GENERATION_ATTEMPTS = 100000;

    public static List<List<Integer>> CreateRandomMap(
            Position start,
            Position finish,
            int height,
            int width,
            int barrierPercent
    ) {
        int startRow = start.getX();
        int startCol = start.getY();
        int finishRow = finish.getX();
        int finishCol = finish.getY();

        Random random = new Random();

        for (int attempt = 0; attempt < MAX_GENERATION_ATTEMPTS; attempt++) {
            int[][] grid = new int[height][width];

            // Заполняем пустотой
            for (int r = 0; r < height; r++) {
                for (int c = 0; c < width; c++) {
                    grid[r][c] = EMPTY;
                }
            }

            // Стенки по краям
            for (int r = 0; r < height; r++) {
                grid[r][0] = WALL;
                grid[r][width - 1] = WALL;
            }
            for (int c = 0; c < width; c++) {
                grid[0][c] = WALL;
                grid[height - 1][c] = WALL;
            }

            grid[startRow][startCol] = START;
            grid[finishRow][finishCol] = FINISH;

            int innerRows = height - 2;
            int innerCols = width - 2;
            int innerCellCount = innerRows * innerCols;

            int protectedCells = 2;
            int maxPlaceableWalls = Math.max(0, innerCellCount - protectedCells);

            int requestedWalls = (int) Math.round(innerCellCount * (barrierPercent / 100.0));
            int wallsToPlace = Math.min(requestedWalls, maxPlaceableWalls);

            int placed = 0;

            while (placed < wallsToPlace) {
                int r = 1 + random.nextInt(height - 2);
                int c = 1 + random.nextInt(width - 2);

                if ((r == startRow && c == startCol) || (r == finishRow && c == finishCol)) {
                    continue;
                }
                if (grid[r][c] == WALL) {
                    continue;
                }

                grid[r][c] = WALL;
                placed++;
            }

            if (isPassableByExpansion(grid, startRow, startCol, finishRow, finishCol)) {
                grid[startRow][startCol] = EMPTY;
                return toList(grid);
            }
        }

        throw new IllegalStateException("Не удалось сгенерировать проходимую карту за " + MAX_GENERATION_ATTEMPTS + " попыток.");
    }

    private static boolean isPassableByExpansion(int[][] original, int startRow, int startCol, int finishRow, int finishCol) {
        int height = original.length;
        int width = original[0].length;

        int[][] grid = new int[height][width];
        for (int r = 0; r < height; r++) {
            System.arraycopy(original[r], 0, grid[r], 0, width);
        }

        grid[startRow][startCol] = START;
        grid[finishRow][finishCol] = FINISH;

        while (true) {
            boolean changed = false;

            for (int r = 0; r < height; r++) {
                for (int c = 0; c < width; c++) {

                    int value = grid[r][c];

                    if (value == START) {
                        if (hasNeighbor(grid, r, c, FINISH)) {
                            return true;
                        }
                        changed |= spreadToEmpty(grid, r, c, START);
                    } else if (value == FINISH) {
                        if (hasNeighbor(grid, r, c, START)) {
                            return true;
                        }
                        changed |= spreadToEmpty(grid, r, c, FINISH);
                    }
                }
            }


            if (!changed) {
                return false;
            }
        }
    }

    private static boolean hasNeighbor(int[][] grid, int r, int c, int target) {
        if (r - 1 >= 0 && grid[r - 1][c] == target) return true;
        if (c - 1 >= 0 && grid[r][c - 1] == target) return true;
        if (r + 1 < grid.length && grid[r + 1][c] == target) return true;
        if (c + 1 < grid[0].length && grid[r][c + 1] == target) return true;

        return false;
    }

    private static boolean spreadToEmpty(int[][] grid, int r, int c, int mark) {
        boolean changed = false;

        if (r - 1 >= 0 && grid[r - 1][c] == EMPTY) {
            grid[r - 1][c] = mark;
            changed = true;
        }
        if (c - 1 >= 0 && grid[r][c - 1] == EMPTY) {
            grid[r][c - 1] = mark;
            changed = true;
        }
        if (r + 1 < grid.length && grid[r + 1][c] == EMPTY) {
            grid[r + 1][c] = mark;
            changed = true;
        }
        if (c + 1 < grid[0].length && grid[r][c + 1] == EMPTY) {
            grid[r][c + 1] = mark;
            changed = true;
        }

        return changed;
    }

    private static List<List<Integer>> toList(int[][] grid) {
        int height = grid.length;
        int width = grid[0].length;

        List<List<Integer>> result = new ArrayList<>(height);
        for (int r = 0; r < height; r++) {
            List<Integer> row = new ArrayList<>(width);
            for (int c = 0; c < width; c++) {
                row.add(grid[r][c]);
            }
            result.add(row);
        }
        return result;
    }
}
