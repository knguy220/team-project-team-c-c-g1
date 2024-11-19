import acm.graphics.*;
import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

public class Player {
    private GameApp gameApp;
    private GOval playerShape;
    private GLine gunLine;
    private GRect healthBarBackground;
    private GRect updatingHealthBar;

    public static final int PLAYER_SIZE = 40; 
    private static final int MAX_HEALTH = 100; 
    private static final int HEALTH_BAR_WIDTH = 200; 
    private static final int HEALTH_BAR_HEIGHT = 20; 

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

    public Player(GameApp gameApp, int startX, int startY, int gunLength) {
        this.gameApp = gameApp;
        this.x = startX;
        this.y = startY;
        this.gunLength = gunLength;

        // Create the player shape
        playerShape = new GOval(startX, startY, PLAYER_SIZE, PLAYER_SIZE);
        playerShape.setFilled(true);
        playerShape.setColor(Color.BLUE);

        // Create the gun line
        gunLine = new GLine(startX + PLAYER_SIZE / 2, startY + PLAYER_SIZE / 2, startX + PLAYER_SIZE / 2 + gunLength, startY + PLAYER_SIZE / 2);
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
     * Initializes the player and health bar on the screen.
     */
    public void initialize() {
        gameApp.add(playerShape);
        gameApp.add(gunLine);
        gameApp.add(healthBarBackground);
        gameApp.add(updatingHealthBar);
    }

    /**
     * Updates the player's movement and UI elements.
     */
    public void updateMovement() {
        x += velocityX;
        y += velocityY;

        // Ensure the player stays within screen boundaries
        x = Math.max(0, Math.min(gameApp.getWidth() - PLAYER_SIZE, x));
        y = Math.max(0, Math.min(gameApp.getHeight() - PLAYER_SIZE, y));

        // Update player's position
        playerShape.setLocation(x, y);

        // Update gun line based on current aiming angle
        updateGunLine();
    }

    /**
     * Updates the player's aim direction based on mouse position.
     */
    public void updateAiming(MouseEvent e) {
        double mouseX = e.getX();
        double mouseY = e.getY();

        // Calculate direction
        aimAngle = Math.atan2(mouseY - (y + PLAYER_SIZE / 2), mouseX - (x + PLAYER_SIZE / 2));

        // Update gun line
        updateGunLine();
    }

    /**
     * Updates the gun line based on the current aiming angle.
     */
    private void updateGunLine() {
        double gunX = x + PLAYER_SIZE / 2 + gunLength * Math.cos(aimAngle);
        double gunY = y + PLAYER_SIZE / 2 + gunLength * Math.sin(aimAngle);

        gunLine.setStartPoint(x + PLAYER_SIZE / 2, y + PLAYER_SIZE / 2);
        gunLine.setEndPoint(gunX, gunY);
    }

    /**
     * Handles key presses to set movement directions.
     */
    public void handleKeyPress(KeyEvent e) {
        int speed = 8; // Updated speed for faster movement
        switch (e.getKeyCode()) {
            case KeyEvent.VK_W:
                movingUp = true;
                velocityY = -speed;
                break;
            case KeyEvent.VK_S:
                movingDown = true;
                velocityY = speed;
                break;
            case KeyEvent.VK_A:
                movingLeft = true;
                velocityX = -speed;
                break;
            case KeyEvent.VK_D:
                movingRight = true;
                velocityX = speed;
                break;
        }
    }


    /**
     * Handles key releases to stop movement in specific directions.
     */
    public void handleKeyRelease(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_W:
                movingUp = false;
                if (!movingDown) velocityY = 0;
                break;
            case KeyEvent.VK_S:
                movingDown = false;
                if (!movingUp) velocityY = 0;
                break;
            case KeyEvent.VK_A:
                movingLeft = false;
                if (!movingRight) velocityX = 0;
                break;
            case KeyEvent.VK_D:
                movingRight = false;
                if (!movingLeft) velocityX = 0;
                break;
        }
    }

    /**
     * Updates the health bar when the player takes damage or heals.
     */
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

