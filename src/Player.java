import acm.graphics.*;
import java.awt.*;


public class Player {
	private int health;
	private int damage;
	private int speed;
	private Position position;
	private GImage playerBody;
	
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
}
