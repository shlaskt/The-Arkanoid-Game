package animation;

import biuoop.DrawSurface;
import game.Constants;

import java.awt.Color;


/**
 * The type Pause screen.
 */
public class PauseScreen implements Animation {
    private Constants constants = new Constants();

    /**
     * Instantiates a new Pause screen.
     */
    public PauseScreen() {
    }

    /**
     * stop the screen until the player press the space key.
     *
     * @param d  the DrawSurface
     * @param dt dt
     */
    public void doOneFrame(DrawSurface d, double dt) {
        // draw background
        d.setColor(Color.cyan.darker().darker());
        d.fillRectangle(constants.X_START_FRAME, constants.Y_START_FRAME, d.getWidth(), d.getHeight());
        d.setColor(Color.orange);

        d.drawText(10, d.getHeight() / 2, "Paused - press space to continue", 45);
    }

    /**
     * check if the user press the space key.
     *
     * @return if should stop
     */
    public boolean shouldStop() {
        return false;
    }
}