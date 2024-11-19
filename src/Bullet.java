import acm.graphics.*;
import java.awt.Color;

public class Bullet {
    private GameApp gameApp;
    private StartGame startGame;
    private GOval bulletShape;
    private static final int BULLET_SIZE = 12; // Bullet size
    private static final double BULLET_SPEED = 15; // Bullet speed
    private static final Color BULLET_COLOR = Color.YELLOW; // Bullet color
    private double x, y;
    private double speedX, speedY;

    public Bullet(GameApp gameApp, StartGame startGame, double startX, double startY, double directionX, double directionY) {
        this.gameApp = gameApp;
        this.startGame = startGame;

        // Normalize direction
        double magnitude = Math.hypot(directionX, directionY);
        this.speedX = (directionX / magnitude) * BULLET_SPEED;
        this.speedY = (directionY / magnitude) * BULLET_SPEED;

        this.x = startX;
        this.y = startY;

        // Create the bullet shape
        bulletShape = new GOval(startX, startY, BULLET_SIZE, BULLET_SIZE);
        bulletShape.setFilled(true);
        bulletShape.setColor(BULLET_COLOR);

        // Add bullet to the game screen
        gameApp.add(bulletShape);
    }

    public void updatePosition() {
        x += speedX;
        y += speedY;

        // Check if bullet is off-screen
        if (x < 0 || x > gameApp.getWidth() || y < 0 || y > gameApp.getHeight()) {
            destroy();
            return;
        }

        // Create a trailing effect
        GOval trail = new GOval(x, y, BULLET_SIZE, BULLET_SIZE);
        trail.setFilled(true);
        trail.setColor(new Color(255, 255, 0, 80)); // Semi-transparent yellow
        gameApp.add(trail);

        // Animate the trail fading and shrinking
        new Thread(() -> {
            try {
                for (int i = 0; i < 5; i++) {
                    Thread.sleep(30);
                    trail.setColor(new Color(255, 69, 0, 80 - i * 15)); // Gradually fade to orange
                    trail.setSize(trail.getWidth() - 2, trail.getHeight() - 2);
                    trail.setLocation(trail.getX() + 1, trail.getY() + 1); // Center shrinking
                }
                gameApp.remove(trail);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

        // Update bullet location and color
        bulletShape.setLocation(x, y);
        bulletShape.setColor(new Color(255, (int) Math.max(0, 255 - y / 2), 0)); // Gradually turn red
    }


    /**
     * Creates an explosion animation when the bullet hits an enemy.
     */
    public void createImpactAnimation() {
        // Main explosion circle
        GOval explosion = new GOval(x - BULLET_SIZE, y - BULLET_SIZE, BULLET_SIZE * 2, BULLET_SIZE * 2);
        explosion.setFilled(true);
        explosion.setColor(new Color(255, 69, 0)); // Vibrant orange
        gameApp.add(explosion);

        // Animate explosion expansion and fade
        new Thread(() -> {
            try {
                for (int i = 0; i < 15; i++) {
                    Thread.sleep(30);
                    explosion.setSize(explosion.getWidth() + 5, explosion.getHeight() + 5);
                    explosion.setLocation(explosion.getX() - 2.5, explosion.getY() - 2.5);
                    explosion.setColor(new Color(255, 69, 0, 255 - i * 15)); // Gradual fade-out
                }
                gameApp.remove(explosion);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

        // Trigger additional particle burst
        createParticles(x, y);
    }


    /**
     * Creates particles for a visual effect at the given position.
     */
    private void createParticles(double x, double y) {
        for (int i = 0; i < 15; i++) {
            double angle = Math.random() * 2 * Math.PI;
            double speed = Math.random() * 4 + 2;
            double size = Math.random() * 8 + 3; // Randomize particle size

            GOval particle = new GOval(x, y, size, size);
            particle.setFilled(true);
            particle.setColor(new Color(255, (int) (Math.random() * 155 + 100), 0)); // Randomized orange shades
            gameApp.add(particle);

            // Animate particle movement and fading
            new Thread(() -> {
                try {
                    for (int j = 0; j < 20; j++) {
                        particle.move(Math.cos(angle) * speed, Math.sin(angle) * speed);
                        Thread.sleep(30);
                        particle.setColor(new Color(255, 69, 0, 255 - j * 12)); // Gradual transparency
                    }
                    gameApp.remove(particle);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }).start();
        }
    }




    /**
     * Destroys the bullet by removing it from the screen.
     */
    public void destroy() {
        gameApp.remove(bulletShape);
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
        return true; // Implement shooting rate logic if needed
    }
}



