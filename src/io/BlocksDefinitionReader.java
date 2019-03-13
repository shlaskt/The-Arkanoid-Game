package io;

import decorator.BlockCreator;
import decorator.DefaultBlockCreator;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The type Blocks definition reader.
 */
public class BlocksDefinitionReader {
    private IoUtils utils = new IoUtils();


    /**
     * Split file to lines list.
     *
     * @param reader the reader
     * @return the list
     */


    /**
     * From file blocks from symbols factory.
     *
     * @param filename the filename
     * @return the blocks from symbols factory
     */
    public BlocksFromSymbolsFactory fromFile(String filename) {
        Reader fileReader = null;
        try {
            fileReader = new FileReader(new File(filename));
            return fromReader(fileReader);
        } catch (IOException e) {
            throw new RuntimeException("cannot load- " + e);
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

    /**
     * From reader blocks from symbols factory.
     *
     * @param reader the reader
     * @return the blocks from symbols factory
     */
    public static BlocksFromSymbolsFactory fromReader(Reader reader) {
        BlocksFromSymbolsFactory blocksFromSymbolsFactory = new BlocksFromSymbolsFactory();
        BlockCreatorFactory blockCreatorFactory = new BlockCreatorFactory();
        IoUtils utils = new IoUtils();

        BlocksDefinitionReader me = new BlocksDefinitionReader();
        // split to lines from the file
        List<String> blockDefString = me.utils.splitFileToLines(reader);

        Map<String, String> temporaryMap = new HashMap<>();
        Map<String, String> mapOfBlock = new HashMap<>();
        Map<String, String> defultMap = new HashMap<>();
        try {
            for (String line : blockDefString) {
                line = line.trim();

                // default
                if (line.startsWith("default")) {
                    line = line.substring("default".length()).trim();
                    defultMap = utils.dividedeLine(line);

                    // bdef
                } else if (line.startsWith("bdef")) {
                    line = line.substring("bdef".length()).trim();
                    temporaryMap.clear();
                    temporaryMap = utils.dividedeLine(line);
                    mapOfBlock.clear();
                    mapOfBlock.putAll(defultMap);
                    mapOfBlock.putAll(temporaryMap);
                    if (mapOfBlock.containsKey("symbol")) {
                        String symbol = mapOfBlock.get("symbol");
                        BlockCreator specBlockCreator = new DefaultBlockCreator();
                        for (Map.Entry<String, String> entry : mapOfBlock.entrySet()) {
                            if (entry.getKey().equals("symbol")) {
                                continue;
                            }
                            specBlockCreator = blockCreatorFactory.blocksFactory(entry, specBlockCreator);
                        }

//                        if (mapOfBlock.containsKey("stroke")) {
//                            Map<String, String> tempStrokeMap = new HashMap<>();
//                            tempStrokeMap.put("stroke", mapOfBlock.get("stroke"));
//                            for (Map.Entry<String, String> entryStroke : tempStrokeMap.entrySet()) {
//                                specBlockCreator = blockCreatorFactory.blocksFactory(entryStroke, specBlockCreator);
//                            }
//                            Color color = utils.setColorFromString(mapOfBlock.get("stroke"));
//                            specBlockCreator = new BlockDrawOrFillCreator(specBlockCreator, color, true, 3);
//                        }

                        blocksFromSymbolsFactory.addToBlockCreators(symbol, specBlockCreator);

                    }    // sdef
                } else if (line.startsWith("sdef")) {
                    line = line.substring("sdef".length()).trim();
                    Map<String, String> spacerMap = utils.dividedeLine(line);
                    if (spacerMap.containsKey("symbol")
                            && spacerMap.containsKey("width")) {
                        String symbol = spacerMap.get("symbol");
                        Integer width = Integer.valueOf(spacerMap.get("width"));
                        blocksFromSymbolsFactory.addToSpacerWidths(symbol, width);
                    } else {
                        throw new RuntimeException("Error in reading file, symbol not found- " + line);
                    }

                    // else - invalid syntax
                } else {
                    throw new RuntimeException("Error in reading file, invalid syntax- " + line);
                }
            }

        } catch (Exception e) {
            throw new RuntimeException("Error in reading file- " + e);
        }

        return blocksFromSymbolsFactory;
    }

}

