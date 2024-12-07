import acm.graphics.GImage;
import acm.program.GraphicsProgram;
import java.util.ArrayList;
import java.util.List;

public class Boosts {
	private List<GImage> ammo;
	private List<GImage> medKit;
	private List<GImage> mistakes;
	
	/*private GImage medIcon;
	private GImage ammoIcon;*/
	
	private int medBoost = 50;
	//private int ammoBoost = 15;
	private GImage index;
	
	private GameApp gameApp;

	
	/*public Boosts (GameApp gameApp, double x, double y, int medBoost, int ammoBoost) {
		this.gameApp = gameApp;
		this.medBoost = medBoost; 
		this.ammoBoost = ammoBoost;
	}
	public Boosts(boolean isAmmo){
		if (isAmmo) {
			ammo = new ArrayList<>();
		} else {
			medKit = new ArrayList<>();
		}
	}*/
	
	public Boosts (GameApp ga, boolean isAmmo) {
		this.gameApp = ga;
		if (isAmmo) {
			this.ammo = new ArrayList<>();
		} else {
			this.medKit = new ArrayList<>();
		}
	}
	
	public void createMedKit(double x, double y, double s) {
		GImage medkit = new GImage("MedKit_Trans.png", x, y);
		medkit.scale(s);
		medKit.add(medkit);
		gameApp.add(medkit);
	}
	public void createAmmo(double x, double y, double s) {
		GImage a = new GImage("Bullet.png", x, y);
		a.scale(s);
		ammo.add(a);
		gameApp.add(a);
	}
	public List<GImage> getAmmo() {
		return ammo;
	}
	public List<GImage> getMedKit() {
		return medKit;
	}
	public boolean ammoOverlapping(Player player) {
		for (GImage a: ammo) {
			if (player.getCenterX() > a.getX() && player.getCenterX() < a.getX() + a.getWidth() && player.getCenterY() > a.getY() && player.getCenterY() < a.getY() + a.getHeight()) {
				return true;
			}
		}
		return false;
	}
	public boolean medKitOverlapping(Player player) {
		for (GImage m: medKit) {
			if (player.getCenterX() > m.getX() && player.getCenterX() < m.getX() + m.getWidth() && player.getCenterY() > m.getY() && player.getCenterY() < m.getY() + m.getHeight()) {
				index = m;
				return true;
			}
		}
		return false;
	}
	public void removeMedKit() {
		medKit.remove(index);
		gameApp.remove(index);
	}
	public void addHealth(Player player) { 
		player.updateHealth(0, medBoost);
	}
	public void ifInsideWall(Map m) {
		for (GImage e: medKit) {
			double sizeOfImage = e.getX()*e.getHeight();
			int topLeftX = (int) (e.getX()/Map.getTileSize());
			int topLeftY = (int) (e.getY()/Map.getTileSize());
			int topRightX = (int) ((e.getX()+sizeOfImage-1)/Map.getTileSize());
			int topRightY = topLeftY;
			int bottomLeftX = topLeftX;
			int bottomLeftY = (int) ((e.getX()+sizeOfImage-1)/Map.getTileSize());
			int bottomRightX = topRightX;
			int bottomRightY = bottomLeftY;
			if (!checkingWall(topLeftX,topLeftY,m) && !checkingWall(topRightX,topRightY,m) && 
					!checkingWall(bottomLeftX,bottomLeftY,m) && !checkingWall(bottomRightX,bottomRightY,m)) {
				mistakes.add(e);
			}
		}
	}
	public void erasingMistake() {
		for (GImage m: mistakes) {
			medKit.remove(m);
			gameApp.remove(m);
			mistakes.remove(m);
		}
	}
	public boolean checkingWall(int x, int y, Map m) {
		if (x < 0 || y < 0 || x >= m.getCols() || y >= m.getRows()) {
			return false;
		}
		return !m.isWall(x, y);
	}
	//any headers?
	//public void addAmmo() {
		//a function that updates ammunition?
	//}
	/*
	public void createMedKit(double x, double y, double s) {
		GImage medkit = new GImage("MedKit.jpg", x, y);
		medkit.scale(s);
		medKit.add(medkit);
	}
	public void createAmmo(double x, double y, double s) {
		GImage a = new GImage("Bullet.png", x, y);
		a.scale(s);
		ammo.add(a);
	}
	public void addingAmmo(GameApp gameApp) {
		for (GImage a: ammo) {
			gameApp.add(a);
		}
	}
	public void addingMedKit(GameApp gameApp) {
		for (GImage m: medKit) {
			gameApp.add(m);
		}
	}
	public GImage getMedKit() {
		return medIcon;
	}
	
	public GImage getAmmo() {
		return ammoIcon;
	}
	
	public int getMedBoost() {
		return medBoost;
	}
	
	public int getAmmoBoost() {
		return ammoBoost;
	}
	
	public void remove() {
		gameApp.remove(medIcon);
		gameApp.remove(ammoIcon);
	}*/
}
	
	

