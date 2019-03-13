package collidables;

import decorator.BlockBackgroundDrawer;
import decorator.BlockFillColor;
import game.Constants;
import game.GameLevel;
import geometry.Point;
import geometry.Rectangle;
import sprites.Ball;
import sprites.Sprite;
import sprites.Velocity;
import biuoop.DrawSurface;

import java.awt.Color;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;


/**
 * A game block's class.
 * block has to implements - colidable and sprite
 * it has size, color, number of hits and boolean to know if it got hit
 */
public class Block implements Collidable, Sprite, HitNotifier, Cloneable {
    // members
    private Rectangle block;
    private Color color;
    private boolean isHitten;
    private int numOfHits;
    private Constants constants;
    private List<HitListener> hitListeners;
    private Map<Integer, List<BlockBackgroundDrawer>> drawersMap = new HashMap<>();


    /**
     * first constructor - by rectangle.
     *
     * @param rect get the rectangle and set to the block
     */
    public Block(Rectangle rect) {
        this.block = rect;
        this.isHitten = false; //first time
        this.hitListeners = new ArrayList<HitListener>();
    }

    /**
     * second constructor - by values.
     *
     * @param upperLeft the start point
     * @param width     width from the start point
     * @param height    height from the start point
     * @param c         color
     */
    public Block(Point upperLeft, double width, double height, Color c) {
        this.block = new Rectangle(upperLeft, width, height);
        this.color = c;
        this.isHitten = false; //first time
        this.hitListeners = new ArrayList<HitListener>();
    }

    /**
     * Instantiates a new Block.
     *
     * @param upperLeft the upper left
     * @param width     the width
     * @param height    the height
     * @param numOfHits the num of hits
     * @param c         the c
     */
    public Block(Point upperLeft, double width, double height, int numOfHits, Color c) {
        this.block = new Rectangle(upperLeft, width, height);
        List<BlockBackgroundDrawer> blockBackgroundDrawers = new ArrayList<>();
        blockBackgroundDrawers.add(new BlockFillColor(c));
        this.drawersMap.put(-1, blockBackgroundDrawers);
        this.isHitten = false; //first time
        this.hitListeners = new ArrayList<HitListener>();
    }

    /**
     * Instantiates a new Block with x,y.
     *
     * @param x the x
     * @param y the y
     */
    public Block(double x, double y) {
        this.block = new Rectangle(new Point(x, y), 0, 0);
        this.numOfHits = 1;
        this.isHitten = false; //first time
        this.hitListeners = new ArrayList<HitListener>();
    }


    /**
     * Return the "collision shape" of the object - block.
     *
     * @return the new block (made of rectangle)
     */

    public Rectangle getCollisionRectangle() {
        return this.block;
    }

    /**
     * method when the ball hit the block.
     * change the velocity in order to the limit you hit
     * decrease the number of hits by one
     *
     * @param hitter          the hitter block
     * @param collisionPoint  the point you hit
     * @param currentVelocity the current velocity
     * @return the new velocity- after change the dx, dy, or both
     */
    public Velocity hit(Ball hitter, Point collisionPoint, Velocity currentVelocity) {
        if (!this.isHitten) {
            letTheBlockKnowItHitten(); // let the ball know is hitten
        }

        Point upperLeft = this.block.getUpperLeft();
        Point downRight = this.block.getDownRight();
        double newDx = currentVelocity.getX();
        double newDy = currentVelocity.getY();

        // if you hit the left or right limit - change the dx
        if ((collisionPoint.getX() == upperLeft.getX()) || (collisionPoint.getX() == downRight.getX())) {
            if ((collisionPoint.getY() >= upperLeft.getY()) && (collisionPoint.getY() <= downRight.getY())) {
                newDx = -newDx;
            }
            // if you hit the up or down limit - change the dy
        } else if ((collisionPoint.getY() == upperLeft.getY()) || (collisionPoint.getY() == downRight.getY())) {
            if ((collisionPoint.getX() >= upperLeft.getX()) && (collisionPoint.getX() <= downRight.getX())) {
                newDy = -newDy;
            }
        }

        decreaseNumOfHits(); // decrease the number of hits by 1
        this.notifyHit(hitter);
        // return the new velocity
        return new Velocity(newDx, newDy);
    }

