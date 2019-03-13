package game;

import collidables.Block;
import collidables.HitListener;
import sprites.Ball;

/**
 * The type Score tracking listener.
 */
public class ScoreTrackingListener implements HitListener {
    private Counter currentScore;

    /**
     * Instantiates a new Score tracking listener.
     *
     * @param scoreCounter the score counter
     */
    public ScoreTrackingListener(Counter scoreCounter) {
        this.currentScore = scoreCounter;
    }

    /**
     * an hit event - increase point if needed.
     *
     * @param beingHit the block that being hit
     * @param hitter   the hitter ball
     */
    public void hitEvent(Block beingHit, Ball hitter) {
        if (beingHit.getHitPoints() > 0) { // if there us more hit-points in the block - increase 5 points
            this.currentScore.increase(5);
        } else if (beingHit.getHitPoints() == 0) { // if the block "die" - increase 10 points
            this.currentScore.increase(10);
        }
    }
}
