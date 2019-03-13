package io;

import decorator.BlockCreator;
import decorator.BlockDrawOrFillCreator;
import decorator.BlockHeighCreator;
import decorator.BlockPointsCreator;
import decorator.BlockWidthCreator;

import java.awt.Color;
import java.awt.Image;
import java.util.Map;

/**
 * The type Block creator factory.
 */
public class BlockCreatorFactory {
    private IoUtils utils = new IoUtils();


    /**
     * Blocks factory block creator.
     *
     * @param property  the property
     * @param decoretad the decoretad
     * @return the block creator
     */
    public BlockCreator blocksFactory(Map.Entry<String, String> property, BlockCreator decoretad) {
        if (property.getKey().equals("width")) {
            return new BlockWidthCreator(decoretad, Integer.parseInt(property.getValue()));
        } else if (property.getKey().equals("height")) {
            return new BlockHeighCreator(decoretad, Integer.parseInt(property.getValue()));
        } else if (property.getKey().equals("hit_points")) {
            return new BlockPointsCreator(decoretad, Integer.parseInt(property.getValue()));
        } else if (property.getKey().startsWith("stroke") || property.getKey().startsWith("fill")) {
            int numOfHits = -1; // default
            int i = property.getKey().indexOf("-");
            if (i != -1) { // check if there is number after "fill"
                numOfHits = Integer.parseInt(property.getKey().substring(i + 1)); // change numOfHits
            }
            if (property.getValue().startsWith("color(")) {
                Color color = this.utils.setColorFromString(property.getValue());
                return new BlockDrawOrFillCreator(decoretad, color, property.getKey().startsWith("stroke"), numOfHits);
            } else if (property.getValue().startsWith("image(")) {
                Image image = this.utils.setImageFromString(property.getValue());
                return new BlockDrawOrFillCreator(decoretad, image, numOfHits);
            }
        } else if (property.getKey().equals("") || property.getKey().equals(" ")) {
            return decoretad;
        }
        // else - invalid input
        throw new RuntimeException("Error while reading block definition file- in: <"
                + property.getKey() + " : " + property.getValue() + ">");
    }
}
