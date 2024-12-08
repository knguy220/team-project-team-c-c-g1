import acm.graphics.*;
import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

public class Player {
    private GameApp gameApp;
    private Map map;
    private GOval playerShape;
    private GLine gunLine;
    private GRect healthBarBackground;
    private GRect updatingHealthBar;
    private GImage body;
    private boolean pressedE;
    private PowerUps powerUps;

    public static final int PLAYER_SIZE = 40; 
    private static final int MAX_HEALTH = 100; 
    private static final int HEALTH_BAR_WIDTH = 200; 
    public static final int HEALTH_BAR_HEIGHT = 20; 

    private double x, y; // Position
    private double velocityX = 0;
    private double velocityY = 0;
    
    private int playerHealth = MAX_HEALTH;

    private boolean movingUp = false;
    private boolean movingDown = false;
    private boolean movingLeft = false;
    private boolean movingRight = false;

    private int gunLength;
    private double aimAngle = 0; // Default aiming angle

    public Player(GameApp gameApp, int startX, int startY, int gunLength, Map map) {
        this.gameApp = gameApp;
        this.map = map;
        this.x = startX;
        this.y = startY;
        this.gunLength = gunLength;

        // Create the player shape
        playerShape = new GOval(startX, startY, PLAYER_SIZE, PLAYER_SIZE);
        playerShape.setFilled(true);
        playerShape.setColor(Color.BLUE);
        playerShape.setVisible(false);

        // Create the player image
        body = new GImage("DezLeft.png", startX - 8, startY - 8);
        body.scale(0.08);

        // Create the gun line
        gunLine = new GLine(startX + PLAYER_SIZE / 2, startY + PLAYER_SIZE / 2, 
                            startX + PLAYER_SIZE / 2 + gunLength, startY + PLAYER_SIZE / 2);
        gunLine.setColor(Color.BLACK);

        // Create the health bar
        healthBarBackground = new GRect(10, 10, HEALTH_BAR_WIDTH, HEALTH_BAR_HEIGHT);
        healthBarBackground.setFilled(true);
        healthBarBackground.setColor(Color.GRAY);

        updatingHealthBar = new GRect(10, 10, HEALTH_BAR_WIDTH, HEALTH_BAR_HEIGHT);
        updatingHealthBar.setFilled(true);
        updatingHealthBar.setColor(Color.GREEN);
    }

    /**
     * Sets the PowerUps instance for the player.
     */
    public void setPowerUps(PowerUps powerUps) {
        this.powerUps = powerUps;
    }

    /**
     * Initializes the player and health bar on the screen.
     */
    public void initialize() {
        gameApp.add(playerShape);
        gameApp.add(body);        
        gameApp.add(gunLine);
        gameApp.add(healthBarBackground);
        gameApp.add(updatingHealthBar);
    }

    /**
     * Updates the player's movement and UI elements.
     */
    public void updateMovement() {
        // Reset velocity
        velocityX = 0;
        velocityY = 0;

        // Calculate movement direction based on flags
        if (movingUp) velocityY = -8; // Move up
        if (movingDown) velocityY = 8; // Move down
        if (movingLeft) velocityX = -8; // Move left
        if (movingRight) velocityX = 8; // Move right

        // Apply movement
        double newX = x + velocityX;
        double newY = y + velocityY;

        if (canMoveTo(newX, y)) x = newX;
        if (canMoveTo(x, newY)) y = newY;

        // Ensure the player stays within screen boundaries
        x = Math.max(0, Math.min(gameApp.getWidth() - PLAYER_SIZE, x));
        y = Math.max(0, Math.min(gameApp.getHeight() - PLAYER_SIZE, y));

        // Update the player's position on the screen
        playerShape.setLocation(x, y);
        body.setLocation(x - 8, y - 8);

        // Update gun line based on the new position
        updateGunLine();
    }

    private boolean canMoveTo(double newX, double newY) {
        // Convert player's bounding box corners to tile coordinates
        int tileTopLeftX = (int) (newX / Map.getTileSize());
        int tileTopLeftY = (int) (newY / Map.getTileSize());

        int tileTopRightX = (int) ((newX + PLAYER_SIZE - 1) / Map.getTileSize());
        int tileTopRightY = tileTopLeftY;

        int tileBottomLeftX = tileTopLeftX;
        int tileBottomLeftY = (int) ((newY + PLAYER_SIZE - 1) / Map.getTileSize());

        int tileBottomRightX = tileTopRightX;
        int tileBottomRightY = tileBottomLeftY;

        // Check if all corners are within bounds and not walls
        return isValidTile(tileTopLeftX, tileTopLeftY) &&
               isValidTile(tileTopRightX, tileTopRightY) &&
               isValidTile(tileBottomLeftX, tileBottomLeftY) &&
               isValidTile(tileBottomRightX, tileBottomRightY);
    }

