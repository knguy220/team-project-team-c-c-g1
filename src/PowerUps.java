import acm.graphics.*;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

public class PowerUps {
    private Player player;
    private GameApp gameApp;
    private StartGame startGame;

    // Cooldown times in milliseconds
    private static final long HAZMAT_COOLDOWN = 30000;
    private static final long FLY_SWAT_COOLDOWN = 60000;
    private static final long BUG_REPELLENT_COOLDOWN = 120000;

    private long lastHazmatUse = -HAZMAT_COOLDOWN;
    private long lastFlySwatUse = -FLY_SWAT_COOLDOWN;
    private long lastBugRepellentUse = -BUG_REPELLENT_COOLDOWN;

    public PowerUps(Player player, GameApp gameApp, StartGame startGame) {
        this.player = player;
        this.gameApp = gameApp;
        this.startGame = startGame;
    }

    public void activateHazmatSuit() {
        long currentTime = System.currentTimeMillis();
        if (currentTime - lastHazmatUse >= HAZMAT_COOLDOWN) {
            lastHazmatUse = currentTime;

            // Create and activate the hazmat shield
            GOval shield = new GOval(player.getCenterX() - 50, player.getCenterY() - 50, 100, 100);
            shield.setColor(Color.YELLOW);
            shield.setFilled(true);
            shield.setFillColor(new Color(255, 255, 0, 128)); // Semi-transparent yellow
            gameApp.add(shield);

            long hazmatEndTime = currentTime + 20000; // 20 seconds duration
            startGame.activateHazmatShield(shield, hazmatEndTime);
        }
    }

    public void activateFlySwat() {
        long currentTime = System.currentTimeMillis();
        if (currentTime - lastFlySwatUse >= FLY_SWAT_COOLDOWN) {
            lastFlySwatUse = currentTime;

            // Create the fly swat bullet
            GOval flySwat = new GOval(player.getCenterX() - 15, player.getCenterY() - 15, 30, 30);
            flySwat.setColor(Color.CYAN);
            flySwat.setFilled(true);
            gameApp.add(flySwat);

            new Thread(() -> {
                try {
                    List<Enemy> enemiesToHit = new ArrayList<>(startGame.getConsole().getEnemies());
                    int hits = 0;

                    for (Enemy target : enemiesToHit) {
                        if (hits >= 10) break; // Limit to 10 hits
                        if (!target.isAlive()) continue; // Skip if enemy is already dead

                        while (target.isAlive() && !flySwat.getBounds().intersects(target.getEnemyShape().getBounds())) {
                            double targetX = target.getEnemyShape().getX() + Enemy.getEnemySize() / 2;
                            double targetY = target.getEnemyShape().getY() + Enemy.getEnemySize() / 2;

                            double deltaX = targetX - (flySwat.getX() + flySwat.getWidth() / 2);
                            double deltaY = targetY - (flySwat.getY() + flySwat.getHeight() / 2);
                            double distance = Math.hypot(deltaX, deltaY);

                            // Normalize direction
                            double directionX = deltaX / distance;
                            double directionY = deltaY / distance;

                            // Move fly swat closer to the target (faster speed)
                            flySwat.move(directionX * 10, directionY * 10); // Increased step size
                            Thread.sleep(10); // Reduced delay for smoother, faster movement
                        }

                        // Apply damage and remove enemy if hit
                        if (flySwat.getBounds().intersects(target.getEnemyShape().getBounds())) {
                            target.updateHealth(100); // Apply significant damage
                            if (!target.isAlive()) {
                                startGame.getConsole().removeEnemy(target, true);
                            }
                            hits++;
                        }
                    }

                    // Remove fly swat after hitting enemies
                    gameApp.remove(flySwat);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }).start();
        }
    }



    public void activateBugRepellent() {
        long currentTime = System.currentTimeMillis();
        if (currentTime - lastBugRepellentUse >= BUG_REPELLENT_COOLDOWN) {
            lastBugRepellentUse = currentTime;

            // Remove all enemies on the screen
            List<Enemy> enemies = new ArrayList<>(startGame.getConsole().getEnemies());
            for (Enemy enemy : enemies) {
                startGame.getConsole().removeEnemy(enemy, true);
            }
        }
    }

    public void updateCooldowns() {
        // No active updates needed for cooldowns
    }

    public long getHazmatCooldown() {
        return Math.max(0, HAZMAT_COOLDOWN - (System.currentTimeMillis() - lastHazmatUse));
    }

    public long getFlySwatCooldown() {
        return Math.max(0, FLY_SWAT_COOLDOWN - (System.currentTimeMillis() - lastFlySwatUse));
    }

    public long getBugRepellentCooldown() {
        return Math.max(0, BUG_REPELLENT_COOLDOWN - (System.currentTimeMillis() - lastBugRepellentUse));
    }
}

