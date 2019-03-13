package sprites;

import collidables.Collidable;
import collidables.CollisionInfo;
import collidables.Paddle;
import game.Constants;
import game.GameEnvironment;
import game.GameLevel;
import geometry.Line;
import geometry.Point;
import geometry.Rectangle;
import biuoop.DrawSurface;

import java.util.List;

/**
 * a ball class.
 * ball has- center point (x,y), radius, color and velocity.
 */
public class Ball implements Sprite {
    private Point center; //the center of the ball
    private int radius; // the radius
    private java.awt.Color color; // the color
    private Velocity velocity; // the speed and motion
    private GameEnvironment gameEnvironment;
    private Paddle paddle;
    private Constants constants;
    private boolean isChangdedDt;

    /**
     * constructor.
     * has a default velocity that the user can change with "set velocity" method
     *
     * @param center   point
     * @param r        radius
     * @param color    color
     * @param velocity the velocity
     */
    public Ball(Point center, int r, java.awt.Color color, Velocity velocity) {
        this.center = center;
        this.radius = r;
        this.color = color;
        this.constants = new Constants(); // for all the constants
        // a default velocity
        this.velocity = velocity;
        this.isChangdedDt = false;
    }

    // accessors

    /**
     * return the x coordinate of the ball's circle point.
     *
     * @return x x
     */
    public int getX() {
        return (int) this.center.getX();
    }

    /**
     * return the y coordinate of the ball's circle point.
     *
     * @return y y
     */
    public int getY() {
        return (int) this.center.getY();
    }

    /**
     * return the ball's radius.
     *
     * @return radius size
     */
    public int getSize() {
        return this.radius;
    }

    /**
     * return the ball's color.
     *
     * @return color color
     */
    public java.awt.Color getColor() {
        return this.color;
    }

    /**
     * set the excise paddle to tha ball.
     *
     * @param p paddle
     */
    public void setPaddle(Paddle p) {
        this.paddle = p;
    }

    /**
     * draw the ball on the given DrawSurface.
     *
     * @param surface to draw on
     */
    public void drawOn(DrawSurface surface) {
        // set the inside color of the ball
        surface.setColor(this.getColor());
        surface.fillCircle(this.getX(), this.getY(), this.getSize());
        // set frame in ball - default
        surface.setColor(constants.FRAMES_COLOR);
        surface.drawCircle(this.getX(), this.getY(), this.getSize());
    }

    /**
     * get velocity and set it to the ball.
     *
     * @param v velocity
     */
    public void setVelocity(Velocity v) {
        this.velocity = v;
    }

    /**
     * get velocity - dx and dy, and set it to the ball.
     *
     * @param dx how many to move in the x
     * @param dy how many to move in the y
     */
    public void setVelocity(double dx, double dy) {
        this.velocity = new Velocity(dx, dy);
    }

    /**
     * return the ball's velocity.
     *
     * @return velocity velocity
     */
    public Velocity getVelocity() {
        return this.velocity;
    }


    /**
     * set the excise game environment to the ball.
     *
     * @param g the game environment
     */
    public void setGameEnvironment(GameEnvironment g) {
        this.gameEnvironment = g;
    }

    /**
     * get the excise game environment to the ball.
     *
     * @return the game environment
     */
    public GameEnvironment getGameEnvironment() {
        return this.gameEnvironment;
    }

    /**
     * let the ball know that the time passed.
     * <p>
     * call 'move one step' method.
     *
     * @param dt the dt
     */
    public void timePassed(double dt) {
        this.moveOneStep(dt);
    }

    /**
     * add the ball to the game.
     * add the ball to the sprites's list
     *
     * @param g the game
     */
    public void addToGame(GameLevel g) {
        g.addSprite(this);
    }

    /**
     * change the center to just a little before the collision point.
     *
     * @param collisionInfo information about the collision - point and rectangle (block / paddle ect.)
     * @return the new ball's center
     */
    public Point makeNewCenter(CollisionInfo collisionInfo) {
        Point collPoint = collisionInfo.collisionPoint();
        Point newCenter = collPoint; // default
        int r = this.getSize();
        // check where in the rectangle you hit
        String where = collisionInfo.collisionObject().getCollisionRectangle().whereYouHit(collPoint);
        if (where.equals("Left")) {
            newCenter = new Point(collPoint.getX() - r, collPoint.getY()); // one radius before (less) the hit
        } else if (where.equals("Right")) {
            newCenter = new Point(collPoint.getX() + r, collPoint.getY()); // one radius before (more) the hit
        } else if (where.equals("Up")) {
            newCenter = new Point(collPoint.getX(), collPoint.getY() - r); // one radius before (less) the hit
        } else if (where.equals("Down")) {
            newCenter = new Point(collPoint.getX(), collPoint.getY() + r); // one radius before (more) the hit
        }
        return newCenter; // return the new ball's center
    }

