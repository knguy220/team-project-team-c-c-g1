import acm.graphics.*;
import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

public class StartGame {
    private GameApp gameApp;
    private GImage backgroundImage;
    private GOval playerCircle;
    private GLine gunLine;
    private GButton pauseButton;
    private final int STEP_SIZE = 10;
    private final int GUN_LENGTH = 40; 
    private Map map; 
    private List<Bullet> bullets; 
    private List<Bullet> inactiveBullets; 
    private static final int MAX_BULLETS = 20; 

    public StartGame(GameApp gameApp) {
        this.gameApp = gameApp;
        map = new Map(); 
        bullets = new ArrayList<>(); 
        inactiveBullets = new ArrayList<>(); // Initialize inactive bullets list
        initGame();
    }

    private void initGame() {
        gameApp.removeAll();

        // Add background image
        backgroundImage = new GImage("groundhd.png");
        backgroundImage.setSize(gameApp.getWidth(), gameApp.getHeight());
        gameApp.add(backgroundImage);

        // Initialize player circle
        playerCircle = new GOval(gameApp.getWidth() / 2 - 25, gameApp.getHeight() / 2 - 25, 50, 50);
        playerCircle.setFilled(true);
        playerCircle.setColor(Color.BLUE);
        gameApp.add(playerCircle);

        // Initialize gun line - Positioned initially pointing to the right
        double centerX = playerCircle.getX() + playerCircle.getWidth() / 2;
        double centerY = playerCircle.getY() + playerCircle.getHeight() / 2;
        gunLine = new GLine(centerX, centerY, centerX + GUN_LENGTH, centerY);
        gunLine.setColor(Color.BLACK);
        gameApp.add(gunLine);

        // Initialize pause button
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
        if (backgroundImage != null) gameApp.add(backgroundImage);
        if (playerCircle != null) gameApp.add(playerCircle);
        if (gunLine != null) gameApp.add(gunLine);
        if (pauseButton != null) {
            gameApp.add(pauseButton.getRect());
            gameApp.add(pauseButton.getMessage());
        }

        // Render the map (add the map to the game screen)
        map.render(gameApp); // Draw the map

        // Add existing bullets to the screen
        for (Bullet bullet : bullets) {
            gameApp.add(bullet.getBulletShape());
        }
    }

    public void hide() {
        if (backgroundImage != null) gameApp.remove(backgroundImage);
        if (playerCircle != null) gameApp.remove(playerCircle);
        if (gunLine != null) gameApp.remove(gunLine);
        if (pauseButton != null) {
            gameApp.remove(pauseButton.getRect());
            gameApp.remove(pauseButton.getMessage());
        }

        // Remove all bullets from the screen
        for (Bullet bullet : bullets) {
            gameApp.remove(bullet.getBulletShape());
        }
    }

    // Handles key press events for movement
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

        // Set the gun line to point from the center of the player to the calculated endpoint
        gunLine.setStartPoint(centerX, centerY);
        gunLine.setEndPoint(gunEndX, gunEndY);
    }

    // Handle shooting based on mouse press
    public void handleShooting(MouseEvent e) {
        if (Bullet.canShoot() && bullets.size() < MAX_BULLETS) {
            double centerX = playerCircle.getX() + playerCircle.getWidth() / 2;
            double centerY = playerCircle.getY() + playerCircle.getHeight() / 2;
            double mouseX = e.getX();
            double mouseY = e.getY();

            // Calculate direction of bullet
            double angle = Math.atan2(mouseY - centerY, mouseX - centerX);
            double directionX = Math.cos(angle);
            double directionY = Math.sin(angle);

            Bullet bullet;
            if (!inactiveBullets.isEmpty()) {
                bullet = inactiveBullets.remove(0);
                bullet.reinitialize(centerX, centerY, directionX, directionY);
            } else {
                bullet = new Bullet(gameApp, this, centerX, centerY, directionX, directionY);
            }
            bullets.add(bullet);
            gameApp.add(bullet.getBulletShape());
        }
    }

    public void removeBullet(Bullet bullet) {
        bullets.remove(bullet);
        inactiveBullets.add(bullet);
        gameApp.remove(bullet.getBulletShape());
    }
}



