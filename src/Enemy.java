import acm.graphics.*;
import java.awt.Color;

public class Enemy {
	private GameApp gameApp;
	private GRect enemyShape;
	private static final int ENEMY_SIZE = 40; // Enemy size
	private double x, y;
	private double speedX, speedY;
	private static final double ENEMY_SPEED = 2.0; // Speed of the enemy

	public Enemy(GameApp gameApp, double x, double y) {
		this.gameApp = gameApp;
		this.x = x;
		this.y = y;

		// Create the enemy shape
		enemyShape = new GRect(x, y, ENEMY_SIZE, ENEMY_SIZE);
		enemyShape.setFilled(true);
		enemyShape.setColor(Color.RED);
	}

	// Spawns the enemy off-screen.
	public void spawn() {
		gameApp.add(enemyShape);
	}

	// Updates the enemy's behavior to follow the player.
	public void updateBehavior(Player player) {
		double playerX = player.getCenterX();
		double playerY = player.getCenterY();

		// Calculate direction toward the player
		double deltaX = playerX - x;
		double deltaY = playerY - y;
		double magnitude = Math.hypot(deltaX, deltaY);

		// Normalize direction and apply speed
		speedX = (deltaX / magnitude) * ENEMY_SPEED;
		speedY = (deltaY / magnitude) * ENEMY_SPEED;

		// Update enemy position
		x += speedX;
		y += speedY;

		// Move the enemy shape
		enemyShape.setLocation(x, y);
	}

	// Destroys the enemy by removing it from the screen.
	public void destroy() {
		gameApp.remove(enemyShape);
	}

	// Returns the shape of the enemy for collision detection.
	public GRect getEnemyShape() {
		return enemyShape;
	}

	// Returns the size of the enemy.
	public static int getEnemySize() {
		return ENEMY_SIZE;
	}
}
