package menu;

/**
 * The interface Task.
 *
 * @param <T> the type parameter
 */
public interface Task<T> {
    /**
     * Run a given task.
     *
     * @return the t
     */
    T run();
}
