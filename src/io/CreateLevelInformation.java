package io;

import collidables.Block;
import game.Constants;
import game.GenericLevel;
import sprites.Velocity;

import java.util.ArrayList;
import java.util.List;

/**
 * The type Create level information.
 */
public class CreateLevelInformation {
    private Constants constants;
    private int numOfBalls;
    private List<Velocity> velocities;
    private int paddleSpe;
    private int paddleWid;
    private String nameLevel;
    private BackgroundIO backgroundSprite;
    private int numbOfBlocksToRemove;
    private BlockProperties blockProperties;
//    private File blockDefinetion;
    private IoUtils ioUtils = new IoUtils();
    private String blockDefinitionsFileName;


    /**
     * Instantiates a new Create level information.
     *
     * @param levelData the level data
     */
    public CreateLevelInformation(NewLevelData levelData) {
        this.nameLevel = levelData.getValue("level_name");
        this.paddleSpe = ioUtils.convertToInt(levelData.getValue("paddle_speed"));
        this.paddleWid = ioUtils.convertToInt(levelData.getValue("paddle_width"));
        this.numbOfBlocksToRemove = ioUtils.convertToInt(levelData.getValue("num_blocks"));
        this.blockProperties = new BlockProperties(
                ioUtils.convertToDouble(levelData.getValue("blocks_start_x")),
                ioUtils.convertToDouble(levelData.getValue("blocks_start_y")),
                ioUtils.convertToDouble(levelData.getValue("row_height")));
        makeListOfVelocities(levelData.getValue("ball_velocities"));
        setNewBackground(levelData.getValue("background"));
        this.numOfBalls = this.velocities.size();
//        this.blockDefinetion = createBlockDefinitions(levelData.getValue("block_definitions"));
        this.blockDefinitionsFileName = levelData.getValue("block_definitions");
    }

    /**
     * Create block definitions file.
     *
     * @param path the path
     * @return the file
     */
//    public File createBlockDefinitions(String path) {
//        File blockDef = new File(path);
//        if (blockDef.exists()) {
//            return blockDef;
//        }
//        throw new RuntimeException("Invalid input - cannot open block definition from- " + path);
//    }


    /**
     * Divide by space list.
     *
     * @param velocitiesString the velocities string
     * @return the list
     */
    public List<String> divideBySpace(String velocitiesString) {
        List<String> velocitiesDxDy = new ArrayList<>();
        if (velocitiesString.contains(" ")) {
            String[] divide = velocitiesString.split(" ");
            for (int i = 0; i < divide.length; i++) {
                velocitiesDxDy.add(divide[i]);
            }
        } else if (velocitiesString.contains(",")) {
            velocitiesDxDy.add(velocitiesString);
        } else {
            throw new RuntimeException("Invalid input - don't contain \',\'");
        }
        return velocitiesDxDy;
    }

    /**
     * Sets velocities.
     *
     * @param stringList the string list
     */
    public void setVelocities(List<String> stringList) {
        // check if divided by comma
        for (String s : stringList) {
            if (s.contains(",")) {
                String[] divideDxDy = s.split(",");
                try {
                    velocities.add(Velocity.fromAngleAndSpeed(Double.parseDouble(divideDxDy[0]),
                            Double.parseDouble(divideDxDy[1])));
                } catch (NumberFormatException e) {
                    throw new RuntimeException("Invalid input - cannot convert velocity of- " + s + e);
                }
            } else {
                throw new RuntimeException("Invalid input-" + s + "don't contain \',\'");
            }
        }
    }

    /**
     * Make list of velocities.
     *
     * @param velocitiesString the velocities string
     */
    public void makeListOfVelocities(String velocitiesString) {
        this.velocities = new ArrayList<>();
        List<String> velocitiesBySpaces = divideBySpace(velocitiesString);
        setVelocities(velocitiesBySpaces); // throw exception if needed
    }


    /**
     * Sets new background.
     *
     * @param backgroundString the background string
     */
    public void setNewBackground(String backgroundString) {
        if (backgroundString.startsWith("color(")) {
            this.backgroundSprite = new BackgroundIO(ioUtils.setColorFromString(backgroundString));
        } else if (backgroundString.startsWith("image(")) {
            this.backgroundSprite = new BackgroundIO(ioUtils.setImageFromString(backgroundString));
        } else {
            throw new RuntimeException("Invalid input-" + backgroundString + "Unable to find valid color / image");
        }
    }

    /**
     * Set blocks list.
     *
     * @param l    the List<String>
     * @param bfsf the BlocksFromSymbolsFactory
     * @return the blocks list
     */
    public List<Block> setBlocksList(List<String> l, BlocksFromSymbolsFactory bfsf) {
        List<String> layout = l;
        BlocksFromSymbolsFactory blocksFromSymbolsFactory = bfsf;

        List<Block> blockList = new ArrayList<>();
        double y = this.blockProperties.getBlocksStartY();
        double deltaY = this.blockProperties.getRowHeight();
        for (String line : layout) {
            double x = this.blockProperties.getBlocksStartX(); // initialize the x in first row
            for (int i = 0; i < line.length(); i++) {
                String symbol = String.valueOf(line.charAt(i));
                if (blocksFromSymbolsFactory.isBlockSymbol(symbol)) { // add block
                    Block block = blocksFromSymbolsFactory.getBlock(symbol, (int) x, (int) y);
                    blockList.add(block);
                    x += block.getCollisionRectangle().getWidth();
                } else if (blocksFromSymbolsFactory.isSpaceSymbol(symbol)) { // add space
                    x += blocksFromSymbolsFactory.getSpaceWidth(symbol);
                } else { // invalid symbol
                    throw new RuntimeException("symbol " + symbol + " isn't valid");
                }
            }
            y += deltaY; // update y
        }
        return blockList;
    }

    /**
     * Gets block definitions file name.
     *
     * @return the block definitions file name
     */
    public String getBlockDefinitionsFileName() {
        return blockDefinitionsFileName;
    }

    /**
     * Initialize level information generic level.
     *
     * @param level  the level
     * @param blocks the blocks
     * @return the generic level
     */
    public GenericLevel initializeLevelInformation(GenericLevel level, List<Block> blocks) {
        level.setNameLevel(this.nameLevel);
        level.setPaddleSpe(this.paddleSpe);
        level.setPaddleWid(this.paddleWid);
        level.setBackgroundSprite(this.backgroundSprite);
        level.setVelocities(this.velocities);
        level.setNumOfBalls(this.numOfBalls);
        level.setNumbOfBlocksToRemove(this.numbOfBlocksToRemove);
        level.setBlocksList(blocks);

        return level;
    }

} // end of class
