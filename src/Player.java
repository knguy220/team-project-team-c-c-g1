import javax.swing.Timer;
import acm.graphics.*;
import acm.program.*;
import java.awt.*;
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
	private String aName;
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
		aName = name;
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
			aName = stand;
		} else if (getFoot() == 0) {
			setImage(walk1,playerX,playerY,playerScale);
			aName = walk1;
		} else if (getFoot() == 1) {
			setImage(walk2,playerX,playerY,playerScale);
			aName = walk2;
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
		playerBody.setLocation(x, y);
		double angle = Math.atan2(y, x);
		playerBody.rotate(angle);
	}
	
	public void init() {
		setSize(800,800);
		GOval evil = new GOval(400,400,100,100);
		evil.setColor(Color.BLUE);
		evil.setFilled(true);
		add(evil);
		setImage(stand,evil.getX()-evil.getWidth()/4,evil.getY()-evil.getHeight()/3,0.5);
		add(getImage());
	}
	
	public static void main(String[] args) {
		new Player().start();
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		Timer time = new Timer(100,this);
		time.start();
	}
	public void actionPerformed(ActionEvent e) {
		//turningX("r");
		turningY("d");
		playerRotate(playerX,playerY);
	}
}
