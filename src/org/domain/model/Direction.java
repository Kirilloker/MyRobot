package org.domain.model;

public enum Direction {
    NORTH, // 0
    EAST,  // 1
    SOUTH, // 2
    WEST;  // 3

    public Direction turnRight() {
        int nextIndex = (this.ordinal() + 1) % values().length;
        return values()[nextIndex];
    }

    public Direction turnLeft() {
        int nextIndex = (this.ordinal() - 1 + values().length) % values().length; // 3 % 4 = 3
        return values()[nextIndex];
    }
}
