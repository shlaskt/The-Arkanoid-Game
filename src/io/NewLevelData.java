package io;

import java.util.HashMap;
import java.util.Map;

/**
 * The type New level data.
 */
public class NewLevelData {
    private Map<String, String> stringListMap;
    private Map<String, Boolean> isKeysUpdate;

    /**
     * Instantiates a new New level data.
     */
    public NewLevelData() {
        this.stringListMap = new HashMap<>();
        this.isKeysUpdate = new HashMap<>();
    }


    /**
     * Create keys.
     */
    public void initializeMaps() {
        // initialize first map
        stringListMap.put("level_name", null);
        stringListMap.put("ball_velocities", null);
        stringListMap.put("paddle_speed", null);
        stringListMap.put("paddle_width", null);
        stringListMap.put("background", null);
        stringListMap.put("block_definitions", null);
        stringListMap.put("blocks_start_x", null);
        stringListMap.put("blocks_start_y", null);
        stringListMap.put("row_height", null);
        stringListMap.put("num_blocks", null);


        // initialize second map
        isKeysUpdate.put("level_name", false);
        isKeysUpdate.put("ball_velocities", false);
        isKeysUpdate.put("paddle_speed", false);
        isKeysUpdate.put("paddle_width", false);
        isKeysUpdate.put("background", false);
        isKeysUpdate.put("block_definitions", false);
        isKeysUpdate.put("blocks_start_x", false);
        isKeysUpdate.put("blocks_start_y", false);
        isKeysUpdate.put("row_height", false);
        isKeysUpdate.put("num_blocks", false);
    }


    /**
     * Is all keys updated.
     */
    public void isAllKeysUpdated() {
        for (Boolean isUpdateKey : isKeysUpdate.values()) {
            if (!isUpdateKey) { // if not update key
                throw new RuntimeException("Invalid input -not all key was update");
            }
        }
    }

    /**
     * Gets string list map.
     *
     * @return the string list map
     */
    public Map<String, String> getStringListMap() {
        return stringListMap;
    }

    /**
     * Gets is keys update.
     *
     * @return the is keys update
     */
    public Map<String, Boolean> getIsKeysUpdate() {
        return isKeysUpdate;
    }

    /**
     * Gets value.
     *
     * @param key the key
     * @return the value
     */
    public String getValue(String key) {
        return stringListMap.get(key); // return null if not found key
    }
}
