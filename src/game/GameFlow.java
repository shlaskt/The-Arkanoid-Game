package game;

import animation.Animation;
import animation.AnimationRunner;
import animation.EndScreen;
import animation.HighScoresAnimation;
import animation.KeyPressStoppableAnimation;
import biuoop.DialogManager;
import biuoop.GUI;
import biuoop.KeyboardSensor;
import table.HighScoresTable;
import table.ScoreInfo;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static game.Constants.FILE_PATH;

/**
 * The type Game flow.
 */
public class GameFlow {
    private game.Constants constants;
    private Counter numberOfLives;
    private Counter score;
    private KeyboardSensor keyboardSensor;
    private AnimationRunner animationRunner;
    private GUI g;
    private HighScoresTable table;

    /**
     * Instantiates a new Game flow.
     *
     * @param ar                       the AnimationRunner
     * @param ks                       the KeyboardSensor
     * @param numOfLivesInTheBeginning the number of lives in the beginning
     * @param gui                      the gui
     * @param t                        the HighScoresTable
     */
    public GameFlow(AnimationRunner ar, KeyboardSensor ks, int numOfLivesInTheBeginning, GUI gui, HighScoresTable t) {
        this.constants = new game.Constants();
        this.keyboardSensor = ks;
        this.animationRunner = ar;
        this.numberOfLives = new Counter();
        this.numberOfLives.increase(numOfLivesInTheBeginning);
        this.score = new Counter();
        this.g = gui;
        this.table = t;
    }


    /**
     * Is player should be on table boolean.
     *
     * @param playerScore the player score
     * @return the boolean
     */
    public boolean isPlayerShouldBeOnTable(int playerScore) {
        // if there is more space
        if (table.getHighScores().size() < table.size()) {
            return true;
        }
        // if the player's score is highest from one scores in the list
        for (ScoreInfo scoreInfo : table.getHighScores()) {
            if ((playerScore > scoreInfo.getScore())) {
                return true;
            }
        }
        return false;
    }

    /**
     * Add player to table.
     *
     * @param gu          the gui
     * @param playerScore the player score
     */
    public void addPlayerToTable(GUI gu, int playerScore) {
        // ask for name
        DialogManager dialog = gu.getDialogManager();
        String name = dialog.showQuestionDialog("Name", "What is your name?", "Anonymous");
        //insert name and score to the table
        table.add(new ScoreInfo(name, playerScore));
    }

    /**
     * Print end screens.
     *
     * @param isWinner the is winner
     */
    public void printEndScreens(boolean isWinner) {
        Animation endScreen = new EndScreen(keyboardSensor, this.score, isWinner);
        Animation pauseableEndScreen = new KeyPressStoppableAnimation(keyboardSensor,
                keyboardSensor.SPACE_KEY, endScreen);
        // run the end screen
        animationRunner.run(pauseableEndScreen);
        int playerScore = this.score.getValue(); // get the score
        // if need to insert player - insert player and update (save) table
        if (isPlayerShouldBeOnTable(playerScore)) {
            addPlayerToTable(g, playerScore);
            try {
                table.save(new File(FILE_PATH));
            } catch (IOException e) {
                System.out.println("cannot save" + e);
            }
        }
        /**
         * check try catch
         */
        // print the table
        Animation highScore = new HighScoresAnimation(table);
        Animation pausableHighScore = new KeyPressStoppableAnimation(keyboardSensor,
                keyboardSensor.SPACE_KEY, highScore);
        animationRunner.run(pausableHighScore);
    }

    /**
     * Run levels.
     *
     * @param levels the levels
     */
    public void runLevels(List<LevelInformation> levels) {
        for (LevelInformation levelInfo : levels) { // for every level in the list

            LevelInformation thisLevel = levelInfo.clone(); //create copy of level info to avoid duplicates
            GameLevel level = new GameLevel(thisLevel, this.keyboardSensor,
                    this.animationRunner, this.numberOfLives, this.score);

            level.initialize(); // initialize level
            // while level has more blocks and player has more lives

            while (level.getNumOfBlocksLeft() > 0 && this.numberOfLives.getValue() != 0) {
                level.playOneTurn();
            }
            // if there is no more lives
            if (this.numberOfLives.getValue() == 0) {
                // print lose massage
                printEndScreens(false);
                return;
            }
        }
        // print win massage
        printEndScreens(true);
    }

}
