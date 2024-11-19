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
    private Player player;
    private List<Bullet> bullets;
    private static final int MAX_BULLETS = 20;
    private Console console;
    private Timer gameLoopTimer;

    public StartGame(GameApp gameApp) {
        this.gameApp = gameApp;
        this.bullets = new ArrayList<>();

        // Calculate the center of the screen
        int startX = (int) (gameApp.getWidth() / 2 - Player.PLAYER_SIZE / 2);
        int startY = (int) (gameApp.getHeight() / 2 - Player.PLAYER_SIZE / 2);

        this.player = new Player(gameApp, startX, startY, GUN_LENGTH);
        this.console = new Console(gameApp);

        initializeGame();

        // Game loop timer to update gameplay elements
        gameLoopTimer = new Timer(20, e -> update());
        gameLoopTimer.start();
    }

    private void initializeGame() {
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
        if (backgroundImage != null)
            gameApp.add(backgroundImage);
        if (pauseButton != null) {
            gameApp.add(pauseButton.getRect());
            gameApp.add(pauseButton.getMessage());
        }

        // Show player
        player.show();
        player.resetMovement();

        // Add existing bullets to the screen
        for (Bullet bullet : bullets) {
            gameApp.add(bullet.getBulletShape());
        }

        // Show all enemies
        console.showAllEnemies();

        // Resume timers
        resumeGameLoop();
        console.resumeWaveTimer();
    }

    public void hide() {
        if (backgroundImage != null)
            gameApp.remove(backgroundImage);
        if (pauseButton != null) {
            gameApp.remove(pauseButton.getRect());
            gameApp.remove(pauseButton.getMessage());
        }

        // Hide player and bullets
        player.hide();
        player.resetMovement();
        for (Bullet bullet : bullets) {
            gameApp.remove(bullet.getBulletShape());
        }

        // Hide enemies and pause timers
        console.hideAllEnemies();
        pauseGameLoop();
        console.pauseWaveTimer();
    }

    public void pauseGameLoop() {
        if (gameLoopTimer != null) {
            gameLoopTimer.stop();
        }
    }

    public void resumeGameLoop() {
        if (gameLoopTimer != null && !gameLoopTimer.isRunning()) {
            gameLoopTimer.start();
        }
    }

    public void handleKeyPress(KeyEvent e) {
        player.handleKeyPress(e);

        // Trigger GameOverScreen with 'Q'
        if (e.getKeyCode() == KeyEvent.VK_Q) {
            triggerGameOver();
        }
    }

    public void handleKeyRelease(KeyEvent e) {
        player.handleKeyRelease(e);
    }

    public void updateAiming(MouseEvent e) {
        player.updateAiming(e);
    }

    public void handleShooting(MouseEvent e) {
        if (Bullet.canShoot() && bullets.size() < MAX_BULLETS) {
            double playerCenterX = player.getCenterX();
            double playerCenterY = player.getCenterY();
            double mouseX = e.getX();
            double mouseY = e.getY();

            // Calculate direction of bullet
            double angle = Math.atan2(mouseY - playerCenterY, mouseX - playerCenterX);
            double directionX = Math.cos(angle);
            double directionY = Math.sin(angle);

            Bullet bullet = new Bullet(gameApp, this, playerCenterX, playerCenterY, directionX, directionY);
            bullets.add(bullet);
        }
    }

    public void removeBullet(Bullet bullet) {
        bullets.remove(bullet);
        gameApp.remove(bullet.getBulletShape());
    }

    private void update() {
        // Update player movement
        player.updateMovement();

        // Update enemies to follow the player
        console.updateEnemies(player);

        // Update bullets
        for (int i = bullets.size() - 1; i >= 0; i--) {
            Bullet bullet = bullets.get(i);
            bullet.updatePosition();
        }

        // Check for collisions
        checkCollisions();

        // Check for player and enemy collisions
        checkPlayerEnemyCollisions();

        // Trigger Game Over when health reaches 0
        if (!player.isAlive()) {
            triggerGameOver();
        }
    }

    private void checkPlayerEnemyCollisions() {
        for (Enemy enemy : console.getEnemies()) {
            if (isPlayerCollidingWithEnemy(player, enemy)) {
                player.updateHealth(10, 0); // Decrease health by 10
                console.removeEnemy(enemy); // Optionally remove the enemy upon collision
                break;
            }
        }
    }

    private boolean isPlayerCollidingWithEnemy(Player player, Enemy enemy) {
        double playerX = player.getCenterX();
        double playerY = player.getCenterY();
        double enemyX = enemy.getEnemyShape().getX() + Enemy.getEnemySize() / 2;
        double enemyY = enemy.getEnemyShape().getY() + Enemy.getEnemySize() / 2;

        double distance = Math.hypot(playerX - enemyX, playerY - enemyY);
        return distance < (Player.PLAYER_SIZE / 2 + Enemy.getEnemySize() / 2);
    }

    private void triggerGameOver() {
        pauseGameLoop();
        console.pauseWaveTimer();
        int finalScore = console.getEnemiesDefeated(); // Get the player's score
        gameApp.showGameOverScreen(finalScore); // Navigate to Game Over screen
    }

    private void checkCollisions() {
        for (int i = bullets.size() - 1; i >= 0; i--) {
            Bullet bullet = bullets.get(i);
            for (int j = console.getEnemies().size() - 1; j >= 0; j--) {
                Enemy enemy = console.getEnemies().get(j);
                if (isBulletCollidingWithEnemy(bullet, enemy)) {
                    console.removeEnemy(enemy);
                    bullet.createImpactAnimation();
                    bullet.destroy();
                    break;
                }
            }
        }
    }

    private boolean isBulletCollidingWithEnemy(Bullet bullet, Enemy enemy) {
        double bulletX = bullet.getBulletShape().getX() + Bullet.getBulletSize() / 2;
        double bulletY = bullet.getBulletShape().getY() + Bullet.getBulletSize() / 2;
        double enemyX = enemy.getEnemyShape().getX() + Enemy.getEnemySize() / 2;
        double enemyY = enemy.getEnemyShape().getY() + Enemy.getEnemySize() / 2;

        double distance = Math.hypot(bulletX - enemyX, bulletY - enemyY);
        return distance < Bullet.getBulletSize() / 2 + Enemy.getEnemySize() / 2;
    }

    public Player getPlayer() {
        return player;
    }
}
