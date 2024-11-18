import acm.graphics.*;
import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.Timer;

public class StartGame {
    private GameApp gameApp;
    private GImage backgroundImage;
    private GButton pauseButton;
    private final int GUN_LENGTH = 40; 
    private Map map; 
    private List<Bullet> bullets; 
    private static final int MAX_BULLETS = 20; 
    private Player player;

    public StartGame(GameApp gameApp) {
        this.gameApp = gameApp;
        map = new Map(); 
        bullets = new ArrayList<>(); 
        player = new Player(gameApp, GUN_LENGTH); // Initialize player
        initGame();

        // Timer to continuously check for movement
        Timer movementTimer = new Timer(20, e -> player.updateMovement());
        movementTimer.start();
    }

    private void initGame() {
        gameApp.removeAll();

        // Add background image
        backgroundImage = new GImage("groundhd.png");
        backgroundImage.setSize(gameApp.getWidth(), gameApp.getHeight());
        gameApp.add(backgroundImage);

        // Initialize player
        player.initialize();

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
        if (pauseButton != null) {
            gameApp.add(pauseButton.getRect());
            gameApp.add(pauseButton.getMessage());
        }

        // Render the map (add the map to the game screen)
        map.render(gameApp); // Draw the map

        // Show player
        player.show();

        // Add existing bullets to the screen
        for (Bullet bullet : bullets) {
            gameApp.add(bullet.getBulletShape());
        }
    }

    public void hide() {
        if (backgroundImage != null) gameApp.remove(backgroundImage);
        if (pauseButton != null) {
            gameApp.remove(pauseButton.getRect());
            gameApp.remove(pauseButton.getMessage());
        }

        // Hide player
        player.hide();

        // Remove all bullets from the screen
        for (Bullet bullet : bullets) {
            gameApp.remove(bullet.getBulletShape());
        }
    }

    public void handleKeyPress(KeyEvent e) {
        player.handleKeyPress(e);
    }

    public void handleKeyRelease(KeyEvent e) {
        player.handleKeyRelease(e);
    }

    public void updateAiming(MouseEvent e) {
        player.updateAiming(e);
    }

    public void handleShooting(MouseEvent e) {
        if (Bullet.canShoot() && bullets.size() < MAX_BULLETS) {
            double centerX = player.getCenterX();
            double centerY = player.getCenterY();
            double mouseX = e.getX();
            double mouseY = e.getY();

            // Calculate direction of bullet
            double angle = Math.atan2(mouseY - centerY, mouseX - centerX);
            double directionX = Math.cos(angle);
            double directionY = Math.sin(angle);

            Bullet bullet = new Bullet(gameApp, this, centerX, centerY, directionX, directionY);
            bullets.add(bullet);
        }
    }

    public void removeBullet(Bullet bullet) {
        bullets.remove(bullet);
        gameApp.remove(bullet.getBulletShape());
    }
}

