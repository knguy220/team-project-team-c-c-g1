import acm.graphics.*;
import java.awt.Color;
import java.awt.event.MouseEvent;


public class DifficultyMenu {
    private GameApp gameApp;
    private GButton easyButton;
    private GButton mediumButton;
    private GButton hardButton;
    private GLabel titleLabel;
    private GImage background;

    public DifficultyMenu(GameApp gameApp) {
        this.gameApp = gameApp;
    }

    public void show() {
        // Clear the screen
        gameApp.removeAll();

        // Add background
        background = new GImage("clouds.png"); // Replace with your image path
        background.setSize(gameApp.getWidth(), gameApp.getHeight());
        gameApp.add(background);

        // Add title
        titleLabel = new GLabel("Select Difficulty", gameApp.getWidth() / 2.0 - 100, 100);
        titleLabel.setFont("Arial-Bold-36");
        titleLabel.setColor(Color.WHITE);
        gameApp.add(titleLabel);

        // Add difficulty buttons
        easyButton = new GButton("Easy", (int) gameApp.getWidth() / 2 - 75, 200, 150, 50, Color.GREEN, Color.BLACK);
        mediumButton = new GButton("Medium", (int) gameApp.getWidth() / 2 - 75, 300, 150, 50, Color.ORANGE, Color.BLACK);
        hardButton = new GButton("Hard", (int) gameApp.getWidth() / 2 - 75, 400, 150, 50, Color.RED, Color.BLACK);

        gameApp.add(easyButton.getRect());
        gameApp.add(easyButton.getMessage());

        gameApp.add(mediumButton.getRect());
        gameApp.add(mediumButton.getMessage());

        gameApp.add(hardButton.getRect());
        gameApp.add(hardButton.getMessage());
    }

    public void handleMouseClick(MouseEvent e) {
        double mouseX = e.getX();
        double mouseY = e.getY();
        handleMouseClick(mouseX, mouseY); // Call the existing method
    }

    public void handleMouseClick(double x, double y) {
        if (easyButton.contains(x, y)) {
            gameApp.startGameWithDifficulty("Easy");
        } else if (mediumButton.contains(x, y)) {
            gameApp.startGameWithDifficulty("Medium");
        } else if (hardButton.contains(x, y)) {
            gameApp.startGameWithDifficulty("Hard");
        }
    }



}
