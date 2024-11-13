import acm.graphics.GImage;

public class Bullet {
	private GImage bulletIcon;
	private Position bulletPosition; 
	private Position clickedLocation;
	
	public Bullet (GImage bulletIcon, Position startPosition, Position targetPosition) {
		this.bulletIcon = bulletIcon;
		this.bulletPosition = startPosition;
		this.clickedLocation = targetPosition;
		bulletIcon = new GImage ("rename.png");
		
	}
	public void bulletMovementPath(int X, int Y) {
		double differenceX = clickedLocation.getX() - bulletPosition.getX();
		double differenceY = clickedLocation.getY() - bulletPosition.getY();
		
		//double distance = Math.sqrt(differenceX * differenceX + differenceY * differenceY);
		
		double fraction = 0.5;
		
		int moveX = (int) (differenceX * fraction);
		int moveY = (int) (differenceY * fraction);
		
		bulletPosition.setX(bulletPosition.getX() + moveX);
	    bulletPosition.setY(bulletPosition.getY() + moveY);

	     
	    bulletIcon.setLocation(bulletPosition.getX(), bulletPosition.getY());
	}
	
	public void targetLocation(Position newTarget) {
		this.clickedLocation = newTarget;
	}
	
	
}