    /**
     * take the ball out of a given rectangle.
     * this method called if the ball goes into the rectangle - and take it out to the closest point
     *
     * @param rect a given rectangle
     */
    public void takeTheBallOutOfTheRect(Rectangle rect) {
        if ((this.getVelocity().getX() < 0)) { // negative dx - the ball came from the right
            // change is center to one radius from the right of the paddle
            this.center = new Point(rect.getDownRight().getX() + this.getSize(), this.center.getY());
        } else if ((this.getVelocity().getX() > 0)) { // positive dx - the ball came from the left
            // change is center to one radius from the left of the paddle
            this.center = new Point(rect.getUpperLeft().getX() - this.getSize(), this.center.getY());
        }
        // set the velocity to -dx (to make it go back from where it came)
        Velocity newVel = new Velocity(this.getVelocity().getX() * -1, this.getVelocity().getY());
        this.setVelocity(newVel);
    }

    /**
     * check if the ball is inside the paddle.
     * if the ball inside the paddle - and not came from the top of the paddle - return true
     * if it is from the top - get it in "move one step" like every collidable
     *
     * @param rect paddle
     * @return true if it inside paddle (from left/right), false otherwise
     */
    public boolean isTheBallInsidePaddle(Rectangle rect) {
        // set tae  point in to steps from your location - (next next)
        Point next = this.velocity.applyToPoint(this.center);
        Point nextNext = this.getVelocity().applyToPoint(next);
        // if the nextNext is in the paddle
        if ((nextNext.getX() > rect.getUpperLeft().getX())
                && (nextNext.getX() < rect.getDownRight().getX())
                && (nextNext.getY() > rect.getUpperLeft().getY())
                && (nextNext.getY() < rect.getDownRight().getY())) {
            // check where hit will hit
            // set line from the exist location to 3*vector
            Line curTrajectory = new Line(this.center,
                    new Point(this.getVelocity().applyToPoint(nextNext).getX(),
                            this.getVelocity().applyToPoint(nextNext).getY()));
            CollisionInfo collisionInfo = this.gameEnvironment.getClosestCollision(curTrajectory);
            if (collisionInfo == null) {
                return false; // if it not find any
            }
            String where;
            where = collisionInfo.collisionObject().getCollisionRectangle().whereYouHit(collisionInfo.collisionPoint());
            // if it is from the top - get it in "move one step" like every collidable
            if (where.equals("Up")) {
                return false;
            }
            return true;
        }
        return false;
    }


    /**
     * check if the ball is inside a collidable (block / paddle / limits).
     * if it is - take it out to the closest point (with another method)
     */
    public void ifTheBallIsInsideColliudibleTakeItOut() {
        List<Collidable> colList = this.gameEnvironment.getCollidables();
        for (int i = 0; i < colList.size(); i++) { // for every collidable in the list
            Rectangle rect = colList.get(i).getCollisionRectangle(); // name the collidable 'rect'
            // if it inside all the limits (- inside the rectangle)
            if ((this.center.getX() > rect.getUpperLeft().getX())
                    && (this.center.getX() < rect.getDownRight().getX())
                    && (this.center.getY() > rect.getUpperLeft().getY())
                    && (this.center.getY() < rect.getDownRight().getY())) {
                takeTheBallOutOfTheRect(rect); // take it out
            }
        }
        return;
    }

    /**
     * move the ball one step every time.
     * keep it in the frame and check if it touch one of it's limits, if it is- change it velocity
     * if it touched one of the blocks - chang it velocity
     * if it take out of the frame - take it back (for now)
     * if it go inside block /  paddle - take it out
     *
     * @param dt the dt
     */
    public void moveOneStep(double dt) {
        // check if it's the first time - if it is changet the velocity with the dt
        if (!isChangdedDt) {
            Velocity vel = new Velocity(this.velocity.getX() * dt, this.velocity.getY() * dt);
            this.setVelocity(vel);
            this.isChangdedDt = true;
        }

        // ifTheBallOutOfBoundsGetItIn(); // if the ball is out of the frame- put it in random place in the game limits
        if (isTheBallInsidePaddle(this.paddle.getRect())) { // if the ball is inside the paddle in his next step
            takeTheBallOutOfTheRect(this.paddle.getRect()); /// take it out
        }

        // check the near collision point
        Line curTrajectory = new Line(this.center, this.getVelocity().applyToPoint(this.center));
        CollisionInfo collisionInfo = this.gameEnvironment.getClosestCollision(curTrajectory);

        if (collisionInfo.collisionPoint() != null) { // if there is a collision
            // change the center to just a little before the collision
            Point newCenter = makeNewCenter(collisionInfo);
            // check if the bew center is in a collidable
            if (!gameEnvironment.isPointInAnyCollidable(newCenter)) {
                this.center = newCenter;
            } else { // if it is - take it out
                ifTheBallIsInsideColliudibleTakeItOut();
            }
            //set the new velocity after you change in order to the collision of the ball with the object
            Velocity newVel = collisionInfo.collisionObject().hit(this,
                    collisionInfo.collisionPoint(), this.getVelocity());
            this.setVelocity(newVel);

        } else { // no collision point
            // move one step in your velocity
            this.center = this.getVelocity().applyToPoint(this.center);
            ifTheBallIsInsideColliudibleTakeItOut();
        }


    }

    /**
     * Remove from game.
     *
     * @param g the GameLevel
     */
    public void removeFromGame(GameLevel g) {
        g.removeSprite(this);
    }


}