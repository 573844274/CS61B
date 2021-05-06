package byog.lab6;

import edu.princeton.cs.introcs.StdDraw;

import java.awt.Color;
import java.awt.Font;
import java.util.Random;

public class MemoryGame {
    private int width;
    private int height;
    private int round;
    private Random rand;
    private boolean gameOver;
    private boolean playerTurn;
    private static final char[] CHARACTERS = "abcdefghijklmnopqrstuvwxyz".toCharArray();
    private static final String[] ENCOURAGEMENT = {"You can do this!", "I believe in you!",
                                                   "You got this!", "You're a star!", "Go Bears!",
                                                   "Too easy for you!", "Wow, so impressive!"};

    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("Please enter a seed");
            return;
        }

        int seed = Integer.parseInt(args[0]);
        MemoryGame game = new MemoryGame(40, 40, seed);
        game.startGame();
    }

    public MemoryGame(int width, int height, int seed) {
        /* Sets up StdDraw so that it has a width by height grid of 16 by 16 squares as its canvas
         * Also sets up the scale so the top left is (0,0) and the bottom right is (width, height)
         */
        this.width = width;
        this.height = height;
        StdDraw.setCanvasSize(this.width * 16, this.height * 16);
        Font font = new Font("Monaco", Font.BOLD, 30);
        StdDraw.setFont(font);
        StdDraw.setXscale(0, this.width);
        StdDraw.setYscale(0, this.height);
        StdDraw.clear(Color.BLACK);
        StdDraw.enableDoubleBuffering();

        //Initialize random number generator
        rand = new Random(seed);
    }

    public String generateRandomString(int n) {
        // Generate random string of letters of length n
        String output = "";
        for (int i = 0; i < n; i += 1) {
            output += CHARACTERS[rand.nextInt(CHARACTERS.length)];
        }
        return output;
    }

    public void drawFrame(String s) {
        //TODO: Take the string and display it in the center of the screen
        //TODO: If game is not over, display relevant game information at the top of the screen
        StdDraw.clear(Color.BLACK);
        Font font = new Font("Monaco", Font.BOLD, 30);
        StdDraw.setPenColor(255, 255, 255);
        StdDraw.setFont(font);
        StdDraw.text(0.5 * width, 0.5 * height, s);
        StdDraw.show();
        StdDraw.pause(1000);
    }

    public void flashSequence(String letters) {
        //TODO: Display each character in letters, making sure to blank the screen between letters
        char[] lettersArray = letters.toCharArray();
        Font font = new Font("Monaco", Font.BOLD, 30);
        StdDraw.setPenColor(255, 255, 255);
        StdDraw.setFont(font);
        for (char letter : lettersArray) {
            StdDraw.clear(Color.BLACK);
            StdDraw.text(0.5 * width, 0.5 * height,
                    Character.toString(letter));
            StdDraw.show();
            StdDraw.pause(1000);
            StdDraw.clear(Color.BLACK);
            StdDraw.show();
            StdDraw.pause(1000 / 2);
        }
    }

    public String solicitNCharsInput(int n) {
        //TODO: Read n letters of player input
        String letters = new String("");
        int count = 0;
        while (count < n) {
            if (StdDraw.hasNextKeyTyped()) {
                char l = StdDraw.nextKeyTyped();
                letters += Character.toString(l);
                count += 1;
            }
        }
        return letters;
    }

    public void startGame() {
        //TODO: Set any relevant variables before the game starts
        int roundNumber = 0;
        String answer;
        String response;
        boolean continued = true;
        //TODO: Establish Game loop
        while (continued) {
            roundNumber += 1;
            drawFrame("Round: " + roundNumber);
            answer = generateRandomString(roundNumber);
            flashSequence(answer);
            response = solicitNCharsInput(roundNumber);
            if (!response.equals(answer)) {
                continued = false;
            }
        }
        drawFrame("Game over! You made it to round: " + roundNumber);

    }

}
