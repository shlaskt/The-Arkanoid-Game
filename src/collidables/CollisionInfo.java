package collidables;

import geometry.Point;

/**
 * the collisionInfo has the information about the hit - the exact point of collision.
 * and the 'shape'of the rectangle it hit
 */
public class CollisionInfo {
    // members
    private Point theCollisionPoint;
    private Collidable theCollisionObject;

    /**
     * constructor.
     *
     * @param p the collision geometry.Point
     * @param c the interface - collidables.Collidable
     */
    public CollisionInfo(Point p, Collidable c) {
        this.theCollisionPoint = p;
        this.theCollisionObject = c;
    }

    /**
     * the point at which the collision occurs.
     *
     * @return the collision geometry.Point
     */
    public Point collisionPoint() {
        return this.theCollisionPoint;
    }

    /**
     * the collidable object involved in the collision..
     *
     * @return the interface - collidables.Collidable.
     */
    public Collidable collisionObject() {
        return this.theCollisionObject;
    }
}
