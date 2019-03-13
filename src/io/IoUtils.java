package io;

import javax.imageio.ImageIO;
import java.awt.Color;
import java.awt.Image;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The type Io utils.
 */
public class IoUtils {

    /**
     * Split file to lines java . util . list.
     *
     * @param reader the reader
     * @return the java . util . list
     */
    public List<String> splitFileToLines(Reader reader) {
        List<String> textFromFile = new ArrayList<>();
        BufferedReader readLines = null;
        try {
            readLines = new BufferedReader(reader);
            String line = readLines.readLine();
            while (line != null) {
                if ((line.isEmpty()) || (line.startsWith("#"))) { // empty line or remarks
                    line = readLines.readLine();
                    continue; // do not copy to the array
                }
                textFromFile.add(line);
                line = readLines.readLine();
            }
        } catch (IOException e) {
            System.out.println(" Something went wrong while reading!");
        } finally {
            if (readLines != null) { // Exception might have happened at constructor
                try {
                    readLines.close(); // closes FileInputStream too
                } catch (IOException e) {
                    System.out.println(" Failed closing the file !");
                }
            }
        }
        return textFromFile;
    }

    /**
     * Sets image from string.
     *
     * @param line the line
     * @return the image from string
     */
    public Image setImageFromString(String line) {
        if (line.startsWith("image(")) {
            line = line.substring("image(".length());
            line = line.replace(")", "");
        }
        try {
            InputStream inputStream = ClassLoader.getSystemClassLoader().getResourceAsStream(line);
            Image image = ImageIO.read(inputStream);
            return image;
        } catch (IOException e) {
            throw new RuntimeException("Invalid input-" + line + "Unable to find background image");
        }
    }


    /**
     * Sets color from string.
     *
     * @param line the line
     * @return the color from string
     */
    public Color setColorFromString(String line) {
        if (line.startsWith("color(")) {
            line = line.substring("color(".length());
            if (line.startsWith("RGB(")) {
                line = line.substring("RGB(".length());
                line = line.replace("))", "");
                String[] parts = line.split(",");
                if (!(parts.length == 3)) {
                    throw new RuntimeException("Invalid input-" + line + "must contain 3 parameters");
                }
                int r = convertToInt(parts[0]);
                int g = convertToInt(parts[1]);
                int b = convertToInt(parts[2]);
                return new Color(r, g, b);

            } else {
                line = line.replace(")", "");
                if (line.equals("black")) {
                    return Color.BLACK;
                } else if (line.equals("blue")) {
                    return Color.BLUE;
                } else if (line.equals("cyan")) {
                    return Color.CYAN;
                } else if (line.equals("gray")) {
                    return Color.GRAY;
                } else if (line.equals("lightGray")) {
                    return Color.LIGHT_GRAY;
                } else if (line.equals("green")) {
                    return Color.GREEN;
                } else if (line.equals("orange")) {
                    return Color.ORANGE;
                } else if (line.equals("pink")) {
                    return Color.PINK;
                } else if (line.equals("red")) {
                    return Color.RED;
                } else if (line.equals("white")) {
                    return Color.WHITE;
                } else if (line.equals("yellow")) {
                    return Color.YELLOW;

                } else {
                    throw new RuntimeException("Invalid input-" + line + "color is not defined");
                }
            }

        } else {
            throw new RuntimeException("Invalid input-" + line + "invalid format");
        }
    }

    /**
     * Convert to int int.
     *
     * @param stringToConvert the string to convert
     * @return the int
     */
    public int convertToInt(String stringToConvert) {
        try {
            if (Integer.valueOf(stringToConvert) >= 0) {
                return Integer.valueOf(stringToConvert);
            }
            throw new RuntimeException("Invalid input-" + stringToConvert + "isn't positive");
        } catch (NumberFormatException e) {
            throw new RuntimeException("Invalid input-" + stringToConvert + "isn't integer");
        }
    }

    /**
     * Convert to double double.
     *
     * @param stringToConvert the string to convert
     * @return the double
     */
    public double convertToDouble(String stringToConvert) {
        try {
            if (Double.valueOf(stringToConvert) >= 0) {
                return Double.valueOf(stringToConvert);
            }
            throw new RuntimeException("Invalid input-" + stringToConvert + "isn't positive");
        } catch (NumberFormatException e) {
            throw new RuntimeException("Invalid input-" + stringToConvert + "isn't double");
        }
    }

    /**
     * Set new value in the map.
     * if not contain the key / not have ":" - throw exception
     *
     * @param line          the line
     * @param stringListMap the string list map
     * @param isKeysUpdate  the is keys update
     */
    public void setNewValue(String line, Map<String, String> stringListMap, Map<String, Boolean> isKeysUpdate) {
        String key;
        String value;

        if (line.contains(":")) {
            String[] divide = line.split(":");
            key = divide[0];
            value = divide[1];
        } else {
            throw new RuntimeException("Invalid input- " + line + " - don't contain /':/'");
        }

        if (stringListMap.containsKey(key)) {
            // if the key is already update
            if (isKeysUpdate.get(key)) {
                throw new RuntimeException("Invalid input - key already update");
            }

            stringListMap.put(key, value);
            isKeysUpdate.put(key, true);

        } else {
            throw new RuntimeException("Invalid input- " + line);
        }
    }

    /**
     * gets a string and a line number and returns the line's data in map.
     *
     * @param line the given line.
     * @return a map with the line information.
     */
    public Map<String, String> dividedeLine(String line) {
        Map<String, String> returnVal = new HashMap<>();

        String[] pairs = line.split(" ");
        for (String pair : pairs) {
            String[] parts = pair.split(":");
            if (parts.length != 2) {
                throw new RuntimeException("Invalid input - more than 2 args in key:val - " + line);
            }
            returnVal.put(parts[0], parts[1]);
        }
        return returnVal;
    }
}
