package animation;

import game.Constants;
import biuoop.DrawSurface;
import biuoop.GUI;

/**
 * The Animation's runner.
 */
public class AnimationRunner {
    private Constants constants;
    private GUI gui;
    private int framesPerSecond;

    /**
     * Instantiates a new Animation runner.
     *
     * @param fps frames Per Second
     * @param g   the GUI
     */
    public AnimationRunner(int fps, GUI g) {
        this.gui = g;
        this.framesPerSecond = fps;
    }

    /**
     * Run.
     * run one frame of the given animation every time
     *
     * @param animation the animation
     */
    public void run(Animation animation) {
        double dt = (double) 1 / this.framesPerSecond;

        int millisecondsPerFrame = 1000 / this.framesPerSecond;
        biuoop.Sleeper sleeper = new biuoop.Sleeper();
        while (!animation.shouldStop()) {
            long startTime = System.currentTimeMillis(); // timing
            DrawSurface d = this.gui.getDrawSurface();

            animation.doOneFrame(d, dt);

            this.gui.show(d);
            long usedTime = System.currentTimeMillis() - startTime;
            long milliSecondLeftToSleep = millisecondsPerFrame - usedTime;
            if (milliSecondLeftToSleep > 0) {
                sleeper.sleepFor(milliSecondLeftToSleep);
            }
        }
    }

    /**
     * Gets gui.
     *
     * @return the gui
     */
    public GUI getGui() {
        return gui;
    }
}
