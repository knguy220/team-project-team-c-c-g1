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
    private PowerUps powerUps;
    private String difficulty;


    private int score = 0; // Player's score
    private GLabel scoreLabel; // Score label for display
    private GLabel hazmatCooldownLabel;
    private GLabel flySwatCooldownLabel;
    private GLabel bugRepellentCooldownLabel;

    // Fields for Hazmat Suit
    private GOval hazmatShield;
    private long hazmatEndTime = 0; // Tracks when the hazmat shield expires

    public StartGame(GameApp gameApp, String difficulty) {
        this.gameApp = gameApp;
        this.difficulty = difficulty;
        // Other initialization code
        
        int screenWidth = (int) gameApp.getWidth();
        int screenHeight = (int) gameApp.getHeight();

        this.gameMap = new Map(screenWidth, screenHeight);
        this.bullets = new ArrayList<>();
        this.console = new Console(gameApp, gameMap);

        // Find a valid starting position for the player
        int[] startPos = findValidStartPosition();
        this.player = new Player(gameApp, startPos[0], startPos[1], GUN_LENGTH, gameMap);

     // Instantiate PowerUps with StartGame passed as an argument
        this.powerUps = new PowerUps(player, gameApp, this);
        player.setPowerUps(powerUps); // Link PowerUps to the player
        initializeGame();

        // Game loop timer to update gameplay elements
        gameLoopTimer = new Timer(30, e -> update());
        gameLoopTimer.start();
    }

    private int[] findValidStartPosition() {
        int startX, startY;
        int maxAttempts = 100;
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

        player.initialize();

        gameMap.render(gameApp);

        // Score label
        scoreLabel = new GLabel("Score: 0");
        scoreLabel.setFont("Arial-Bold-20");
        scoreLabel.setColor(Color.WHITE);
        scoreLabel.setLocation(10, 40);
        gameApp.add(scoreLabel);
        
        GLabel difficultyLabel = new GLabel("Difficulty: " + difficulty, 10, 60); // Show difficulty
        difficultyLabel.setFont("Arial-Bold-18");
        difficultyLabel.setColor(Color.WHITE);
        gameApp.add(difficultyLabel);

        // Pause button
        pauseButton = new GButton("Pause", (int) (gameApp.getWidth() - 100), 20, 80, 30, Color.BLACK, Color.WHITE);
        pauseButton.addActionListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                gameApp.showPauseScreen();
            }
        });
        gameApp.add(pauseButton.getRect());
        gameApp.add(pauseButton.getMessage());

        // Cooldown Labels
        hazmatCooldownLabel = new GLabel("Hazmat: Ready");
        hazmatCooldownLabel.setFont("Arial-Bold-16");
        hazmatCooldownLabel.setColor(Color.YELLOW);
        hazmatCooldownLabel.setLocation(10, 90);
        gameApp.add(hazmatCooldownLabel);

        flySwatCooldownLabel = new GLabel("Fly Swat: Ready");
        flySwatCooldownLabel.setFont("Arial-Bold-16");
        flySwatCooldownLabel.setColor(Color.CYAN);
        flySwatCooldownLabel.setLocation(10, 110);
        gameApp.add(flySwatCooldownLabel);

        bugRepellentCooldownLabel = new GLabel("Bug Repellent: Ready");
        bugRepellentCooldownLabel.setFont("Arial-Bold-16");
        bugRepellentCooldownLabel.setColor(Color.GREEN);
        bugRepellentCooldownLabel.setLocation(10, 130);
        gameApp.add(bugRepellentCooldownLabel);
    }

    private void updateCooldownLabels() {
        hazmatCooldownLabel.setLabel("Hazmat: " +
            (powerUps.getHazmatCooldown() == 0 ? "Ready" : powerUps.getHazmatCooldown() / 1000 + "s"));
        flySwatCooldownLabel.setLabel("Fly Swat: " +
            (powerUps.getFlySwatCooldown() == 0 ? "Ready" : powerUps.getFlySwatCooldown() / 1000 + "s"));
        bugRepellentCooldownLabel.setLabel("Bug Repellent: " +
            (powerUps.getBugRepellentCooldown() == 0 ? "Ready" : powerUps.getBugRepellentCooldown() / 1000 + "s"));
    }

    public void activateHazmatShield(GOval shield, long endTime) {
        this.hazmatShield = shield;
        this.hazmatEndTime = endTime;
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

        // Update power-up cooldowns
        powerUps.updateCooldowns();
        updateCooldownLabels();

        // Update hazmat shield
        if (hazmatShield != null) {
            if (System.currentTimeMillis() > hazmatEndTime) {
                gameApp.remove(hazmatShield);
                hazmatShield = null;
            } else {
                hazmatShield.setLocation(player.getCenterX() - 50, player.getCenterY() - 50);
                List<Enemy> enemies = new ArrayList<>(console.getEnemies());
                for (Enemy enemy : enemies) {
                    if (isCollidingWithShield(hazmatShield, enemy)) {
                        console.removeEnemy(enemy, true); // Use Console's removeEnemy
                    }
                }
            }
        }

        if (!player.isAlive()) {
            triggerGameOver();
        }
    }

    private boolean isCollidingWithShield(GOval shield, Enemy enemy) {
        double shieldCenterX = shield.getX() + shield.getWidth() / 2;
        double shieldCenterY = shield.getY() + shield.getHeight() / 2;

        double enemyCenterX = enemy.getEnemyShape().getX() + Enemy.getEnemySize() / 2;
        double enemyCenterY = enemy.getEnemyShape().getY() + Enemy.getEnemySize() / 2;

        double distance = Math.hypot(shieldCenterX - enemyCenterX, shieldCenterY - enemyCenterY);
        return distance < shield.getWidth() / 2;
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

    public void updateScore(int points) {
        score += points;
        scoreLabel.setLabel("Score: " + score);
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

    public Player getPlayer() {
        return player;
    }

    public Console getConsole() {
        return console;
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

            // Play gun sound reliably
            gameApp.playGunSound();
        }
    }


    public void removeBullet(Bullet bullet) {
        bullets.remove(bullet);
        gameApp.remove(bullet.getBulletShape());
    }

    public void checkingPlayerAndMedKit() {
        if (player.getPressedE() && console.checkPlayerMedKit(player)) {
            console.healPlayer(player);
            console.removeMedKit();
        }
    }
}

