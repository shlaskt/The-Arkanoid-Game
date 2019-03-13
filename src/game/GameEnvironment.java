package game;

import collidables.Collidable;
import collidables.CollisionInfo;
import geometry.Line;
import geometry.Point;
import sprites.Ball;

import java.util.ArrayList;
import java.util.List;

/**
 * collection of objects a ball can collide with.
 */
public class GameEnvironment {
    // Member - list of collidables
    private List<Collidable> collidables;
    private List<Ball> balls;

    /**
     * constructor.
     */
    public GameEnvironment() {
        this.collidables = new ArrayList<>();
        this.balls = new ArrayList<>();
    }

    /**
     * Remove ball.
     *
     * @param b the ball
     */
    public void removeBall(Ball b) {
        balls.remove(b);
    }

    /**
     * Add ball.
     *
     * @param b the ball
     */
    public void addBall(Ball b) {
        balls.add(b);
    }


    /**
     * remove the given collidable from the environment.
     *
     * @param c collidable
     */
    public void removeCollidable(Collidable c) {
        collidables.remove(c);
    }

    /**
     * add the given collidable to the environment.
     *
     * @param c collidable
     */
    public void addCollidable(Collidable c) {
        collidables.add(c);
    }

    /**
     * check if the line intersect with one of the rectangles
     * If this object will not collide with any of the collidables return null.
     * otherwise, return the information about the closest collision that is going to occur.
     *
     * @param trajectory the line
     * @return collidables.CollisionInfo information about the collision - point and shape
     */
    public CollisionInfo getClosestCollision(Line trajectory) {
        if (collidables.isEmpty()) { // an empty list
            return null;
        }
        int i = 0; // the index in the array
        Point tempPoint; // initialize the temp point to null
        Point closerPoint = null; // for the first "real" (not null) point
        Collidable rectangle = null; // for the first "real" (not null) point
        // go over the array until you find the first intersection point
        for (; i < collidables.size(); i++) {
            closerPoint = trajectory.closestIntersectionToStartOfLine(this.collidables.get(i).getCollisionRectangle());
            rectangle = collidables.get(i); // set the first collidible
            if (closerPoint != null) {
                break; // stop the for loop
            }
        } // end of for loop - now we have the first point

        for (int j = (i + 1); j < collidables.size(); j++) {
            tempPoint = trajectory.closestIntersectionToStartOfLine(this.collidables.get(j).getCollisionRectangle());
            if (tempPoint == null) { // if there is no intersection with that object
                continue; // start the fot loop again with j++
            }
            // if you find point that closer - assign the closer point and the matching object to the closest one
            if (trajectory.start().distance(tempPoint) < trajectory.start().distance(closerPoint)) {
                closerPoint = tempPoint; // return the closer point
                rectangle = collidables.get(j); // return the matching object
            }
        }

        CollisionInfo collisionInfo = new CollisionInfo(closerPoint, rectangle);
        return collisionInfo; // return the information about the collision - point and shape
    }

    /**
     * get a list of collidables.
     *
     * @return collidables - list
     */
    public List<Collidable> getCollidables() {
        return collidables;
    }

    /**
     * check if the center of the ball is in any collidable.
     *
     * @param point ball's center
     * @return boolean if the ball is inside
     */
    public Boolean isPointInAnyCollidable(Point point) {
        for (Collidable collidable : collidables) {
            if (collidable.getCollisionRectangle().isPointInsideMe(point)) {
                return true;
            }
        }
        return false;
    }
}
