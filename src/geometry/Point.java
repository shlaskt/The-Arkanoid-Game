package geometry;

/**
 * a class of a point (x,y).
 * the class has the x and y values, the distance from another point, and can check if the're equals
 */
public class Point {
    private double x;
    private double y;

    /**
     * constructor.
     *
     * @param x double
     * @param y double
     */
    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    /**
     * return the distance of this point to the other point.
     *
     * @param other point
     * @return the distance in double
     */
    public double distance(Point other) {
        double dx = this.x - other.getX();
        double dy = this.y - other.getY();
        return Math.sqrt((dx * dx) + (dy * dy));
    }


    /**
     * return true is the points are equal, false otherwise.
     *
     * @param other point
     * @return true or false
     */
    public boolean equals(Point other) {
        return ((this.x == other.getX()) && (this.y == other.getY()));
    }

    /**
     * return x value of the point.
     *
     * @return x value
     */
    public double getX() {
        return this.x;
    }

    /**
     * return y value of the point.
     *
     * @return y value
     */
    public double getY() {
        return this.y;
    }

    /**
     * toString - print the point in format "point - (x, y)".
     * @return string to print
     */
    public String toString() {
        String x1 = String.valueOf(this.getX());
        String y1 = String.valueOf(this.getY());
        String printMe =  ("point - (" + x1 + "," + y1 + ")");
        return printMe;
    }

}
