package game;

import animation.AnimationRunner;
import animation.HighScoresAnimation;
import biuoop.GUI;
import biuoop.KeyboardSensor;
import menu.CreateSubMenu;
import menu.Menu;
import menu.MenuAnimation;
import menu.QuitGameTask;
import menu.ShowHiScoresTask;
import menu.Task;
import table.HighScoresTable;

import java.io.File;
import java.io.IOException;

/**
 * The type Game manager.
 */
public class GameManager {
    private game.Constants constants;
    private String filePath;
    private int numberOfLives;
    private KeyboardSensor keyboardSensor;
    private AnimationRunner runner;
    private GUI g;
    private HighScoresTable table;

    /**
     * Instantiates a new Game flow.
     *
     * @param path the path
     */
    public GameManager(String path) {
        this.constants = new game.Constants();
        this.numberOfLives = constants.NUM_OF_LIVES;
        this.g = new GUI(constants.GAME_NAME, constants.WIDTH, constants.HEIGHT); // create nuw gui with name and size
        this.runner = new AnimationRunner(constants.FRAMES_PER_SECONDS, g);
        this.keyboardSensor = g.getKeyboardSensor();
        this.table = loadOrCreateHighScoresTable();
        this.filePath = path;
    }

    /**
     * Load or create high scores table.
     *
     * @return the high scores table
     */
    public HighScoresTable loadOrCreateHighScoresTable() {
        this.table = null;
        File file = new File(constants.FILE_PATH);
        if (file.exists()) {
            return table.loadFromFile(file);
        } else {
            table = new HighScoresTable(constants.HIGH_SCORE_TABLE_MAX_SIZE);
            try {
                table.save(file);
            } catch (IOException e) {
                System.out.println("cannot save file-");
            }
        }
        return table;
    }

    /**
     * Initialize sub menu menu.
     *
     * @param path the path
     * @return the menu
     */
    public Menu<Task<Void>> initializeSubMenu(String path) {
        CreateSubMenu createSubMenu = new CreateSubMenu(path);
        createSubMenu.createMenu(runner, keyboardSensor, numberOfLives, g, table);
        return createSubMenu.getSubMenu();
    }

    /**
     * Create game menu.
     *
     * @param path the path
     * @return the menu
     */
    public Menu<Task<Void>> createMenu(String path) {
        // main menu
        Menu<Task<Void>> mainMenu = new MenuAnimation<>(keyboardSensor, runner);

        // initialize main menu
        mainMenu.addSubMenu("s", "Start game", initializeSubMenu(path));
        mainMenu.addSelection("t", "High score table", new ShowHiScoresTask(
                runner, new HighScoresAnimation(table)));
        mainMenu.addSelection("q", "Quit game", new QuitGameTask());
        return mainMenu;
    }

    /**
     * Run the program.
     */
    public void runProgram() {
        // create the menu
        Menu<Task<Void>> menu = createMenu(this.filePath);

        // start program - will stop when the user chose 'q' exit
        while (true) {
            runner.run(menu);
            // wait for user selection
            Task<Void> task = menu.getStatus();
            task.run();
        }
    }
}
