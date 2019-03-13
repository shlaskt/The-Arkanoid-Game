package collidables;

import game.Constants;
import game.GameLevel;
import geometry.Line;
import geometry.Point;
import geometry.Rectangle;
import sprites.Ball;
import sprites.Sprite;
import sprites.Velocity;
import biuoop.DrawSurface;
import biuoop.KeyboardSensor;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

/**
 * A game paddle's class.
 * paddle has to implements - colidable and sprite
 * it has size, color and sensitive paddle (5 parts and angles)
 */
public class Paddle implements Sprite, Collidable {
    private Rectangle paddle;
    private int speed;
    private Color color;
    private KeyboardSensor keyboard;
    private Constants constants;
    private double paddleWidth;

    /**
     * constructor.
     * with point to start from
     *
     * @param pStart point to start from
     * @param width  paddle's width
     * @param height paddle's height
     * @param c      paddle's color
     * @param sp     the paddle speed
     */
    public Paddle(Point pStart, double width, double height, Color c, int sp) {
        this.paddleWidth = width;
        this.paddle = new Rectangle(pStart, width, height);
        this.color = c;
        this.speed = sp;
        this.constants = new Constants();
    }

    /**
     * constructor.
     * creates the paddle in the bottom-middle
     * by calculate the game limits and size
     *
     * @param width    paddle's width
     * @param height   paddle's height
     * @param c        paddle's color
     * @param sp       the paddle speed
     * @param keyboard the keyboard
     */
    public Paddle(double width, double height, Color c, int sp, KeyboardSensor keyboard) {
        this.constants = new Constants();
        this.paddleWidth = width;
        this.speed = sp;
        int newX = (int) (constants.WIDTH / 2 - width / 2);
        // set the point of the start of the paddle - in the center above the bottom limit
        Point paddleStarPoint = new Point(newX,
                constants.HEIGHT - constants.LIM_SIZE - height);
        this.paddle = new Rectangle(paddleStarPoint, width, height);
        this.color = c;
        this.keyboard = keyboard;
    }

    /**
     * get the rectangle - paddle.
     * for the ball (move one step)
     *
     * @return paddle (rectangle)
     */
    public Rectangle getRect() {
        return this.paddle;
    }

    /**
     * move left.
     * move X steps (const) to the left by create new paddle in the new place
     *
     * @param dt the dt
     */
    public void moveLeft(double dt) {
        //set new paddle in Negative offset - to the left
        this.paddle.setUpperLeftX(-this.speed, dt);
    }

    /**
     * move right.
     * move X steps (const) to the right by create new paddle in the new place
     *
     * @param dt the dt
     */
    public void moveRight(double dt) {
        this.paddle.setUpperLeftX(this.speed, dt);
    }

    /**
     * let the paddle know the time passed.
     * move the paddle in order to the user's choice
     *
     * @param dt the dt
     */
    public void timePassed(double dt) {
        // if the user chose left
        if ((keyboard.isPressed(KeyboardSensor.LEFT_KEY)) || (keyboard.isPressed("a"))) {
            this.moveLeft(dt);
        } else if ((keyboard.isPressed(KeyboardSensor.RIGHT_KEY)) || (keyboard.isPressed("d"))) {
            // if the user chose right
            this.moveRight(dt);
        }

        // if you in the edge of the game's limits
        if (this.paddle.getDownRight().getX() > constants.WIDTH - constants.LIM_SIZE) {
            this.getRect().setNewRec(constants.WIDTH - (constants.LIM_SIZE + this.paddleWidth));
            // if you in the edge of the game's limits
        } else if (this.paddle.getUpperLeft().getX() < constants.LIM_SIZE) {
            this.getRect().setNewRec(constants.LIM_SIZE);
        }
    }

    /**
     * get a paddle color.
     *
     * @return color color
     */
    public Color getColor() {
        return this.color;
    }

    /**
     * draw the paddle on a given surface.
     *
     * @param d to draw on
     */
    public void drawOn(DrawSurface d) {
        // set the x, y and distances and convert it into int
        int x1 = (int) this.paddle.getUpperLeft().getX();
        int y1 = (int) this.paddle.getUpperLeft().getY();
        int width = (int) (this.paddle.getDownRight().getX() - this.paddle.getUpperLeft().getX());
        int high = (int) (this.paddle.getDownRight().getY() - this.paddle.getUpperLeft().getY());

        d.setColor(this.getColor()); // set the color to the block
        d.fillRectangle(x1, y1, width, high); // fill the block
        d.setColor(constants.FRAMES_COLOR); //set default color to the paddle's frame
        d.drawRectangle(x1, y1, width, high); // draw the block's frame
    }