    /**
     * Resets all movement states and stops the player.
     */
    public void resetMovement() {
        movingUp = false;
        movingDown = false;
        movingLeft = false;
        movingRight = false;
        velocityX = 0;
        velocityY = 0;
    }

    /**
     * Stops the player from moving.
     */
    public void stopMoving() {
        velocityX = 0;
        velocityY = 0;
    }

    /**
     * Shows the player and health bar on the screen.
     */
    public void show() {
        gameApp.add(playerShape);
        gameApp.add(gunLine);
        gameApp.add(healthBarBackground);
        gameApp.add(updatingHealthBar);
    }

    /**
     * Hides the player and health bar from the screen.
     */
    public void hide() {
        gameApp.remove(playerShape);
        gameApp.remove(gunLine);
        gameApp.remove(healthBarBackground);
        gameApp.remove(updatingHealthBar);
    }

    /**
     * Returns the player's center X position.
     */
    public double getCenterX() {
        return x + PLAYER_SIZE / 2;
    }

    /**
     * Returns the player's center Y position.
     */
    public double getCenterY() {
        return y + PLAYER_SIZE / 2;
    }
}


/*import javax.swing.Timer;
import acm.graphics.*;
import acm.program.*;
import java.awt.*;
import java.awt.event.*;



public class Player {
	private int health;
	private int damage;
	private int speed;
	private Position position;
	private GImage playerBody;
	private boolean isMoving;
	private double playerScale;
	private String walk1 = "Walk1.png";
	private String walk2 = "Walk2.png";
	private String stand = "Stand.png";
	private int foot;
	private double playerX;
	private double playerY;
	private double tX;
	private double tY;
	
	
	public void setSpeed(int s) {
		speed = s;
	}
	public int getSpeed() {
		return speed;
	}
	public void setHealth(int h) {
		health = h;
	}
	public int getHealth() {
		return health;
	}
	public void setDamage(int d) {
		damage = d;
	}
	public int getDamage() {
		return damage;
	}
	public void setImage(String name, double x, double y, double s) {
		playerBody = new GImage(name,x,y);
		playerBody.scale(s);
		playerScale = s;
		playerX = x;
		playerY = y;
		tX = x;
		tY = y;
	}
	public GImage getImage() {
		return playerBody;
	}
	public void setMove(boolean m) {
		isMoving = m;
	}
	public boolean getMove() {
		return isMoving;
	}
	public void setFoot(int f) {
		foot = f;
	}
	public int getFoot() {
		return foot;
	}
	public void increaseFoot() {
		foot++;
		if (foot >= 2) {
			setFoot(0);
		}
	}
	public void changeImage() {
		if (getFoot() == -1) {
			setImage(stand,playerX,playerY,playerScale);
		} else if (getFoot() == 0) {
			setImage(walk1,playerX,playerY,playerScale);
		} else if (getFoot() == 1) {
			setImage(walk2,playerX,playerY,playerScale);
		}
	}
	public void setPlayerX(double x) {
		playerX = x;
		tX = x;
	}
	public void setPlayerY(double y) {
		playerY = y;
		tY = y;
	}
	public double getPlayerX() {
		return playerX;
	}
	public double getPlayerY() {
		return playerY;
	}
	public void turningX(String d) {
		if (d == "r") {
			playerX++;
		} else if (d == "l") {
			playerX--;
		} else return;
		if (playerX >= tX+playerBody.getWidth()+1) {
			playerX = tX+playerBody.getWidth();
		}
		if (playerX <= tX-1) {
			playerX = tX;
		}
	}
	public void turningY(String d) {
		if (d == "d") {
			playerY++;
		} else if (d == "u") {
			playerY--;
		} else return;
		if (playerY >= tY+playerBody.getHeight()+1) {
			playerY = tY+playerBody.getHeight();
		}
		if (playerY <= tY-1) {
			playerY = tY;
		}
	}
	public void playerRotate(double x, double y) {
		double angle = Math.atan2(y, x);
		playerBody.rotate(angle);
	}
}*/
