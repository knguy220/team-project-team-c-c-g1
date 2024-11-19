import acm.graphics.*;
import java.awt.Color;
import java.awt.event.MouseEvent;

public class GameOverScreen {
    private GameApp gameApp;
    private GLabel titleLabel;
    private GLabel scoreLabel;
    private GLabel playAgainLabel;
    private GLabel returnToStartLabel;
    private GImage restartIcon;
    private GButton returnToStartButton;
    private GImage background;

    private String username;

    public GameOverScreen(GameApp gameApp, String username) {
        this.gameApp = gameApp;
        this.username = username;
    }

    /**
     * Initializes and displays the Game Over screen.
     */
    public void show(int playerScore) {
        // Add background
        background = new GImage("background.jpg");
        background.setSize(gameApp.getWidth(), gameApp.getHeight());
        gameApp.add(background);

        // Add title label
        titleLabel = new GLabel("Game Over!");
        titleLabel.setFont("Arial-Bold-36");
        titleLabel.setColor(Color.BLACK);
        double titleX = (gameApp.getWidth() - titleLabel.getWidth()) / 2;
        double titleY = gameApp.getHeight() / 6.0;
        titleLabel.setLocation(titleX, titleY);
        gameApp.add(titleLabel);

        // Add score label with username
        scoreLabel = new GLabel(username + "'s score: " + playerScore + " points");
        scoreLabel.setFont("Arial-Bold-24");
        scoreLabel.setColor(Color.BLACK);
        double scoreX = (gameApp.getWidth() - scoreLabel.getWidth()) / 2;
        double scoreY = gameApp.getHeight() / 2.0;
        scoreLabel.setLocation(scoreX, scoreY);
        gameApp.add(scoreLabel);

        // Add restart icon
        restartIcon = new GImage("restart.jpg");
        restartIcon.setSize(75, 75);
        double iconX = (gameApp.getWidth() - restartIcon.getWidth()) / 2;
        double iconY = gameApp.getHeight() * 0.75;
        restartIcon.setLocation(iconX, iconY);
        gameApp.add(restartIcon);

        // Add "Play Again?" label
        playAgainLabel = new GLabel("Play Again?");
        playAgainLabel.setFont("Arial-Bold-30");
        playAgainLabel.setColor(Color.BLACK);
        double playAgainX = (gameApp.getWidth() - playAgainLabel.getWidth()) / 2;
        double playAgainY = iconY - 10;
        playAgainLabel.setLocation(playAgainX, playAgainY);
        gameApp.add(playAgainLabel);

        // Add "Return to Start Screen" button
        returnToStartButton = new GButton("Return to Start Screen", 
            (int) (gameApp.getWidth() / 2 - 100), (int) (iconY + 100), 200, 40, Color.BLACK, Color.WHITE);
        gameApp.add(returnToStartButton.getRect());
        gameApp.add(returnToStartButton.getMessage());

        // Add mouse listener to detect clicks
        gameApp.addMouseListeners();
    }

    /**
     * Handles mouse clicks on the Game Over screen.
     */
    public void handleMouseClick(MouseEvent e) {
        if (restartIcon != null && restartIcon.contains(e.getX(), e.getY())) {
            restartGame();
        } else if (returnToStartButton != null && returnToStartButton.getRect().contains(e.getX(), e.getY())) {
            returnToStartScreen();
        }
    }

    /**
     * Restarts the game by navigating to the StartGame screen.
     */
    private void restartGame() {
        System.out.println("Restarting game...");
        gameApp.startGame(); // Reset and start the game
    }


    /**
     * Returns to the StartScreen2.
     */
    private void returnToStartScreen() {
        System.out.println("Returning to Start Screen...");
        gameApp.showStartScreen();
    }
}