    private boolean isValidTile(int tileX, int tileY) {
        // Ensure tile is within bounds and not a wall
        if (tileX < 0 || tileY < 0 || tileX >= map.getCols() || tileY >= map.getRows()) {
            return false;
        }
        return !map.isWall(tileX, tileY);
    }
    
    public double getAimAngle() {
        return aimAngle;
    }

    public void updateAiming(MouseEvent e) {
        double mouseX = e.getX();
        double mouseY = e.getY();

        // Calculate direction
        aimAngle = Math.atan2(mouseY - (y + PLAYER_SIZE / 2), mouseX - (x + PLAYER_SIZE / 2));

        // Update gun line
        updateGunLine();
    }

    private void updateGunLine() {
        double gunX = x + PLAYER_SIZE / 2 + gunLength * Math.cos(aimAngle);
        double gunY = y + PLAYER_SIZE / 2 + gunLength * Math.sin(aimAngle);

        gunLine.setStartPoint(x + PLAYER_SIZE / 2, y + PLAYER_SIZE / 2);
        gunLine.setEndPoint(gunX, gunY);
    }

    public void handleKeyPress(KeyEvent e) {
        int speed = 8; // Updated speed for faster movement
        switch (e.getKeyCode()) {
            case KeyEvent.VK_W:
                movingUp = true;
                break;
            case KeyEvent.VK_S:
                movingDown = true;
                break;
            case KeyEvent.VK_A:
                movingLeft = true;
                body.setImage("DezLeft.png");
                break;
            case KeyEvent.VK_D:
                movingRight = true;
                body.setImage("DezRight.png");
                break;
            case KeyEvent.VK_2:
                if (powerUps != null) powerUps.activateHazmatSuit();
                break;
            case KeyEvent.VK_3:
                if (powerUps != null) powerUps.activateFlySwat();
                break;
            case KeyEvent.VK_4:
                if (powerUps != null) powerUps.activateBugRepellent();
                break;
            case KeyEvent.VK_E:
                pressedE = true;
                break;
        }
    }

    public void handleKeyRelease(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_W:
                movingUp = false;
                velocityY = 0;
                break;
            case KeyEvent.VK_S:
                movingDown = false;
                velocityY = 0;
                break;
            case KeyEvent.VK_A:
                movingLeft = false;
                velocityX = 0;
                break;
            case KeyEvent.VK_D:
                movingRight = false;
                velocityX = 0;
                break;
            case KeyEvent.VK_E:
                pressedE = false;
                break;
        }
    }

    public void updateHealth(int damage, int medkit) {
        // Adjust health
        playerHealth += medkit;
        playerHealth = Math.min(playerHealth, MAX_HEALTH);

        playerHealth -= damage;
        playerHealth = Math.max(playerHealth, 0);

        // Update health bar width
        double healthBarWidth = (double) playerHealth / MAX_HEALTH * HEALTH_BAR_WIDTH;
        updatingHealthBar.setSize(healthBarWidth, HEALTH_BAR_HEIGHT);
    }

    public boolean isAlive() {
        return playerHealth > 0;
    }

    public void resetMovement() {
        movingUp = false;
        movingDown = false;
        movingLeft = false;
        movingRight = false;
        velocityX = 0;
        velocityY = 0;
    }

    public void stopMoving() {
        velocityX = 0;
        velocityY = 0;
    }

    public void show() {
        gameApp.add(playerShape);
        gameApp.add(body);
        gameApp.add(gunLine);
        gameApp.add(healthBarBackground);
        gameApp.add(updatingHealthBar);
    }

    public void hide() {
        gameApp.remove(playerShape);
        gameApp.remove(body);
        gameApp.remove(gunLine);
        gameApp.remove(healthBarBackground);
        gameApp.remove(updatingHealthBar);
    }

    public double getCenterX() {
        return x + PLAYER_SIZE / 2;
    }

    public double getCenterY() {
        return y + PLAYER_SIZE / 2;
    }

    public boolean getPressedE() {
        return pressedE;
    }
}
