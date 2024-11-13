import java.util.Timer;
import java.util.TimerTask;

public class FlySwat extends Weapon{
	private int coolDownTime = 60; // 1 min
	private Timer coolDownTimer;
	private int enemiesKilled = 0;
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
		coolDownTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                isOnCoolDown = false;
                System.out.println("Fly swat Ready for Activation");
                coolDownTimer.cancel(); // Stops the timer after execution
            }
        }, coolDownTime * 1000);
	}

}
