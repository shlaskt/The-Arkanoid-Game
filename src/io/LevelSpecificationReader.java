package io;

import collidables.Block;
import game.GenericLevel;
import game.LevelInformation;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

/**
 * The type Level specification reader.
 */
public class LevelSpecificationReader {
    private IoUtils utils = new IoUtils();


    /**
     * New level level information.
     *
     * @param levelString the level string
     * @return the level information
     */
    public LevelInformation newLevel(List<String> levelString) {
        // create and initialize the map of keys anf values of level
        NewLevelData newLevelMapData = new NewLevelData();
        newLevelMapData.initializeMaps();
        // get to string until "START_BLOCKS"
        List<String> levelWithoutBlocks = getTextUntilBlocks(levelString);
        for (String line : levelWithoutBlocks) {
            utils.setNewValue(line, newLevelMapData.getStringListMap(), newLevelMapData.getIsKeysUpdate());
            // if has problem or values missed - will throw exception
        }
        newLevelMapData.isAllKeysUpdated(); // check if all the keys get values and only once
        CreateLevelInformation createLevelInformation = new CreateLevelInformation(newLevelMapData);

        // ... createLevelInformation sets to itself all the level properties except blockList

        List<String> blocksDataLayout = blocksLayout(levelString); // set list of block layout
        BlocksDefinitionReader blocksDefinitionReader = new BlocksDefinitionReader();
        // get block definition path
        String blockFilePath = createLevelInformation.getBlockDefinitionsFileName();

        // create the file to reader
        InputStreamReader reader;
        try {
            InputStream ireader = ClassLoader.getSystemClassLoader().getResourceAsStream(blockFilePath);
            reader = new InputStreamReader(ireader);
        } catch (Exception e) {
            throw new RuntimeException("cannot open file" + e);
        }

        // set BlocksFromSymbolsFactory with the blocks
        BlocksFromSymbolsFactory blocksFromSymbolsFactory = blocksDefinitionReader.fromReader(reader);

        // add all the blocks to the level information creator list
        List<Block> levelBlocks = createLevelInformation.setBlocksList(blocksDataLayout, blocksFromSymbolsFactory);

        // create generic level info and set all the values of the current level
        GenericLevel levelInformation = new GenericLevel();
        levelInformation = createLevelInformation.initializeLevelInformation(levelInformation, levelBlocks);

        return levelInformation;
    }

    /**
     * Blocks layout list.
     *
     * @param level the level
     * @return the list
     */
    public List<String> blocksLayout(List<String> level) {
        List<String> blocksLevelLayout = new ArrayList<>();
        int i = 0;

        for (; i < level.size(); i++) {
            if (level.get(i).equals("START_BLOCKS")) {
                break;
            }
        }
        if (i == level.size()) { // no "START_BLOCKS"
            throw new RuntimeException("Invalid text - not have with \'START_BLOCKS\'");
        }
        i++; // to skip the START_BLOCKS

        for (; i < level.size(); i++) {
            if (level.get(i).equals("END_BLOCKS")) {
                break;
            }
            blocksLevelLayout.add(level.get(i));
        }
        if (i == level.size()) { // no "END_BLOCKS"
            throw new RuntimeException("Invalid text - not have with \'END_BLOCKS\'");
        }

        return blocksLevelLayout;
    }

    /**
     * Gets text until blocks.
     *
     * @param level the level
     * @return the text until blocks
     */
    public List<String> getTextUntilBlocks(List<String> level) {
        List<String> levelWithoutBlocks = new ArrayList<>();
        int i = 0;
        for (; i < level.size(); i++) {
            if (level.get(i).equals("START_BLOCKS")) { // while line != START_BLOCKS
                break;
            }
            levelWithoutBlocks.add(level.get(i)); // copy to the new list
        }
        if (!level.get(i).equals("START_BLOCKS")) {
            throw new RuntimeException("not have START_BLOCKS" + level.get(i));
        }
        return levelWithoutBlocks;
    }

    /**
     * Split string to levels list.
     *
     * @param input the input
     * @return the list
     */
    public List<List<String>> splitStringToLevels(List<String> input) {
        List<List<String>> listOfListsLevels = new ArrayList<>();

        for (int i = 0; i < input.size(); i++) {
            List<String> dividedList = new ArrayList<>();

            if (!input.get(i).equals("START_LEVEL")) {
                throw new RuntimeException("Invalid text - not start with \'START_LEVEL\' " + input.get(i));
            }
            i++; // to skip the "START_LEVEL"
            while ((!input.get(i).equals("END_LEVEL")) && (i < input.size())) {
                dividedList.add(input.get(i));
                i++;
            }
            if (!input.get(i).equals("END_LEVEL")) {
                throw new RuntimeException("Invalid text - not end with \'END_LEVEL\' " + input.get(i));
            }
            i++; // for the "END_LEVEL"

            listOfListsLevels.add(dividedList); // add the list to the list of lists
            i--; // to check the "START_LEVEL"
        }
        return listOfListsLevels;
    }


    /**
     * From reader list.
     *
     * @param reader the reader
     * @return the list
     */
    public List<LevelInformation> fromReader(java.io.Reader reader) {
        List<String> fileToString = utils.splitFileToLines(reader); // convert to list of rows
        List<List<String>> stringDivideByLevels = splitStringToLevels(fileToString); // divided to list of levels rows
        List<LevelInformation> levelInfoList = new ArrayList<>(); // create list of level information
        for (List<String> level : stringDivideByLevels) {
            levelInfoList.add(newLevel(level)); // create level information and add it to list
        }
        return levelInfoList;
    }

    /**
     * From file list.
     *
     * @param filename the filename
     * @return the list
     */
    public List<LevelInformation> fromFile(String filename) {
        Reader fileReader = null;
        try {
            fileReader = new InputStreamReader(ClassLoader.getSystemClassLoader().getResourceAsStream(filename));
            return fromReader(fileReader);
        } finally {
            if (fileReader != null) {
                try {
                    fileReader.close();
                } catch (IOException ex) {
                    System.out.println("IO Exception thrown while closing stream: " + ex.toString());
                }
            }
        }
    }
}
