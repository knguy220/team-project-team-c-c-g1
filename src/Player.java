import javax.swing.Timer;
import acm.graphics.*;
import acm.program.*;
import java.awt.event.*;



public class Player extends GraphicsProgram implements ActionListener {
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
		playerX = x;
		playerY = y;
		playerBody.scale(s);
		playerScale = s;
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
	
	public void init() {
		setSize(800,800);
		setImage(stand,200,200,0.5);
		setMove(true);
		setFoot(1);
		changeImage();
		add(getImage());
	}
	
	public static void main(String[] args) {
		new Player().start();
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}
	public void actionPerformed(ActionEvent e) {
		
	}
}
