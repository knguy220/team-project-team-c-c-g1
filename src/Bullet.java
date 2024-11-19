import acm.graphics.*;
import java.awt.Color;

public class Bullet {
    private GameApp gameApp;
    private StartGame startGame;
    private GOval bulletShape;
    private GImage bulletImage;
    private static final int BULLET_SIZE = 12; // Bullet size
    private static final Color BULLET_COLOR = Color.YELLOW; // Bullet color
    private static final double BULLET_SPEED_MULTIPLIER = 15; // Increased bullet speed
    private double x, y;
    private double speedX, speedY;

    public Bullet(GameApp gameApp, StartGame startGame, double startX, double startY, double directionX, double directionY) {
        this.gameApp = gameApp;
        this.startGame = startGame;

        // Normalize direction
        double magnitude = Math.hypot(directionX, directionY);
        this.speedX = (directionX / magnitude) * BULLET_SPEED_MULTIPLIER;
        this.speedY = (directionY / magnitude) * BULLET_SPEED_MULTIPLIER;

        this.x = startX;
        this.y = startY;

        // Create the bullet shape
        bulletShape = new GOval(startX, startY, BULLET_SIZE, BULLET_SIZE);
        bulletShape.setFilled(true);
        bulletShape.setColor(BULLET_COLOR);

        // Add bullet to the game screen
        gameApp.add(bulletShape);
        
        bulletImage = new GImage("Bullet.png"); 
        bulletImage.setSize(BULLET_SIZE * 2, BULLET_SIZE * 2); 
        bulletImage.setLocation(startX, startY);
        gameApp.add(bulletImage);
    }

    /**
     * Updates the bullet's position and removes it if it goes off-screen.
     */
    public void updatePosition() {
        x += speedX;
        y += speedY;

        // Check if bullet is off-screen
        if (x < 0 || x > gameApp.getWidth() || y < 0 || y > gameApp.getHeight()) {
            destroy();
            return;
        }

        // Update the bullet's location
        bulletShape.setLocation(x, y);
        bulletImage.setLocation(x, y);
    }

    /**
     * Destroys the bullet by removing it from the screen.
     */
    public void destroy() {
        gameApp.remove(bulletShape);
        gameApp.remove(bulletImage);
        startGame.removeBullet(this);
    }

    /**
     * Returns the shape of the bullet for collision detection.
     */
    public GOval getBulletShape() {
        return bulletShape;
    }

    /**
     * Returns the size of the bullet.
     */
    public static int getBulletSize() {
        return BULLET_SIZE;
    }

    /**
     * Static method to allow firing bullets at a specific rate.
     */
    public static boolean canShoot() {
        // Implement shooting rate logic here if needed (e.g., cooldown timer)
        return true;
    }
}


