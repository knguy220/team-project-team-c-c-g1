
public class Weapon {
	private WeaponType type;
	private int damageCaused; 
	private int enemiesKilled = 0;
	private HazmatSuit hazmat; 
	private FlySwat flySwat;
	private BugRepellent BR;


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

public void isEnemyKilled(){
	enemiesKilled++;
	if(enemiesKilled == 10){
		hazmat.Activate();
	}
	if(enemiesKilled == 25){
		flySwat.Activate();
	}
	if(enemiesKilled == 35){
		BR.Activate();
	}
}

}


