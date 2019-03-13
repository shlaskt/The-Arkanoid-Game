package io;

import biuoop.DrawSurface;
import game.Constants;
import game.GameLevel;
import sprites.Sprite;

import java.awt.Color;
import java.awt.Image;

/**
 * The type Background io.
 */
public class BackgroundIO implements Sprite {
    private Constants constants = new Constants();
    private Color color;
    private Image image;
//    private String imagePath;

    /**
     * Instantiates a new Background io.
     *
     * @param i the
     */
    public BackgroundIO(Image i) {
        this.image = i;
        this.color = null;
    }

    /**
     * Instantiates a new Background io.
     *
     * @param c the c
     */
    public BackgroundIO(Color c) {
        this.color = c;
//        this.imagePath = null;
        this.image = null;
    }


    @Override
    public void drawOn(DrawSurface d) {
        if (this.color == null) { // draw image
//            Image img = null;
//            try {
//                img = ImageIO.read(new File(imagePath));
//            } catch (IOException e) {
//                System.out.println("cannot draw Image- " + e);
//            }
            d.drawImage(constants.X_START_FRAME, constants.Y_START_FRAME, this.image);
        } else { // draw color
            d.setColor(color);
            d.fillRectangle(constants.X_START_FRAME, constants.Y_START_FRAME, constants.WIDTH, constants.HEIGHT);
        }
    }

    @Override
    public void timePassed(double dt) {
        return;
    }

    @Override
    public void addToGame(GameLevel g) {
        g.addSprite(this);
    }
}
