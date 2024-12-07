import acm.graphics.*;
import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.Timer;

public class StartGame {
    private GameApp gameApp;
    private Map gameMap;
    private GButton pauseButton;
    private final int GUN_LENGTH = 40;
    private Player player;
    private List<Bullet> bullets;
    private static final int MAX_BULLETS = 20;
    private Console console;
    private Timer gameLoopTimer;

    private int score = 0; // Player's score
    private GLabel scoreLabel; // Score label for display

    public StartGame(GameApp gameApp) {
        this.gameApp = gameApp;

        int screenWidth = (int) gameApp.getWidth();
        int screenHeight = (int) gameApp.getHeight();

        this.gameMap = new Map(screenWidth, screenHeight);
        this.bullets = new ArrayList<>();
        this.console = new Console(gameApp, gameMap);

        // Find a valid starting position for the player
        int[] startPos = findValidStartPosition();
        this.player = new Player(gameApp, startPos[0], startPos[1], GUN_LENGTH, gameMap);

        initializeGame();

        // Game loop timer to update gameplay elements
        gameLoopTimer = new Timer(30, e -> update());
        gameLoopTimer.start();
    }

    private int[] findValidStartPosition() {
        int startX, startY;
        int maxAttempts = 100; // Avoid infinite loops
        int attempts = 0;

        do {
            startX = (int) (Math.random() * (gameMap.getCols() * Map.getTileSize() - Player.PLAYER_SIZE));
            startY = (int) (Math.random() * (gameMap.getRows() * Map.getTileSize() - Player.PLAYER_SIZE));
            attempts++;
        } while ((isWall(startX, startY) || !hasClearSpace(startX, startY)) && attempts < maxAttempts);

        if (attempts >= maxAttempts) {
            System.out.println("Failed to find a valid spawn position, spawning at center.");
            startX = gameMap.getCols() * Map.getTileSize() / 2;
            startY = gameMap.getRows() * Map.getTileSize() / 2;
        }

        return new int[]{startX, startY};
    }

    private boolean isWall(int x, int y) {
        int tileX = x / Map.getTileSize();
        int tileY = y / Map.getTileSize();
        return tileX >= 0 && tileX < gameMap.getCols() &&
               tileY >= 0 && tileY < gameMap.getRows() &&
               gameMap.isWall(tileX, tileY);
    }

    private boolean hasClearSpace(int x, int y) {
        int tileX = x / Map.getTileSize();
        int tileY = y / Map.getTileSize();

        // Check neighboring tiles for obstacles
        for (int dx = -1; dx <= 1; dx++) {
            for (int dy = -1; dy <= 1; dy++) {
                int neighborX = tileX + dx;
                int neighborY = tileY + dy;
                if (neighborX >= 0 && neighborX < gameMap.getCols() &&
                    neighborY >= 0 && neighborY < gameMap.getRows() &&
                    gameMap.isWall(neighborX, neighborY)) {
                    return false;
                }
            }
        }
        return true;
    }

    private void initializeGame() {
        gameApp.removeAll();

        // Initialize player
        player.initialize();

        gameMap.render(gameApp);

        // Initialize score label
        scoreLabel = new GLabel("Score: 0");
        scoreLabel.setFont("Arial-Bold-20");
        scoreLabel.setColor(Color.WHITE);
        scoreLabel.setLocation(10, 50);
        gameApp.add(scoreLabel);

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
        if (pauseButton != null) {
            gameApp.add(pauseButton.getRect());
            gameApp.add(pauseButton.getMessage());
        }

        player.show();
        player.resetMovement();
        gameApp.add(scoreLabel);

        for (Bullet bullet : bullets) {
            gameApp.add(bullet.getBulletShape());
        }

        console.showAllEnemies();
        resumeGameLoop();
        console.resumeWaveTimer();
    }

    public void hide() {
        if (pauseButton != null) {
            gameApp.remove(pauseButton.getRect());
            gameApp.remove(pauseButton.getMessage());
        }

        player.hide();
        player.resetMovement();
        for (Bullet bullet : bullets) {
            gameApp.remove(bullet.getBulletShape());
        }

        gameApp.remove(scoreLabel);

        console.hideAllEnemies();
        pauseGameLoop();
        console.pauseWaveTimer();
    }

    public void updateScore(int points) {
        score += points;
        scoreLabel.setLabel("Score: " + score);
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

            double angle = Math.atan2(mouseY - playerCenterY, mouseX - playerCenterX);
            double directionX = Math.cos(angle);
            double directionY = Math.sin(angle);

            Bullet bullet = new Bullet(gameApp, this, playerCenterX, playerCenterY, directionX, directionY);
            bullets.add(bullet);
            gameApp.playGunSound();
        }
    }

    public void removeBullet(Bullet bullet) {
        bullets.remove(bullet);
        gameApp.remove(bullet.getBulletShape());
    }

    private void update() {
        player.updateMovement();
        console.updateEnemies(player);

        for (int i = bullets.size() - 1; i >= 0; i--) {
            Bullet bullet = bullets.get(i);
            bullet.updatePosition();
        }

        checkCollisions();
        checkPlayerEnemyCollisions();
        checkingPlayerAndMedKit();

        if (!player.isAlive()) {
            triggerGameOver();
        }
    }

    private void checkPlayerEnemyCollisions() {
        for (Enemy enemy : console.getEnemies()) {
            if (isPlayerCollidingWithEnemy(player, enemy)) {
                player.updateHealth(enemy.getDamage(), 0);
                console.removeEnemy(enemy, false);
                break;
            }
        }
    }

    private void checkingPlayerAndMedKit() {
        if (player.getPressedE() && console.checkPlayerMedKit(player)) {
            console.healPlayer(player);
            console.removeMedKit();
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
        int finalScore = console.getEnemiesDefeated();
        gameApp.showGameOverScreen(finalScore);
    }

    private void checkCollisions() {
        for (int i = bullets.size() - 1; i >= 0; i--) {
            Bullet bullet = bullets.get(i);
            for (int j = console.getEnemies().size() - 1; j >= 0; j--) {
                Enemy enemy = console.getEnemies().get(j);
                if (isBulletCollidingWithEnemy(bullet, enemy)) {
                    enemy.updateHealth(10);
                    if (!enemy.isAlive()) {
                        console.removeEnemy(enemy, true);
                        updateScore(1);
                    }
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
        return distance < (Bullet.getBulletSize() / 2 + Enemy.getEnemySize() / 2);
    }
    
    public Player getPlayer() {
        return player;
    }
}
