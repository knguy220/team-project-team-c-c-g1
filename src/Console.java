import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.swing.Timer;
import acm.graphics.GLabel;

public class Console {
    private GameApp gameApp;
    private List<Enemy> enemies;
    private int enemiesDefeated;
    private int waveNumber;
    private boolean isSpawningWave;
    private Timer waveTimer;
    private GLabel waveLabel;
    private Boosts medKits;
    private Map map;

    private static final double BASE_ENEMY_SPEED = 6.0; // Increased base speed
    private static final int BASE_SPAWN_DELAY = 600;   // Faster spawn rate

    public Console(GameApp gameApp) {
        this.gameApp = gameApp;
        this.enemies = new ArrayList<>();
        this.enemiesDefeated = 0;
        this.waveNumber = 0;
        this.isSpawningWave = false;
        this.medKits = new Boosts(gameApp, false);

        // Initialize wave label
        waveLabel = new GLabel("", gameApp.getWidth() / 2.0, 50);
        waveLabel.setFont("Arial-Bold-24");
        waveLabel.setColor(java.awt.Color.RED);
        gameApp.add(waveLabel);

        // Spawn 2 medkits at the start
        spawnMedKits(2);

        // Initialize wave spawning timer
        waveTimer = new Timer(1000, e -> startNextWave()); // Short delay to prepare for the first wave
        waveTimer.setRepeats(false); // Only runs once at the start
        waveTimer.start();
    }

    public Console(GameApp gameApp, Map m) {
        this.gameApp = gameApp;
        this.enemies = new ArrayList<>();
        this.enemiesDefeated = 0;
        this.waveNumber = 0;
        this.isSpawningWave = false;
        this.map = m;
        this.medKits = new Boosts(gameApp, false, m);

        // Initialize wave label
        waveLabel = new GLabel("", gameApp.getWidth() / 2.0, 50);
        waveLabel.setFont("Arial-Bold-24");
        waveLabel.setColor(java.awt.Color.RED);
        gameApp.add(waveLabel);

        // Spawn 2 medkits at the start
        spawnMedKits(2);

        // Initialize wave spawning timer
        waveTimer = new Timer(1000, e -> startNextWave()); // Short delay to prepare for the first wave
        waveTimer.setRepeats(false); // Only runs once at the start
        waveTimer.start();
    }

    /**
     * Starts the next wave of enemies.
     */
    public void startNextWave() {
        if (gameApp.getGameState() != GameApp.GameState.ACTIVE || isSpawningWave) {
            return;
        }

        isSpawningWave = true;
        waveNumber++;

        // Update wave number display
        waveLabel.setLabel("Wave " + waveNumber);
        waveLabel.setLocation(
            (gameApp.getWidth() - waveLabel.getWidth()) / 2,
            60 // Ensure it's below health bar and score
        );

        // Bring wave label to front
        gameApp.remove(waveLabel);
        gameApp.add(waveLabel);

        // Logic for spawning enemies
        int enemyCount = Math.min(5 + waveNumber * 2, 30);
        int spawnDelay = Math.max(100, BASE_SPAWN_DELAY - waveNumber * 50);
        double enemySpeed = BASE_ENEMY_SPEED + waveNumber;

        for (int i = 0; i < enemyCount; i++) {
            int finalI = i;
            Timer spawnTimer = new Timer(spawnDelay * finalI, e -> {
                if (gameApp.getGameState() == GameApp.GameState.ACTIVE) {
                    spawnEnemy(enemySpeed);
                }
            });
            spawnTimer.setRepeats(false);
            spawnTimer.start();
        }

        // Spawn 2 medkits every 5 waves
        if (waveNumber % 5 == 0) {
            spawnMedKits(2);
        }

        // Delay before next wave starts
        Timer nextWaveTimer = new Timer(15000, e -> startNextWave());
        nextWaveTimer.setRepeats(false);
        nextWaveTimer.start();

        isSpawningWave = false;
    }

    /**
     * Spawns a standard enemy at a random off-screen position with specified speed.
     */
    public void spawnEnemy(double speed) {
        double x = Math.random() > 0.5 ? -50 : gameApp.getWidth() + 50; // Off-screen X
        double y = Math.random() > 0.5 ? -50 : gameApp.getHeight() + 50; // Off-screen Y
        Random rand = new Random();
        int type = rand.nextInt(3) + 1;
        Enemy enemy = new Enemy(gameApp, x, y);
        if (type == 3) {
            enemy.setSpeed(speed * 0.25);
            enemy.setDamage(25);
            enemy.setHealth(50);
            enemy.setColor(Color.green);
            enemy.setBody("GreenLeft.png");
            enemy.setOffSet(10);
        } else if (type == 2) {
            enemy.setSpeed(speed * 0.5);
            enemy.setDamage(10);
            enemy.setHealth(25);
            enemy.setColor(Color.blue);
            enemy.setBody("BlueLeft.png");
            enemy.setOffSet(12);
        } else if (type == 1) {
            enemy.setSpeed(speed * 0.75);
            enemy.setDamage(1);
            enemy.setHealth(10);
            enemy.setColor(Color.red);
            enemy.setBody("RedLeft.png");
            enemy.setOffSet(14);
        }
        enemies.add(enemy);
        gameApp.add(enemy.getEnemyShape());
        gameApp.add(enemy.getBody());
    }