    /**
     * change the "is hitten" member to true.
     */
    public void letTheBlockKnowItHitten() {
        this.isHitten = true;
    }

    /**
     * set the number of hits in the block (in the initialize).
     *
     * @param n number of hits
     */
    public void setNumOfHits(int n) {
        this.numOfHits = n;
    }

    /**
     * decrease the number of hits.
     */
    public void decreaseNumOfHits() {
        if (this.numOfHits <= 0) { // the min value
            return;
        }
        this.numOfHits--; // decrease the number of hits by 1
    }

    /**
     * set color to the block (after initialize).
     *
     * @param c color
     */
    public void setColor(Color c) {
        this.color = c;
    }

    /**
     * get the block's color.
     *
     * @return block 's color
     */
    public Color getColor() {
        return this.color;
    }

    /**
     * get the block's number of hits that can be.
     *
     * @return number of points
     */
    public int getHitPoints() {
        return this.numOfHits;
    }

    /**
     * draw the block on a given surface.
     *
     * @param surface to draw on
     */

    public void drawOn(DrawSurface surface) {
        List<BlockBackgroundDrawer> drawers = null;
        if ((this.drawersMap.containsKey(numOfHits)) && (numOfHits >= 0)) {
            drawers = drawersMap.get(numOfHits);
        } else {
            drawers = drawersMap.get(-1);
        }
        if (drawers != null) {
            for (BlockBackgroundDrawer b : drawers) {
                b.drawBlock(surface, getCollisionRectangle());
            }
        }
    }


    /**
     * let the ball know that the time passed.
     * do nothing
     *
     * @param dt dt
     */
    public void timePassed(double dt) {
        return;
    }

    /**
     * add the block to the game.
     * add the block to the sprites & collidables list
     *
     * @param g the game
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

    /**
     * called whenever a hit() occurs.
     * notifiers all of the registered collidables.HitListener objects
     * by calling their hitEvent method.
     *
     * @param hitter the hitter ball
     */
    // private??
    private void notifyHit(Ball hitter) {
        // Make a copy of the hitListeners before iterating over them.
//        List<collidables.HitListener> listeners = new ArrayList<collidables.HitListener>(this.hitListeners);
        List<HitListener> listeners = new ArrayList<>(this.hitListeners);
//        listeners.addAll(this.hitListeners);
        // Notify all listeners about a hit event:
        for (HitListener hl : listeners) {
            hl.hitEvent(this, hitter);
        }
    }

    /**
     * see in interface collidables.HitNotifier.
     *
     * @param hl the hit listener
     */
    public void addHitListener(HitListener hl) {
        this.hitListeners.add(hl);
    }

    /**
     * see in interface collidables.HitNotifier.
     *
     * @param hl the hit listener
     */
    public void removeHitListener(HitListener hl) {
        this.hitListeners.remove(hl);
    }

    /**
     * Sets heigh.
     *
     * @param heig the heig
     */
    public void setHeigh(int heig) {
        this.getCollisionRectangle().setHeigh(heig);
    }

    /**
     * Sets width.
     *
     * @param widt the widt
     */
    public void setWidth(int widt) {
        this.getCollisionRectangle().setWidth(widt);
    }


    /**
     * Add new drawer.
     *
     * @param numOfHitP the num of hit p
     * @param drawer    the drawer
     */
    public void addNewDrawer(int numOfHitP, BlockBackgroundDrawer drawer) {
        if (drawersMap.containsKey(numOfHitP)) {
            drawersMap.get(numOfHitP).add(drawer);
        } else {
            List<BlockBackgroundDrawer> newDrawersList = new ArrayList<>();
            newDrawersList.add(drawer);
            drawersMap.put(numOfHitP, newDrawersList);
        }
    }

    /**
     * clone block.
     * @return block
     */
    public Block clone() {
        try {
            return (Block) super.clone();
        } catch (CloneNotSupportedException ex) {
            return null;
        }
    }

}
