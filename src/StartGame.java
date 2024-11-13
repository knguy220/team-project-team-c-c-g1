import acm.graphics.*;
import java.awt.Color;
import java.awt.event.KeyEvent;

public class StartGame {
    private GameApp gameApp;
    private GOval playerCircle; // The circle representing the player
    private final int STEP_SIZE = 10; // Amount of movement in each key press
    //test
    /**
     * Constructor that initializes StartGame with a reference to the main GameApp.
     *
     * @param gameApp The main GameApp instance to enable adding graphics objects
     */
    public StartGame(GameApp gameApp) {
        this.gameApp = gameApp;
        initGame();
    }

    /**
     * Initializes the game screen by adding the player circle.
     */
    private void initGame() {
        // Create a circle at the center of the screen
        playerCircle = new GOval(gameApp.getWidth() / 2 - 25, gameApp.getHeight() / 2 - 25, 50, 50);
        playerCircle.setFilled(true);
        playerCircle.setColor(Color.BLUE);
        gameApp.add(playerCircle); // Add the circle to GameApp
    }

    /**
     * Handles key press events for moving the player circle.
     *
     * @param e KeyEvent containing the key code of the pressed key
     */
    public void handleKeyPress(KeyEvent e) {
        int keyCode = e.getKeyCode();
        switch (keyCode) {
            case KeyEvent.VK_W: // Move up
                playerCircle.move(0, -STEP_SIZE);
                break;
            case KeyEvent.VK_A: // Move left
                playerCircle.move(-STEP_SIZE, 0);
                break;
            case KeyEvent.VK_S: // Move down
                playerCircle.move(0, STEP_SIZE);
                break;
            case KeyEvent.VK_D: // Move right
                playerCircle.move(STEP_SIZE, 0);
                break;
        }
    }
}
