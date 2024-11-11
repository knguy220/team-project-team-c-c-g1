
public class Weapon {
	private WeaponType type;
	private int damageCaused; 


public Weapon (WeaponType type, int damageCaused){
	this.type = type;
	this.damageCaused = damageCaused;
}

public WeaponType getWeapon(WeaponType type) {
	return this.type;
}

public int getDamage(int damageCaused){
	return this.damageCaused; 
}

public void setDamage(int damageCaused){
	this.damageCaused = damageCaused; 
}



}


