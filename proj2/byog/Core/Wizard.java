package byog.Core;

import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;
import byog.lab5.Position;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/** Wizard is a drawer to generate the maze.
 * He can walk around and create rooms and hallways. */
public class Wizard {
    private Position position;
    private String facing;
    private int seed;
    private TETile[][] world;
    private int worldHeight;
    private int worldWidth;
    private ArrayList<Position> note;

    private Map<Integer, TETile> terrain = new HashMap<Integer, TETile>();
    private Map<Integer, String> direction = new HashMap<Integer, String>();

    private static final int minRoomLength = 3;
    private static final int maxRoomLength = 6;
    private static final int maxRoomWidth = 3;
    private static final int minHallwayLength = 3;
    private static final int maxHallwayLength = 8;

    private static final int minRoomNum = 20;
    private static final int maxRoomNum = 24;

    private static final int minHallwayNum = 8;
    private static final int maxHallwayNum = 12;

    public Wizard(int x, int y, int seed, TETile[][] world) {
        terrain.put(0, Tileset.FLOWER);
        terrain.put(1, Tileset.GRASS);
        terrain.put(2, Tileset.MOUNTAIN);
        terrain.put(3, Tileset.SAND);
        terrain.put(4, Tileset.TREE);
        terrain.put(5, Tileset.WATER);
        direction.put(0, "Up");
        direction.put(1, "Down");
        direction.put(2, "Left");
        direction.put(3, "Right");

        this.position = new Position(x, y);
        this.seed = seed;
        this.facing = direction.get(0);
        this.world = world;
        this.worldHeight = world[0].length;
        this.worldWidth = world.length;
        this.note = new ArrayList<Position>();
    }

    public Wizard(Position p, int seed, TETile[][] world) {
        terrain.put(0, Tileset.FLOWER);
        terrain.put(1, Tileset.GRASS);
        terrain.put(2, Tileset.MOUNTAIN);
        terrain.put(3, Tileset.SAND);
        terrain.put(4, Tileset.TREE);
        terrain.put(5, Tileset.WATER);
        direction.put(0, "Up");
        direction.put(1, "Down");
        direction.put(2, "Left");
        direction.put(3, "Right");
        this.position = p;
        this.seed = seed;
        this.facing = direction.get(0);
        this.world = world;
        this.worldHeight = world[0].length;
        this.worldWidth = world.length;
        this.note = new ArrayList<Position>();
    }

    public void move(Position p) {
        position = p;
    }

    public void move(int x, int y) {
        position = new Position(x, y);
    }

    public void moveUp(int steps) {
        turn("Up");
        position  = new Position(position.x, position.y + steps);
    }

    public void moveDown(int steps) {
        turn("Down");
        position  = new Position(position.x, position.y - steps);
    }

    public void moveLeft(int steps) {
        turn("Left");
        position  = new Position(position.x - steps, position.y);
    }

    public void moveRight(int steps) {
        turn("Right");
        position  = new Position(position.x + steps, position.y);
    }

    /**
     * The wizard changes his direction.
     */
    public void turn(String d) {
        facing = d;
    }

    public void turn(int directionIndex) {
        facing = direction.get(directionIndex);
    }

    /**
     * The wizard draw something at a GIVEN place.
     */
    public void draw(Position p, TETile te) {
        world[p.x][p.y] = te;
    }

    /**
     * The wizard draw something at his present place.
     */
    public void draw(TETile te) {
        world[position.x][position.y] = te;
    }

    /**
     * The wizard paves the floor at his present place.
     */
    public void pave() {
        if (!world[position.x][position.y].equals(Tileset.NOTHING)) {
            System.out.println("I cannot pave at this place.");
            return;
        }
        world[position.x][position.y] = Tileset.FLOOR;
    }

    private void addHorizontalLine(Position p, int size, TETile te) {
        int x = p.x;
        int y = p.y;
        for (int i = x; i < x + size; i += 1) {
            world[i][y] = te;
        }
    }

    private void addVerticalLine(Position p, int size, TETile te) {
        int x = p.x;
        int y = p.y;
        for (int i = y; i < y + size; i += 1) {
            world[x][i] = te;
        }
    }

    /** The wizard creates a new block to his right. */
    private void addRightBlock(Position p, TETile te,
                              int len, int leftWid, int rightWid) {
        int x = p.x;
        int y = p.y;
        for (int i = x; i < x + len; i += 1) {
            for (int j = y - rightWid; j < y + leftWid + 1; j += 1) {
                world[i][j] = te;
            }
        }
    }

    /** The wizard creates a new block to his left. */
    private void addLeftBlock(Position p, TETile te,
                             int len, int leftWid, int rightWid) {

        int x = p.x;
        int y = p.y;
        for (int i = x; i > x - len; i -= 1) {
            for (int j = y - leftWid; j < y + rightWid + 1; j += 1) {
                world[i][j] = te;
            }
        }
    }

