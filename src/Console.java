//handles updating the game
		
		//adding enemies 
		// spawning boosts
		
		//handles collisions
import java.util.ArrayList;
import java.util.List;

public class Console {
    private GameApp gameApp;
    private List<Enemy> enemies;
    private int enemiesDefeated;

    public Console(GameApp gameApp) {
        this.gameApp = gameApp;
        this.enemies = new ArrayList<>();
        this.enemiesDefeated = 0; // Initialize counter
    }

    // Method to spawn a new enemy
    public void spawnEnemy() {
        double x = Math.random() > 0.5 ? -50 : gameApp.getWidth() + 50; // Off-screen X
        double y = Math.random() > 0.5 ? -50 : gameApp.getHeight() + 50; // Off-screen Y
        enemies.add(new Enemy(gameApp, x, y));
    }

    // Method to remove an enemy
    public void removeEnemy(Enemy enemy) {
        gameApp.remove(enemy.getEnemyShape());
        enemies.remove(enemy);
        enemiesDefeated++; // Increment counter when an enemy is removed
    }

    // Update enemies' positions to follow the player
    public void updateEnemies(Player player) {
        for (Enemy enemy : enemies) {
            enemy.moveTowardsPlayer(player.getCenterX(), player.getCenterY());
        }
    }

    // Show all enemies
    public void showAllEnemies() {
        for (Enemy enemy : enemies) {
            gameApp.add(enemy.getEnemyShape());
        }
    }

    // Hide all enemies
    public void hideAllEnemies() {
        for (Enemy enemy : enemies) {
            gameApp.remove(enemy.getEnemyShape());
        }
    }

    // Get the list of current enemies
    public List<Enemy> getEnemies() {
        return enemies;
    }

    // Get the number of defeated enemies
    public int getEnemiesDefeated() {
        return enemiesDefeated;
    }
}

