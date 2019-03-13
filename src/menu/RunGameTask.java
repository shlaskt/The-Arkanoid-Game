package menu;


import animation.AnimationRunner;
import biuoop.GUI;
import biuoop.KeyboardSensor;
import game.GameFlow;
import game.LevelInformation;
import table.HighScoresTable;

import java.util.List;

/**
 * The type Run game task.
 *
 * @param <T> the type parameter
 */
public class RunGameTask<T> implements Task<T> {
    private List<LevelInformation> levels;
    private GameFlow gameFlow;
    private AnimationRunner run;
    private KeyboardSensor keyboardSensor;
    private int numOfLives;
    private GUI gui;
    private HighScoresTable table;

    /**
     * Instantiates a new Run game task.
     *
     * @param runner                   the runner
     * @param keyboard                 the keyboard
     * @param numOfLivesInTheBeginning the num of lives in the beginning
     * @param g                        the g
     * @param list                     the list
     * @param t                        the t
     */
    public RunGameTask(AnimationRunner runner, KeyboardSensor keyboard,
                       int numOfLivesInTheBeginning, GUI g, List<LevelInformation> list, HighScoresTable t) {
        this.levels = list;
        this.run = runner;
        this.keyboardSensor = keyboard;
        this.numOfLives = numOfLivesInTheBeginning;
        this.gui = g;
        this.table = t;
        this.gameFlow = new GameFlow(run, keyboardSensor, numOfLives, gui, table);
    }

    /**
     * run game.
     * create new game flow and run the levels.
     *
     * @return null
     */
    public T run() {
        this.gameFlow = new GameFlow(run, keyboardSensor, numOfLives, gui, table);
        this.gameFlow.runLevels(this.levels);
        return null;
    }

}
