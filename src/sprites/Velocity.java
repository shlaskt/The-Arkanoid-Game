package sprites;

import geometry.Point;

/**
 * sprites.Velocity specifies the change in position on the `x` and the `y` axes.
 */
public class Velocity {
    private double dx;
    private double dy;

    /**
     * constructor.
     *
     * @param dx dx
     * @param dy dy
     */
    public Velocity(double dx, double dy) {
        this.dx = dx;
        this.dy = dy;
    }

    /**
     * change-convert from angle and speed into dx, dy and return the velocity.
     *
     * @param angle the angle of the vector
     * @param speed the size of the vector
     * @return new velocity
     */
    public static Velocity fromAngleAndSpeed(double angle, double speed) {
        double newDx = Math.round((Math.cos(Math.toRadians(angle) - Math.PI / 2) * speed));
        double newDy = Math.round((Math.sin(Math.toRadians(angle) - Math.PI / 2) * speed));
        return new Velocity(newDx, newDy);
    }

    /**
     * get the speed of the ball (the size of the vector).
     * @return speed
     */
    public double getSpeed() {
        // pythagoras
        return Math.sqrt((this.dx * this.dx) + (this.dy * this.dy));
    }


    /**
     * return the dx value.
     *
     * @return dx
     */
    public int getX() {
        return (int) this.dx;
    }

    /**
     * return the dy value.
     *
     * @return dy
     */
    public int getY() {
        return (int) this.dy;
    }


    /**
     * Take a point with position (x,y) and return a new point with position (x+dx, y+dy).
     *
     * @param p point
     * @return new point
     */
    public Point applyToPoint(Point p) {
        double newX = p.getX() + this.dx;
        double newY = p.getY() + this.dy;
        Point newP = new Point(newX, newY);
        return newP;
    }
}