package sprites;

import collidables.Block;
import collidables.HitListener;
import game.Counter;
import game.GameLevel;

/**
 * The type Ball remover.
 */
public class BallRemover implements HitListener {
    private GameLevel game;
    private Counter remainingBalls;

    /**
     * Instantiates a new Ball remover.
     *
     * @param game         the game
     * @param removedBalls the removed balls
     */
    public BallRemover(GameLevel game, Counter removedBalls) {
        this.game = game;
        this.remainingBalls = removedBalls;
    }


    /**
     * remove ball if it hit the "death block".
     *
     * @param beingHit the being hit
     * @param hitter   the hitter
     */
    public void hitEvent(Block beingHit, Ball hitter) {
        hitter.removeFromGame(this.game);
        this.game.removeBall(hitter);
        this.remainingBalls.decrease(1); // update the balls counter
    }
}
