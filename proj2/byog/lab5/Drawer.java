package byog.lab5;

import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/** A robot who can move and draw.*/
public class Drawer {
    private int x;
    private int y;
    private int seed;

    private Map<Integer, TETile> terrain = new HashMap<Integer, TETile>();

    public Drawer(int x, int y, int seed) {
        this.x = x;
        this.y = y;
        this.seed = seed;
        terrain.put(0, Tileset.FLOWER);
        terrain.put(1, Tileset.GRASS);
        terrain.put(2, Tileset.MOUNTAIN);
        terrain.put(3, Tileset.SAND);
        terrain.put(4, Tileset.TREE);
        terrain.put(5, Tileset.WATER);
    }

    public void drawHex(TETile[][] world, int size, TETile te) {
        HexWorld.addHexagon(world, new Position(x, y), size, te);
    }

    public void drawRandomHex(TETile[][] world, int size) {
        Random random = new Random(seed);
        seed = random.nextInt(100);
        int terrainIndex = random.nextInt(6);
        drawHex(world,size, terrain.get(terrainIndex));
    }

    public void move(Position p) {
        x = p.x;
        y = p.y;
    }

    public void moveUp(int size) {
        y += 2 * size;
    }

    public void moveDown(int size) {
        y -= 2 * size;
    }

    public void moveUpLeft(int size) {
        for (int i = 0; i < size; i += 1) {
            x -= 1;
            y += 1;
        }
        for (int i = 0; i < size - 1; i += 1) {
            x -= 1;
        }
    }

    public void moveUpRight(int size) {
        for (int i = 0; i < size; i += 1) {
            x += 1;
            y += 1;
        }
        for (int i = 0; i < size - 1; i += 1) {
            x += 1;
        }
    }

    public void moveDownLeft(int size) {
        for (int i = 0; i < size; i += 1) {
            x -= 1;
            y -= 1;
        }
        for (int i = 0; i < size - 1; i += 1) {
            x -= 1;
        }
    }

    public void moveDownRight(int size) {
        for (int i = 0; i < size; i += 1) {
            x += 1;
            y -= 1;
        }
        for (int i = 0; i < size - 1; i += 1) {
            x += 1;
        }
    }

}
