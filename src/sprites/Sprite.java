package sprites;

import game.GameLevel;
import biuoop.DrawSurface;

/**
 * sprites interface.
 * have all the game objects that can be drawn on the screen.
 * sprites can be drawn on the screen, and can be notified that time has passed.
 */
public interface Sprite {
    /**
     * draw the sprite to the screen.
     *
     * @param d the DrawSurface
     */
    void drawOn(DrawSurface d);

    /**
     * notify the sprite that time has passed.
     *
     * @param dt the dt
     */
    void timePassed(double dt);

    /**
     * add a sprite to the game.
     *
     * @param g the game
     */
    void addToGame(GameLevel g);
}
