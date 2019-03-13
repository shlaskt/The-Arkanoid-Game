package collidables;

import sprites.Ball;

/**
 * The interface Hit listener.
 */
public interface HitListener {
    /**
     * Hit event.
     * This method is called whenever the beingHit object is hit.
     *
     * @param beingHit the being hit
     * @param hitter   the hitter
     */

    void hitEvent(Block beingHit, Ball hitter);
}
