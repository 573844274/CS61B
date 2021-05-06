package byog.lab5;
import org.junit.Test;
import static org.junit.Assert.*;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

import java.util.Random;

/**
 * Draws a world consisting of hexagonal regions.
 */
public class HexWorld {
    private static final int WIDTH = 100;
    private static final int HEIGHT = 80;

    /**
     * Initialize the TERenderer with WIDTH and HEIGHT.
     */
    private static TERenderer initializingTer(int WIDTH, int HEIGHT) {
        TERenderer ter = new TERenderer();
        ter.initialize(WIDTH, HEIGHT);
        return ter;
    }

    /**
     * Initialize the world with WIDTH and HEIGHT.
     */
    private static TETile[][] initializingWorld(int WIDTH, int HEIGHT) {
        TETile[][] world = new TETile[WIDTH][HEIGHT];
        for (int x = 0; x < WIDTH; x += 1) {
            for (int y = 0; y < HEIGHT; y += 1) {
                world[x][y] = Tileset.NOTHING;
            }
        }
        return world;
    }

    /**
     * Add a block of walls to a given world.
     */
    private static void addBoringWorld(TETile[][] world) {
        for (int x = 20; x < 35; x += 1) {
            for (int y = 5; y < 10; y += 1) {
                world[x][y] = Tileset.WALL;
            }
        }
    }

    private static void addHorizontalLine(TETile[][] world, Position p, int size, TETile te) {
        int x = p.x;
        int y = p.y;
        for (int i = x; i < x + size; i += 1) {
            world[i][y] = te;
        }
    }

    /**
     * Add the core of a Hex.
     * The center is chosen at the up-left corner
     */
    private static void addHexCore(TETile[][] world, Position p, int size, TETile te) {
        int center_x = p.x;
        int center_y = p.y;
        for (int rowIndex = center_y; rowIndex > center_y - 2; rowIndex -= 1) {
            addHorizontalLine(world, new Position(center_x, rowIndex), size, te);
        }
    }

    /**
     * Add the side of a Hex.
     * The center is chosen at the up-left corner of the core.
     */
    private static void addHexSide(TETile[][] world, Position p, int size, TETile te) {
        int center_x = p.x;
        int center_y = p.y;
        Position p1 = new Position(center_x - size + 1, center_y);
        Position p2 = new Position(center_x - size + 1, center_y - 1);
        Position p3 = new Position(center_x + size, center_y);
        Position p4 = new Position(center_x + size, center_y - 1);
        addHorizontalLine(world, p1, size - 1, te);
        addHorizontalLine(world, p2, size - 1, te);
        addHorizontalLine(world, p3, size - 1, te);
        addHorizontalLine(world, p4, size - 1, te);
    }

    /**
     * Add the head of a Hex.
     * The center is chosen at the up-left corner of the core.
     */
    private static void addHexHead(TETile[][] world, Position p, int size, TETile te) {
        int center_x = p.x;
        int center_y = p.y;
        int beginX = center_x;
        int beginY = center_y + size - 1;
        int beginSize = size;
        while (beginY > center_y) {
            addHorizontalLine(world, new Position(beginX, beginY), beginSize, te);
            beginX -= 1;
            beginY -= 1;
            beginSize += 2;
        }
    }

    /**
     * Add the bottom of a Hex.
     * The center is chosen at the up-left corner of the core.
     */
    private static void addHexBottom(TETile[][] world, Position p, int size, TETile te) {
        int center_x = p.x;
        int center_y = p.y;
        int beginX = center_x;
        int beginY = center_y - size;
        int beginSize = size;
        while (beginY < center_y) {
            addHorizontalLine(world, new Position(beginX, beginY), beginSize, te);
            beginX -= 1;
            beginY += 1;
            beginSize += 2;
        }
    }

    /**
     * Generate a Hex at position p.
     */
    public static void addHexagon(TETile[][] world, Position p, int size, TETile te) {
        addHexCore(world, p, size, te);
        addHexSide(world, p, size, te);
        addHexHead(world, p, size, te);
        addHexBottom(world, p, size, te);
    }

    public static void drawCatanPlan(TETile[][] world, int size, TETile te) {
        int seed = 9;
        Position startPoint = new Position(WIDTH / 2, HEIGHT / 2);
        Drawer robot = new Drawer(startPoint.x, startPoint.y, seed);
        robot.drawRandomHex(world, size);
        robot.moveUp(size);
        robot.drawRandomHex(world, size);
        robot.moveDownRight(size);
        robot.drawRandomHex(world, size);
        robot.moveDown(size);
        robot.drawRandomHex(world, size);
        robot.moveDownLeft(size);
        robot.drawRandomHex(world, size);
        robot.moveUpLeft(size);
        robot.drawRandomHex(world, size);
        robot.moveUp(size);
        robot.drawRandomHex(world, size);
        robot.moveUp(size);
        robot.drawRandomHex(world, size);
        robot.moveUpRight(size);
        robot.drawRandomHex(world, size);
        robot.moveDownRight(size);
        robot.drawRandomHex(world, size);
        robot.moveDownRight(size);
        robot.drawRandomHex(world, size);
        robot.moveDown(size);
        robot.drawRandomHex(world, size);
        robot.moveDown(size);
        robot.drawRandomHex(world, size);
        robot.moveDownLeft(size);
        robot.drawRandomHex(world, size);
        robot.moveDownLeft(size);
        robot.drawRandomHex(world, size);
        robot.moveUpLeft(size);
        robot.drawRandomHex(world, size);
        robot.moveUpLeft(size);
        robot.drawRandomHex(world, size);
        robot.moveUp(size);
        robot.drawRandomHex(world, size);
        robot.moveUp(size);
        robot.drawRandomHex(world, size);
    }

    public static void main(String[] args) {
        TERenderer ter = initializingTer(WIDTH, HEIGHT);
        TETile[][] world = initializingWorld(WIDTH, HEIGHT);
        drawCatanPlan(world, 4, Tileset.FLOWER);
        ter.renderFrame(world);
    }
}


