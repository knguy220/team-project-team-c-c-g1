import acm.graphics.GImage;

public class Boosts {

	private GImage medIcon;
	private GImage ammoIcon;
	
	private int medBoost;
	private int ammoBoost;
	
	private GameApp gameApp;

	
	public Boosts (GameApp gameApp, double x, double y, int medBoost, int ammoBoost) {
		this.gameApp = gameApp;
		this.medBoost = medBoost; 
		this.ammoBoost = ammoBoost;
		
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
	}
}
	
	

