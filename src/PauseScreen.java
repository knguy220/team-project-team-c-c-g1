import acm.graphics.*;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class PauseScreen {
    private GameApp gameApp;
    private GButton resumeButton;
    private GButton settingsButton;
    private GButton quitButton;

    public PauseScreen(GameApp gameApp) {
        this.gameApp = gameApp;
    }

    public void show() {
        gameApp.removeAll(); // Clear any elements from StartGame to display the pause screen

        // Title label for the pause screen
        GLabel pauseLabel = new GLabel("Paused", gameApp.getWidth() / 2 - 50, 100);
        pauseLabel.setFont(new Font("Serif", Font.BOLD, 32));
        pauseLabel.setColor(Color.BLACK);
        gameApp.add(pauseLabel);

        // Resume button setup
        resumeButton = new GButton("Resume", (int) (gameApp.getWidth() / 2 - 50), 200, 100, 40, Color.BLACK, Color.WHITE);
        resumeButton.addActionListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                gameApp.resumeGame(); // Call to resume the game
            }
            @Override public void mousePressed(MouseEvent e) {}
            @Override public void mouseReleased(MouseEvent e) {}
            @Override public void mouseEntered(MouseEvent e) {}
            @Override public void mouseExited(MouseEvent e) {}
        });
        gameApp.add(resumeButton.getRect());
        gameApp.add(resumeButton.getMessage());

        // Settings button setup
        settingsButton = new GButton("Settings", (int) (gameApp.getWidth() / 2 - 50), 270, 100, 40, Color.BLACK, Color.WHITE);
        settingsButton.addActionListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                gameApp.showSettingsScreen(); // Navigate to settings screen
            }
            @Override public void mousePressed(MouseEvent e) {}
            @Override public void mouseReleased(MouseEvent e) {}
            @Override public void mouseEntered(MouseEvent e) {}
            @Override public void mouseExited(MouseEvent e) {}
        });
        gameApp.add(settingsButton.getRect());
        gameApp.add(settingsButton.getMessage());

        // Quit button setup
        quitButton = new GButton("Quit", (int) (gameApp.getWidth() / 2 - 50), 340, 100, 40, Color.BLACK, Color.WHITE);
        quitButton.addActionListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                gameApp.showStartScreen(); // Return to the start screen
            }
            @Override public void mousePressed(MouseEvent e) {}
            @Override public void mouseReleased(MouseEvent e) {}
            @Override public void mouseEntered(MouseEvent e) {}
            @Override public void mouseExited(MouseEvent e) {}
        });
        gameApp.add(quitButton.getRect());
        gameApp.add(quitButton.getMessage());
    }
}

