package byog.lab6;

import org.junit.Test;

public class TestMemoryGame {
    @Test
    public void testRandom() {
        int seed = 124;
        MemoryGame game = new MemoryGame(40, 40, seed);
        game.startGame();
        //String output = game.generateRandomString(4);
        //game.drawFrame(output);
        System.out.println("HOld on");

    }
}