    /** The wizard creates a new block to his up. */
    private void addUpBlock(Position p, TETile te,
                           int len, int leftWid, int rightWid) {
        int x = p.x;
        int y = p.y;
        for (int j = y; j < y + len; j += 1) {
            for (int i = x - leftWid; i < x + rightWid + 1; i += 1) {
                world[i][j] = te;
            }
        }
    }

    /** The wizard creates a new block to his down.
     * The leftWid is actually on the right. */
    private void addDownBlock(Position p, TETile te,
                             int len, int leftWid, int rightWid) {
        int x = p.x;
        int y = p.y;
        for (int j = y; j > y - len; j -= 1) {
            for (int i = x - rightWid; i < x + leftWid + 1; i += 1) {
                world[i][j] = te;
            }
        }
    }
    /** The wizard creates a Room with given length and widths,
     * based on his position and facing. */
    public void createRoom(int len, int leftWid, int rightWid) {
        switch (facing) {
            case "Up":
                addUpBlock(position, Tileset.FLOOR, len, leftWid, rightWid);
                break;
            case "Down":
                addDownBlock(position, Tileset.FLOOR, len, leftWid, rightWid);
                break;
            case "Left":
                addLeftBlock(position, Tileset.FLOOR, len, leftWid, rightWid);
                break;
            case "Right":
                addRightBlock(position, Tileset.FLOOR, len, leftWid, rightWid);
                break;
            default:
                System.out.println("The wizard loses his direction!");

        }
    }

    /** The wizard creates a Hallway with given length,
     * based on his position and facing. */
    public void createHallway(int len) {
        createRoom(len, 0, 0);
    }

    /** The wizard check if there is enough space to his right. */
    private boolean checkRightSpace(Position p, int len,
                                  int leftWid, int rightWid) {
        int x = p.x;
        int y = p.y;
        for (int i = x; i < x + len; i += 1) {
            for (int j = y - rightWid; j < y + leftWid + 1; j += 1) {
                if (i >= worldWidth) {
                    return false;
                }
                if (j < 0 || j >= worldHeight) {
                    return false;
                }
                if (!world[i][j].equals(Tileset.NOTHING)) {
                    return false;
                }
            }
        }
        return true;
    }

    /** The wizard check if there is enough space to his left. */
    private boolean checkLeftSpace(Position p, int len,
                                    int leftWid, int rightWid) {

        int x = p.x;
        int y = p.y;
        for (int i = x; i > x - len; i -= 1) {
            for (int j = y - leftWid; j < y + rightWid + 1; j += 1) {
                if (i < 0) {
                    return false;
                }
                if (j < 0 || j >= worldHeight) {
                    return false;
                }
                if (!world[i][j].equals(Tileset.NOTHING)) {
                    return false;
                }
            }
        }
        return true;
    }

    /** The wizard check if there is enough space to his up. */
    private boolean checkUpSpace(Position p,
                                  int len, int leftWid, int rightWid) {
        int x = p.x;
        int y = p.y;
        for (int j = y; j < y + len; j += 1) {
            for (int i = x - leftWid; i < x + rightWid + 1; i += 1) {
                if (j >= worldHeight) {
                    return false;
                }
                if (i < 0 || i >= worldWidth) {
                    return false;
                }
                if (!world[i][j].equals(Tileset.NOTHING)) {
                    return false;
                }
            }
        }
        return true;
    }

    /** The wizard creates a new block to his down.
     * The leftWid is actually on the right. */
    private boolean checkDownSpace(Position p, int len,
                                   int leftWid, int rightWid) {
        int x = p.x;
        int y = p.y;
        for (int j = y; j > y - len; j -= 1) {
            for (int i = x - rightWid; i < x + leftWid + 1; i += 1) {
                if (j < 0) {
                    return false;
                }
                if (i < 0 || i >= worldWidth) {
                    return false;
                }
                if (!world[i][j].equals(Tileset.NOTHING)) {
                    return false;
                }
            }
        }
        return true;
    }

    /** The wizard will check if there is enough space for ...
     *
     */
    public boolean checkEnoughSpace(int len, int leftWid, int rightWid) {
        switch (facing) {
            case "Up":
                return checkUpSpace(position, len, leftWid, rightWid);
            case "Down":
                return checkDownSpace(position, len, leftWid, rightWid);
            case "Left":
                return checkLeftSpace(position, len, leftWid, rightWid);
            case "Right":
                return checkRightSpace(position, len, leftWid, rightWid);
            default:
                System.out.println("Where am I looking?");
        }
        return false;
    }


