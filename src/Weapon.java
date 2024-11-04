
public class Weapon {
	private WeaponType type;
	private int damageCaused; 


public Weapon (WeaponType type, int damgeCaused){
	this.type = type;
	this.damageCaused = damgeCaused;
}

public WeaponType getWeapon(WeaponType type) {
	return this.type;
}

public void setDamage(int damageCaused){
	this.damageCaused = damageCaused; 
}



}