    /**
     * Removes an enemy from the game.
     */
    public void removeEnemy(Enemy enemy, boolean isDead) {
        gameApp.remove(enemy.getEnemyShape());
        gameApp.remove(enemy.getBody());
        enemies.remove(enemy);
        if (isDead) {
            enemiesDefeated++;
        }
        createDamageNumber(enemy.getEnemyShape().getX() + Enemy.ENEMY_SIZE / 2,
                enemy.getEnemyShape().getY() + Enemy.ENEMY_SIZE / 2,
                "10"); // Example damage value
    }

    /**
     * Spawns exactly the specified number of medkits.
     */
    public void spawnMedKits(int count) {
        for (int i = 0; i < count; i++) {
            double x = Math.random() * (gameApp.getWidth() - 100) + 50;
            double y = Math.random() * (gameApp.getHeight() - 100) + 50;
            medKits.createMedKit(x, y, 0.05);
        }
    }

    public boolean checkPlayerMedKit(Player p) {
        return medKits.medKitOverlapping(p);
    }

    public void healPlayer(Player p) {
        medKits.addHealth(p);
    }

    public void removeMedKit() {
        medKits.removeMedKit();
    }

    private void createDamageNumber(double x, double y, String damage) {
        // Determine the color of the damage number based on value
        int damageValue = Integer.parseInt(damage);
        Color damageColor;
        if (damageValue > 50) {
            damageColor = Color.RED; // High damage
        } else if (damageValue > 20) {
            damageColor = Color.ORANGE; // Medium damage
        } else {
            damageColor = Color.YELLOW; // Low damage
        }

        // Create the damage label
        GLabel damageLabel = new GLabel(damage);
        damageLabel.setFont("Arial-Bold-20"); // Base font size
        damageLabel.setColor(damageColor);
        damageLabel.setLocation(x - damageLabel.getWidth() / 2, y);
        gameApp.add(damageLabel);

        // Animation logic in a separate thread
        new Thread(() -> {
            try {
                for (int frame = 0; frame < 30; frame++) {
                    Thread.sleep(30);

                    // Move the damage number upwards with a slight curve
                    double offsetX = Math.sin(frame * 0.2) * 2; // Slight horizontal curve
                    damageLabel.move(offsetX, -2);

                    // Gradually fade out
                    Color currentColor = damageLabel.getColor();
                    int alpha = Math.max(0, currentColor.getAlpha() - 8); // Reduce alpha
                    damageLabel.setColor(new Color(
                            currentColor.getRed(),
                            currentColor.getGreen(),
                            currentColor.getBlue(),
                            alpha
                    ));

                    // Scale the damage number for emphasis in the first few frames
                    if (frame < 10) {
                        damageLabel.setFont("Arial-Bold-" + (20 + frame));
                    }
                }
                // Remove the damage label after animation
                gameApp.remove(damageLabel);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }

    /**
     * Updates enemy positions to follow the player.
     */
    public void updateEnemies(Player player) {
        for (Enemy enemy : enemies) {
            enemy.moveTowardsPlayer(player.getCenterX(), player.getCenterY());
        }
    }

    /**
     * Pauses wave spawning.
     */
    public void pauseWaveTimer() {
        if (waveTimer != null) {
            waveTimer.stop();
        }
    }

    /**
     * Resumes wave spawning.
     */
    public void resumeWaveTimer() {
        if (waveTimer != null && !waveTimer.isRunning()) {
            waveTimer.start();
        }
    }

    /**
     * Shows all enemies on the screen.
     */
    public void showAllEnemies() {
        for (Enemy enemy : enemies) {
            gameApp.add(enemy.getEnemyShape());
            gameApp.add(enemy.getBody());
        }
        medKits.showMedKits();
    }

    /**
     * Hides all enemies from the screen.
     */
    public void hideAllEnemies() {
        for (Enemy enemy : enemies) {
            gameApp.remove(enemy.getEnemyShape());
            gameApp.remove(enemy.getBody());
        }
        medKits.hideMedKits();
    }

    /**
     * Returns the list of current enemies.
     */
    public List<Enemy> getEnemies() {
        return enemies;
    }

    /**
     * Returns the number of defeated enemies.
     */
    public int getEnemiesDefeated() {
        return enemiesDefeated;
    }
}

