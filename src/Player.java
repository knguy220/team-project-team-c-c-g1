import javax.swing.Timer;
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
}
