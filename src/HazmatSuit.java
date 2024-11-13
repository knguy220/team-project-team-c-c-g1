
import java.util.Timer;
import java.util.TimerTask;


public class HazmatSuit extends Weapon{
	private int coolDownTime = 30; // 30 seconds
	private Timer coolDownTimer;
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
		coolDownTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                isOnCoolDown = false;
                System.out.println("Hazmat Suit Ready for Activation");
                coolDownTimer.cancel(); // Stops the timer after execution
            }
        }, coolDownTime * 1000);
	}

}
