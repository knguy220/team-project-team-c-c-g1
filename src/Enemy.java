import java.awt.Color;
import acm.graphics.GImage;

public class Enemy {
	private int Health; // will update kill count 
	private int Damage;
	private int speed;
	private Position position; 
	private GImage bugIcon;
	private Color bugColor;
	
	public void moveLeft(){}
	public void moveRight(){}
	public void moveUp(){}
	public void moveDown(){}
	public void spawnIn(){}
	public void attack(){}
	public void beenHit(){}
	
}

class BugOne extends Enemy{}
class BugTwo extends Enemy{}
class BugThree extends Enemy{}