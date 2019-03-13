package animation;

import game.Constants;
import sprites.SpriteCollection;
import biuoop.DrawSurface;
import biuoop.Sleeper;

import java.awt.Color;

/**
 * The type Countdown animation.
 */
public class CountdownAnimation implements Animation {
    private double numOfSec;
    private int countFromN;
    private int originalCountFrom;
    private SpriteCollection theGameScreen;
    private boolean stop;
    private Constants constants;

    /**
     * Instantiates a new Countdown animation.
     *
     * @param numOfSeconds the num of seconds
     * @param countFrom    the count from
     * @param gameScreen   the game screen
     */
    public CountdownAnimation(double numOfSeconds, int countFrom, SpriteCollection gameScreen) {
        this.numOfSec = numOfSeconds;
        this.countFromN = countFrom;
        this.originalCountFrom = countFrom;
        this.theGameScreen = gameScreen;
        this.stop = false;
    }

    /**
     * an on-screen countdown from countFrom to 1, which will show up at the beginning of each turn.
     * Only after the countdown reaches zero, things will start moving and we will start with the game play.
     * each number will appear on the screen for (numOfSeconds / countFrom) seconds,
     * before it is replaced with the next one.
     *
     * @param d the DrawSurface to draw also
     * @param dt the td
     */
    public void doOneFrame(DrawSurface d, double dt) {
        Sleeper sleeper = new Sleeper();
        double timeLeft = numOfSec * 1000 / (originalCountFrom); // calculate the time for the sleeper in milisec
        this.theGameScreen.drawAllOn(d); // draw all others objects
        String toPrint = Integer.toString(countFromN);
        int width = d.getWidth() / 2 - constants.DELTA_TEXT;
        if (this.countFromN == 0) { // if you finish counting
            toPrint = "Go!";
            width -= constants.TEXT_SIZE / 2;
        }
        d.setColor(Color.darkGray); // set the color of the back-text
        d.drawText(width, d.getHeight() / 2, toPrint,
                constants.TEXT_SIZE + 10);
        d.setColor(Color.cyan); // set the color of the text
        d.drawText(width, d.getHeight() / 2, toPrint,
                constants.TEXT_SIZE);

        sleeper.sleepFor((long) timeLeft); // stop for the given time

        if (this.countFromN < 0) { // if you finish counting
            this.stop = true; //chang the stop to true
        }
        // decrease the count in 1
        this.countFromN--;
    }

    /**
     * return this.stop.
     *
     * @return true if it should stop, false otherwise
     */
    public boolean shouldStop() {
        return this.stop;
    }
}