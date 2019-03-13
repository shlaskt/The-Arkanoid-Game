package sprites;

import java.util.ArrayList;
import java.util.List;
import biuoop.DrawSurface;

import static game.Constants.FRAMES_PER_SECONDS;

/**
 * sprites.SpriteCollection hold a collection of sprites.
 * can add a sprite to the list and call timePassed() / drawOn(d) on all sprites.
 */
public class SpriteCollection {
    // Members
    private List<Sprite> sprites;

    /**
     * constructor.
     */
    public SpriteCollection() {
        this.sprites = new ArrayList<>();
    }

    /**
     * add a given sprites to the sprites.Sprite Collection.
     *
     * @param s a sprite
     */
    public void addSprite(Sprite s) {
        sprites.add(s);
    }

    /**
     * remove a given sprites from the sprites.Sprite Collection.
     *
     * @param s a sprite
     */
    public void removeSprite(Sprite s) {
        sprites.remove(s);
    }


    /**
     * call timePassed() on all sprites.
     */
    public void notifyAllTimePassed() {
        double dt = (double) 1 / FRAMES_PER_SECONDS;
        if (sprites.isEmpty()) {
            return; // if the list is empty
        }
        for (int i = 0; i < sprites.size(); i++) {
            sprites.get(i).timePassed(dt);
        }
    }

    /**
     * call drawOn(d) on all sprites.
     *
     * @param d a DrawSurface
     */

    public void drawAllOn(DrawSurface d) {
        if (sprites.isEmpty()) {
            return; // if the list is empty
        }
        for (int i = 0; i < sprites.size(); i++) {
            sprites.get(i).drawOn(d);
        }
    }
}
