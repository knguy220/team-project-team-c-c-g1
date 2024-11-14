import acm.graphics.*;
import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

public class StartGame {
    private GameApp gameApp;
    private GOval playerCircle;
    private GLine gunLine;
    private GButton pauseButton;
    private final int STEP_SIZE = 10;
    private final int GUN_LENGTH = 40; 

    public StartGame(GameApp gameApp) {
        this.gameApp = gameApp;
        initGame();
    }

    private void initGame() {
        gameApp.removeAll();

        playerCircle = new GOval(gameApp.getWidth() / 2 - 25, gameApp.getHeight() / 2 - 25, 50, 50);
        playerCircle.setFilled(true);
        playerCircle.setColor(Color.BLUE);
        gameApp.add(playerCircle);

        double centerX = playerCircle.getX() + playerCircle.getWidth() / 2;
        double centerY = playerCircle.getY() + playerCircle.getHeight() / 2;
        gunLine = new GLine(centerX, centerY, centerX + GUN_LENGTH, centerY);
        gunLine.setColor(Color.BLACK);
        gameApp.add(gunLine);

        pauseButton = new GButton("Pause", (int) (gameApp.getWidth() - 100), 20, 80, 30, Color.BLACK, Color.WHITE);
        pauseButton.addActionListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                gameApp.showPauseScreen();
            }
        });
        gameApp.add(pauseButton.getRect());
        gameApp.add(pauseButton.getMessage());
    }

    public void show() {
        if (playerCircle != null) gameApp.add(playerCircle);
        if (gunLine != null) gameApp.add(gunLine);
        if (pauseButton != null) {
            gameApp.add(pauseButton.getRect());
            gameApp.add(pauseButton.getMessage());
        }
    }

    public void hide() {
        if (playerCircle != null) gameApp.remove(playerCircle);
        if (gunLine != null) gameApp.remove(gunLine);
        if (pauseButton != null) {
            gameApp.remove(pauseButton.getRect());
            gameApp.remove(pauseButton.getMessage());
        }
    }

    public void handleKeyPress(KeyEvent e) {
        int keyCode = e.getKeyCode();

        // Current player position
        double playerX = playerCircle.getX();
        double playerY = playerCircle.getY();
        double playerWidth = playerCircle.getWidth();
        double playerHeight = playerCircle.getHeight();

        // Window boundaries
        double rightBoundary = gameApp.getWidth() - 20;
        double bottomBoundary = gameApp.getHeight() - 20;
        double leftBoundary = 20;
        double topBoundary = 20;

        switch (keyCode) {
            case KeyEvent.VK_W: // Move up
                if (playerY - STEP_SIZE >= topBoundary) {
                    playerCircle.move(0, -STEP_SIZE);
                    gunLine.move(0, -STEP_SIZE);
                }
                break;
            case KeyEvent.VK_A: // Move left
                if (playerX - STEP_SIZE >= leftBoundary) {
                    playerCircle.move(-STEP_SIZE, 0);
                    gunLine.move(-STEP_SIZE, 0);
                }
                break;
            case KeyEvent.VK_S: // Move down
                if (playerY + playerHeight + STEP_SIZE <= bottomBoundary) {
                    playerCircle.move(0, STEP_SIZE);
                    gunLine.move(0, STEP_SIZE);
                }
                break;
            case KeyEvent.VK_D: // Move right
                if (playerX + playerWidth + STEP_SIZE <= rightBoundary) {
                    playerCircle.move(STEP_SIZE, 0);
                    gunLine.move(STEP_SIZE, 0);
                }
                break;
        }
    }

    public void updateAiming(MouseEvent e) {
        double centerX = playerCircle.getX() + playerCircle.getWidth() / 2;
        double centerY = playerCircle.getY() + playerCircle.getHeight() / 2;
        double mouseX = e.getX();
        double mouseY = e.getY();

        // Calculate the angle between the player center and mouse position
        double angle = Math.atan2(mouseY - centerY, mouseX - centerX);

        // Calculate the end point for the gun barrel based on the angle and gun length
        double gunEndX = centerX + Math.cos(angle) * (playerCircle.getWidth() / 2 + GUN_LENGTH);
        double gunEndY = centerY + Math.sin(angle) * (playerCircle.getHeight() / 2 + GUN_LENGTH);

        gunLine.setStartPoint(centerX, centerY);
        gunLine.setEndPoint(gunEndX, gunEndY);
    }

    // Handle shooting based on mouse press
    public void handleShooting(MouseEvent e) {
        // Shooting mechanism code here
    }
}




