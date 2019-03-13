package table;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import static game.Constants.HIGH_SCORE_TABLE_MAX_SIZE;

/**
 * The type High scores table.
 */
public class HighScoresTable implements Serializable {
    private List<ScoreInfo> scoreInfoList;
    private int maxSize;

    /**
     * Create an empty high-scores table with the specified size.
     * The size means that the table holds up to size top scores.
     *
     * @param size the size
     */
//
    // The size means that the table holds up to size top scores.
    public HighScoresTable(int size) {
        this.scoreInfoList = new ArrayList<>();
        this.maxSize = size;
    }

    /**
     * Add a high-score to the table.
     *
     * @param score the score
     */
    public void add(ScoreInfo score) {
        int rank = this.getRank(score.getScore());
        if (rank > this.maxSize) {
            return;
        } else if (this.scoreInfoList.size() == this.maxSize) {
            this.scoreInfoList.remove(this.maxSize - 1);
        }
        this.scoreInfoList.add(rank - 1, score);
    }

    /**
     * return max size of table.
     *
     * @return the int
     */
    public int size() {
        return this.maxSize;
    }

    /**
     * Gets high scores.
     *
     * @return the high scores
     */
// Return the current high scores.
    // The list is sorted such that the highest
    // scores come first.
    public List<ScoreInfo> getHighScores() {
//        this.scoreInfoList.sort(new HighScoreComparator());
        return this.scoreInfoList;
    }

    /**
     * Gets rank.
     *
     * @param score the score
     * @return the rank
     */
// return the rank of the current score: where will it
    // be on the list if added?
    // Rank 1 means the score will be highest on the list.
    // Rank `size` means the score will be lowest.
    // Rank > `size` means the score is too low and will not
    //      be added to the list.
    public int getRank(int score) {
        int i = 0;
        for (; i < this.scoreInfoList.size(); i++) {
            if (score > this.scoreInfoList.get(i).getScore()) {
                return i + 1;
            }
        }
        return i + 1;
    }

    /**
     * Clear.
     */
// Clears the table
    public void clear() {
        this.scoreInfoList.clear();
    }

    /**
     * Load.
     *
     * @param filename the filename
     * @throws IOException the io exception
     */
// Load table data from file.
    // Current table data is cleared.
    public void load(File filename) throws IOException {
        this.clear();
        ObjectInputStream objectInputStream = null;
        HighScoresTable highScoresTable = null;
        try {
            objectInputStream = new ObjectInputStream(
                    new FileInputStream(filename));
            highScoresTable = (HighScoresTable) objectInputStream.readObject();
            this.scoreInfoList = highScoresTable.getHighScores();
        } catch (FileNotFoundException e) { // Can't find file to open
            System.err.println("Unable to find file: " + filename);
            return;
        } catch (ClassNotFoundException e) { // The class in the stream is unknown to the JVM
            System.err.println("Unable to find class for object in file: " + filename);
            return;
        } catch (IOException e) { // Some other problem
            System.err.println("Failed reading object");
            e.printStackTrace(System.err);
            return;
        } finally {
            try {
                if (objectInputStream != null) {
                    objectInputStream.close();
                }
            } catch (IOException e) {
                System.err.println("Failed closing file: " + filename);
            }
        }
    }


    /**
     * Save.
     *
     * @param filename the filename
     * @throws IOException the io exception
     */
// Save table data to the specified file.
    public void save(File filename) throws IOException {
        ObjectOutputStream objectOutputStream = null;
        HighScoresTable highScoresTable = this;
        try {
            objectOutputStream = new ObjectOutputStream(
                    new FileOutputStream(filename));

            objectOutputStream.writeObject(highScoresTable);
        } catch (IOException e) {
            System.err.println("Failed saving object");
            e.printStackTrace(System.err);
        } finally {
            try {
                if (objectOutputStream != null) {
                    objectOutputStream.close();
                }
            } catch (IOException e) {
                System.err.println("Failed closing file: " + filename);
            }
        }
    }


    /**
     * Load from file high scores table.
     *
     * @param filename the filename
     * @return the high scores table
     */
// Read a table from file and return it.
    // If the file does not exist, or there is a problem with
    // reading it, an empty table is returned.
    public static HighScoresTable loadFromFile(File filename) {
        HighScoresTable highScoreTable = new HighScoresTable(HIGH_SCORE_TABLE_MAX_SIZE);
        try {
            highScoreTable.load(filename);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return highScoreTable;
        }
        return highScoreTable;
    }

    /**
     * Print table.
     */
    public void printTable() {
        int i = 1;
        for (ScoreInfo currScore : this.scoreInfoList) {
            System.out.println("RANK(" + i + ") - name: " + currScore.getName() + " score: " + currScore.getScore());
            i++;
        }
    }
}
