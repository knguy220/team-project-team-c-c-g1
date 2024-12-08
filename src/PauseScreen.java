import acm.graphics.*;
import java.awt.Color;
import java.awt.Font;

public class PauseScreen {
    private GameApp gameApp;
    private GLabel pausedLabel;
    private GButton resumeButton;
    private GButton quitButton;

    public PauseScreen(GameApp gameApp) {
        this.gameApp = gameApp;

        pausedLabel = new GLabel("Paused");
        pausedLabel.setFont(new Font("Serif", Font.BOLD, 48));
        pausedLabel.setColor(Color.RED);

        resumeButton = new GButton("Resume", 0, 0, 150, 50, Color.DARK_GRAY, Color.WHITE);
        quitButton = new GButton("Quit", 0, 0, 150, 50, Color.DARK_GRAY, Color.WHITE);
    }

    public void show() {
        int centerX = (int) gameApp.getWidth() / 2;
        int centerY = (int) gameApp.getHeight() / 2;

        pausedLabel.setLocation(centerX - pausedLabel.getWidth() / 2, centerY - 100);
        gameApp.add(pausedLabel);

        resumeButton.getRect().setLocation(centerX - resumeButton.getRect().getWidth() / 2, centerY - 30);
        resumeButton.getMessage().setLocation(centerX - resumeButton.getMessage().getWidth() / 2, centerY);
        gameApp.add(resumeButton.getRect());
        gameApp.add(resumeButton.getMessage());

        quitButton.getRect().setLocation(centerX - quitButton.getRect().getWidth() / 2, centerY + 40);
        quitButton.getMessage().setLocation(centerX - quitButton.getMessage().getWidth() / 2, centerY + 70);
        gameApp.add(quitButton.getRect());
        gameApp.add(quitButton.getMessage());
    }

    public void hide() {
        gameApp.remove(pausedLabel);
        gameApp.remove(resumeButton.getRect());
        gameApp.remove(resumeButton.getMessage());
        gameApp.remove(quitButton.getRect());
        gameApp.remove(quitButton.getMessage());
    }

    public boolean isResumeButtonClicked(int x, int y) {
        return resumeButton.getRect().contains(x, y);
    }

    public boolean isQuitButtonClicked(int x, int y) {
        return quitButton.getRect().contains(x, y);
    }
}

