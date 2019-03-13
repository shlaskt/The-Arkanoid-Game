package table;

import java.io.Serializable;

/**
 * The type Score info.
 */
public class ScoreInfo implements Serializable {
    private String playerName;
    private int playerScore;

    /**
     * Instantiates a new Score info.
     *
     * @param name  the name
     * @param score the score
     */
    public ScoreInfo(String name, int score) {
        this.playerName = name;
        this.playerScore = score;
    }

    /**
     * Gets name.
     *
     * @return the name
     */
    public String getName() {
        return this.playerName;
    }

    /**
     * Gets score.
     *
     * @return the score
     */
    public int getScore() {
        return this.playerScore;
    }
}
