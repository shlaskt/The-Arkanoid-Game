package menu;

import animation.Animation;
import animation.AnimationRunner;
import animation.KeyPressStoppableAnimation;
import biuoop.KeyboardSensor;


/**
 * The type Show hi scores task.
 */
public class ShowHiScoresTask implements Task<Void> {
    private AnimationRunner runner;
    private Animation highScoresAnimation;

    /**
     * Instantiates a new Show hi scores task.
     *
     * @param runner              the runner
     * @param highScoresAnimation the high scores animation
     */
    public ShowHiScoresTask(AnimationRunner runner, Animation highScoresAnimation) {
        this.runner = runner;
        this.highScoresAnimation = highScoresAnimation;
    }

    /**
     * run the highScore.
     * show the high score animation and wait until the user pressed the key
     * @return null
     */
    public Void run() {
        Animation highScoreA = new KeyPressStoppableAnimation(this.runner.getGui().getKeyboardSensor(),
                KeyboardSensor.SPACE_KEY, this.highScoresAnimation);
        this.runner.run(highScoreA);
        return null;
    }
}
