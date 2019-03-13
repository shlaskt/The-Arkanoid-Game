package geometry;

import java.util.List;

/**
 * a line that created from two points/ 4 x&y values.
 * the line has start, middle and end point, the line can check its distance
 * can check if it equal to another line, if it has intersection with another line and where
 */
public class Line {
    // create two points
    private Point pStart;
    private Point pEnd;

    /**
     * First constructor.
     * get 2 points
     *
     * @param start geometry.Point to the start
     * @param end   geometry.Point to the end
     */
    public Line(Point start, Point end) {
        this.pStart = start;
        this.pEnd = end;
    }

    /**
     * Second constructor.
     * get the x&y values of two points
     *
     * @param x1 of the first point
     * @param y1 of the first point
     * @param x2 of the second point
     * @param y2 of the second point
     */
    public Line(double x1, double y1, double x2, double y2) {
        this.pStart = new Point(x1, y1);
        this.pEnd = new Point(x2, y2);
    }

    /**
     * Return the length of the line.
     *
     * @return len
     */
    public double length() {
        return this.pStart.distance(pEnd);
    }


    /**
     * Returns the middle point of the line.
     *
     * @return a point
     */
    public Point middle() {
        double newX = (this.pEnd.getX() + this.pStart.getX()) / 2;
        double newY = (this.pEnd.getY() + this.pStart.getY()) / 2;
        Point pMiddle = new Point(newX, newY);
        return pMiddle;
    }


    /**
     * Returns the start point of the line.
     *
     * @return point
     */
    public Point start() {
        return this.pStart;
    }


    /**
     * Returns the end point of the line.
     *
     * @return point
     */
    public Point end() {
        return this.pEnd;
    }


    /**
     * Checks if the lines intersect.
     *
     * @param other line
     * @return true if the lines intersect, false otherwise
     */
    public boolean isIntersecting(Line other) {
        if (this.intersectionWith(other) == null) {
            return false;
        }
        return true;
    }


    /**
     * Find the intersection of two line segments.
     * used some complex math equations
     *
     * @param other line
     * @return the point if there is, null otherwise
     */

    public Point intersectionWith(Line other) {
        //calculate the denominator.
        double denominator = (other.pEnd.getY() - other.pStart.getY())
                * (this.pEnd.getX() - this.pStart.getX()) - (other.pEnd.getX() - other.pStart.getX())
                * (this.pEnd.getY() - this.pStart.getY());
        // check if the lines are parallel.
        if (denominator == 0) {
            return null;
        }
        //calculate a and b before checking their "behaviour"
        double aFormula = ((other.pEnd.getX() - other.pStart.getX())
                * (this.pStart.getY() - other.pStart.getY()) - (other.pEnd.getY() - other.pStart.getY())
                * (this.pStart.getX() - other.pStart.getX())) / denominator;
        double bFormula = ((this.pEnd.getX() - this.pStart.getX())
                * (this.pStart.getY() - other.pStart.getY()) - (this.pEnd.getY() - this.pStart.getY())
                * (this.pStart.getX() - other.pStart.getX())) / denominator;
        //if both a and b and between 0 and 1 the points intersect.
        if (aFormula >= 0 && aFormula <= 1 && bFormula >= 0 && bFormula <= 1) {
            // returns the intersection point.
            return new Point((int) (this.pStart.getX() + aFormula
                    * (this.pEnd.getX() - this.pStart.getX())), (int) (this.pStart.getY() + aFormula
                    * (this.pEnd.getY() - this.pStart.getY())));
        }
        return null;
    }


    /**
     * Check if two lines equals.
     *
     * @param other line
     * @return true is the lines are equal, false otherwise
     */
    public boolean equals(Line other) {
        if (((this.pStart.equals(other.start())) && (this.pEnd.equals(other.end())))
                || ((this.pStart.equals(other.end())) && (this.pEnd.equals(other.start())))) {
            return true;
        }
        return false;
    }

    /**
     * If this line does not intersect with the rectangle, return null.
     * Otherwise, return the closest intersection point to the start of the line.
     *
     * @param rect the rectangle
     * @return geometry.Point the closest point
     */
    public Point closestIntersectionToStartOfLine(Rectangle rect) {
        List<Point> pointList = rect.intersectionPoints(this); // get the list of intersection point with the line
        if (pointList.isEmpty()) { // if there are no points - return null
            return null;
        }
        // set the smallest value to the distance from the first point in the array
        double smallestDistance = this.pStart.distance(pointList.get(0));
        // set the smallest point to the first point in the array
        Point closerPoint = pointList.get(0);
        // find the smallest distance
        for (int i = 1; i < pointList.size(); i++) {
            if (this.pStart.distance(pointList.get(i)) < smallestDistance) {
                smallestDistance = this.pStart.distance(pointList.get(i)); // set the new smallest distance
                closerPoint = pointList.get(i); // set the new point
            }
        }
        return closerPoint; // return the closer point
    }

}
