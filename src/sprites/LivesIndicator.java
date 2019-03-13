package sprites;

import game.Constants;
import game.Counter;
import game.GameLevel;
import geometry.Point;
import geometry.Rectangle;
import biuoop.DrawSurface;

/**
 * The type Lives indicator.
 */
public class LivesIndicator implements Sprite {
    private Counter lives;
    private Rectangle rectangle;
    private Constants constants;

    /**
     * Instantiates a new Lives indicator.
     *
     * @param upperLeft    the upper left
     * @param width        the width
     * @param height       the height
     * @param livesCounter the livesCounter
     */
    public LivesIndicator(Point upperLeft, double width, double height, Counter livesCounter) {
        this.lives = livesCounter;
        this.rectangle = new geometry.Rectangle(upperLeft, width, height);
    }

    /**
     * draw the sprite to the screen.
     *
     * @param d the DrawSurface
     */
    public void drawOn(DrawSurface d) {
        // set the x, y and distances and convert it into int
        int x1 = (int) this.rectangle.getUpperLeft().getX();
        int y1 = (int) this.rectangle.getUpperLeft().getY();
        int high = (int) (this.rectangle.getDownRight().getY() - this.rectangle.getUpperLeft().getY());

        String livesText = Integer.toString(this.lives.getValue()); // set string to the number in the blocks

        d.setColor(constants.INDICATOR_TEXT_COLOR); // set the color to the text
        // place the text in the middle of the block
        d.drawText(x1 + (30), y1 + ((high / 2) + 5), ("Lives: " + livesText), constants.INDICATOR_TEXT_SIZE);
    }

    /**
     * notify the sprite that time has passed.
     *
     * @param dt the dt
     */

    public void timePassed(double dt) {
        return;
    }

    /**
     * add a sprite to the game.
     *
     * @param g the game
     */
    public void addToGame(GameLevel g) {
        g.addSprite(this);
    }
}

