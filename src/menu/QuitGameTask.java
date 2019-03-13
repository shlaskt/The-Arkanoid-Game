package menu;

/**
 * The type Quit game task.
 */
public class QuitGameTask implements Task<Void> {

    /**
     * Instantiates a new Quit game task.
     */
    public QuitGameTask() {
    }

    /**
     * exit game.
     * @return null
     */
    public Void run() {
        System.exit(0);
        return null;
    }
}
