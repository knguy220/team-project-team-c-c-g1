import acm.graphics.*;
import java.awt.Color;
import java.awt.Font;

public class PauseScreen {
    private GameApp gameApp;
    private GLabel pausedLabel;
    private GButton resumeButton;
    private GButton settingsButton;
    private GButton quitButton;

    public PauseScreen(GameApp gameApp) {
        this.gameApp = gameApp;

        pausedLabel = new GLabel("Paused");
        pausedLabel.setFont(new Font("Serif", Font.BOLD, 48));
        pausedLabel.setColor(Color.RED);

        resumeButton = new GButton("Resume", 0, 0, 150, 50, Color.BLACK, Color.WHITE);
        settingsButton = new GButton("Settings", 0, 0, 150, 50, Color.BLACK, Color.WHITE);
        quitButton = new GButton("Quit", 0, 0, 150, 50, Color.BLACK, Color.WHITE);
    }

    // Display the pause screen, centered on the screen
    public void show() {
        int centerX = (int) gameApp.getWidth() / 2;
        int centerY = (int) gameApp.getHeight() / 2;

        // Center the "Paused" label
        pausedLabel.setLocation(centerX - pausedLabel.getWidth() / 2, centerY - 150);
        gameApp.add(pausedLabel);

        // Center the Resume button below the "Paused" label
        resumeButton.getRect().setLocation(centerX - resumeButton.getRect().getWidth() / 2, centerY - 50);
        resumeButton.getMessage().setLocation(centerX - resumeButton.getMessage().getWidth() / 2, centerY - 20);
        gameApp.add(resumeButton.getRect());
        gameApp.add(resumeButton.getMessage());

        // Center the Settings button below the Resume button
        settingsButton.getRect().setLocation(centerX - settingsButton.getRect().getWidth() / 2, centerY + 20);
        settingsButton.getMessage().setLocation(centerX - settingsButton.getMessage().getWidth() / 2, centerY + 50);
        gameApp.add(settingsButton.getRect());
        gameApp.add(settingsButton.getMessage());

        // Center the Quit button below the Settings button
        quitButton.getRect().setLocation(centerX - quitButton.getRect().getWidth() / 2, centerY + 90);
        quitButton.getMessage().setLocation(centerX - quitButton.getMessage().getWidth() / 2, centerY + 120);
        gameApp.add(quitButton.getRect());
        gameApp.add(quitButton.getMessage());
    }

    // Hide the pause screen
    public void hide() {
        gameApp.remove(pausedLabel);
        gameApp.remove(resumeButton.getRect());
        gameApp.remove(resumeButton.getMessage());
        gameApp.remove(settingsButton.getRect());
        gameApp.remove(settingsButton.getMessage());
        gameApp.remove(quitButton.getRect());
        gameApp.remove(quitButton.getMessage());
    }

    // Check if the Resume button was clicked
    public boolean isResumeButtonClicked(int x, int y) {
        return resumeButton.getRect().contains(x, y);
    }

    // Check if the Settings button was clicked
    public boolean isSettingsButtonClicked(int x, int y) {
        return settingsButton.getRect().contains(x, y);
    }

    // Check if the Quit button was clicked
    public boolean isQuitButtonClicked(int x, int y) {
        return quitButton.getRect().contains(x, y);
    }
}