    /**
     * The wizard is teleported to a random place and
     * faces random direction.
     */
    public void randomTeleport() {
        Random random = new Random(seed);
        seed = random.nextInt(10000);
        int randomY = RandomUtils.uniform(random, 0, worldHeight);
        int randomX = RandomUtils.uniform(random, 0, worldWidth);
        int randomDirection = RandomUtils.uniform(random, 0, 4);
        move(randomX, randomY);
        turn(randomDirection);
    }

    /**
     * The wizard is random teleported and try to create a room.
     * If he fails, he will immediately begin a new try.
     */
    public void randomBuildRoom(int len, int leftWid, int rightWid) {
        randomTeleport();
        while (!checkEnoughSpace(len, leftWid, rightWid)) {
            randomTeleport();
        }
        createRoom(len, leftWid, rightWid);
    }

    public void randomBuildRoom() {
        Random random = new Random(seed);
        seed = random.nextInt(10000);
        int len = RandomUtils.uniform(random, minRoomLength, maxRoomLength + 1);
        int leftWid = RandomUtils.uniform(random, 1, maxRoomWidth + 1);
        int rightWid = RandomUtils.uniform(random, 1, maxRoomWidth + 1);
        randomBuildRoom(len, leftWid, rightWid);
    }

    public void randomBuildHallway(int len) {
        randomBuildRoom(len, 0, 0);
    }

    public void randomBuildHallway() {
        Random random = new Random(seed);
        seed = random.nextInt(1000); // I don't know why it can't be too large.
        int len = RandomUtils.uniform(random, minHallwayLength, maxHallwayLength + 1);
        randomBuildHallway(len);
    }

    /**
     * Create a discrete world with GIVEN number of rooms and hallways.
     * The length and width are random.
     */
    public void buildDiscreteWorld(int numOfRooms, int numOfHallways) {
        for (int i = 0; i < numOfRooms; i += 1) {
            randomBuildRoom();
            note.add(position);
        }

        for (int j = 0; j < numOfHallways; j += 1) {
            randomBuildHallway();
            note.add(position);
        }
    }

    public void buildDiscreteWorld() {
        Random random = new Random(seed);
        seed = random.nextInt(10000);
        int numOfRooms = RandomUtils.uniform(random, minRoomNum, maxRoomNum + 1);
        int numOfHallways = RandomUtils.uniform(random, minHallwayNum, maxHallwayNum + 1);
        buildDiscreteWorld(numOfRooms, numOfHallways);
    }

    /**
     * Now we begin to think about how we can develop our discrete world into
     * a connected one.
     */

    /**
     * Reorganize the note of positions so that it is ordered by
     * minimal distance one by one.
     */
    public void reorganizeNote() {
        note = Position.orderList(note);
    }

    /**
     * The wizard will see if there is a floor in the next step
     * closer to the given position
     * in the X/Y direction.
     */
    private boolean seeNextFloorX(Position target) {
        if (target.x == position.x) {
            return false;
        }
        if (target.x > position.x) {

            return world[position.x + 1][position.y].equals(Tileset.FLOOR);
        }
        if (target.x < position.x) {
            return world[position.x - 1][position.y].equals(Tileset.FLOOR);
        }
        return false;
    }
    private boolean seeNextFloorY(Position target) {
        if (target.y == position.y) {
            return false;
        }
        if (target.y > position.y) {

            return world[position.x][position.y + 1].equals(Tileset.FLOOR);
        }
        if (target.y < position.y) {
            return world[position.x][position.y - 1].equals(Tileset.FLOOR);
        }
        return false;
    }

    /**
     * The wizard will move one step closer to the given position
     * in the X/Y direction if there is an existing floor.
     */

    private void getCloserOnFloorX(Position target) {
        if (target.x > position.x
                && world[position.x + 1][position.y].equals(Tileset.FLOOR)) {
            moveRight(1);
        }
        if (target.x < position.x
                && world[position.x - 1][position.y].equals(Tileset.FLOOR)) {
            moveLeft(1);
        }
    }

    private void getCloserOnFloorY(Position target) {
        if (target.y > position.y
                && world[position.x][position.y + 1].equals(Tileset.FLOOR)) {
            moveUp(1);
        }
        if (target.y < position.y
                && world[position.x][position.y - 1].equals(Tileset.FLOOR)) {
            moveDown(1);
        }
    }

    /**
     * The wizard will see if he reaches the target position
     * in the X/Y direction.
     */

    private boolean reachX(Position target) {
        return position.x == target.x;
    }

    private boolean reachY(Position target) {
        return position.y == target.y;
    }

    /**
     * The wizard will move one step closer to the given position
     * in the X/Y direction.
     */

