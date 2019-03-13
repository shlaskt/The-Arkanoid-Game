package menu;

import animation.AnimationRunner;
import biuoop.GUI;
import biuoop.KeyboardSensor;
import io.IoUtils;
import io.LevelSpecificationReader;
import game.LevelInformation;
import table.HighScoresTable;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

/**
 * The type Create sub menu.
 */
public class CreateSubMenu {
    private String fileName;
    private Menu<Task<Void>> subMenu;

    /**
     * Instantiates a new Create sub menu.
     *
     * @param pathName the path name
     */
    public CreateSubMenu(String pathName) {
        this.fileName = pathName;
    }


    /**
     * Create menu.
     *
     * @param runner                   the runner
     * @param keyboard                 the keyboard
     * @param numOfLivesInTheBeginning the num of lives in the beginning
     * @param g                        the g
     * @param t                        the t
     */
    public void createMenu(AnimationRunner runner, KeyboardSensor keyboard,
                           int numOfLivesInTheBeginning, GUI g, HighScoresTable t) {
        IoUtils utils = new IoUtils();

        // create sub menu
        InputStreamReader reader;
        try {
            InputStream ireader = ClassLoader.getSystemClassLoader().getResourceAsStream(fileName);
            reader = new InputStreamReader(ireader);
        } catch (Exception e) {
            throw new RuntimeException("cannot open file" + e);
        }
        List<String> fileToString = utils.splitFileToLines(reader); // create string from file

        this.subMenu = new MenuAnimation(keyboard, runner); // create new SubMenu

        for (int i = 0; i < fileToString.size(); i++) {
            String[] parts = fileToString.get(i).split(":");
            if (parts.length != 2) {
                throw new RuntimeException("Invalid input - more than 2 args in key:val - " + fileToString.get(i));
            }
            String key = parts[0];
            String name = parts[1];
            // now we have the key & name
            i++; // go to the next line
            String path = fileToString.get(i);
            // now we have all the three - key, name and path

            LevelSpecificationReader levelSpecificationReader = new LevelSpecificationReader();

            InputStreamReader subReader;
            try {
                InputStream ireader = ClassLoader.getSystemClassLoader().getResourceAsStream(path);
                subReader = new InputStreamReader(ireader);
            } catch (Exception e) {
                throw new RuntimeException("cannot open file" + e);
            }

            List<LevelInformation> levels = levelSpecificationReader.fromReader(subReader);

            RunGameTask runGameTask = new RunGameTask(runner, keyboard, numOfLivesInTheBeginning, g, levels, t);
            subMenu.addSelection(key, name, runGameTask);
        }

    }

    /**
     * Gets sub menu.
     *
     * @return the sub menu
     */
    public Menu<Task<Void>> getSubMenu() {
        return subMenu;
    }
} // end of class
