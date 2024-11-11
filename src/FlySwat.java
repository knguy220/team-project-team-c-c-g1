import java.util.concurrent.TimeUnit;

public class FlySwat extends Weapon{
	private int coolDownTime = 60; // 1 min
	//private int enemiesKilled = 0;
	private boolean isOnCoolDown = false; 

	public FlySwat(WeaponType type, int damgeCaused) {
		super(type, damgeCaused);
	}
	public void Activate(){
		if(isOnCoolDown){
			System.out.println("Fly swat is cooling down");
			return;
		}
		System.out.println("Fly sawt is activated");
		//TODO what happens when activated 
		startCoolDown();
	}
	
	private void startCoolDown(){
		isOnCoolDown = true;
		new Thread(() -> {
			try {
				TimeUnit.SECONDS.sleep(coolDownTime);
			}catch(InterruptedException e) {
				e.printStackTrace();
			}
			isOnCoolDown = false;
			// ready for activation
		}).start();
	}

}
