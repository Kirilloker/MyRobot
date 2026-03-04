package org.domain.model;

import java.util.List;

public class GameField {
    List<List<Integer>> map;

    int barrierPercent;

    Position finishPosition;

    public GameField(List<List<Integer>> initMap)
    {
        map = initMap;
    }

    public GameField(List<List<Integer>> initMap, int barrierPercent)
    {
        map = initMap;
        this.barrierPercent = barrierPercent;
    }

    public boolean canMoveTo(Position pos)
    {
        var entity = getCellType(pos);

        return entity == CellType.EMPTY || entity == CellType.FINISH;
    }

    public int getHeight()
    {
        return map.size();
    }

    public int getWidth()
    {
        if (!map.isEmpty())
        {
            return map.get(0).size();
        }

        return 0;
    }

    public CellType getCellType(Position pos)
    {
        return getCellType(pos.getX(), pos.getY());
    }

    public CellType getCellType(int x, int y)
    {
        if (x >= 0 && y >= 0 && map.size() > x && map.get(x).size() > y)
        {
            int entityIndex = map.get(x).get(y);
            return CellType.values()[entityIndex];
        }

        return CellType.UNKNOWN;
    }

    public void setCellType(Position position, CellType cellType){
        map.get(position.getX()).set(position.getY(), cellType.ordinal());
    }

    public Position getFinishPosition()
    {
        return finishPosition;
    }

    public void setFinishPosition(Position pos)
    {
        finishPosition = pos;
        setCellType(pos, CellType.FINISH);
    }

    public int getBarrierPercent() {
        return barrierPercent;
    }
}
