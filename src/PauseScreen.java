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

    public void show() {
        int centerX = (int) gameApp.getWidth() / 2;
        int centerY = (int) gameApp.getHeight() / 2;

        pausedLabel.setLocation(centerX - pausedLabel.getWidth() / 2, centerY - 150);
        gameApp.add(pausedLabel);

        resumeButton.getRect().setLocation(centerX - resumeButton.getRect().getWidth() / 2, centerY - 50);
        resumeButton.getMessage().setLocation(centerX - resumeButton.getMessage().getWidth() / 2, centerY - 20);
        gameApp.add(resumeButton.getRect());
        gameApp.add(resumeButton.getMessage());

        settingsButton.getRect().setLocation(centerX - settingsButton.getRect().getWidth() / 2, centerY + 20);
        settingsButton.getMessage().setLocation(centerX - settingsButton.getMessage().getWidth() / 2, centerY + 50);
        gameApp.add(settingsButton.getRect());
        gameApp.add(settingsButton.getMessage());

        quitButton.getRect().setLocation(centerX - quitButton.getRect().getWidth() / 2, centerY + 90);
        quitButton.getMessage().setLocation(centerX - quitButton.getMessage().getWidth() / 2, centerY + 120);
        gameApp.add(quitButton.getRect());
        gameApp.add(quitButton.getMessage());
    }

    public void hide() {
        gameApp.remove(pausedLabel);
        gameApp.remove(resumeButton.getRect());
        gameApp.remove(resumeButton.getMessage());
        gameApp.remove(settingsButton.getRect());
        gameApp.remove(settingsButton.getMessage());
        gameApp.remove(quitButton.getRect());
        gameApp.remove(quitButton.getMessage());
    }

    public boolean isResumeButtonClicked(int x, int y) {
        return resumeButton.getRect().contains(x, y);
    }

    public boolean isSettingsButtonClicked(int x, int y) {
        return settingsButton.getRect().contains(x, y);
    }

    public boolean isQuitButtonClicked(int x, int y) {
        return quitButton.getRect().contains(x, y);
    }
}

