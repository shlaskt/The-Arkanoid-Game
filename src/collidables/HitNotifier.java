package collidables;

/**
 * The interface Hit notifier.
 */
public interface HitNotifier {
    /**
     * Add a hit listener as a listener to hit events..
     *
     * @param hl the hit listener
     */
    void addHitListener(HitListener hl);

    /**
     * Remove a hit listener from the list of listeners to hit events..
     *
     * @param hl the hit listener
     */
    void removeHitListener(HitListener hl);
}
