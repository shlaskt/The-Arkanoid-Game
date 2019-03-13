package collidables;

import geometry.Point;
import geometry.Rectangle;
import sprites.Ball;
import sprites.Velocity;

/**
 * The collidables.Collidable interface is in used by things that can be collided with.
 * for now - blocks and paddle.
 */
public interface Collidable {

    /**
     * Return the "collision shape" of the object.
     *
     * @return a rectangle - the block/paddle
     */
    Rectangle getCollisionRectangle();

    /**
     * Notify the object that we collided with it at collisionPoint with a given velocity.
     * The return is the new velocity expected after the hit (based on
     * the force the object inflicted on us).
     *
     * @param hitter          the ball that hit the block
     * @param collisionPoint  the collision point (x,y)
     * @param currentVelocity the current velocity (dx, dy)
     * @return the new velocity expected after the hit
     */
    Velocity hit(Ball hitter, Point collisionPoint, Velocity currentVelocity);
}
