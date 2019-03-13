package geometry;

import biuoop.DrawSurface;

import java.awt.Color;
import java.awt.Image;
import java.util.ArrayList;
import java.util.List;

/**
 * a geometry.Rectangle that created from point, width and height
 * the geometry.Rectangle has List of intersection points with the specified line.
 * can check if where a ball hit it - (side)
 */
public class Rectangle {
    private Point upperLeftRec;
    private Point downRightRec;
    private double widthRec;
    private double heightRec;


    /**
     * Create a new rectangle with location, width and height.
     *
     * @param upperLeft location
     * @param width     width
     * @param height    height
     */
    public Rectangle(Point upperLeft, double width, double height) {
        this.upperLeftRec = upperLeft;
        this.widthRec = width;
        this.heightRec = height;
    }


    /**
     * Return a (possibly empty) List of intersection points with the specified line.
     *
     * @param line specified line
     * @return List of intersection points
     */
    public List<Point> intersectionPoints(Line line) {
        // create the other points of the rectangle
        downRightRec = this.getDownRight();
        Point upperRightRec = new Point(this.widthRec + upperLeftRec.getX(), upperLeftRec.getY());
        Point downLeftRec = new Point(upperLeftRec.getX(), this.heightRec + upperLeftRec.getY());
        // create the 4 lines- limits of the rectangle
        Line upperLine = new Line(this.upperLeftRec, upperRightRec);
        Line downLine = new Line(downLeftRec, downRightRec);
        Line leftLine = new Line(this.upperLeftRec, downLeftRec);
        Line rightLine = new Line(upperRightRec, downRightRec);
        // create an array of points
        List<Point> pointList = new ArrayList<>();
        // check if the line is intersection with one of the rectangle's limits
        // if it is - add the intersection point to the list
        if (line.isIntersecting(leftLine)) {
            pointList.add(line.intersectionWith(leftLine));
        }
        if (line.isIntersecting(rightLine)) {
            pointList.add(line.intersectionWith(rightLine));
        }
        if (line.isIntersecting(upperLine)) {
            pointList.add(line.intersectionWith(upperLine));
        }
        if (line.isIntersecting(downLine)) {
            pointList.add(line.intersectionWith(downLine));
        }
        // return the list
        return pointList;
    }

    /**
     * Return the width of the rectangle.
     *
     * @return width width
     */
    public double getWidth() {
        return this.widthRec;
    }

    /**
     * Return the height of the rectangle.
     *
     * @return height height
     */
    public double getHeight() {
        return this.heightRec;
    }

    /**
     * set the upper-left point of the rectangle.
     *
     * @param howToMove how much steps to initialize the new paddle and to what direction
     * @param dt        the dt
     */
    public void setUpperLeftX(int howToMove, double dt) {
        this.upperLeftRec = new Point(this.getUpperLeft().getX() + howToMove * dt, this.upperLeftRec.getY());
    }

    /**
     * Returns the upper-left point of the rectangle.
     *
     * @return upper -left point
     */
    public Point getUpperLeft() {
        return this.upperLeftRec;
    }

    /**
     * Returns the down-right point of the rectangle.
     *
     * @return down -right point
     */
    public Point getDownRight() {
        downRightRec = new Point(this.widthRec + upperLeftRec.getX(), this.heightRec + upperLeftRec.getY());
        return this.downRightRec;
    }

    /**
     * returns the side that the ball hit the rectangle.
     *
     * @param collPoint the hit point
     * @return string - the side
     */
    public String whereYouHit(Point collPoint) {
        if (collPoint == null) {
            return "none";
        }
        if (collPoint.getX() == this.getUpperLeft().getX()) {
            return "Left";
        } else if (collPoint.getX() == this.getDownRight().getX()) {
            return "Right";
        } else if (collPoint.getY() == this.getUpperLeft().getY()) {
            return "Up";
        } else if (collPoint.getY() == this.getDownRight().getY()) {
            return "Down";
        }
        return "none"; // if there is a problem
    }

    /**
     * check if the center of the ball is in the rectangle.
     *
     * @param point ball's center
     * @return boolean - if it is
     */
    public Boolean isPointInsideMe(Point point) {
        return upperLeftRec.getX() <= point.getX() && point.getX() <= upperLeftRec.getX() + getWidth()
                && upperLeftRec.getY() <= point.getY() && point.getY() <= upperLeftRec.getY() + getHeight();
    }

    /**
     * a toString to make the print of the rectangle.
     * print the upper left point and down right point
     *
     * @return the text to print - (x1, y1) (x2, y2)
     */
    public String toString() {
        String x1 = String.valueOf(this.upperLeftRec.getX());
        String y1 = String.valueOf(this.upperLeftRec.getY());
        String x2 = String.valueOf(this.upperLeftRec.getX() + this.widthRec);
        String y2 = String.valueOf(this.upperLeftRec.getY() + this.heightRec);
        String printMe = ("upper left point - (" + x1 + "," + y1 + ")" + " \n "
                + "bottom right point - (" + x2 + "," + y2 + ")");
        return printMe;
    }

    /**
     * Sets heigh.
     *
     * @param heigt the heigt
     */
    public void setHeigh(int heigt) {
        this.heightRec = heigt;
    }

    /**
     * Sets width.
     *
     * @param width the width
     */
    public void setWidth(int width) {
        this.widthRec = width;
    }

    /**
     * Fill rect.
     *
     * @param d the d
     * @param c the c
     */
    public void fillRect(DrawSurface d, Color c) {
        d.setColor(c);
        d.fillRectangle((int) this.upperLeftRec.getX(), (int) this.upperLeftRec.getY(),
                (int) this.widthRec, (int) this.heightRec);
    }

    /**
     * Draw rect.
     *
     * @param d the d
     * @param c the c
     */
    public void drawRect(DrawSurface d, Color c) {
        d.setColor(c);
        d.drawRectangle((int) this.upperLeftRec.getX(), (int) this.upperLeftRec.getY(),
                (int) this.widthRec, (int) this.heightRec);
    }

    /**
     * Draw image rect.
     *
     * @param d the d
     * @param i the
     */
    public void drawImageRect(DrawSurface d, Image i) {
        d.drawImage((int) this.upperLeftRec.getX(), (int) this.upperLeftRec.getY(), i);
    }

    /**
     * Set new rec.
     *
     * @param x the x
     */
    public void setNewRec(double x) {
        this.upperLeftRec = new Point(x, getUpperLeft().getY());
    }

}
