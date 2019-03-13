import game.GameManager;

/**
 * the ass5 game class.
 */
public class Ass6Game {

    /**
     * run the game.
     * create new game, initialize it and run it.
     *
     * @param args nothing.
     */
    public static void main(String[] args) {
        String path = "level_sets.txt"; // default file path
        if (args.length > 0) { // if the user insert another file path
            path = args[0];
        }
        GameManager gameManager = new GameManager(path);
        gameManager.runProgram();
    }
}
