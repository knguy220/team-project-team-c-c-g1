import java.util.concurrent.TimeUnit;

public class BugRepellent extends Weapon {
	private int coolDownTime = 120; //2 min
	private int enemiesKilled;
	private boolean isOnCoolDown = false;

	public BugRepellent(WeaponType type, int damgeCaused) {
		super(type, damgeCaused);
	}
	
	public void Activate(){
		if(isOnCoolDown){
			System.out.println("Bug Repellent Cooling Down");
			return;
		}
		System.out.println("Bug Repellent Suit Activated");
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
