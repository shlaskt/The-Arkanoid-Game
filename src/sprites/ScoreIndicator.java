package sprites;

import game.Constants;
import game.Counter;
import game.GameLevel;
import geometry.Point;
import geometry.Rectangle;
import biuoop.DrawSurface;

import java.awt.Color;

/**
 * The type Score indicator.
 */
public class ScoreIndicator implements Sprite {
    private Counter score;
    private geometry.Rectangle rectangle;
    private Color color;
    private Constants constants;
    private String levelName;

    /**
     * Instantiates a new Score indicator.
     *
     * @param upperLeft    the upper left
     * @param width        the width
     * @param height       the height
     * @param c            the Color
     * @param scoreCounter the score counter
     * @param level        the level
     */
    public ScoreIndicator(Point upperLeft, double width, double height, Color c, Counter scoreCounter, String level) {
        this.score = scoreCounter;
        this.rectangle = new Rectangle(upperLeft, width, height);
        this.color = c;
        this.levelName = level;
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
        int width = (int) (this.rectangle.getDownRight().getX() - this.rectangle.getUpperLeft().getX());
        int high = (int) (this.rectangle.getDownRight().getY() - this.rectangle.getUpperLeft().getY());

        String scoreText = Integer.toString(this.score.getValue()); // set string to the number in the blocks
        d.setColor(this.color); // set the color to the block
        d.fillRectangle(x1, y1, width, high); // fill the block
        d.setColor(constants.FRAMES_COLOR); //set default color to the block's frame
        d.drawRectangle(x1, y1, width, high); // draw the block's frame
        d.setColor(constants.INDICATOR_TEXT_COLOR); // set the color to the text

        d.drawText(x1 + (width / 2 - 30), y1 + (high / 2 + 5),
                ("Score: " + scoreText), constants.INDICATOR_TEXT_SIZE); // place the score text in the middle
        d.drawText(x1 + (width - 220), y1 + (high / 2 + 5),
                ("Level Name: " + levelName), constants.INDICATOR_TEXT_SIZE); // place the level text in the right
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
