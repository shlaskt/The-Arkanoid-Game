package game;

/**
 * The type game.Counter.
 */
public class Counter {
    private int counter;

    /**
     * constructor.
     */
    public Counter() {
        this.counter = 0;
    }

    /**
     * add number to current count..
     *
     * @param number the number
     */
    public void increase(int number) {
        this.counter += number;
    }

    /**
     * subtract number from current count.
     *
     * @param number the number
     */
    public void decrease(int number) {
        this.counter -= number;
    }

    /**
     * get current count..
     *
     * @return the int
     */
    public int getValue() {
        return this.counter;
    }
}