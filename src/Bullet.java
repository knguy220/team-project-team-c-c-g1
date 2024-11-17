import acm.graphics.*;
import java.awt.Color;
import java.awt.event.ActionEvent;
import javax.swing.Timer;

public class Bullet {
    private static final int BULLET_SIZE = 5;
    private static final int BULLET_SPEED = 15;
    private static final int BULLET_LIFETIME = 5000; 
    private static final int SHOOT_COOLDOWN = 500; 
    private GOval bulletShape;
    private double directionX;
    private double directionY;
    private Timer bulletTimer;
    private GameApp gameApp;
    private StartGame startGame;
    private static long lastShootTime = 0;

    public Bullet(GameApp gameApp, StartGame startGame, double startX, double startY, double directionX, double directionY) {
        this.gameApp = gameApp;
        this.startGame = startGame;
        this.directionX = directionX;
        this.directionY = directionY;

        bulletShape = new GOval(startX, startY, BULLET_SIZE, BULLET_SIZE);
        bulletShape.setFilled(true);
        bulletShape.setColor(Color.RED);
        gameApp.add(bulletShape);

        // Timer to control bullet movement
        bulletTimer = new Timer(30, this::moveBullet); 
        bulletTimer.start();
    }

    public static boolean canShoot() {
        long currentTime = System.currentTimeMillis();
        if (currentTime - lastShootTime >= SHOOT_COOLDOWN) {
            lastShootTime = currentTime;
            return true;
        }
        return false;
    }

    public void reinitialize(double startX, double startY, double directionX, double directionY) {
        this.directionX = directionX;
        this.directionY = directionY;
        bulletShape.setLocation(startX, startY);
        bulletTimer.restart();
    }

    private void moveBullet(ActionEvent e) {
        bulletShape.move(BULLET_SPEED * directionX, BULLET_SPEED * directionY);
        if (isOutOfBounds()) {
            destroyBullet();
        }
    }

    private boolean isOutOfBounds() {
        double x = bulletShape.getX();
        double y = bulletShape.getY();
        double screenBuffer = 10; 
        return x < -screenBuffer || x > gameApp.getWidth() + screenBuffer || y < -screenBuffer || y > gameApp.getHeight() + screenBuffer;
    }

    private void destroyBullet() {
        if (bulletTimer != null) {
            bulletTimer.stop();
        }
        gameApp.remove(bulletShape);
        startGame.removeBullet(this);
    }

    public GOval getBulletShape() {
        return bulletShape;
    }
}