    // collidables.Collidable

    /**
     * get the collision rectangle.
     * to know what the ball collision with
     *
     * @return paddle
     */
    public Rectangle getCollisionRectangle() {
        return this.paddle;
    }

    /**
     * separate the paddle to segments.
     * create list of lines with the (x,y) points of start and end
     *
     * @return list of lines-segments
     */
    public List<Line> separateThePaddle() {
        List<Line> paddleSeparateLines = new ArrayList<>();
        // set the number of how much to move
        double whereToSep = this.paddleWidth / constants.SEPARATE_PADDLE_TO_SEGMENTS;
        double y = this.paddle.getUpperLeft().getY(); // set the const y
        // set the first line
        Line l1 = new Line(this.paddle.getUpperLeft(), new Point(this.paddle.getUpperLeft().getX() + whereToSep, y));
        // add the first line to the list
        paddleSeparateLines.add(l1);
        for (int i = 1; i < constants.SEPARATE_PADDLE_TO_SEGMENTS; i++) {
            Line l = new Line(l1.end(), new Point(l1.end().getX() + whereToSep, y));
            // add the line to the list
            paddleSeparateLines.add(l);
            // set l1 to be the last line (for the new x of the net line)
            l1 = l;
        }
        return paddleSeparateLines;
    }

    /**
     * return new velocity that depends on the segment in the paddle.
     *
     * @param segment         the specific segment
     * @param currentVelocity the current velocity
     * @return new velocity
     */
    public Velocity changeValBySegment(int segment, Velocity currentVelocity) {
        segment++; // to be synchronized with the number of the lines
        double angle;
        switch (segment) {
            case (1):
                angle = 300;
                break;
            case (2):
                angle = 330;
                break;
            case (3): // if you hit the middle - return velocity like in "normal" block
                return new Velocity(currentVelocity.getX(), -currentVelocity.getY());
            case (4):
                angle = 30;
                break;
            case (5):
                angle = 60;
                break;
            default: // like regular block
                return new Velocity(currentVelocity.getX(), -currentVelocity.getY());

        }
        // set the new velocity
        Velocity newVel = currentVelocity.fromAngleAndSpeed(angle, currentVelocity.getSpeed());
        return newVel;
    }

    /**
     * method when the ball hit the paddle.
     * change the velocity in order to the place in the paddle you hit
     * separate the paddle to 5 segments
     * the behavior of the ball's bounce depends on where it hits the paddle
     *
     * @param hitter          the hiiter ball
     * @param collisionPoint  the collision point (x,y)
     * @param currentVelocity the current velocity (dx, dy)
     * @return new velocity
     */
    public Velocity hit(Ball hitter, Point collisionPoint, Velocity currentVelocity) {
        Point upperLeft = this.paddle.getUpperLeft();
        Point downRight = this.paddle.getDownRight();
        double newDx = currentVelocity.getX();
        double newDy = currentVelocity.getY();

        // check if the ball hit the paddle from top in one of the paddle's parts
        List<Line> paddleSeparateLines = separateThePaddle(); // create list of segments
        for (int i = 0; i < paddleSeparateLines.size(); i++) {
            // if the ball hit in one of the segments of the paddle (from the top)
            if ((collisionPoint.getX() >= paddleSeparateLines.get(i).start().getX())
                    && (collisionPoint.getX() <= paddleSeparateLines.get(i).end().getX())
                    && collisionPoint.getY() == upperLeft.getY()) {
                // change the velocity
                Velocity newVel = changeValBySegment(i, currentVelocity);
                return newVel;
            }
        }
        // check if the ball hit the paddle from the sides
        // if you hit the left or right limit - change the dx
        if ((collisionPoint.getX() >= upperLeft.getX()) || (collisionPoint.getX() <= downRight.getX())) {
            if ((collisionPoint.getY() >= upperLeft.getY()) && (collisionPoint.getY() <= downRight.getY())) {
                newDx = -newDx;
                return new Velocity(newDx, newDy);
            }
        }
        // otherwise - return the current velocity
        return currentVelocity;
    }


    /**
     * Add this paddle to the game.
     *
     * @param g game
     */
    public void addToGame(GameLevel g) {
        g.addSprite(this);
        g.addCollidable(this);
    }

    /**
     * Remove from game.
     *
     * @param game the game
     */
    public void removeFromGame(GameLevel game) {
        game.removeCollidable(this);
        game.removeSprite(this);
    }

}