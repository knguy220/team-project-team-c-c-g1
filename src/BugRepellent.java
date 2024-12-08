/*import java.util.Timer;
import java.util.TimerTask;


public class BugRepellent extends Weapon {
	private int coolDownTime = 120; //2 min
	private Timer coolDownTimer;
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
		coolDownTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                isOnCoolDown = false;
                System.out.println("Bug Repellent Ready for Activation");
                coolDownTimer.cancel(); // Stops the timer after execution
            }
        }, coolDownTime * 1000);
	}

}
*/