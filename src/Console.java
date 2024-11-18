//handles updating the game
		
		//adding enemies 
		// spawning boosts
		
		//handles collisions
import java.util.ArrayList;
import java.util.List;

public class Console {
    private GameApp gameApp;
    private List<Enemy> enemies;

    public Console(GameApp gameApp) {
        this.gameApp = gameApp;
        this.enemies = new ArrayList<>();
    }

     //Spawns a new enemy off-screen at a random position.
    public void spawnEnemy() {
        double x, y;
        double screenWidth = gameApp.getWidth();
        double screenHeight = gameApp.getHeight();

        // Randomly decide which side of the screen the enemy spawns
        switch ((int) (Math.random() * 4)) {
            case 0: // Top of the screen
                x = Math.random() * screenWidth;
                y = -Enemy.getEnemySize();
                break;
            case 1: // Bottom of the screen
                x = Math.random() * screenWidth;
                y = screenHeight + Enemy.getEnemySize();
                break;
            case 2: // Left of the screen
                x = -Enemy.getEnemySize();
                y = Math.random() * screenHeight;
                break;
            default: // Right of the screen
                x = screenWidth + Enemy.getEnemySize();
                y = Math.random() * screenHeight;
                break;
        }

        // Create and spawn the enemy
        Enemy newEnemy = new Enemy(gameApp, x, y);
        enemies.add(newEnemy);
        newEnemy.spawn();
    }

     //Updates the behavior of all enemies to follow the player.
    public void updateEnemies(Player player) {
        for (Enemy enemy : enemies) {
            enemy.updateBehavior(player);
        }
    }

     //Returns the list of currently active enemies.
    public List<Enemy> getEnemies() {
        return enemies;
    }

     //Removes an enemy from the game.
    public void removeEnemy(Enemy enemy) {
        enemies.remove(enemy);
        enemy.destroy();
    }

     //Clears all enemies from the game.
    public void clearEnemies() {
        for (Enemy enemy : enemies) {
            enemy.destroy();
        }
        enemies.clear();
    }

     //Hides all enemies (used when pausing gameplay).
    public void hideAllEnemies() {
        for (Enemy enemy : enemies) {
            enemy.destroy();
        }
    }

     //Respawns all enemies on the screen (used when resuming gameplay).
    public void showAllEnemies() {
        for (Enemy enemy : enemies) {
            enemy.spawn();
        }
    }
}
