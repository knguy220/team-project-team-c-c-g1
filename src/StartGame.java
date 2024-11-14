import acm.graphics.*;
import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class StartGame {
    private GameApp gameApp;
    private GOval playerCircle; // Represents the player character as a circle
    private GButton pauseButton; // Pause button at the top-right corner
    private final int STEP_SIZE = 10; // Movement increment for each key press

    public StartGame(GameApp gameApp) {
        this.gameApp = gameApp;
        initGame();
    }

    // Initialize the game elements
    private void initGame() {
        gameApp.removeAll(); // Clear previous screen elements

        // Player circle setup in the center of the screen
        playerCircle = new GOval(gameApp.getWidth() / 2 - 25, gameApp.getHeight() / 2 - 25, 50, 50);
        playerCircle.setFilled(true);
        playerCircle.setColor(Color.BLUE);
        gameApp.add(playerCircle);

        // Pause button setup in the top-right corner
        pauseButton = new GButton("Pause", (int) (gameApp.getWidth() - 100), 20, 80, 30, Color.BLACK, Color.WHITE);
        pauseButton.addActionListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                gameApp.showPauseScreen(); // Show pause screen when clicked
            }
            @Override public void mousePressed(MouseEvent e) {}
            @Override public void mouseReleased(MouseEvent e) {}
            @Override public void mouseEntered(MouseEvent e) {}
            @Override public void mouseExited(MouseEvent e) {}
        });
        gameApp.add(pauseButton.getRect());
        gameApp.add(pauseButton.getMessage());
    }

    // Show method to re-add elements to the screen when resuming the game
    public void show() {
        gameApp.add(playerCircle); // Add the player circle back to the screen
        gameApp.add(pauseButton.getRect());
        gameApp.add(pauseButton.getMessage());
    }

    // Hide method to remove elements from the screen when pausing the game
    public void hide() {
        gameApp.remove(playerCircle); // Remove the player circle from the screen
        gameApp.remove(pauseButton.getRect());
        gameApp.remove(pauseButton.getMessage());
    }

    // Handles key press events for movement
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

