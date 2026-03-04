package org.domain.model;

public class Robot {
    private Position pos;
    private Direction dir;

    public Robot(Position pos)
    {
        this.pos = pos;
        this.dir = Direction.NORTH;
    }

    public Direction getDirection() {
        return dir;
    }

    public Position getPosition() {
        return pos;
    }

    public void moveTo(Position pos) {
        this.pos = pos;
    }

    public void turnRight() {
        dir = dir.turnRight();
    }

    public void turnLeft() {
        dir = dir.turnLeft();
    }

    public Position getNextPosition()
    {
        int x = pos.getX();
        int y = pos.getY();

        return switch (dir) {
            case NORTH -> new Position(x - 1, y);
            case EAST -> new Position(x, y + 1);
            case SOUTH ->  new Position(x + 1, y);
            case WEST ->  new Position(x, y - 1);
        };
    }
}