    private void getCloserX(Position target) {
        if (target.x > position.x) {
            moveRight(1);
        }
        if (target.x < position.x) {
            moveLeft(1);
        }
    }

    private void getCloserY(Position target) {
        if (target.y > position.y) {
            moveUp(1);
        }
        if (target.y < position.y) {
            moveDown(1);
        }
    }
    /**
     * What the wizard will do in a single step when connecting the world.
     * */
    private void oneStepPavePlan(Position end) {
        if (seeNextFloorX(end)) {
            getCloserOnFloorX(end);
            return;
        }
        if (seeNextFloorY(end)) {
            getCloserOnFloorY(end);
            return;
        }
        if (!reachX(end)) {
            getCloserX(end);
            //draw(Tileset.FLOWER);
            pave();
            return;
        }
        if (!reachY(end)) {
            getCloserY(end);
            pave();
            //draw(Tileset.FLOWER);
            return;
        }
    }

    /**
     * The wizard will pave a way from the starting position to
     * the end position.
     */
    private void paveAWay(Position start, Position end) {
         move(start);
         while (!position.equals(end)) {
             oneStepPavePlan(end);
         }
    }


    /**
     * Link the discrete world into a whole one.
     * */
    public void connectWorld() {
        Position start, end;
        for (int i = 0; i < note.size() - 1; i += 1) {
            start = note.get(i);
            end = note.get(i + 1);
            paveAWay(start, end);
        }
    }

    /**
     * Build a boundary wall encircling the world.
     */

    public void buildBoundaryWall() {
        for (int i = 0; i < worldWidth; i += 1) {
            for (int j = 0; j < worldHeight; j += 1) {
                if (i == 0 || i == worldWidth - 1
                        || j == 0 || j == worldHeight - 1) {
                    world[i][j] = Tileset.WALL;
                }
            }
        }
    }

    public void removeBoundaryWall() {
        for (int i = 0; i < worldWidth; i += 1) {
            for (int j = 0; j < worldHeight; j += 1) {
                if (i == 0 || i == worldWidth - 1
                        || j == 0 || j == worldHeight - 1) {
                    world[i][j] = Tileset.NOTHING;
                }
            }
        }
    }

    /**
     * Check if there is a need to build the wall.
     */
    public boolean isAdjacentFloor(int x, int y) {
        Position pUp = new Position(x, y + 1);
        Position pDown = new Position(x, y - 1);
        Position pLeft = new Position(x - 1, y);
        Position pRight = new Position(x + 1, y);
        Position pUpLeft = new Position(x - 1, y + 1);
        Position pUpRight = new Position(x + 1, y + 1);
        Position pDownLeft = new Position(x - 1, y - 1);
        Position pDownRight = new Position(x + 1, y - 1);
        if (pUp.y < worldHeight - 1) {
            if (world[pUp.x][pUp.y].equals(Tileset.FLOOR)) {
                return true;
            }
        }
        if (pDown.y > 0) {
            if (world[pDown.x][pDown.y].equals(Tileset.FLOOR)) {
                return true;
            }
        }
        if (pLeft.x > 0) {
            if (world[pLeft.x][pLeft.y].equals(Tileset.FLOOR)) {
                return true;
            }
        }
        if (pRight.x < worldWidth - 1) {
            if (world[pRight.x][pRight.y].equals(Tileset.FLOOR)) {
                return true;
            }
        }
        if (pUpLeft.y < worldHeight - 1 && pUpLeft.x > 0) {
            if (world[pUpLeft.x][pUpLeft.y].equals(Tileset.FLOOR)) {
                return true;
            }
        }
        if (pUpRight.y < worldHeight - 1 && pUpRight.x < worldWidth - 1) {
            if (world[pUpRight.x][pUpRight.y].equals(Tileset.FLOOR)) {
                return true;
            }
        }
        if (pDownLeft.y > 0 && pDownLeft.x > 0) {
            if (world[pDownLeft.x][pDownLeft.y].equals(Tileset.FLOOR)) {
                return true;
            }
        }
        if (pDownRight.y > 0 && pDownRight.x < worldWidth - 1) {
            if (world[pDownRight.x][pDownRight.y].equals(Tileset.FLOOR)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Now, build the wall!
     */
    public void buildWall() {
        for (int i = 0; i < worldWidth; i += 1) {
            for (int j = 0; j < worldHeight; j += 1) {
                if (world[i][j].equals(Tileset.NOTHING)
                        && isAdjacentFloor(i, j)) {
                    world[i][j] = Tileset.WALL;
                }
            }
        }
    }

    /**
     * The whole building plan.
     */
    public void buildWorld() {
        buildBoundaryWall();
        buildDiscreteWorld();
        removeBoundaryWall();
        reorganizeNote();
        connectWorld();
        buildWall();
    }
}
