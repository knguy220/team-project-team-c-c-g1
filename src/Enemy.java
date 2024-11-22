import acm.graphics.*;
import java.awt.Color;

public class Enemy {
    private GOval enemyShape;
    private double speed;
    private int health;
    private int damage;

    public static final int ENEMY_SIZE = 30;

    public Enemy(GameApp gameApp, double startX, double startY) {
        // Create the enemy shape
        enemyShape = new GOval(startX, startY, ENEMY_SIZE, ENEMY_SIZE);
        enemyShape.setFilled(true);
        enemyShape.setColor(Color.RED);

        // Define default speed for the enemy
        this.speed = 2.0;

        // Add the enemy to the game
        gameApp.add(enemyShape);
    }
    public Enemy(GameApp gameApp, double startX, double startY, double s, int h, int d, Color c) {
        // Create the enemy shape
        enemyShape = new GOval(startX, startY, ENEMY_SIZE, ENEMY_SIZE);
        enemyShape.setFilled(true);
        enemyShape.setColor(c);

        // Define speed, health, and damage for the enemy
        this.speed = s;
        this.health = h;
        this.damage = d;

        // Add the enemy to the game
        gameApp.add(enemyShape);
    }

    /**
     * Moves the enemy toward the player's position.
     *
     * @param playerX The X-coordinate of the player's center.
     * @param playerY The Y-coordinate of the player's center.
     */
    public void moveTowardsPlayer(double playerX, double playerY) {
        double enemyCenterX = enemyShape.getX() + ENEMY_SIZE / 2;
        double enemyCenterY = enemyShape.getY() + ENEMY_SIZE / 2;

        // Calculate direction vector
        double directionX = playerX - enemyCenterX;
        double directionY = playerY - enemyCenterY;

        // Normalize the direction vector
        double length = Math.sqrt(directionX * directionX + directionY * directionY);
        if (length != 0) {
            directionX /= length;
            directionY /= length;
        }

        // Move the enemy in the direction of the player
        enemyShape.move(directionX * speed, directionY * speed);
    }

    /**
     * Sets the speed of the enemy.
     *
     * @param speed The new speed value for the enemy.
     */
    public void setSpeed(double speed) {
        this.speed = speed;
    }
    // Health and Damage values for the enemy
    public void setHealth(int h) {
    	this.health = h;
    }
    public void setDamage(int d) {
    	this.damage = d;
    }
    public void setColor(Color c) {
    	enemyShape.setColor(c);
    }

    /**
     * Returns the enemy's shape for collision detection.
     */
    public GOval getEnemyShape() {
        return enemyShape;
    }

    /**
     * Returns the size of the enemy for collision detection.
     */
    public static int getEnemySize() {
        return ENEMY_SIZE;
    }
    
    //returns damage of enemy
    public int getDamage() {
    	return damage;
    }
    
    /**
     * Removes the enemy from the screen.
     */
    public void removeFromGame(GameApp gameApp) {
        gameApp.remove(enemyShape);
    }
    
    //Updates the health
    public void updateHealth(int d) {
    	health -= d;
    	health = Math.max(health, 0);
    }
    public boolean isAlive() {
    	return health > 0;
    }
}