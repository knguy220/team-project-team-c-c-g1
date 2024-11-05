import java.util.concurrent.TimeUnit;

public class HazmatSuit extends Weapon{
	private int coolDownTime = 30; // 30 seconds
	private int enemiesKilled;
	private boolean isOnCoolDown = false;

	public HazmatSuit(WeaponType type, int damgeCaused) {
		super(type, damgeCaused);
	}
	public void Activate(){
		if(isOnCoolDown){
			System.out.println("Hazmat Cooling Down");
			return;
		}
		System.out.println("Hazmat Suit Activated");
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
