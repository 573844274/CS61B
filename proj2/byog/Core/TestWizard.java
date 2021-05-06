package byog.Core;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import byog.lab5.Drawer;
import byog.lab5.Position;
import org.junit.Test;
import static org.junit.Assert.*;

public class TestWizard {
    private static final int WIDTH = 80;
    private static final int HEIGHT = 40;

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

    public static void wizardPlan(TETile[][] world) {
        Wizard wizard = new Wizard(WIDTH / 2, HEIGHT / 2, 1327, world);
        wizard.buildWorld();
    }

    public static void main(String[] args) {
        TERenderer ter = initializingTer(WIDTH, HEIGHT);
        TETile[][] world = initializingWorld(WIDTH, HEIGHT);
        wizardPlan(world);
        ter.renderFrame(world);
    }

    @Test
    public void testSomething() {
        TERenderer ter = initializingTer(WIDTH, HEIGHT);
        TETile[][] world = initializingWorld(WIDTH, HEIGHT);
        wizardPlan(world);
       // ter.renderFrame(world);

    }

    @Test
    public void testDistance() {
        Position a = new Position(1,1);
        Position b = new Position(2,3);
        Position a1 = new Position(0,0);
        Position a2 = new Position(3,3);
        Position a3 = new Position(3,4);
        ArrayList<Position> arrList = new ArrayList<Position>();
        arrList.add(a1);
        arrList.add(a2);
        arrList.add(a3);
        assertEquals(0, a.findMinimalDistanceIndex(arrList));
        assertEquals(1, b.findMinimalDistanceIndex(arrList));
    }

    @Test
    public void testOrderedDistance() {
        Position a0 = new Position(1,1);
        Position a1 = new Position(2,3);
        Position a2 = new Position(0,0);
        Position a3 = new Position(3,3);
        Position a4 = new Position(3,4);
        Position a5 = new Position(1,2);
        ArrayList<Position> arrList = new ArrayList<Position>();
        arrList.add(a0);
        arrList.add(a1);
        arrList.add(a2);
        arrList.add(a3);
        arrList.add(a4);
        arrList.add(a5);
        ArrayList<Position> orderedList = Position.orderList(arrList);
        System.out.println("Hold on!");
    }
}


