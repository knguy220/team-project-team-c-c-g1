import acm.graphics.*;
import java.awt.Color;

public class Enemy {
    private GOval enemyShape;
    private double speed;
    private int health;
    private int damage;
    private GImage body; 
    private double offSet;
    private String bodyName;

    public static final int ENEMY_SIZE = 30;

    public Enemy(GameApp gameApp, double startX, double startY) {
    	offSet = 8;
        // Create the enemy shape
        enemyShape = new GOval(startX, startY, ENEMY_SIZE, ENEMY_SIZE);
        enemyShape.setFilled(true);
        enemyShape.setColor(Color.RED);
        enemyShape.setVisible(false);
        body = new GImage("RedLeft.png",startX-offSet,startY-offSet);
        body.scale(0.08);

        // Define default speed for the enemy
        this.speed = 2.0;

        // Add the enemy to the game
        gameApp.add(enemyShape);
        gameApp.add(body);
    }
    public Enemy(GameApp gameApp, double startX, double startY, double s, int h, int d, Color c) {
        // Create the enemy shape
    	offSet = 8;
        enemyShape = new GOval(startX, startY, ENEMY_SIZE, ENEMY_SIZE);
        enemyShape.setFilled(true);
        enemyShape.setColor(c);
        enemyShape.setVisible(false);
        body = new GImage("RedLeft.png",startX-offSet,startY-offSet);
        body.scale(0.08);

        // Define speed, health, and damage for the enemy
        this.speed = s;
        this.health = h;
        this.damage = d;

        // Add the enemy to the game
        gameApp.add(enemyShape);
        gameApp.add(body);
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
        if (bodyName == "GreenLeft.png" && playerX > enemyShape.getX()) {
        	setBody("GreenRight.png");
        } else if (bodyName == "GreenRight.png" && playerX < enemyShape.getX()) {
        	setBody("GreenLeft.png");
        }
        if (bodyName == "BlueLeft.png" && playerX > enemyShape.getX()) {
        	setBody("BlueRight.png");
        } else if (bodyName == "BlueRight.png" && playerX < enemyShape.getX()) {
        	setBody("BlueLeft.png");
        }
        if (bodyName == "RedLeft.png" && playerX > enemyShape.getX()) {
        	setBody("RedRight.png");
        } else if (bodyName == "RedRight.png" && playerX < enemyShape.getX()) {
        	setBody("RedLeft.png");
        }
        body.setLocation(enemyShape.getX()-offSet, enemyShape.getY()-offSet);
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
    public void setBody(String s) {
    	body.setImage(s);
    	bodyName = s;
    }
    public void setOffSet(double o) {
    	offSet = o;
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
    public GImage getBody() {
    	return body;
    }
    
    /**
     * Removes the enemy from the screen.
     */
    public void removeFromGame(GameApp gameApp) {
        gameApp.remove(enemyShape);
        gameApp.remove(body);
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