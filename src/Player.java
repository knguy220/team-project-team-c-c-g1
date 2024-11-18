import acm.graphics.*;
import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

public class Player {
    private GameApp gameApp;
    private GOval playerCircle;
    private GLine gunLine;
    private final int STEP_SIZE = 10;
    private final int GUN_LENGTH;

    // Movement flags
    private boolean wPressed = false;
    private boolean aPressed = false;
    private boolean sPressed = false;
    private boolean dPressed = false;

    public Player(GameApp gameApp, int gunLength) {
        this.gameApp = gameApp;
        this.GUN_LENGTH = gunLength;
    }

    public void initialize() {
        // Initialize player circle
        playerCircle = new GOval(gameApp.getWidth() / 2 - 25, gameApp.getHeight() / 2 - 25, 50, 50);
        playerCircle.setFilled(true);
        playerCircle.setColor(Color.BLUE);
        gameApp.add(playerCircle);

        // Initialize gun line - Positioned initially pointing to the right
        double centerX = playerCircle.getX() + playerCircle.getWidth() / 2;
        double centerY = playerCircle.getY() + playerCircle.getHeight() / 2;
        gunLine = new GLine(centerX, centerY, centerX + GUN_LENGTH, centerY);
        gunLine.setColor(Color.BLACK);
        gameApp.add(gunLine);
    }

    public void show() {
        if (playerCircle != null) gameApp.add(playerCircle);
        if (gunLine != null) gameApp.add(gunLine);
    }

    public void hide() {
        if (playerCircle != null) gameApp.remove(playerCircle);
        if (gunLine != null) gameApp.remove(gunLine);
    }

    public void handleKeyPress(KeyEvent e) {
        int keyCode = e.getKeyCode();

        switch (keyCode) {
            case KeyEvent.VK_W:
                wPressed = true;
                break;
            case KeyEvent.VK_A:
                aPressed = true;
                break;
            case KeyEvent.VK_S:
                sPressed = true;
                break;
            case KeyEvent.VK_D:
                dPressed = true;
                break;
        }
    }

    public void handleKeyRelease(KeyEvent e) {
        int keyCode = e.getKeyCode();

        switch (keyCode) {
            case KeyEvent.VK_W:
                wPressed = false;
                break;
            case KeyEvent.VK_A:
                aPressed = false;
                break;
            case KeyEvent.VK_S:
                sPressed = false;
                break;
            case KeyEvent.VK_D:
                dPressed = false;
                break;
        }
    }

    public void updateMovement() {
        double playerX = playerCircle.getX();
        double playerY = playerCircle.getY();
        double playerWidth = playerCircle.getWidth();
        double playerHeight = playerCircle.getHeight();

        double rightBoundary = gameApp.getWidth() - 20;
        double bottomBoundary = gameApp.getHeight() - 20;
        double leftBoundary = 20;
        double topBoundary = 20;

        if (wPressed && playerY - STEP_SIZE >= topBoundary) {
            playerCircle.move(0, -STEP_SIZE);
            gunLine.move(0, -STEP_SIZE);
        }
        if (aPressed && playerX - STEP_SIZE >= leftBoundary) {
            playerCircle.move(-STEP_SIZE, 0);
            gunLine.move(-STEP_SIZE, 0);
        }
        if (sPressed && playerY + playerHeight + STEP_SIZE <= bottomBoundary) {
            playerCircle.move(0, STEP_SIZE);
            gunLine.move(0, STEP_SIZE);
        }
        if (dPressed && playerX + playerWidth + STEP_SIZE <= rightBoundary) {
            playerCircle.move(STEP_SIZE, 0);
            gunLine.move(STEP_SIZE, 0);
        }
    }

    public void updateAiming(MouseEvent e) {
        double centerX = playerCircle.getX() + playerCircle.getWidth() / 2;
        double centerY = playerCircle.getY() + playerCircle.getHeight() / 2;
        double mouseX = e.getX();
        double mouseY = e.getY();

        // Calculate the angle between the player center and mouse position
        double angle = Math.atan2(mouseY - centerY, mouseX - centerX);

        // Calculate the end point for the gun barrel based on the angle and gun length
        double gunEndX = centerX + Math.cos(angle) * (playerCircle.getWidth() / 2 + GUN_LENGTH);
        double gunEndY = centerY + Math.sin(angle) * (playerCircle.getHeight() / 2 + GUN_LENGTH);

        // Set the gun line to point from the center of the player to the calculated endpoint
        gunLine.setStartPoint(centerX, centerY);
        gunLine.setEndPoint(gunEndX, gunEndY);
    }

    public double getCenterX() {
        return playerCircle.getX() + playerCircle.getWidth() / 2;
    }

    public double getCenterY() {
        return playerCircle.getY() + playerCircle.getHeight() / 2;
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
