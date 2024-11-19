//handles updating the game
import java.util.ArrayList;
import java.util.List;
import javax.swing.Timer;
import acm.graphics.GLabel;
import acm.graphics.GRect;

public class Console {
    private GameApp gameApp;
    private List<Enemy> enemies;
    private int enemiesDefeated;
    private int waveNumber;
    private boolean isSpawningWave;
    private Timer waveTimer;
    private GLabel waveLabel;
    private GRect waveLabelBackground;

    private static final double BASE_ENEMY_SPEED = 3.0; // Increased base speed
    private static final int BASE_SPAWN_DELAY = 600;   // Faster spawn rate

    public Console(GameApp gameApp) {
        this.gameApp = gameApp;
        this.enemies = new ArrayList<>();
        this.enemiesDefeated = 0;
        this.waveNumber = 1; 
        this.isSpawningWave = false;

        // Initialize wave label background
        waveLabelBackground = new GRect(gameApp.getWidth() / 2.0 - 75, 20, 150, 40);
        waveLabelBackground.setFilled(true);
        waveLabelBackground.setColor(java.awt.Color.BLACK);
        gameApp.add(waveLabelBackground);

        // Initialize wave label
        waveLabel = new GLabel("", gameApp.getWidth() / 2.0, 50);
        waveLabel.setFont("Arial-Bold-24");
        waveLabel.setColor(java.awt.Color.WHITE);
        gameApp.add(waveLabel);

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

        // Update and display wave number
        waveLabel.setLabel("Wave " + waveNumber);
        waveLabel.setLocation(
            (gameApp.getWidth() - waveLabel.getWidth()) / 2, 
            waveLabelBackground.getY() + waveLabel.getAscent() + 10
        );

        int enemyCount = Math.min(5 + waveNumber * 2, 50); // Increase enemies per wave
        int spawnDelay = Math.max(100, BASE_SPAWN_DELAY - waveNumber * 50); // Reduce spawn delay per wave
        double enemySpeed = BASE_ENEMY_SPEED + waveNumber * 0.5; // Faster enemies per wave

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

        // Delay before the next wave starts
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
        Enemy enemy = new Enemy(gameApp, x, y);
        enemy.setSpeed(speed);
        enemies.add(enemy);
        gameApp.add(enemy.getEnemyShape());
    }

    /**
     * Removes an enemy from the game.
     */
    public void removeEnemy(Enemy enemy) {
        gameApp.remove(enemy.getEnemyShape());
        enemies.remove(enemy);
        enemiesDefeated++;
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
        }
    }

    /**
     * Hides all enemies from the screen.
     */
    public void hideAllEnemies() {
        for (Enemy enemy : enemies) {
            gameApp.remove(enemy.getEnemyShape());
        }
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
