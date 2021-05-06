package byog.lab5;

import byog.TileEngine.TETile;

import java.util.ArrayList;
import java.util.List;

public class Position {
    public int x;
    public int y;

    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public double distance(Position p) {
        return Math.sqrt(Math.pow((x - p.x), 2) + Math.pow((y - p.y), 2));
    }

    /**
     * Find the element with the minimal distance compared to a given position.
     */
    public int findMinimalDistanceIndex(List<Position> pList) {
        int index = 0;
        double minDistance = distance(pList.get(0));
        for (int i = 1; i < pList.size(); i += 1) {
            if (distance(pList.get(i)) < minDistance) {
                index = i;
                minDistance = distance(pList.get(i));
            }
        }
        return index;
    }

    /**
     * A recursive method to order the positions.
     */
    public static ArrayList<Position> orderList(ArrayList<Position> arrList) {
        int length = arrList.size();
        if (length <= 2) {
            return arrList;
        }
        ArrayList<Position> clone = (ArrayList<Position>) arrList.clone();
        Position first = clone.remove(0);
        int minDistanceIndex = first.findMinimalDistanceIndex(clone);
        Position second = clone.remove(minDistanceIndex);
        ArrayList<Position> remainList = new ArrayList<Position>();
        remainList.add(second);
        remainList.addAll(clone);

        ArrayList<Position> newList = new ArrayList<Position>();
        newList.add(first);
        newList.addAll(orderList(remainList));
        return newList;
    }

    public boolean equals(Object x) {
        if (this == x) {
            return true;
        }
        if (x == null) {
            return false;
        }
        if (this.getClass() != x.getClass()) {
            return false;
        }
        Position that = (Position) x;
        return this.x == that.x && this.y == that.y;
    }
}
