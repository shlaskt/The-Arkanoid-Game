package menu;

import animation.AnimationRunner;
import biuoop.DrawSurface;
import biuoop.KeyboardSensor;
import game.Constants;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

/**
 * The type Menu animation.
 *
 * @param <T> the type parameter
 */
public class MenuAnimation<T> implements Menu<T> {
    private List<String> keyList;
    private List<String> messageList;
    private List<T> valList;
    private T status;
    private Constants constants;
    private KeyboardSensor keyboard;
    private AnimationRunner runner;
    private List<Menu<T>> subMenusList;
    private List<Boolean> isChoseSubMenu;

    /**
     * Instantiates a new Menu animation.
     *
     * @param keyboardSensor  the keyboard sensor
     * @param animationRunner the animation runner
     */
    public MenuAnimation(KeyboardSensor keyboardSensor, AnimationRunner animationRunner) {
        this.constants = new Constants();
        this.keyList = new ArrayList<>();
        this.messageList = new ArrayList<>();
        this.valList = new ArrayList();
        this.keyboard = keyboardSensor;
        this.status = null;
        this.runner = animationRunner;
        this.isChoseSubMenu = new ArrayList<>();
        this.subMenusList = new ArrayList<>();
    }

    @Override
    public T getStatus() {
        T tempStatus = this.status;
        this.status = null;
        return tempStatus;
    }

    @Override
    public void addSelection(String key, String message, T returnVal) {
        this.keyList.add(key);
        this.messageList.add(message);
        this.valList.add(returnVal);
        this.subMenusList.add(null); // to keep the the same index in the iteration
        this.isChoseSubMenu.add(false);
    }

    @Override
    public void addSubMenu(String key, String message, Menu<T> subMenu) {
        this.keyList.add(key);
        this.messageList.add(message);
        this.subMenusList.add(subMenu);
        this.valList.add(null); // to keep the the same index in the iteration
        this.isChoseSubMenu.add(true);
    }


    @Override
    public void doOneFrame(DrawSurface d, double dt) {
        // draw background
        d.setColor(Color.cyan.darker().darker());
        d.fillRectangle(constants.X_START_FRAME, constants.Y_START_FRAME, d.getWidth(), d.getHeight());

        d.setColor(Color.cyan.darker().darker());

        // draw Game name title
        d.setColor(Color.orange);
        d.drawText(100, constants.TABLE_TITLE_Y, constants.GAME_NAME,
                constants.TABLE_TEXT_SIZE * 2);
        // draw options
        d.setColor(Color.white);
        int yLine = constants.TABLE_Y;
        // draw table
        d.setColor(Color.cyan);
        for (int i = 0; i < this.keyList.size(); i++) {
            yLine += constants.TABLE_DY;
            d.drawText(100, yLine, "(" + this.keyList.get(i) + ")", constants.TABLE_TEXT_SIZE);
            d.drawText(150, yLine, this.messageList.get(i), constants.TABLE_TEXT_SIZE);
        }

        for (int i = 0; i < this.keyList.size(); i++) {
            if (keyboard.isPressed(this.keyList.get(i))) {
                if (this.isChoseSubMenu.get(i)) { // if the user chose sub menu
                    this.runner.run(this.subMenusList.get(i));
                    this.status = this.subMenusList.get(i).getStatus();
                    this.subMenusList.get(i).doOneFrame(d, dt); // to avoid main menu (second)
                    this.subMenusList.get(i).resetMenu();
                    break;
                } else { // the user chose another option
                    this.status = this.valList.get(i);
                }
            }
        }

    }


    @Override
    public boolean shouldStop() {
        return this.status != null;
    }

    @Override
    public void resetMenu() {
        this.status = null;
    }
}
